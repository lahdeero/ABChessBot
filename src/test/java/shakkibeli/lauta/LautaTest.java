package shakkibeli.lauta;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import shakkibeli.lauta.Lauta;
import shakkibeli.nappula.*;
import static shakkibeli.nappula.Arvo.*;
import static shakkibeli.nappula.Vari.*;

public class LautaTest {

    Nappula sotilas;
    Lauta lauta;

    public LautaTest() {

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
        lauta = new Lauta();
    }

    @After
    public void tearDown() {
    }

    public boolean tyhjaLauta() {
        Lauta uusiLauta = new Lauta();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (uusiLauta.getNappula(x, y) != null) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean mustanSotilaanLisays() {

        if (lauta.lisaaNappula(sotilas) == false) {
            return false;
        }

        Nappula testattava = lauta.getNappula(1, 1);

        return sotilas.equals(testattava);
    }

    public boolean mustanSotilaanLiikutus() {

        if (lauta.lisaaNappula(sotilas) == false) {
            return false;
        }

        sotilas.liiku(1, 3);

        if (lauta.getNappula(1, 3).equals(sotilas)) {
            return true;
        }
        return false;
    }

    @Test
    public void testaaTyhjaLauta() {
        assertTrue("Tyhjä lauta on oikein", tyhjaLauta());
    }

    @Test
    public void testaaSotilaanLisays() {
        assertTrue("Sotilaan lisääminen onnistui", mustanSotilaanLisays());
    }

    @Test
    public void testaaSotilaanLiikutus() {
        assertTrue("Sotilaan liikuttaminen onnistui", mustanSotilaanLiikutus());
    }

    @Test // HUOM EI LIIKUTTAA VAAN ASETTAA
    public void testaaEtteiVoiAsettaaNappulaaNappulanPaalle() {
        lauta.lisaaNappula(sotilas);
        Nappula torni = new Nappula(1, 1, TORNI, VALKOINEN);

        assertFalse("Nappulan lisääminen samaan ruutuun", lauta.lisaaNappula(torni));
    }

    @Test
    public void nappulanPoistoToimii() {
        lauta.lisaaNappula(sotilas);
        lauta.lisaaNappula(new Nappula(0, 0, TORNI, MUSTA));
        lauta.poistaNappula(1, 1);

        assertEquals("Nappulan poisto listasta toimii", 1, lauta.getNappulaLista().size());
    }

    @Test
    public void testaaAsetaAloitusNappulat() {
        lauta.asetaAloitusNappulat();
        int tasmaa = 0;
        int y = 0;
        for (int i = 0; i < 2; i++) {
            if (lauta.getNappula(0, y).getArvo() == TORNI) {
                tasmaa++;
            }
            if (lauta.getNappula(1, y).getArvo() == HEVONEN) {
                tasmaa++;
            }
            if (lauta.getNappula(2, y).getArvo() == LAHETTI) {
                tasmaa++;
            }
            if (lauta.getNappula(3, y).getArvo() == KUNINGATAR) {
                tasmaa++;
            }
            if (lauta.getNappula(4, y).getArvo() == KUNINGAS) {
                tasmaa++;
            }
            if (lauta.getNappula(5, y).getArvo() == LAHETTI) {
                tasmaa++;
            }
            if (lauta.getNappula(6, y).getArvo() == HEVONEN) {
                tasmaa++;
            }
            if (lauta.getNappula(7, y).getArvo() == TORNI) {
                tasmaa++;
            }
            y = 7;
        }
        y = 1;
        for (int x = 0; x < 8; x++) {
            if (lauta.getNappula(x, y).getArvo() == SOTILAS && lauta.getNappula(x, y).getVari() == MUSTA) {
                tasmaa++;
            }
        }
        y = 6;
        for (int x = 0; x < 8; x++) {
            if (lauta.getNappula(x, y).getArvo() == SOTILAS && lauta.getNappula(x, y).getVari() == VALKOINEN) {
                tasmaa++;
            }
        }
        assertEquals("Aloitusnappulat täsmää", 32, tasmaa);
    }
}
