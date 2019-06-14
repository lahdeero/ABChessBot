package shakkibeli.logiikka;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import shakkibeli.kayttoliittyma.TekstiKayttoliittyma;
import shakkibeli.lauta.Lauta;
import static shakkibeli.nappula.Arvo.HEVONEN;
import static shakkibeli.nappula.Arvo.KUNINGAS;
import static shakkibeli.nappula.Arvo.KUNINGATAR;
import static shakkibeli.nappula.Arvo.LAHETTI;
import static shakkibeli.nappula.Arvo.SOTILAS;
import static shakkibeli.nappula.Arvo.TORNI;
import shakkibeli.nappula.Nappula;
import static shakkibeli.nappula.Vari.MUSTA;
import static shakkibeli.nappula.Vari.VALKOINEN;

public class PelilogiikkaTest {

    Lauta lauta;
    Pelilogiikka pelo; // ALOITUSPELILOGIIKKA //

    public PelilogiikkaTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.pelo = new Pelilogiikka();
        pelo.getLauta().asetaAloitusNappulat();
        this.lauta = pelo.getLauta();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testaaVari() {
        Nappula mustaSotilas = lauta.getNappula(0, 1);
        if (pelo.testaaVari(new Siirto(1, 1, 1, 2, mustaSotilas))) {
            assertTrue("Musta voi aloittaa", false);
        }
        aloitusSiirrot();
        if (pelo.testaaVari(new Siirto(7, 4, 7, 5, lauta.getNappula(7, 4))) == true) { // musta sotilas h4 -> h3
            assertTrue("Musta voi liikkua kaksi kertaa peräkkäin", false);
        }
        pelo.liikutaNappulaa(0, 6, 0, 5);
        if (pelo.liikutaNappulaa(1, 6, 1, 5) == true) {
            assertTrue("Valkoinen voi liikkua kaksi kertaa peräkkäin", false);
        }
    }
    
    @Test
    public void kuningatarSyoHevosen() {
       Pelilogiikka p = new Pelilogiikka();
        Lauta lauta = p.getLauta();
        p.getLauta().poistaKaikkiNappulat();
        p.getLauta().lisaaNappula(new Nappula(0, 0, TORNI, MUSTA));
        p.getLauta().lisaaNappula(new Nappula(1, 0, HEVONEN, MUSTA));
        p.getLauta().lisaaNappula(new Nappula(3, 3, KUNINGATAR, MUSTA));
        p.getLauta().lisaaNappula(new Nappula(4, 0, KUNINGAS, MUSTA));
        p.getLauta().lisaaNappula(new Nappula(5, 0, LAHETTI, MUSTA));
        p.getLauta().lisaaNappula(new Nappula(6, 0, HEVONEN, MUSTA));
        p.getLauta().lisaaNappula(new Nappula(7, 0, TORNI, MUSTA));

        p.getLauta().lisaaNappula(new Nappula(0, 1, SOTILAS, MUSTA));
        p.getLauta().lisaaNappula(new Nappula(1, 2, SOTILAS, MUSTA));
        p.getLauta().lisaaNappula(new Nappula(2, 3, SOTILAS, MUSTA));
        p.getLauta().lisaaNappula(new Nappula(3, 1, SOTILAS, MUSTA));
        p.getLauta().lisaaNappula(new Nappula(4, 1, SOTILAS, MUSTA));
        p.getLauta().lisaaNappula(new Nappula(5, 1, SOTILAS, MUSTA));
        p.getLauta().lisaaNappula(new Nappula(6, 1, SOTILAS, MUSTA));
        p.getLauta().lisaaNappula(new Nappula(7, 1, SOTILAS, MUSTA));

        p.getLauta().lisaaNappula(new Nappula(0, 6, SOTILAS, VALKOINEN));
        p.getLauta().lisaaNappula(new Nappula(1, 6, SOTILAS, VALKOINEN));
        p.getLauta().lisaaNappula(new Nappula(2, 6, SOTILAS, VALKOINEN));
        p.getLauta().lisaaNappula(new Nappula(4, 3, SOTILAS, VALKOINEN));
        p.getLauta().lisaaNappula(new Nappula(5, 6, SOTILAS, VALKOINEN));
        p.getLauta().lisaaNappula(new Nappula(6, 4, SOTILAS, VALKOINEN));
        p.getLauta().lisaaNappula(new Nappula(7, 6, SOTILAS, VALKOINEN));

        p.getLauta().lisaaNappula(new Nappula(0, 7, TORNI, VALKOINEN));
        p.getLauta().lisaaNappula(new Nappula(1, 3, LAHETTI, VALKOINEN));
        p.getLauta().lisaaNappula(new Nappula(5, 7, TORNI, VALKOINEN));
        p.getLauta().lisaaNappula(new Nappula(4, 5, LAHETTI, VALKOINEN));
        p.getLauta().lisaaNappula(new Nappula(4, 4, HEVONEN, VALKOINEN));
        p.getLauta().lisaaNappula(new Nappula(5, 5, HEVONEN, VALKOINEN));
        p.getLauta().lisaaNappula(new Nappula(6, 7, KUNINGAS, VALKOINEN));
        
        TekstiKayttoliittyma te = new TekstiKayttoliittyma(lauta);
        te.tulostaLauta();
        Siirto edellinen = new Siirto(5,7,3,7, lauta.getNappula(5, 7));
        p.liikutaNappulaa(edellinen);
        Siirto kuningatarSyoHevosen = new Siirto(3, 3, 4, 4, lauta.getNappula(3, 3));
        assertTrue("Musta kuningatar ei voi syödä valkoista hevosta", p.liikutaNappulaa(kuningatarSyoHevosen));
    }

    @Test
    public void testaaSiirto() {
        pelo.liikutaNappulaa(4, 6, 4, 4);
        pelo.liikutaNappulaa(4, 1, 4, 3);
        pelo.liikutaNappulaa(3, 7, 7, 3);
        Siirto shakkiOmaanPaatyyn = new Siirto(5, 1, 5, 2, lauta.getNappula(5, 1));
        if (pelo.liikutaNappulaa(shakkiOmaanPaatyyn)) {
            assertTrue("Musta voi avata uhkauksen omalle kunikaalle", false);
        }
    }

    @Test
    public void testaaSyoNappula() {
        pelo.liikutaNappulaa(3, 6, 3, 4);
        pelo.liikutaNappulaa(3, 1, 3, 3);
        Siirto omaSyonti = new Siirto(3, 7, 3, 4, lauta.getNappula(3, 7));
        if (pelo.syoNappula(omaSyonti)) {
            assertTrue("Valkoinen kuningatar voi syödä oman sotilaansa", false);
        }
        pelo.liikutaNappulaa(7, 1, 7, 2);
    }

    @Test
    public void sotilaanLogiikka() {
        Nappula mustaSotilas = lauta.getNappula(0, 1);
        Nappula valkoinenSotilas = lauta.getNappula(1, 6);
        Siirto virheellinen1 = new Siirto(0, 4, mustaSotilas);
        Siirto virheellinen2 = new Siirto(1, 3, valkoinenSotilas);
        if (pelo.liikutaNappulaa(virheellinen1) == true || pelo.liikutaNappulaa(virheellinen2) == true) {
            assertTrue("Sotilas voi liikkua liian pitkälle aloituksessa", false);
        }
    }

    @Test
    public void sotilaanLogiikka2() {
        pelo.liikutaNappulaa(3, 6, 3, 4);
        pelo.liikutaNappulaa(0, 1, 0, 3);
        pelo.liikutaNappulaa(3, 4, 3, 3);
        pelo.liikutaNappulaa(4, 1, 4, 3);
        if (pelo.liikutaNappulaa(3, 3, 4, 2) == false) {
            assertTrue("Ohitus ei toimi", false);
        }
        pelo.liikutaNappulaa(0, 3, 0, 4);
        pelo.liikutaNappulaa(1, 6, 1, 4);
        pelo.liikutaNappulaa(6, 0, 7, 2);
        pelo.liikutaNappulaa(6, 7, 7, 5);
        if (pelo.liikutaNappulaa(0, 4, 1, 5) == true) {
            assertTrue("Musta sotilas voi ohittaa myöhässä", false);
        }
    }

    @Test
    public void sotilaanLogiikka3() {
        pelo.liikutaNappulaa(3, 6, 3, 4);
        pelo.liikutaNappulaa(0, 1, 0, 3);
        pelo.liikutaNappulaa(6, 7, 7, 5);
        pelo.liikutaNappulaa(0, 3, 0, 4);
        pelo.liikutaNappulaa(1, 6, 1, 4);
        if (pelo.liikutaNappulaa(0, 4, 1, 5) == false) {
            assertTrue("Mustan sotilas ei voi ohisyödä", false);
        }

    }

    @Test
    public void sotilaanLogiikka4() {
        pelo.liikutaNappulaa(3, 6, 3, 4);
        pelo.liikutaNappulaa(3, 1, 3, 3);
        pelo.liikutaNappulaa(4, 6, 4, 4);
        pelo.liikutaNappulaa(4, 1, 4, 3);
        pelo.liikutaNappulaa(3, 7, 4, 6);
        pelo.liikutaNappulaa(3, 3, 4, 4);
        pelo.liikutaNappulaa(3, 4, 3, 3);
        if (pelo.liikutaNappulaa(4, 3, 3, 4) == true) {
            assertTrue("Musta sotilas voi ohittaa vaikkei valkonen liikkunut kahta", false);
        }
    }

    @Test
    public void sotilaanLogiikka5() {
        pelo.liikutaNappulaa(6, 7, 5, 5);
        pelo.liikutaNappulaa(1, 0, 2, 2);
        if (pelo.liikutaNappulaa(5, 6, 5, 4) == true) {
            assertTrue("Valkoinen sotilas voi ensimmäisellä siirrollaan liikkua oman hevosen yli", false);
        }
        pelo.liikutaNappulaa(5, 5, 6, 3);
        pelo.liikutaNappulaa(1, 1, 1, 3);
        pelo.liikutaNappulaa(6, 3, 4, 2);
        if (pelo.liikutaNappulaa(4, 1, 4, 3) == true) {
            assertTrue("Musta sotilas voi ensimmäisellä siirrollaan liikkua valkoisen hevosen yli", false);
        }
        pelo.liikutaNappulaa(0, 1, 0, 3);
        pelo.liikutaNappulaa(7, 6, 7, 4);
        pelo.liikutaNappulaa(0, 3, 0, 4);
        pelo.liikutaNappulaa(7, 4, 7, 3);
        pelo.liikutaNappulaa(0, 4, 0, 5);
        pelo.liikutaNappulaa(7, 3, 7, 2);
        pelo.liikutaNappulaa(0, 5, 1, 6);
        pelo.liikutaNappulaa(7, 2, 6, 1);
        pelo.liikutaNappulaa(1, 6, 0, 7); // Musta sotilas päätyyn.
        pelo.liikutaNappulaa(6, 1, 7, 0); // Valkoinen sotilas päätyyn.
        if (pelo.liikutaNappulaa(0, 7, 2, 5) == false && pelo.liikutaNappulaa(0, 7, 2, 5) == false) {
            assertTrue("Musta sotilas ei vaihdu upseeriksi saavuttuaan päätyyn1", false);
        } 
        if (pelo.liikutaNappulaa(7, 0, 5, 2) == false && pelo.liikutaNappulaa(7, 0, 6, 2) == false) {
            assertTrue("Valkoinen sotilas ei vaihdu upseeriksi saavutettuaan mustan päädyn", false);
        }
    }

    @Test
    public void torninLogiikka() {
        if (pelo.liikutaNappulaa(new Siirto(5, 5, lauta.getNappula(7, 7))) == true) {
            assertTrue("Torni voi nappuloiden yli", false);
        }
    }

    @Test
    public void hevosenLogiikka() {
        if (pelo.liikutaNappulaa(1, 7, 2, 2) == true) {
            assertTrue("Hevonen voi liikkua väärin", false);
        }
    }

    @Test
    public void lahetinLogiikka1() {
        pelo.liikutaNappulaa(1, 6, 1, 5);
        pelo.liikutaNappulaa(6, 1, 6, 3);
        if (pelo.liikutaNappulaa(2, 7, 0, 5) == false) {
            assertTrue("Lahetti ei voi liikkua vasemmalle ylös", false);
        }
        pelo.liikutaNappulaa(5, 0, 7, 2);
        if (pelo.liikutaNappulaa(0, 5, -1, 4) == true) {
            assertTrue("Lahetti voi liikkua ulos laudalta", false);
        }
        if (pelo.liikutaNappulaa(0, 5, 3, 2) == false) {
            assertTrue("Lahetti ei voi liikkua oikealle ylös", false);
        }
        if (pelo.liikutaNappulaa(7, 2, 0, 2) || pelo.liikutaNappulaa(7, 2, 1, 2)) {
            assertTrue("Lähetti voi liikkua vaakasuoraan", false);
        }
    }

    @Test
    public void lahetinLogiikka2() {
        aloitusSiirrot();
        if (pelo.liikutaNappulaa(5, 7, 5, 3) == true) { // testataan lahetti f1 -> f5
            assertTrue("Lahetti voi liikkua suoraan", false);
        }
        if (pelo.liikutaNappulaa(2, 7, 0, 5) == true) { // testataan lahetti c1 -> a3
            assertTrue("Lahetti voi liikkua nappuloiden yli", false);
        }
    }

    @Test
    public void kuningattarenLogiikka() {
        aloitusSiirrot2();
        pelo.liikutaNappulaa(1, 3, 2, 2);
        if (pelo.liikutaNappulaa(3, 0, 2, 2) == true) {
            assertTrue("Kuningatar voi syödä väärin kuningastaan uhkaavan lähetin", false);
        }
        pelo.liikutaNappulaa(3, 0, 3, 1);

    }

    @Test
    public void kuninkaanLogiikka() {
        aloitusSiirrot2();
        pelo.liikutaNappulaa(1, 3, 2, 2); // lähetti syö mustan hevosen
        if (pelo.liikutaNappulaa(4, 0, 2, 2) == true) {
            assertTrue("Musta kuningas voi liikkua liian pitkälle syödäkseen uhkaavan lähetin", false);
        } else if (pelo.liikutaNappulaa(4, 0, 6, 0) == true) {
            assertTrue("Musta kuningas voi tornittaa vaikka on uhattuna", false);
        }
        pelo.liikutaNappulaa(2, 0, 3, 1);
        if (pelo.liikutaNappulaa(4, 7, 2, 7) == true) {
            assertTrue("Valkoinen kuningas voi tornittaa vaikka välissä on 2 nappulaa!", false);
        }
        pelo.liikutaNappulaa(3, 7, 3, 5);
        if (pelo.liikutaNappulaa(4, 0, 2, 0) == true) {
            assertTrue("Musta kuningas voi tornittaa vaikka kuningatar on välissä!", false);
        }
        pelo.liikutaNappulaa(4, 0, 4, 1); // Musta kuningas siirtyy ulos kolostaan
        if (pelo.liikutaNappulaa(4, 7, 1, 7) || pelo.liikutaNappulaa(4, 7, 2, 7)) {
            assertTrue("Valkoinen kuningas voi tornittaa vaikka lähetti on tiellä", false);
        }
        if (pelo.liikutaNappulaa(4, 7, 5, 6) == true) {
            assertTrue("Valkoinen kuningas voi liikkua oman sotilaansa päälle", false);
        }
        if (pelo.liikutaNappulaa(4, 7, 4, 5) == true || pelo.liikutaNappulaa(4, 7, 6, 5) == true) {
            assertTrue("Kuningas voi liikkua kaksi ruutua kerrallaan", false);
        }
        if (pelo.liikutaNappulaa(4, 7, 3, 6) == false) { // Valkoinen kuningas siirtyy ulos kolostaan
            assertTrue("Kuningas ei voi liikkua viistoon", false);
        }
        pelo.liikutaNappulaa(1, 4, 2, 5); // Musta lähetti(syö hepan ja) uhkaa valkoista kuningasta
        if (pelo.liikutaNappulaa(3, 6, 4, 7) == true) {
            assertTrue("Kuningas voi liikkua pysyen uhattuna", false);
        }
        if (pelo.liikutaNappulaa(3, 6, 2, 5) == false) {
            assertTrue("Kuningas ei voi syödä uhkaavaa lähettiä", false);
        }
        if (pelo.liikutaNappulaa(4, 1, 6, 1) == true) {
            assertTrue("Musta kuningas voi liikkua oman nappinsa päälle ja kaksi ruutua", false);
        }
        pelo.liikutaNappulaa(4, 1, 3, 2);
        if (pelo.liikutaNappulaa(2, 5, 2, 4) == true) {
            assertTrue("Valkoinen kuningas voi liikkua uhattuun ruutuun", false);
        }
        if (pelo.liikutaNappulaa(2, 5, 3, 5) == true) {
            assertTrue("Valkoinen kuningas voi liikkua oman kuningattarensa päälle", false);
        }
        pelo.liikutaNappulaa(2, 5, 1, 4);
        if (pelo.liikutaNappulaa(2, 1, 3, 2) == true) {
            assertTrue("Musta sotilas voi tulla mustan kuninkaan päälle", false);
        }
        if (pelo.liikutaNappulaa(3, 2, 2, 2) == false) {
            assertTrue("Musta kuningas ei voi syödä", false);
        }
        if (pelo.liikutaNappulaa(1, 4, 1, 3) == true) {
            assertTrue("Kuninkaan voivat liikkua liian lähelle toisiaan", false);
        }
        pelo.liikutaNappulaa(3, 5, 1, 3);
        if (pelo.liikutaNappulaa(2, 2, 1, 3) == true) {
            assertTrue("Musta kuningas voi syödä sitä uhkaavan valkoisen kuningattaren, vaikka uhkaavaa kuningatarta suojelee kuningas", false);
        }
    }

    @Test
    public void toimiikoShakkaus() {
        aloitusSiirrot2();
        pelo.liikutaNappulaa(1, 3, 2, 2);
        if (pelo.shakkaako() == false) {
            assertTrue("Logiikan mukaan ei shakata, vaikka valkoinen lähetti uhkaa mustaa kuningasta", false);
        }
        pelo.liikutaNappulaa(1, 1, 2, 2);
        if (pelo.shakkaako() == true) {
            assertTrue("Logiikan mukaan shaktaan, vaikka uhkaaja on jo syöty", false);
        }
    }

    @Test
    public void toimiikoShakinPurku() {
        pelo.liikutaNappulaa(4, 6, 4, 4);
        pelo.liikutaNappulaa(4, 1, 4, 3);
        pelo.liikutaNappulaa(5, 7, 1, 3);
        pelo.liikutaNappulaa(5, 0, 1, 4);
        pelo.liikutaNappulaa(1, 3, 3, 1);
        if (pelo.liikutaNappulaa(1, 0, 3, 1) == false) {
            assertTrue("Musta hevonen ei voi torjua shakkia", false);
        }
        pelo.liikutaNappulaa(4, 0, 3, 1);
        pelo.liikutaNappulaa(1, 7, 0, 5);
        pelo.liikutaNappulaa(1, 4, 3, 6);
        if (pelo.liikutaNappulaa(2, 7, 3, 6) == false) {
            assertTrue("Valkoinen lähetti ei voi torjua shakkia", false);
        }

    }

    @Test
    public void testaaLyhytTornitus() {
        aloitusSiirrot2();
        if (pelo.liikutaNappulaa(4, 7, 6, 7) == false) {
            assertTrue("Valkoisen lyhyt tornitus ei onnistu", false);
        }
        pelo.liikutaNappulaa(2, 0, 3, 1);
        pelo.liikutaNappulaa(2, 7, 3, 6);
        if (pelo.liikutaNappulaa(4, 0, 6, 0) == false) {
            assertTrue("Mustan lyhyt tornitus ei toimi", false);
        }
    }

    @Test
    public void testaaPitkaTornitus() {
        pelo.liikutaNappulaa(3, 6, 3, 4);
        pelo.liikutaNappulaa(3, 1, 3, 3);
        pelo.liikutaNappulaa(3, 7, 3, 5);
        pelo.liikutaNappulaa(3, 0, 3, 2);
        pelo.liikutaNappulaa(2, 7, 3, 6);
        pelo.liikutaNappulaa(2, 0, 3, 1);
        if (pelo.liikutaNappulaa(4, 7, 2, 7) == true) {
            assertTrue("Valkoinen kuningas voi tehdä pitkän tornituksen, vaikka hevonen on välissä", false);
        }
        pelo.liikutaNappulaa(1, 7, 0, 5);
        if (pelo.liikutaNappulaa(4, 0, 2, 0) == true) {
            assertTrue("Musta kuningas voi tehdä pitkän tornituksen, vaikka hevonen on välissä", false);
        }
        pelo.liikutaNappulaa(1, 0, 0, 2);
        if (pelo.liikutaNappulaa(4, 7, 2, 7) == false) {
            assertTrue("Valkoisen pitkä tornitus ei onnistu", false);
        } else if (lauta.getNappula(3, 7).getArvo() != TORNI) {
            assertTrue("Valkoinen torni ei siirry oikeaan paikkaan pitkän tornituksen jälkeen", false);
        }
        pelo.liikutaNappulaa(7, 6, 7, 5);
        if (pelo.liikutaNappulaa(4, 0, 2, 0) == false) {
            assertTrue("Mustan pitkä tornitus ei onnistu", false);
        } else if (lauta.getNappula(3, 0).getArvo() != TORNI) {
            assertTrue("Musta torni ei siirry oikeaan paikkaan pitkän tornituksen jälkeen", false);
        }
    }

    @Test
    public void shakkiTarkistus() {
        aloitusSiirrot2();
        if (pelo.liikutaNappulaa(5, 2, 0, 5) == true) {
            assertTrue("Valkoinen hevonen voi liikkua aiheuttaen shakin omaan päätyyn", false);
        }
        pelo.liikutaNappulaa(4, 7, 4, 6);
        if (pelo.liikutaNappulaa(2, 2, 3, 4) == true) {
            assertTrue("Musta hevonen altistaa oman kuninkaansa", false);
        }
        if (lauta.getNappula(3, 4).getArvo() != SOTILAS || lauta.getNappula(3, 4).getVari() != VALKOINEN) {
            assertTrue("Musta hevonen syö vastoin sääntöjä kun altistaa kuninkaansa", false);
        }
        if (lauta.getNappula(3, 4).getArvo() == HEVONEN) {
            assertTrue("Mustan hevosen haamu jää shakkitarkistettuun ruutuun", false);
        }
    }

    public void aloitusSiirrot() {
        pelo.liikutaNappulaa(5, 6, 5, 4); // valkoinen sotilas f2 -> f4
        pelo.liikutaNappulaa(7, 1, 7, 2); // musta sotilas h7 -> h6
        pelo.liikutaNappulaa(5, 4, 5, 3); // valkoinen sotilas f4 -> f5
        pelo.liikutaNappulaa(7, 2, 7, 3); // musta sotilas h6 -> h5
        pelo.liikutaNappulaa(5, 3, 5, 2); // valkoinen sotilas f5 -> f6
        pelo.liikutaNappulaa(7, 3, 7, 4); // musta sotilas h5 -> h4
    }

    public void aloitusSiirrot2() { // perusaloitus: keskimmäiset sotilaat liikkuvat keskelle ja hevoset keskustaan päin..
        pelo.liikutaNappulaa(4, 6, 4, 4);
        pelo.liikutaNappulaa(4, 1, 4, 3);
        pelo.liikutaNappulaa(6, 7, 5, 5);
        pelo.liikutaNappulaa(1, 0, 2, 2);
        pelo.liikutaNappulaa(3, 6, 3, 4);
        pelo.liikutaNappulaa(6, 0, 5, 2);
        pelo.liikutaNappulaa(1, 7, 2, 5);
        pelo.liikutaNappulaa(3, 1, 3, 3);
        pelo.liikutaNappulaa(5, 7, 1, 3); // Valkoinen lähetti uhkaa hevosta, joka suojelee kuningasta
        pelo.liikutaNappulaa(5, 0, 1, 4); // Musta lähetti uhkaa hevosta, joka suojelee kuningasta
    }
}
