package shakkibeli.nappula;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static shakkibeli.nappula.Arvo.*;
import shakkibeli.nappula.Nappula;
import static shakkibeli.nappula.Vari.*;

public class NappulaTest {

    Nappula sotilas;
    Nappula kuningatar;

    public NappulaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        sotilas = new Nappula(1, 1, SOTILAS, MUSTA);
        kuningatar = new Nappula(3, 0, KUNINGATAR, MUSTA);
    }

    @After
    public void tearDown() {
        sotilas = new Nappula(1, 1, SOTILAS, MUSTA);
        kuningatar = new Nappula(3, 0, KUNINGATAR, MUSTA);
    }
    @Test
    public void konstruktoriAsettaaNappulanOikein() {
        assertEquals("b7", sotilas.toString());
    }

    @Test
    public void sotilastaVoiLiikuttaa() {
        sotilas.liiku(1, 3);

        assertEquals("b5", sotilas.toString());
    }

    @Test
    public void sotilastaEiVoiLiikuttaaLaudanUlkopuolelle() {
        sotilas.liiku(8, 8);

        assertEquals("b7", sotilas.toString());
    }

    @Test
    public void equalsJaHashCodeToimii1() {
        boolean onkoSamat = true;
        if (sotilas.equals(kuningatar)) {
            onkoSamat = false;
        }
        assertTrue(onkoSamat);
    }

    @Test
    public void equalsJaHashCodeToimii2() {
        Nappula sotilas2 = new Nappula(1, 1, SOTILAS, MUSTA);
        boolean onkoSamat = false;
        if (sotilas.equals(sotilas2)) {
            onkoSamat = true;
        }
        assertTrue(onkoSamat);
    }
    @Test
    public void testaaAloitusKaytetty() {
        Nappula valkoinenKuningas = new Nappula(4,7,KUNINGAS,VALKOINEN);
        Nappula mustaTorni = new Nappula (0,0,TORNI,MUSTA);
        
        valkoinenKuningas.liiku(4, 6);
        mustaTorni.liiku(0,7);
        if (valkoinenKuningas.getAloitusKaytetty() == false || mustaTorni.getAloitusKaytetty() == false) {
            assertTrue("Nappula ei käytä liikkeessään aloitusta", false);
        }
    }
    @Test
    public void testaaTestiLiiku() {
        Nappula valkoinenHevonen = new Nappula(1,7,HEVONEN,VALKOINEN);
        Nappula mustaKuningatar = new Nappula(3,0, KUNINGATAR, MUSTA);
        
        valkoinenHevonen.testiLiiku(2, 5);
        if (valkoinenHevonen.getAloitusKaytetty() == true) {
            assertTrue("Nappula käyttää aloituksen testiliikkeessä", false);
        }
        mustaKuningatar.testiLiiku(3, 5);
        if (mustaKuningatar.getAloitusKaytetty() == true) {
            assertTrue("Nappula käyttää aloituksen testiliikkeessä2", false);
        }
    }
    @Test
    public void testaaLiikuTakaisin() {
        Nappula valkoinenLahetti = new Nappula(2,7,LAHETTI,VALKOINEN);
        
        valkoinenLahetti.liiku(6,3);
        valkoinenLahetti.liikuTakaisin();
        if (valkoinenLahetti.getX() != 2 || valkoinenLahetti.getY() != 7) {
            assertTrue("Liikutakaisin ei toimi", false);
        }
    }
    @Test
    public void edellinenToimii() {
        sotilas.liiku(1, 3);
        kuningatar.liiku(7,0);
        if (sotilas.getEdellinenY() != 1) {
            assertTrue("Musta sotilas ei muisata edellistä sijaintijaan y-akselilla", false);
        }
        if (kuningatar.getEdellinenX() != 3) {
            assertTrue("Musta kuningatar ei muista edellistä sijaintiaan x-akselilla", false);
        }
    }
}
