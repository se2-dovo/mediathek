package de.uni_hamburg.informatik.swt.se2.mediathek.materialien;

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
    // Eigenschaften einer Verleihkarte
    private final Medium _medium;
    private Kunde _vormerker1 = null;
    private Kunde _vormerker2 = null;
    private Kunde _vormerker3 = null;

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
        _vormerker1 = vormerker;
    }

    /**
     * Fügt ein vormerker zu die karte
     * 
     * @param vormerker
     */
    public void addVormerker(Kunde vormerker)
    {
        if (vormerkerFrei() && !istKundeVormerker(vormerker))
        {
            if (_vormerker2 == null)
                _vormerker2 = vormerker;
            else if (_vormerker3 == null) _vormerker3 = vormerker;
        }
    }

    public boolean istKundeVormerker(Kunde kunde)
    {

        if (_vormerker1 == kunde)
            return true;
        else if (_vormerker2 == kunde)
            return true;
        else if (_vormerker3 == kunde) return true;
        return false;

    }

    /**
     * Gibt ob vormerktkarte voll für das medium ist.
     * 
     * @return boolean
     */
    public boolean vormerkerFrei()
    {

        return (_vormerker1 == null || _vormerker2 == null || _vormerker3 == null);
    }

    /**
     * Gibt den Vormerker zurück.
     * 
     * @return den Kunden, der das Medium vorgemerkt hat.
     * 
     * @ensure result != null
     */

    public Kunde getVormerker1()
    {
        return _vormerker1;
    }

    /**
     * Gibt den Vormerker zurück.
     * 
     * @return den Kunden, der das Medium vorgemerkt hat.
     * 
     * @ensure result != null
     */

    public Kunde getVormerker2()
    {
        return _vormerker2;
    }

    /**
     * Gibt den Vormerker zurück.
     * 
     * @return den Kunden, der das Medium vorgemerkt hat.
     * 
     * @ensure result != null
     */

    public Kunde getVormerker3()
    {
        return _vormerker3;
    }

    /**
     * Gibt das Medium zurück.
     * 
     * @return das Medium.
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
        /* if (_vormerker2 == null && _vormerker3 == null)
         {
             return _medium.getFormatiertenString() + "vorgemerkt von:\n"
                     + _vormerker1.getFormatiertenString();
         }
         else if (_vormerker3 == null)
         {
             return _medium.getFormatiertenString() + "vorgemerkt von:\n"
                     + _vormerker1.getFormatiertenString() + "\n"
                     + _vormerker2.getFormatiertenString();
         }
         else
             return _medium.getFormatiertenString() + "vorgemerkt von:\n"
                     + _vormerker1.getFormatiertenString() + "\n"
                     + _vormerker2.getFormatiertenString() + "\n"
                     + _vormerker3.getFormatiertenString();*/

        return _medium.getFormatiertenString()
                + "vorgemerkt von:\n"
                + ((_vormerker1 == null) ? " "
                        : _vormerker1.getFormatiertenString())
                + "\n"
                + ((_vormerker2 == null) ? " "
                        : _vormerker2.getFormatiertenString())
                + "\n"
                + ((_vormerker3 == null) ? " "
                        : _vormerker3.getFormatiertenString());

    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((_vormerker1 == null) ? 0 : _vormerker1.hashCode());
        result = prime * result
                + ((_vormerker2 == null) ? 0 : _vormerker2.hashCode());
        result = prime * result
                + ((_vormerker3 == null) ? 0 : _vormerker3.hashCode());

        result = prime * result + ((_medium == null) ? 0 : _medium.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        boolean result = false;
        if (obj instanceof Vormerkungskarte)
        {
            Vormerkungskarte other = (Vormerkungskarte) obj;

            if (_vormerker2 == null && _vormerker3 == null)
            {
                if (other.getVormerker1()
                    .equals(_vormerker1) && other.getMedium()
                    .equals(_medium)) result = true;
            }
            else if (_vormerker3 == null)
            {
                if (other.getVormerker1()
                    .equals(_vormerker1) && other.getVormerker2()
                    .equals(_vormerker2) && other.getMedium()
                    .equals(_medium)) result = true;
            }
            else
            {
                if (other.getVormerker1()
                    .equals(_vormerker1) && other.getVormerker2()
                    .equals(_vormerker2) && other.getVormerker3()
                    .equals(_vormerker3) && other.getMedium()
                    .equals(_medium)) result = true;
            }
        }
        return result;
    }

    @Override
    public String toString()
    {
        return getFormatiertenString();
    }

    /**
     * Rückt Vormerker und Löscht vormerker 1
     */
    public void rueckeAuf()
    {
        _vormerker1 = _vormerker2;
        _vormerker2 = _vormerker3;
        _vormerker3 = null;

    }
}