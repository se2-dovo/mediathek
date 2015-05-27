package de.uni_hamburg.informatik.swt.se2.mediathek.services.verleih;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.uni_hamburg.informatik.swt.se2.mediathek.fachwerte.Datum;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.Kunde;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.Verleihkarte;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.Vormerkungskarte;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.AbstractObservableService;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.kundenstamm.KundenstammService;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.medienbestand.MedienbestandService;

/**
 * Diese Klasse implementiert das Interface VerleihService. Siehe dortiger
 * Kommentar.
 * 
 * @author SE2-Team
 * @version SoSe 2015
 */
public class VerleihServiceImpl extends AbstractObservableService implements
        VerleihService
{
    /**
     * Diese Map speichert für jedes eingefügte Medium die dazugehörige
     * Verleihkarte. Ein Zugriff auf die Verleihkarte ist dadurch leicht über
     * die Angabe des Mediums möglich. Beispiel: _verleihkarten.get(medium)
     */
    private Map<Medium, Verleihkarte> _verleihkarten;

    /**
     * Diese Map speichert für jedes vorgemerkte Medium die dazugehörige
     * Vormerkungskarte. Ein Zugriff auf die Vormerkungskarte ist dadurch leicht über
     * die Angabe des Mediums möglich. Beispiel: _vormerkungskarten.get(medium)
     */
    private Map<Medium, Vormerkungskarte> _vormerkungskarten;

    /**
     * Der Medienbestand.
     */
    private MedienbestandService _medienbestand;

    /**
     * Der Kundenstamm.
     */
    private KundenstammService _kundenstamm;

    /**
     * Der Protokollierer für die Verleihvorgänge.
     */
    private VerleihProtokollierer _protokollierer;

    /**
     * Konstruktor. Erzeugt einen neuen VerleihServiceImpl.
     * 
     * @param kundenstamm Der KundenstammService.
     * @param medienbestand Der MedienbestandService.
     * @param initialBestand Der initiale Bestand.
     * 
     * @require kundenstamm != null
     * @require medienbestand != null
     * @require initialBestand != null
     */
    public VerleihServiceImpl(KundenstammService kundenstamm,
            MedienbestandService medienbestand,
            List<Verleihkarte> initialBestand)
    {
        assert kundenstamm != null : "Vorbedingung verletzt: kundenstamm  != null";
        assert medienbestand != null : "Vorbedingung verletzt: medienbestand  != null";
        assert initialBestand != null : "Vorbedingung verletzt: initialBestand  != null";
        _verleihkarten = erzeugeVerleihkartenBestand(initialBestand);
        _vormerkungskarten = new HashMap<Medium, Vormerkungskarte>();
        _kundenstamm = kundenstamm;
        _medienbestand = medienbestand;
        _protokollierer = new VerleihProtokollierer();
    }

    /**
     * Erzeugt eine neue HashMap aus dem Initialbestand.
     */
    private HashMap<Medium, Verleihkarte> erzeugeVerleihkartenBestand(
            List<Verleihkarte> initialBestand)
    {
        HashMap<Medium, Verleihkarte> result = new HashMap<Medium, Verleihkarte>();
        for (Verleihkarte verleihkarte : initialBestand)
        {
            result.put(verleihkarte.getMedium(), verleihkarte);

        }
        return result;
    }

    @Override
    public List<Verleihkarte> getVerleihkarten()
    {
        return new ArrayList<Verleihkarte>(_verleihkarten.values());
    }

    @Override
    public boolean istVerliehen(Medium medium)
    {
        assert mediumImBestand(medium) : "Vorbedingung verletzt: mediumExistiert(medium)";
        return _verleihkarten.get(medium) != null;
    }

    @Override
    public boolean istVerleihenMoeglich(Kunde kunde, List<Medium> medien)
    {
        assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
        assert medienImBestand(medien) : "Vorbedingung verletzt: medienImBestand(medien)";

        return sindAlleNichtVerliehen(medien);
    }

    @Override
    public void nimmZurueck(List<Medium> medien, Datum rueckgabeDatum)
            throws ProtokollierException
    {
        assert sindAlleVerliehen(medien) : "Vorbedingung verletzt: sindAlleVerliehen(medien)";
        assert rueckgabeDatum != null : "Vorbedingung verletzt: rueckgabeDatum != null";

        for (Medium medium : medien)
        {
            Verleihkarte verleihkarte = _verleihkarten.get(medium);
            _verleihkarten.remove(medium);
            _protokollierer.protokolliere(
                    VerleihProtokollierer.EREIGNIS_RUECKGABE, verleihkarte);
        }

        informiereUeberAenderung();
    }

    @Override
    public boolean sindAlleNichtVerliehen(List<Medium> medien)
    {
        assert medienImBestand(medien) : "Vorbedingung verletzt: medienImBestand(medien)";

        for (Medium medium : medien)
        {
            if (istVerliehen(medium)) return false;
        }
        return true;
    }

    @Override
    public boolean sindAlleVerliehenAn(Kunde kunde, List<Medium> medien)
    {
        assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
        assert medienImBestand(medien) : "Vorbedingung verletzt: medienImBestand(medien)";

        for (Medium medium : medien)
        {
            if (!istVerliehenAn(kunde, medium)) return false;
        }
        return true;
    }

    @Override
    public boolean istVerliehenAn(Kunde kunde, Medium medium)
    {
        assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
        assert mediumImBestand(medium) : "Vorbedingung verletzt: mediumImBestand(medium)";

        return istVerliehen(medium) && getEntleiherFuer(medium).equals(kunde);
    }

    @Override
    public boolean sindAlleVerliehen(List<Medium> medien)
    {
        assert medienImBestand(medien) : "Vorbedingung verletzt: medienImBestand(medien)";

        for (Medium medium : medien)
        {
            if (!istVerliehen(medium)) return false;
        }
        return true;
    }

    @Override
    public void verleiheAn(Kunde kunde, List<Medium> medien, Datum ausleihDatum)
            throws ProtokollierException
    {
        assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";
        assert sindAlleNichtVerliehen(medien) : "Vorbedingung verletzt: sindAlleNichtVerliehen(medien) ";
        assert ausleihDatum != null : "Vorbedingung verletzt: ausleihDatum != null";
        assert istVerleihenMoeglich(kunde, medien) : "Vorbedingung verletzt:  istVerleihenMoeglich(kunde, medien)";

        for (Medium medium : medien)
        {
            if (istVorgemerkt(medium))
            {
                if (istVormerker(kunde, medium))
                {
                    verleihe(medium, kunde, ausleihDatum);
                    // Lösche, wenn nötig, Vormerkungskarte
                    if (_vormerkungskarten.get(medium)
                        .rueckeAuf()) _vormerkungskarten.remove(medium);
                }
            }
            else
                verleihe(medium, kunde, ausleihDatum);
        }
        // XXX Was passiert wenn das Protokollieren mitten in der Schleife
        // schief geht? informiereUeberAenderung in einen finally Block?
        informiereUeberAenderung();
    }

    /**
     * Hilfsmethode zum Verleihen von Medien an Kunden
     * 
     * @param medium - Das zu verleihende Medium
     * @param kunde - Der leihende Kunde
     * @param ausleihDatum - ...
     * @throws ProtokollierException
     * 
     * @require medium != null
     * @require medium ist nicht verliehen (und nicht oder für Kunde Vorgemerkt)
     * @require kunde != null
     * @require ausleihDatum != null
     * 
     */
    private void verleihe(Medium medium, Kunde kunde, Datum ausleihDatum)
            throws ProtokollierException
    {
        Verleihkarte verleihkarte = new Verleihkarte(kunde, medium,
                ausleihDatum);

        _verleihkarten.put(medium, verleihkarte);
        _protokollierer.protokolliere(VerleihProtokollierer.EREIGNIS_AUSLEIHE,
                verleihkarte);
    }

    @Override
    public boolean kundeImBestand(Kunde kunde)
    {
        return _kundenstamm.enthaeltKunden(kunde);
    }

    @Override
    public boolean mediumImBestand(Medium medium)
    {
        return _medienbestand.enthaeltMedium(medium);
    }

    @Override
    public boolean medienImBestand(List<Medium> medien)
    {
        assert medien != null : "Vorbedingung verletzt: medien != null";
        assert !medien.isEmpty() : "Vorbedingung verletzt: !medien.isEmpty()";

        for (Medium medium : medien)
        {
            if (!mediumImBestand(medium)) return false;
        }
        return true;
    }

    @Override
    public List<Medium> getAusgelieheneMedienFuer(Kunde kunde)
    {
        assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";

        List<Medium> result = new ArrayList<Medium>();

        for (Verleihkarte verleihkarte : _verleihkarten.values())
        {
            if (verleihkarte.getEntleiher()
                .equals(kunde))
            {
                result.add(verleihkarte.getMedium());
            }
        }
        return result;
    }

    @Override
    public Kunde getEntleiherFuer(Medium medium)
    {
        assert istVerliehen(medium) : "Vorbedingung verletzt: istVerliehen(medium)";

        return _verleihkarten.get(medium)
            .getEntleiher();
    }

    @Override
    public Verleihkarte getVerleihkarteFuer(Medium medium)
    {
        assert istVerliehen(medium) : "Vorbedingung verletzt: istVerliehen(medium)";

        return _verleihkarten.get(medium);
    }

    @Override
    public List<Verleihkarte> getVerleihkartenFuer(Kunde kunde)
    {
        assert kundeImBestand(kunde) : "Vorbedingung verletzt: kundeImBestand(kunde)";

        List<Verleihkarte> result = new ArrayList<Verleihkarte>();

        for (Verleihkarte verleihkarte : _verleihkarten.values())
        {
            if (verleihkarte.getEntleiher()
                .equals(kunde))
            {
                result.add(verleihkarte);
            }
        }
        return result;
    }

    /**
     * Merkt ein Medium vor.
     * Sofern keine Vormerker vorhanden sind, wird eine Vormerkungskarte erstellt.
     * 
     * @param vormerker Vormerkender Kunde
     * @param medium vorzumerkendes Medium
     * 
     * @require medium != null
     * @require vormerker != null
     * @require Vormerken ist möglich (nicht mehr als 2 Vormerker)
     */
    @Override
    public void merkeVor(Kunde vormerker, Medium medium)
    {
        assert vormerker != null : "vormerker = null";
        assert medium != null : "Medium = null";

        if (!_vormerkungskarten.containsKey(medium))
            _vormerkungskarten.put(medium, new Vormerkungskarte(medium,
                    vormerker));
        else
            _vormerkungskarten.get(medium)
                .addVormerker(vormerker);

        informiereUeberAenderung();
    }

    /**
     * Prüfe, ob für gegebenes Medium vorgemerkt wurde
     * @param medium - Das Medium
     * @return ist vorgemerkt?
     */
    private boolean istVorgemerkt(Medium medium)
    {
        return _vormerkungskarten.containsKey(medium);
    }

    @Override
    public boolean istVormerker(Kunde kunde, Medium medium)
    {
        return _vormerkungskarten.get(medium)
            .getVormerker(0)
            .equals(kunde);
    }

    @Override
    public Vormerkungskarte getVormerkungskarte(Medium medium)
    {
        return _vormerkungskarten.get(medium);
    }
}