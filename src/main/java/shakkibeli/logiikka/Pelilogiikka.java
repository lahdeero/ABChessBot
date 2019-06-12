package shakkibeli.logiikka;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import shakkibeli.lauta.Lauta;
import static shakkibeli.nappula.Arvo.*;
import shakkibeli.nappula.Nappula;
import static shakkibeli.nappula.Vari.*;
import shakkibeli.tekoaly.Abcb;

public class Pelilogiikka {

    private Lauta lauta;
    private PelitilanteenTarkkailija shmapa;
    private LiikkeenTarkistaja lita;
    private Abcb tekis;
    private Abcb tekis2;
    private int pelaajia;
    private boolean shakkaa;
    private int jatkuu;
    private boolean valkoisenVuoro;
    private boolean odotaTekoalya;

    /**
     * Konstruktori luo uuden lauda, sekä logiikan apuluokat
     * PelitilanteenTarkkailijan(entinen shmapa) ja LiikkeenTarkitajan.
     */
    public Pelilogiikka() {
        this.lauta = new Lauta();
        this.shmapa = new PelitilanteenTarkkailija(this);
        this.lita = new LiikkeenTarkistaja(this);
        this.pelaajia = 2;
        this.jatkuu = 0;
        lauta.asetaAloitusNappulat();
        valkoisenVuoro = true;
    }

    /**
     * Konstruktori luo uuden laudan, sekä logiikan apuluokat(katso parametriton
     * konstruktori). Tämän lisäksi luodaan "tekoäly"(Satunnainen) tekispeliä
     * varten.
     *
     * @param valinta 1 on yksinpeli, 2 on kaksinpeli ja 0 on tekoälypeli.
     */
    public Pelilogiikka(int valinta) throws IOException {
        this.lauta = new Lauta();
        this.shmapa = new PelitilanteenTarkkailija(this);
        this.lita = new LiikkeenTarkistaja(this);
        this.pelaajia = valinta;
        lauta.asetaAloitusNappulat();
        this.jatkuu = 0;

        valkoisenVuoro = true;

        if (valinta <= 1) {
            this.tekis = new Abcb(this, lauta.getNappulaLista(), MUSTA);
        }
        if (valinta == 0) {
            this.tekis2 = new Abcb(this, lauta.getNappulaLista(), VALKOINEN);
        }
    }

    /**
     * Metodi luo koordinaateista Siirto-olion, jolla kutsuu varsinaista
     * liikutaNappulaa-metodia.
     *
     * @param x Siirrettävän nappulan sijainti x-akselilla.
     * @param y Siirrettävän nappulan sijainti y-akselilla.
     * @param uusiX Sijainti x-akselilla, johon nappula halutaan siirtää.
     * @param uusiY Sijainti y-akselilla, johon nappula halutaan siirtää.
     * @return Palauttaa true mikäli nappulan siirtäminen onnistuu, muuten
     * false.
     */
    public boolean liikutaNappulaa(int x, int y, int uusiX, int uusiY) {
        if (getNappula(x, y) != null) {
            Siirto siirto = new Siirto(x, y, uusiX, uusiY, lauta.getNappula(x, y));
            return liikutaNappulaa(siirto);
        }
        return false;
    }

    /**
     * Metodi tarkistaa että pelin kulku pysyy normaalin shakin sääntöjen
     * mukaisena kun parametrina annettua nappulaa(sisältyy siirto-olioon)
     * liikutetaan. Mikäli siirto onnistuu päivittään pelitilanne. Voisi kutsua
     * pelin keskushermostoksi, tärkein metodi.
     *
     * @param siirto Annetaan Siirto-olio josta selviää (liikuteltavan) Nappulan
     * tiedot, lähtöruutu ja ruutu johon halutaan liikkua
     * @return Palautetaan true jos nappulan liikkuminen on normaalin shakin
     * sääntöjen rajoissa, muuten false.
     */
    public boolean liikutaNappulaa(Siirto siirto) {
        if (siirto.getNappula().onkoSyoty()) {
            String epaonnistunutSiirto = siirto.toString() + " / " + siirto.getNappula().getVari() + " X: " + siirto.getX() + " Y: " + siirto.getY() + " uusiX: " + siirto.getUusiX() + " uusiY: " + siirto.getUusiY();
            Logger.getLogger(Abcb.class.getName()).log(Level.SEVERE, "Liikutetaan syötyöä nappulaa: " + epaonnistunutSiirto, epaonnistunutSiirto);
        }
        if (testaaVari(siirto) && siirto.getUusiX() >= 0 && siirto.getUusiX() < 8 && siirto.getUusiY() >= 0 && siirto.getUusiY() < 8) {
            if (testaaShakkiOmaanPaatyyn(siirto) == false) {
                if (lita.tarkistaLiikkuminen(siirto)) {
                    //System.out.println(siirto.getNappula().getVari() + " X: " + siirto.getX() + " Y: " + siirto.getY() + " uusiX: " + siirto.getUusiX() + " uusiY: " + siirto.getUusiY());
                    suoritaLiikkumisToimenpiteet(siirto);
                    return true;
                }
            }
        }
        String epaonnistunutSiirto = siirto.toString() + " / " + siirto.getNappula().getVari() + " X: " + siirto.getX() + " Y: " + siirto.getY() + " uusiX: " + siirto.getUusiX() + " uusiY: " + siirto.getUusiY();
        Logger.getLogger(Abcb.class.getName()).log(Level.WARNING, "Yritetty virheellista siirtoa: " + epaonnistunutSiirto, epaonnistunutSiirto);
        return false;
    }

    /**
     * Metodi suorittaa onnistuneen siirron mukaiset toimenpiteet pelilogiikkaan
     * ja laudalle.
     *
     * @param siirto Suoritettava(jo laillisuustarkastusten läpäissyt) siirto.
     */
    public void suoritaLiikkumisToimenpiteet(Siirto siirto) {
        siirto.getNappula().liiku(siirto.getUusiX(), siirto.getUusiY());
        siirto.getNappula().valitse(false);
        if (siirto.getNappula().getArvo() == SOTILAS && (siirto.getUusiY() == 0 || siirto.getUusiY() == 7)) {
            siirto.getNappula().setArvo(KUNINGATAR);
        }

        shakkaa = shmapa.tarkistaShakataanko(siirto);
        lita.shakkaa(shakkaa);
        valkoisenVuoro = siirto.getNappula().getVari() != VALKOINEN;
        lauta.lisaaSiirto(siirto);
        jatkuu = 1;
        if (pelaajia == 0 && shmapa.tarkistaLooppi()) {
            if (valkoisenVuoro) {
                tekis2.forceOutFromLoop();
            } else {
                tekis.forceOutFromLoop();
            }
        }
        odotaTekoalya = false;
    }

    /**
     * Generoi tekoälyn siirron ja suorittaa sen. HUOM! Metodia kutsutaan
     * hiirenkuuntelijasta(yksinpelissä!)!
     */
    public void suoritaTekoaly() {
        if (!valkoisenVuoro && pelaajia < 2) {
            System.out.println(tekis.getVari());
            System.out.println(lauta.getViimeinenSiirto().getNappula());
            if (tekis.getVari() != lauta.getViimeinenSiirto().getNappula().getVari()) {
                tekis.suoritaSiirto(MUSTA);
            }
        } else if (valkoisenVuoro && pelaajia == 0) {
            tekis2.suoritaSiirto(VALKOINEN);
        }
    }

    /**
     * Tekoälyn junnaamisen lopettava metodi.
     */
//    public void pakotaPoisLoopista() {
//        if (pelaajia == 2) {
//            return;
//        }
//        if (valkoisenVuoro) {
//            tekis2.poisLoopista();
//        } else {
//            tekis.poisLoopista();
//        }
//    }
    /**
     * Testataan että uusi siirto on eriväriseltä, kun edellinen. Mikäli
     * siirtohistoriasta ei löydy edellistä siirtoa, niin oletetaan että
     * valkoisen vuoro.
     *
     * @param siirto Testattava siirto.
     * @return True mikäli siirrettävä on eri väriä kun viimeksi siirretty tai
     * mikäli siirrettävä on valkoinen eikä edellistä siirtoa löydy. Laiton
     * siirto = false.
     */
    public boolean testaaVari(Siirto siirto) {
        Siirto edellinen = lauta.getViimeinenSiirto();
        if (edellinen != null) {
            return edellinen.getNappula().getVari() != siirto.getNappula().getVari();
        } else if (siirto.getNappula().getVari() == VALKOINEN) {
            return true;
        }
        return false;
    }

    /**
     * Tarkistetaan että siirtäjä ei avaa omalle kuninkaalle uhkausta.
     * Erikoistapauksena ensimmäinen ehto tarkistaa tornittaessa ettei torniteta
     * uhatun ruudun yli.
     *
     * @param siirto Testattava siirto
     * @return Mikäli liikutettavan päätyyn tulee shakki(laiton siirto)
     * palautetaan true, laillinen siirto palauttaa false.
     */
    public boolean testaaShakkiOmaanPaatyyn(Siirto siirto) {
        if (siirto.getNappula().getArvo() == KUNINGAS && Math.abs(siirto.getX() - siirto.getUusiX()) == 2) {
            if (testaaTornituksenValiinJaavaRuutu(siirto) == false) {
                return true;
            }
        }
        return shmapa.tuleekoLiikkuvanPaatyynShakki(siirto);
    }

    /**
     * Kun halutaan syödä nappula(nappula a menee nappula b:n päälle),
     * tarkistaan että ne ovat erivärisiä, sekä ettei kuningas joudu syödyksi.
     * Mikäli näin on, poistetaan ruudusta "vanha"(syötävä) nappula siihen
     * siirtyvän tieltä.
     *
     * @param siirto Siirto-olio, joka sisältää syövän(pelilaudalla pysyvän)
     * nappulan.
     * @return True mikäli syöminen/poistaminen onnistuu, muuten false.
     */
    public boolean syoNappula(Siirto siirto) {
        if ((lauta.getNappula(siirto.getUusiX(), siirto.getUusiY()) == null)
                || (siirto.getX() == siirto.getUusiX() && siirto.getY() == siirto.getUusiY())) {
            String epaonnistunutSiirto = siirto.toString() + " / " + siirto.getNappula().getVari() + " X: " + siirto.getX() + " Y: " + siirto.getY() + " uusiX: " + siirto.getUusiX() + " uusiY: " + siirto.getUusiY();
            Logger.getLogger(Abcb.class.getName()).log(Level.SEVERE, "Siirrossa ei siirtoa!" + epaonnistunutSiirto, epaonnistunutSiirto);
            return false;
        } else if (siirto.getNappula().onkoSyoty()) {
            String epaonnistunutSiirto = siirto.toString() + " / " + siirto.getNappula().getVari() + " X: " + siirto.getX() + " Y: " + siirto.getY() + " uusiX: " + siirto.getUusiX() + " uusiY: " + siirto.getUusiY();
            Logger.getLogger(Abcb.class.getName()).log(Level.SEVERE, "Yritetty liikuttaa syötyä nappulaa" + epaonnistunutSiirto, epaonnistunutSiirto);
            return false;
        }
        Nappula syotava = lauta.getNappula(siirto.getUusiX(), siirto.getUusiY());
        if (syotava.getVariKoodi() != siirto.getNappula().getVariKoodi() && syotava.getArvo() != KUNINGAS && syotava.onkoSyoty() == false) {
            syotava.setSyodyksi();
            lauta.poistaNappula(syotava);
            return true;
        }
        String epaonnistunutSiirto = siirto.toString() + " / " + siirto.getNappula().getVari() + " X: " + siirto.getX() + " Y: " + siirto.getY() + " uusiX: " + siirto.getUusiX() + " uusiY: " + siirto.getUusiY();
        Logger.getLogger(Abcb.class.getName()).log(Level.SEVERE, "Nappulaa ei voity koska väärä väri, kuningas tai jo syöty" + epaonnistunutSiirto, epaonnistunutSiirto);
        return false;
    }

    private boolean testaaTornituksenValiinJaavaRuutu(Siirto siirto) {
        if (siirto.getUusiX() == 2) {
            Siirto temp = new Siirto(3, siirto.getY(), siirto.getNappula());
            if (shmapa.tuleekoLiikkuvanPaatyynShakki(temp) == true) {
                return false;
            }
        } else if (siirto.getUusiX() == 6) {
            Siirto temp = new Siirto(5, siirto.getY(), siirto.getNappula());
            if (shmapa.tuleekoLiikkuvanPaatyynShakki(temp) == true) {
                return false;
            }
        }
        return true;
    }

    /**
     * Katso luokasta liikkeenTarkistaja samoin nimetty metodi.
     *
     * @param siirto Tarkistettava siirto.
     * @return true mikäli siirto on laillinen, muuten false.
     */
    public boolean tarkistaPystyTaiVaakaLiikkuminen(Siirto siirto) {
        return lita.tarkistaPystyTaiVaakaLiikkuminen(siirto);
    }

    /**
     * Katso luokasta liikkeenTarkistaja samoin nimetty metodi.
     *
     * @param siirto Tarkistettava siirto.
     * @return true mikäli siirto on laillinen, muuten false.
     */
    public boolean tarkistaRistiinLiikkuminen(Siirto siirto) {
        return lita.tarkistaRistiinLiikkuminen(siirto);
    }

    /**
     * Palauttaa nappulan joka sijaitsee laudalla paikassa x, y.
     *
     * @param x Halutun nappulan sijainti x-akselilla.
     * @param y Halutun nappulan sijainti y-akselilla.
     * @return Nappulan joka on x- ja y-akselien määrittämällä ruudulla.
     */
    public Nappula getNappula(int x, int y) {
        return lauta.getNappula(x, y);
    }

    /**
     * Kun halutaan asettaa uusi tila peliin, 0 = alotus, perustila 1 = jatkuu,
     * 2 = matti, 3 = patti/tasapeli.
     *
     * @param k Tahdottu tila.
     */
    public void setJatkuu(int k) {
        this.jatkuu = k;
    }

    /**
     * Jos peli jatkuu niin palauttaa 1 tai 0(peli ei ole alkanut), muissa
     * tapauksissa peli on päättynyt: 2 = matti ja 3 = patti.
     *
     * @return 1 = jatkuu, 2 = matti ja 3 = patti.
     */
    public int jatkuuko() {
        return this.jatkuu;
    }

    /**
     * Kertoo uhataanko jompaakumpaa kuningasta.
     *
     * @return true mikäli jompaakumpaa kuningasta uhataan, false jos
     * ei(kumpaakaan).
     */
    public boolean shakkaako() {
        return this.shakkaa;
    }

    public Pelilogiikka getLogiikka() {
        return this;
    }

    public void setLauta(Lauta lauta) {
        this.lauta = lauta;
    }

    public Lauta getLauta() {
        return this.lauta;
    }

    public void setLiikkeenTarkistaja(LiikkeenTarkistaja lita) {
        this.lita = lita;
    }

    public LiikkeenTarkistaja getLiikkeenTarkistaja() {
        return this.lita;
    }

    public List<Nappula> getNappulaLista() {
        return lauta.getNappulaLista();
    }

    public boolean isValkoisenVuoro() {
        return valkoisenVuoro;
    }

    public void setValkoisenVuoro(boolean valkoisenVuoro) {
        this.valkoisenVuoro = valkoisenVuoro;
    }

    public int getPelaajia() {
        return pelaajia;
    }

    public void setPelaajia(int pelaajia) {
        this.pelaajia = pelaajia;
    }

    public int getJatkuu() {
        return this.jatkuu;
    }

    public boolean getValkoisenVuooro() {
        return this.valkoisenVuoro;
    }

    public boolean odotaTekoalya() {
        return odotaTekoalya;
    }

    public void setOdotaTekoalya(boolean odotaTekoalya) {
        this.odotaTekoalya = odotaTekoalya;
    }

}
