package de.uni_hamburg.informatik.swt.se2.mediathek.materialien;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.uni_hamburg.informatik.swt.se2.mediathek.fachwerte.Kundennummer;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.CD;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;

public class VormerkungskarteTest
{
    private Vormerkungskarte _karte;
    private Kunde _kunde;
    private Medium _medium;

    public VormerkungskarteTest()
    {
        _kunde = new Kunde(new Kundennummer(123456), "ich", "du");

        _medium = new CD("bar", "baz", "foo", 123);
        _karte = new Vormerkungskarte(_medium, _kunde);

    }

    @Test
    public void test_getFormatiertenString() throws Exception
    {
        assertNotNull(_karte.getFormatiertenString());
    }

    @Test
    public void test_Konstruktor() throws Exception
    {
        assertEquals(_kunde, _karte.getVormerker(0));
        assertEquals(_medium, _karte.getMedium());
    }

    @Test
    public void test_equals()
    {
        Kunde kunde1 = new Kunde(new Kundennummer(123457), "lolol", "troll");
        Vormerkungskarte karte1 = new Vormerkungskarte(_medium, kunde1);

        Vormerkungskarte temp = new Vormerkungskarte(_medium, _kunde);

        assertTrue(_karte.equals(temp));
        assertEquals(_karte.hashCode(), temp.hashCode());

        Kunde kunde3 = new Kunde(new Kundennummer(754321), "op", "du");
        Kunde kunde2 = new Kunde(new Kundennummer(654321), "ich", "du");
        CD medium2 = new CD("hallo", "welt", "foo", 321);
        Vormerkungskarte karte2 = new Vormerkungskarte(medium2, kunde2);

        assertFalse(_karte.equals(karte2));
        assertNotSame(_karte.hashCode(), karte2.hashCode());

        assertEquals(kunde2, karte2.getVormerker(0));

        karte1.addVormerker(kunde1);
        assertFalse(karte1.getVormerker(1) != null);

        karte1.addVormerker(kunde3);
        assertEquals(kunde3, karte1.getVormerker(1));

        karte1.rueckeAuf();
        assertEquals(kunde3, karte1.getVormerker(0));

        assertTrue(karte1.istKundeVormerker(kunde3));

        Vormerkungskarte karte4 = new Vormerkungskarte(_medium, kunde1);
        karte4.addVormerker(kunde2);
        karte4.addVormerker(kunde3);
        assertFalse(karte4.vormerkerFrei());

    }
}