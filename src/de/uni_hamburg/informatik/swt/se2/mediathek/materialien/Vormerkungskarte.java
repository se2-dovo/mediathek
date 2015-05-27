package de.uni_hamburg.informatik.swt.se2.mediathek.materialien;

import java.util.LinkedList;

import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;

/**
 * Mit Hilfe von Vormerkungskarte werden beim Vormerken eines Mediums alle relevanten
 * Daten notiert.
 * 
 * Sie beantwortet die folgenden Fragen: Welches Medium wurde vorgemerkt? Wer
 * hat das Medium ausgeliehen?Wer hat das medium Vorgemerkt und wie viele. Wann wurde das Medium vorgemerkt?
 * 
 * Um die Verwaltung der Karten kümmert sich der VerleihService
 * 
 * @author SE2-Team
 * @version SoSe 2015
 */
public class Vormerkungskarte
{
    private final Medium _medium;
    private LinkedList<Kunde> _vormerker;
    private static final int VORMERKERZAHL = 3;

    /**
     * Initialisert eine neue Verleihkarte mit den gegebenen Daten.
     * 
     * @param vormerker Ein Kunde, der das Medium ausleihen will.
     * @param medium Ein Medium.
     * 
     * @require vormerker != null
     * @require medium != null
     * 
     * @ensure #getVormerker() == vormerker
     * @ensure #getMedium() == medium
     */
    public Vormerkungskarte(Medium medium, Kunde vormerker)
    {
        assert medium != null : "Vorbedingung verletzt: medium != null";
        assert vormerker != null : "Vorbedingung verletzt: vormerker != null";

        _medium = medium;
        _vormerker = new LinkedList<Kunde>();
        _vormerker.add(vormerker);
    }

    /**
     * Fügt einen Vormerker zu dieser Karte hinzu, sofern dieser noch nicht vorhanden ist.
     * 
     * @param vormerker
     * @require vormerker != null
     */
    public void addVormerker(Kunde vormerker)
    {
        assert vormerker != null : "Vorbedingung verletzt: vormerker != null";

        if (vormerkerFrei() && !_vormerker.contains(vormerker))
            _vormerker.add(vormerker);
    }

    /**
     * Prüfe ob der Kunde als Vormerker gelistet ist
     * @param kunde 
     * @return Ist Kunde gelistet oder nicht.
     */
    public boolean istKundeVormerker(Kunde kunde)
    {
        return _vormerker.contains(kunde);
    }

    /**
     * Gibt ob vormerktkarte voll für das medium ist.
     * 
     * @return boolean
     */
    public boolean vormerkerFrei()
    {
        return _vormerker.size() < VORMERKERZAHL;
    }

    /**
     * Gibt den Vormerker zurück.
     * 
     * @return den Kunden, der das Medium vorgemerkt hat.
     * 
     * @require index zwischen 0 & VORMERKERZAHL - 1
     * @ensure result == null || result Kunde, der das Medium vorgemerkt hat.
     */
    public Kunde getVormerker(int index)
    {
        assert index >= 0 && index < VORMERKERZAHL : "precondition violated";

        //TODO unschön
        try
        {
            return _vormerker.get(index);
        }
        catch (IndexOutOfBoundsException e)
        {
            return null;
        }
    }

    /**
     * Gibt das Medium der Vormerkungskarte zurück.
     * 
     * @return Medium
     * 
     * @ensure result != null
     */
    public Medium getMedium()
    {
        return _medium;
    }

    /**
     * Gibt eine String-Darstellung der Vormerkungskarte (enhält Zeilenumbrüche)
     * zurück.
     * 
     * @return Eine formatierte Stringrepäsentation der Vormerkungskarte. Enthält
     *         Zeilenumbrüche.
     * 
     * @ensure result != null
     */
    public String getFormatiertenString()
    {
        String formatstr = _medium.getFormatiertenString();
        for (int i = 0; this.getVormerker(i) != null; ++i)
        {
            formatstr = formatstr + "vorgemerkt von:" + "\n"
                    + this.getVormerker(0)
                        .getFormatiertenString();
        }
        return formatstr;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result;

        result = prime + getVormerker(0).hashCode();

        result = prime * result
                + ((getVormerker(1) == null) ? 0 : getVormerker(1).hashCode());

        result = prime * result
                + ((getVormerker(2) == null) ? 0 : getVormerker(2).hashCode());

        return prime * result + _medium.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Vormerkungskarte)
        {
            Vormerkungskarte other = (Vormerkungskarte) obj;

            if (!other.getMedium()
                .equals(_medium)) return false;
            for (int i = 0; i < VORMERKERZAHL; ++i)
            {
                if (!other.getVormerker(i)
                    .equals(this.getVormerker(i))) return false;
            }
            return true;
        }
        else
            return false;
    }

    @Override
    public String toString()
    {
        return getFormatiertenString();
    }

    /**
     * Rückt alle Vormerker auf
     * 
     * @return True wenn die V.Karte entfernt werden kann (/muss)
     * @ensure result = True || False
     */
    public boolean rueckeAuf()
    {
        _vormerker.poll();
        return _vormerker.isEmpty();
    }
}