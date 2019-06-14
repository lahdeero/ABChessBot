package shakkibeli.logiikka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import shakkibeli.kayttoliittyma.TekstiKayttoliittyma;
import shakkibeli.lauta.Lauta;
import static shakkibeli.nappula.Arvo.*;
import shakkibeli.nappula.Nappula;
import static shakkibeli.nappula.Vari.*;

public class PelitilanteenTarkkailijaTest {

    private PelitilanteenTarkkailija tarkkailija;
    private Pelilogiikka logiikka;
    private Lauta lauta;
    private Nappula valkoinenKuningas;
    private Nappula mustaKuningas;

    public PelitilanteenTarkkailijaTest() {
        this.logiikka = new Pelilogiikka();
        this.tarkkailija = new PelitilanteenTarkkailija(logiikka);
        this.lauta = logiikka.getLauta();
        this.logiikka.setLiikkeenTarkistaja(new LiikkeenTarkistaja(logiikka));
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        lauta.poistaKaikkiNappulat();
        valkoinenKuningas = new Nappula(4, 7, KUNINGAS, VALKOINEN);
        lauta.setKuningas(valkoinenKuningas);
        mustaKuningas = new Nappula(4, 0, KUNINGAS, MUSTA);
        lauta.setKuningas(mustaKuningas);
        lauta.lisaaNappula(valkoinenKuningas);
        lauta.lisaaNappula(mustaKuningas);
    }

    @After
    public void tearDown() {
    }

    
    @Test
    public void testaaLooppaako() {
        Nappula valkoinenKuningatar = new Nappula(3, 7, KUNINGATAR, VALKOINEN);
        Siirto valkoinenKuningatarLiikkuuAsemiin = new Siirto(3,7,7,3,valkoinenKuningatar);
        Siirto mustaKuningasLiikkuuAsemiin = new Siirto(4,0,5,0,mustaKuningas);
        logiikka.liikutaNappulaa(valkoinenKuningatarLiikkuuAsemiin);
        logiikka.liikutaNappulaa(mustaKuningasLiikkuuAsemiin);
        Siirto valkoinenKuningatarLiikkuuAsemiin2 = new Siirto(7,3,7,2,valkoinenKuningatar); // shakkaa
        logiikka.liikutaNappulaa(valkoinenKuningatarLiikkuuAsemiin2);
        
        Siirto mustaKuningasLooppaa = new Siirto(5,0,5,1,mustaKuningas);
        Siirto valkoinenKuningatarLooppaa = new Siirto(7,2,7,1,valkoinenKuningatar);
        Siirto mustaKuningasLooppaa2 = new Siirto(5,1,5,0,mustaKuningas);
        Siirto valkoinenKuningatarLooppaa2 = new Siirto(7,1,7,2,valkoinenKuningatar);
        
        for (int i = 0; i < 4; i++) {
            logiikka.liikutaNappulaa(mustaKuningasLooppaa);
            logiikka.liikutaNappulaa(valkoinenKuningatarLooppaa);
            logiikka.liikutaNappulaa(mustaKuningasLooppaa2);
            logiikka.liikutaNappulaa(valkoinenKuningatarLooppaa2);
        }
        
        if (logiikka.getLauta().getHistoria().getHistoriaKoko() < 15) {
            assertTrue("Looppi ei tallennu historiaan", false);
        }
        
        if (!tarkkailija.tarkistaLooppi()) {
            assertTrue("Looppia ei tunnisteta", false);
        }
        
    }
     
    @Test
    public void testaaTuleekoLiikkuvaanPaatyynShakki() {
        Nappula valkoinenKuningatar = new Nappula(3, 7, KUNINGATAR, VALKOINEN);
        lauta.lisaaNappula(valkoinenKuningatar);
        Siirto kuningatarLiikkuu = new Siirto(3, 7, 3, 6, valkoinenKuningatar);
        if (tarkkailija.tuleekoLiikkuvanPaatyynShakki(kuningatarLiikkuu) == true) {
            assertTrue("Valkoiselle tulee shakki ilman syytä", false);
        }
        Siirto mustaKuningasLiikkuu = new Siirto(4, 0, 3, 1, mustaKuningas);
        if (tarkkailija.tuleekoLiikkuvanPaatyynShakki(mustaKuningasLiikkuu) == false) {
            assertTrue("Mustan päätyyn ei tule shakkia, vaikka musta kuningas liikkuisi valkoisen kuningattaren uhkausalueelle", false);
        }
    }

    @Test
    public void testaaTarkistaShakataanko() { //HUOM! Siirto-olio ei oikeasti siirrä vielä mitään
        Nappula heppa = new Nappula(7, 2, HEVONEN, VALKOINEN);
        Nappula lahetti = new Nappula(0, 5, LAHETTI, MUSTA);
        lauta.lisaaNappula(heppa);
        lauta.lisaaNappula(lahetti);
        Siirto siirraHeppaa1 = new Siirto(7, 2, 6, 4, heppa);
        heppa.liiku(6, 4);
        if (tarkkailija.tarkistaShakataanko(siirraHeppaa1) == true) {
            assertTrue("Valkoinen hevonen ei uhkaa mustaa kuningasta", false);
        }
        Siirto siirraLahettia1 = new Siirto(0, 5, 2, 3, lahetti);
        lahetti.liiku(2, 3);
        if (tarkkailija.tarkistaShakataanko(siirraLahettia1) == true) {
            assertTrue("Musta lähetti ei uhkaa valkoista kuningasta", false);
        }
        Siirto siirraHeppaa2 = new Siirto(6, 4, 5, 2, heppa);
        heppa.liiku(5, 2);
        if (tarkkailija.tarkistaShakataanko(siirraHeppaa2) == false) {
            assertTrue("Valkoinen hevonen shakkaa kuningasta!", false);
        }
        Siirto siirraLahettia2 = new Siirto(2, 3, 1, 4, lahetti);
        lahetti.liiku(1, 4);
        if (tarkkailija.tarkistaShakataanko(siirraLahettia2) == false) {
            assertTrue("Musta lahetti shakkaa kuningasta!", false);
        }
    }

    @Test
    public void testaaTarkistaShakkaakoNappula() {
        mustaKuningas.liiku(4, 5);
        Nappula heppa = new Nappula(3, 4, HEVONEN, VALKOINEN);
        Nappula heppa2 = new Nappula(4, 4, HEVONEN, VALKOINEN);
        Nappula heppa3 = new Nappula(5, 4, HEVONEN, VALKOINEN);
        Nappula heppa4 = new Nappula(3, 5, HEVONEN, VALKOINEN);
        Nappula heppa5 = new Nappula(5, 5, HEVONEN, VALKOINEN);
        Nappula heppa6 = new Nappula(3, 6, HEVONEN, VALKOINEN);
        Nappula heppa7 = new Nappula(4, 6, HEVONEN, VALKOINEN);
        Nappula heppa8 = new Nappula(5, 6, HEVONEN, VALKOINEN);
        Nappula heppa9 = new Nappula(5, 7, HEVONEN, VALKOINEN); // Tän pitäis shakata
        Nappula heppa10 = new Nappula(2, 5, HEVONEN, VALKOINEN);
        Nappula heppa11 = new Nappula(4, 3, HEVONEN, VALKOINEN);
        Nappula heppa12 = new Nappula(6, 5, HEVONEN, VALKOINEN);
        Nappula heppa13 = new Nappula(4, 7, HEVONEN, VALKOINEN);
        List<Nappula> hepat = new ArrayList<>();
        hepat.addAll(Arrays.asList(heppa, heppa2, heppa3, heppa4, heppa5, heppa6, heppa7, heppa8, heppa10, heppa11, heppa12, heppa13));
        for (Nappula hevonen : hepat) {
            if (tarkkailija.tarkistaShakkaakoNappula(hevonen) != null) {
                assertTrue("Hevonen shakkaa väärin", false);
            }
        }
        if (!tarkkailija.tarkistaShakkaakoNappula(heppa9).equals(heppa9)) {
            assertTrue("Hevonen ei shakkaa oikein", false);
        }

    }

    @Test
    public void testaaEtteiKuningasVoiTornittaaUhatunRuudunYli() {
        Nappula valkoinenTorni = new Nappula(0, 7, TORNI, VALKOINEN);
        Nappula mustaTorni = new Nappula(3, 0, TORNI, MUSTA);
        if (lauta.lisaaNappula(valkoinenTorni) == false) {
            assertTrue("Nappulan lisäys ei onnistu", false);
        }
        if (lauta.lisaaNappula(mustaTorni) == false) {
            assertTrue("Nappulan lisäys ei onnistu", false);
        }
        if (logiikka.liikutaNappulaa(4, 7, 2, 7) == true) {
            assertTrue("Valkoinen voi tornittaa uhkauksen yli", false);
        }
    }
}
