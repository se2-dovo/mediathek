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
     * Fügt einen Vormerker zu dieser Karte hinzu
     * 
     * @param vormerker
     */
    public void addVormerker(Kunde vormerker)
    {
        if (vormerkerFrei() && !_vormerker.contains(vormerker))
            _vormerker.add(vormerker);
    }

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
     * @ensure result != null
     */

    public Kunde getVormerker1()
    {
        //TODO
        try
        {
            return _vormerker.get(0);
        }
        catch (IndexOutOfBoundsException e)
        {
            return null;
        }
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
        //TODO
        try
        {
            return _vormerker.get(1);
        }
        catch (IndexOutOfBoundsException e)
        {
            return null;
        }
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
        //TODO
        try
        {
            return _vormerker.get(2);
        }
        catch (IndexOutOfBoundsException e)
        {
            return null;
        }
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
                + ((getVormerker1() == null) ? " "
                        : getVormerker1().getFormatiertenString())
                + "\n"
                + ((getVormerker2() == null) ? " "
                        : getVormerker2().getFormatiertenString())
                + "\n"
                + ((getVormerker3() == null) ? " "
                        : getVormerker3().getFormatiertenString());

    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((getVormerker1() == null) ? 0 : getVormerker1().hashCode());
        result = prime * result
                + ((getVormerker2() == null) ? 0 : getVormerker2().hashCode());
        result = prime * result
                + ((getVormerker3() == null) ? 0 : getVormerker3().hashCode());

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

            if (getVormerker2() == null && getVormerker3() == null)
            {
                if (other.getVormerker1()
                    .equals(getVormerker1()) && other.getMedium()
                    .equals(_medium)) result = true;
            }
            else if (getVormerker3() == null)
            {
                if (other.getVormerker1()
                    .equals(getVormerker1()) && other.getVormerker2()
                    .equals(getVormerker2()) && other.getMedium()
                    .equals(_medium)) result = true;
            }
            else
            {
                if (other.getVormerker1()
                    .equals(getVormerker1()) && other.getVormerker2()
                    .equals(getVormerker2()) && other.getVormerker3()
                    .equals(getVormerker3()) && other.getMedium()
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
        _vormerker.poll();
    }
}