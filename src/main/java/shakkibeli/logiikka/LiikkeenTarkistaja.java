package shakkibeli.logiikka;

import java.util.logging.Level;
import java.util.logging.Logger;
import shakkibeli.lauta.Lauta;
import shakkibeli.nappula.*;
import static shakkibeli.nappula.Arvo.*;
import static shakkibeli.nappula.Vari.*;

/**
 * Logiikan apuluokka, jonka tehtävänä on tarkistaa liikkuminen tapahtuu shakin
 * sääntöjen puitteissa.
 *
 */
public class LiikkeenTarkistaja {

    private Pelilogiikka logiikka;
    private Lauta lauta;
    private boolean shakkaa;

    /**
     * Konstruktori lisää oliomuuttujiin logiikan ja laudan.
     *
     * @param logiikka käytettävä logiikka.
     */
    public LiikkeenTarkistaja(Pelilogiikka logiikka) {
        this.logiikka = logiikka;
        this.lauta = logiikka.getLauta();
    }

    /**
     * Tarkistaa että annettu siirto on liikkumissäännöiltään shakin sääntöjen
     * mukainen.
     *
     * @param siirto Tarkistettava Siirto.
     * @return true mikäli siirto on liikkumisen osalta sääntöjen mukainen,
     * muuten false.
     */
    public boolean tarkistaLiikkuminen(Siirto siirto) {
        String tarkistettava = siirto.toString() + " / " + siirto.getNappula().getVari() + " X: " + siirto.getX() + " Y: " + siirto.getY() + " uusiX: " + siirto.getUusiX() + " uusiY: " + siirto.getUusiY();
        Logger.getLogger(LiikkeenTarkistaja.class.getName()).log(Level.INFO, " tarkistetaan " + tarkistettava, tarkistettava);
        if (null != siirto.getNappula().getArvo()) {
            switch (siirto.getNappula().getArvo()) {
                case SOTILAS:
                    return tarkistaSotilaanLiikkuminen(siirto);
                case TORNI:
                    return tarkistaTorninLiikkuminen(siirto);
                case HEVONEN:
                    return tarkistaHevosenLiikkuminen(siirto);
                case LAHETTI:
                    return tarkistaLahetinLiikkuminen(siirto);
                case KUNINGATAR:
                    return tarkistaKuningattarenLiikkuminen(siirto);
                case KUNINGAS:
                    return tarkistaKuninkaanLiikkuminen(siirto);
                default:
                    break;
            }
        }
        return false;
    }

    /**
     * Tarkistaa että sotilaan liikkuminen on sääntöjen mukaista.
     *
     * @param siirto Siirto-olio.
     * @return true mikäli sotilaan siirto on sääntöjen mukainen, muuten false.
     */
    private boolean tarkistaSotilaanLiikkuminen(Siirto siirto) {
        if (siirto.getNappula().getVari() == VALKOINEN && siirto.getY() > siirto.getUusiY() || siirto.getNappula().getVari() == MUSTA && siirto.getY() < siirto.getUusiY()) {
            if (siirto.getX() == siirto.getUusiX() && Math.abs(siirto.getY() - siirto.getUusiY()) == 2
                    && siirto.getNappula().getAloitusKaytetty() == false && lauta.onkoNappulaaRuudussa(siirto.getUusiX(),
                    siirto.getUusiY()) == false && tarkistaPystyLiikkuminen(siirto) == true) {
                return true;
            } else if (siirto.getX() == siirto.getUusiX() && Math.abs(siirto.getY() - siirto.getUusiY()) == 1
                    && lauta.onkoNappulaaRuudussa(siirto.getUusiX(), siirto.getUusiY()) == false) {
                return true;
            } else if (Math.abs(siirto.getX() - siirto.getUusiX()) == 1 && Math.abs(siirto.getY() - siirto.getUusiY()) == 1
                    && lauta.onkoNappulaaRuudussa(siirto.getUusiX(), siirto.getUusiY()) == true) {
                return logiikka.syoNappula(siirto);
            } else if (Math.abs(siirto.getX() - siirto.getUusiX()) == 1 && Math.abs(siirto.getY() - siirto.getUusiY()) == 1) {
                return tarkistaOhitus(siirto);
            }
        }
        return false;
    }

    private boolean tarkistaOhitus(Siirto siirto) {
        if (siirto.getNappula().getVari() == VALKOINEN) {
            Nappula ohitettava = lauta.getNappula(siirto.getUusiX(), siirto.getUusiY() + 1);
            if (lauta.getViimeinenSiirto().getPaivitettyNappula().equals(ohitettava) && Math.abs(ohitettava.getEdellinenY() - ohitettava.getY()) == 2) {
                return syoOhitettu(siirto.getNappula(), ohitettava);
            }
        } else if (siirto.getNappula().getVari() == MUSTA) {
            Nappula ohitettava = lauta.getNappula(siirto.getUusiX(), siirto.getUusiY() - 1);
            if (lauta.getViimeinenSiirto().getPaivitettyNappula().equals(ohitettava) && Math.abs(ohitettava.getEdellinenY() - ohitettava.getY()) == 2) {
                return syoOhitettu(siirto.getNappula(), ohitettava);
            }
        }
        return false;
    }

    private boolean syoOhitettu(Nappula sotilas, Nappula ohitettava) {
        if (sotilas.getArvo() == SOTILAS && ohitettava.getArvo() == SOTILAS && sotilas.getVari() != ohitettava.getVari()) {
            lauta.poistaNappula(ohitettava);
            return true;
        }
        return false;
    }

    /**
     * Tarkistaa tornin liikkumisen apuluokkien tarkistaPystyLiikkuminen ja
     * tarkistaVaakaLiikkuminen apua käyttäen.
     *
     * @param siirto Annettu siirto-olio.
     * @return true mikäli siirto on liikkumisen osalta sääntöjen mukainen(ei
     * nappeja reitillä, paitsi viimeisessä ruudussa voi olla vastustajan
     * nappula).
     */
    private boolean tarkistaTorninLiikkuminen(Siirto siirto) {
        if (tarkistaPystyTaiVaakaLiikkuminen(siirto)) {
            if (lauta.onkoNappulaaRuudussa(siirto.getUusiX(), siirto.getUusiY()) == true) {
                return logiikka.syoNappula(siirto);
            }
            return true;
        }
        return false;
    }

    /**
     * Tarkistaa hevosen liikkumisen.
     *
     * @param siirto Annettu siirto-olio.
     * @return true mikäli liikkuminen on hevosmaista, eli 2 ruutua vaaka- tai
     * pystysuuntaan, sekä yksi ruutu vaaka- tai pystysuuntaan, kunhan se on eri
     * suunta(x- tai y-akselilla) kun ensiksi mainittu.
     */
    public boolean tarkistaHevosenLiikkuminen(Siirto siirto) {
        boolean kunnossa = false;
        if (Math.abs(siirto.getX() - siirto.getUusiX()) == 1 && Math.abs(siirto.getY() - siirto.getUusiY()) == 2) {
            kunnossa = true;
        } else if (Math.abs(siirto.getX() - siirto.getUusiX()) == 2 && Math.abs(siirto.getY() - siirto.getUusiY()) == 1) {
            kunnossa = true;
        }
        if (kunnossa && lauta.onkoNappulaaRuudussa(siirto.getUusiX(), siirto.getUusiY()) == false) {
            return true;
        } else if (kunnossa) {
            return logiikka.syoNappula(siirto);
        }
        return false;
    }

    /**
     * Tarkistaa lähetin liikkumisen, eli ristiliikkumisen(apumetodi)
     * käytännössä.
     *
     * @param siirto Annettu siirto-olio.
     * @return true mikäli liikkuminen on lähettimäistä(ristiin ei nappeja
     * välissä), muuten false.
     */
    private boolean tarkistaLahetinLiikkuminen(Siirto siirto) {
        if (Math.abs(siirto.getX() - siirto.getUusiX()) == Math.abs(siirto.getY() - siirto.getUusiY()) && tarkistaRistiinLiikkuminen(siirto)) {
            if (lauta.onkoNappulaaRuudussa(siirto.getUusiX(), siirto.getUusiY())) {
                return logiikka.syoNappula(siirto);
            }
            return true;
        }
        return false;
    }

    /**
     * Tarkistaa kuningattaren liikkumisen, käyttää
     * tarkistaPystyTaiVaakaLiikkuminen ja tarkistaRistiinliikkuminen
     * apumetodeja.
     *
     * @param siirto Annettu siirto-olio.
     * @return true mikäli siirto on laillinen, muuten false.
     */
    private boolean tarkistaKuningattarenLiikkuminen(Siirto siirto) {
//        if (siirto.getX() == siirto.getUusiX() && tarkistaPystyTaiVaakaLiikkuminen(siirto) || siirto.getY() == siirto.getUusiY() && tarkistaPystyTaiVaakaLiikkuminen(siirto)) {
        if ((siirto.getX() == siirto.getUusiX() || siirto.getY() == siirto.getUusiY()) && tarkistaPystyTaiVaakaLiikkuminen(siirto)) {
            if (lauta.onkoNappulaaRuudussa(siirto.getUusiX(), siirto.getUusiY())) {
                return logiikka.syoNappula(siirto);
            }
            return true;
        } else if (Math.abs(siirto.getX() - siirto.getUusiX()) == Math.abs(siirto.getY() - siirto.getUusiY()) && tarkistaRistiinLiikkuminen(siirto)) {
            if (lauta.onkoNappulaaRuudussa(siirto.getUusiX(), siirto.getUusiY())) {
                return logiikka.syoNappula(siirto);
            }
            return true;
        }
        return false;
    }

    /**
     * Tarkistaa kuninkaan liikkumisen(kaikki viereiset ruudut).
     *
     * @param siirto Annettu siirto-olio.
     * @return true mikli siirto on laillinen, muuten false.
     */
    public boolean tarkistaKuninkaanLiikkuminen(Siirto siirto) {
        if (Math.abs(siirto.getX() - siirto.getUusiX()) <= 1 && Math.abs(siirto.getY() - siirto.getUusiY()) <= 1) {
            if (lauta.onkoNappulaaRuudussa(siirto.getUusiX(), siirto.getUusiY())) {
                return logiikka.syoNappula(siirto);
            }
            return true;
        } else if (Math.abs(siirto.getX() - siirto.getUusiX()) == 2 && siirto.getY() - siirto.getUusiY() == 0 && lauta.onkoNappulaaRuudussa(siirto.getUusiX(), siirto.getUusiY()) == false && shakkaa == false) {
            return tarkistaTornitus(siirto);
        }
        return false;
    }

    private boolean tarkistaTornitus(Siirto siirto) {
        if (siirto.getUusiX() - siirto.getX() == 2 && tarkistaPystyTaiVaakaLiikkuminen(siirto)) {
            if (siirto.getNappula().getAloitusKaytetty() == false && lauta.getNappula(siirto.getUusiX() + 1, siirto.getY()).getAloitusKaytetty() == false) {
                lauta.getNappula(siirto.getUusiX() + 1, siirto.getY()).liiku(siirto.getUusiX() - 1, siirto.getY());
                return true;
            }
        } else if (siirto.getX() - siirto.getUusiX() == 2 && tarkistaPystyTaiVaakaLiikkuminen(siirto.getUusiX() - 2, siirto.getY(), siirto.getNappula()) == true) {
            if (siirto.getNappula().getAloitusKaytetty() == false && lauta.getNappula(siirto.getUusiX() + 2, siirto.getY()).getAloitusKaytetty() == false) {
                lauta.getNappula(siirto.getUusiX() - 2, siirto.getY()).liiku(siirto.getUusiX() + 1, siirto.getY());
                return true;
            }
        }
        return false;
    }

    /**
     * Tarkistaa siirron missä x- ja y-akseli eivät kummatkin muutu.
     *
     * @param siirto Annettu siirto-olio.
     * @return true mikäli välissä ei ole nappuloita, sekä suunta ei ole
     * ristiin, muuten false.
     */
    public boolean tarkistaPystyTaiVaakaLiikkuminen(Siirto siirto) {
        if (siirto.getX() != siirto.getUusiX() && siirto.getY() == siirto.getUusiY()) { //VAAKALIIKKUMINEN
            return tarkistaVaakaLiikkuminen(siirto);
        } else if (siirto.getX() == siirto.getUusiX() && siirto.getY() != siirto.getUusiY()) { // PYSTYLIIKKUMINEN
            return tarkistaPystyLiikkuminen(siirto);
        }
        return false;
    }

    /**
     * Katso katso tarkistaPystyTaiVaakaLiikkuminen(Siirto siirto), muuten sama
     * mutta luo siirto-olion.
     *
     * @param x määränpään x-koordinaatti
     * @param y määäränpään y-koordinaatti
     * @param tarkistettava tarkistettava nappula(mahdollisesti liikutettava).
     * @return katso tarkistaPystyTaiVaakaLiikkuminen.
     */
    public boolean tarkistaPystyTaiVaakaLiikkuminen(int x, int y, Nappula tarkistettava) {
        Siirto testattava = new Siirto(tarkistettava.getX(), tarkistettava.getY(), x, y, tarkistettava);
        return tarkistaPystyTaiVaakaLiikkuminen(testattava);
    }

    /**
     * Tarkistaa ristiin(vinottain) liikkumisen, eli että liikutaan yhtä paljo
     * x-akselilla kuin y-akselilla ja ettei reitillä ole nappuloita tiellä.
     *
     * @param siirto Tarkistettava siirto.
     * @return true mikäli siirto on laillinen, muuten false.
     */
    public boolean tarkistaRistiinLiikkuminen(Siirto siirto) {

        if (Math.abs(siirto.getX() - siirto.getUusiX()) == Math.abs(siirto.getY() - siirto.getUusiY())) {
            if (siirto.getNappula().getX() < siirto.getUusiX() && siirto.getY() < siirto.getUusiY()) { // OIKEELLE ALAS 
                return tarkistaRistiinSilmukalla('+', '+', siirto);
            } else if (siirto.getNappula().getX() < siirto.getUusiX() && siirto.getY() > siirto.getUusiY()) { // OIKEELLE YLÖS
                return tarkistaRistiinSilmukalla('+', '-', siirto);
            } else if (siirto.getNappula().getX() > siirto.getUusiX() && siirto.getY() < siirto.getUusiY()) { // VASEMMALLA ALAS
                return tarkistaRistiinSilmukalla('-', '+', siirto);
            } else if (siirto.getNappula().getX() > siirto.getUusiX() && siirto.getY() > siirto.getUusiY()) { // VASEMMALLE YLÖS
                return tarkistaRistiinSilmukalla('-', '-', siirto);
            }
        }

        return false;
    }

    /**
     * Katso tarkistaRistiinLiikkuminen(Siirto siirto), tämä luo vain
     * koordinaateista siirto-olion.
     *
     * @param x Siirron määränpää x-akselilla.
     * @param y Siirron määränpää y-akselilla.
     * @param testattava Testattava nappula.
     * @return true mikäli siirto on laillinen, muuten false.
     */
    public boolean tarkistaRistiinLiikkuminen(int x, int y, Nappula testattava) {
        return tarkistaRistiinLiikkuminen(new Siirto(testattava.getX(), testattava.getY(), x, y, testattava));
    }

    private boolean tarkistaRistiinSilmukalla(char eka, char toka, Siirto siirto) {
        int testattavaX = siirto.getX();
        int testattavaY = siirto.getY();
        for (int i = 1; i < Math.abs(siirto.getX() - siirto.getUusiX()); i++) {
            testattavaX = lisaaTaiVahenna(testattavaX, eka);
            testattavaY = lisaaTaiVahenna(testattavaY, toka);
            if (lauta.onkoNappulaaRuudussa(testattavaX, testattavaY)) {
                return false;
            }
        }
        return true;
    }

    private int lisaaTaiVahenna(int luku, char operaattori) {
        if (operaattori == '+') {
            luku++;
        } else if (operaattori == '-') {
            luku--;
        }
        return luku;
    }

    private boolean tarkistaVaakaLiikkuminen(Siirto siirto) {
        if (siirto.getY() == siirto.getUusiY()) {
            for (int i = 1; i < Math.abs(siirto.getX() - siirto.getUusiX()); i++) {
                if (siirto.getX() > siirto.getUusiX() && lauta.onkoNappulaaRuudussa(siirto.getX() - i, siirto.getY())) {
                    return false;
                } else if (siirto.getX() < siirto.getUusiX() && lauta.onkoNappulaaRuudussa(siirto.getX() + i, siirto.getY())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean tarkistaPystyLiikkuminen(Siirto siirto) {
        if (siirto.getX() == siirto.getUusiX()) {
            for (int i = 1; i < Math.abs(siirto.getY() - siirto.getUusiY()); i++) {
                if (siirto.getY() < siirto.getUusiY() && lauta.onkoNappulaaRuudussa(siirto.getX(), siirto.getY() + i)) {
                    return false;
                } else if (siirto.getY() > siirto.getUusiY() && lauta.onkoNappulaaRuudussa(siirto.getX(), siirto.getY() - i)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Pitää kirjaa uhataanko jompaakumpaa kuningasta, tilanne jossa uhattaisiin
     * kumpaakin kuningasta yhtäaikaa ei(ainakaan pitäisi) ole mahdollinen.
     *
     * @param shakkaako Palauttaa true jos pelitilanne on shakki(jompaakumpaa
     * kuningasta uhataan), muuten false.
     */
    public void shakkaa(boolean shakkaako) {
        this.shakkaa = shakkaako;
    }
}
