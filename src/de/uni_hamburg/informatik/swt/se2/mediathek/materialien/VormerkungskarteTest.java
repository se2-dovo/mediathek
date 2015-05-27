package de.uni_hamburg.informatik.swt.se2.mediathek.materialien;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.uni_hamburg.informatik.swt.se2.mediathek.fachwerte.Kundennummer;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.CD;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;

public class VormerkungskarteTest
{
    private Vormerkungskarte _karte;
    private Kunde _kunde1;
    private Kunde _kunde2;
    private Medium _medium1;
    private Medium _medium2;

    public VormerkungskarteTest()
    {
        _kunde1 = new Kunde(new Kundennummer(123456), "ich", "du");
        _kunde2 = new Kunde(new Kundennummer(654321), "er", "sie");
        _medium1 = new CD("bar", "baz", "foo", 123);
        _medium2 = new CD("rab", "zab", "oof", 321);
    }

    @Test
    public void test_Konstruktor() throws Exception
    {
        _karte = new Vormerkungskarte(_medium1, _kunde1);
        assertEquals(_kunde1, _karte.getVormerker(0));
        assertEquals(_medium1, _karte.getMedium());
    }

    @Test
    public void test_getFormatiertenString() throws Exception
    {
        _karte = new Vormerkungskarte(_medium1, _kunde1);

        String string = "CD:" + "\n" + "    Titel: bar" + "\n"
                + "    Kommentar: baz" + "\n" + "    Interpret: foo" + "\n"
                + "    Spiellänge: 123" + "\n" + "vorgemerkt von:" + "\n"
                + "    Kundennummer: 123456" + "\n" + "    Name: ich du" + "\n"
                + "    Telefon: unbekannt" + "\n" + "    Anschrift:" + "\n"
                + "    unbekannt";
        assertTrue(_karte.getFormatiertenString()
            .contains(string));

    }

    @Test
    public void test_equals()
    {
        _karte = new Vormerkungskarte(_medium1, _kunde1);
        Vormerkungskarte temp = new Vormerkungskarte(_medium1, _kunde1);
        assertTrue(_karte.equals(temp));
        assertEquals(temp.hashCode(), _karte.hashCode());

        temp.addVormerker(_kunde2);
        assertFalse(_karte.equals(temp));
        assertFalse(temp.hashCode() == _karte.hashCode());

        _karte = new Vormerkungskarte(_medium1, _kunde2);
        _karte.addVormerker(_kunde1);
        assertFalse(_karte.equals(temp));
        assertFalse(temp.hashCode() == _karte.hashCode());

        temp = new Vormerkungskarte(_medium1, _kunde1);
        _karte.rueckeAuf();
        assertTrue(_karte.equals(temp));
        assertTrue(temp.hashCode() == _karte.hashCode());

        _karte = new Vormerkungskarte(_medium2, _kunde2);
        temp = new Vormerkungskarte(_medium1, _kunde2);
        assertFalse(_karte.equals(temp));
        assertFalse(temp.hashCode() == _karte.hashCode());

    }
}