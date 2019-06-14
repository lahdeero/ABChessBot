package shakkibeli.lauta;

import shakkibeli.logiikka.Siirto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static shakkibeli.nappula.Arvo.*;
import shakkibeli.nappula.Nappula;
import shakkibeli.nappula.Vari;
import static shakkibeli.nappula.Vari.*;

public class Lauta {

    private List<Nappula> nappulat;
    private Historia pelihistoria;
    private Nappula valkoinenKuningas;
    private Nappula mustaKuningas;

    /**
     * Konstruktori luo uuden nappulalistan ja pelihistorian.
     */
    public Lauta() {
        this.nappulat = new ArrayList<>();
        this.pelihistoria = new Historia();
    }

    /**
     * Lisää laudalle(listalle) Nappula-olion.
     *
     * @param lisattava Lisättävä Nappula.
     * @return True mikäli lisättävän nappulan koordinaateissa olevassa ruudussa
     * ei ole ennestään nappulaa. False mikäli nappulat menisivät päällekkäin.
     */
    public boolean lisaaNappula(Nappula lisattava) {
        for (Nappula nappula : nappulat) {
            if (nappula.getX() == lisattava.getX() && nappula.getY() == lisattava.getY()) {
                return false;
            }
        }
        nappulat.add(lisattava);
        if (lisattava.getArvo() == KUNINGAS) {
            setKuningas(lisattava);
        }
        return true;
    }

    /**
     * Tyhjentää laudan.
     */
    public void poistaKaikkiNappulat() {
        List<Nappula> tyhja = new ArrayList<>();
        this.nappulat = tyhja;
    }

    /**
     * Poistaa nappulan ruudusta.
     *
     * @param x -koordinaatti
     * @param y -koordinaatti
     */
    public void poistaNappula(int x, int y) {
        for (Nappula nappula : nappulat) {
            if (nappula.getX() == x && nappula.getY() == y) {
                nappulat.remove(nappula);
                System.out.println("nappi poistettu");
                break;
            }
        }
    }

    /**
     * Poistaa parametrina saadun nappulan listalta/laudalta.
     *
     * @param poistettava poistettava nappula.
     */
    public void poistaNappula(Nappula poistettava) {
        int indeksi = -1;
        for (int i = 0; i < nappulat.size(); i++) {
            if (nappulat.get(i).getX() == poistettava.getX() && nappulat.get(i).getY() == poistettava.getY()) {
                indeksi = i;
            }
        }
        if (indeksi >= 0) {
            nappulat.remove(indeksi);
        }
    }

    public void poistaSyodytNappulat() {
        boolean syotyja = false;
        for (Nappula nappula : nappulat) {
            if (nappula.onkoSyoty()) {
                syotyja = true;
                break;
            }
        }
        if (syotyja) {
            List<Nappula> uudetNappulat = new ArrayList<Nappula>();
            for (Nappula nappula : nappulat) {
                if (!nappula.onkoSyoty()) {
                    uudetNappulat.add(nappula);
                }
            }
            this.nappulat = uudetNappulat;
        }
    }

    /**
     * Lisää siirron siirtohistoriaan.
     *
     * @param siirto Historiaan lisättävä siirto.
     */
    public void lisaaSiirto(Siirto siirto) {
        pelihistoria.lisaaSiirto(siirto);
    }

    /**
     * Palauttaa viimeisen toteutuneen siirron nykyisestä pelistä.
     *
     * @return Siirto-olio edellisestä suoritetusta siirrosta, jossei ole(esim.
     * ennen ensimmäistä siirtoa), palautetaan null.
     */
    public Siirto getViimeinenSiirto() {
        if (pelihistoria.getHistoriaKoko() > 0) {
            return pelihistoria.getViimeinenSiirto();
        }
        return null;
    }

    /**
     * Palauttaa siirtohistorian.
     *
     * @return Siirtohistoria listana.
     */
    public Historia getHistoria() {
        return pelihistoria;
    }

    /**
     * Kun tarvitaan käsiteltäväksi tietyssä ruudussa olevaa nappulaa, sen
     * tietojenkäsittelemistä varten.
     *
     * @param x Halutun napin sijainti x-akselilla.
     * @param y Halutun napin sijainti y-akselilla.
     * @return Halutusta sijainnista Nappula, mikäli sellainen on, muuten null.
     */
    public Nappula getNappula(int x, int y) {
        for (Nappula nappula : nappulat) {
            if (nappula.getX() == x && nappula.getY() == y) {
                return nappula;
            }
        }
        return null;
    }

    /**
     * Tarkistetaan onko parametreinä annetussa ruudussa nappulaa.
     *
     * @param x Tarkistettavan ruudun x-koordinaatti.
     * @param y Tarkistettavan ruudun y-koordinaatti.
     * @return Mikäli ruudulla on nappula palautetaan true, jos ruutu on tyhjä
     * palautetaan false.
     */
    public boolean onkoNappulaaRuudussa(int x, int y) {
        for (Nappula nappi : nappulat) {
            if (nappi.getX() == x && nappi.getY() == y) {
                return true;
            }
        }
        return false;
    }

    /**
     * Lisätään laudalle(listalle) normaalin shakin aloitusnappulat oikeille
     * paikoilleen/ruuduille, eli yhteensä 16 sotilasta ja 16 upseeria,
     * valkoisia ja mustia tasapuolisesti. Eli yhteensä 32 nappulaa, jotka
     * vievät (aloituksessa) puolet laudan pinta-alasta.
     */
    public void asetaAloitusNappulat() {
        Vari vari = MUSTA;
        for (int i = 0; i < 2; i++) {
            List<Nappula> upseerit = getAloitusUpseerit(vari);
            for (Nappula lisattava : upseerit) {
                lisaaNappula(lisattava);
            }
            vari = VALKOINEN;
        }
        int y = 6;
        for (int i = 0; i < 2; i++) {
            for (int x = 0; x < 8; x++) {
                Nappula sotilas = new Nappula(x, y, SOTILAS, vari);
                lisaaNappula(sotilas);
            }
            vari = MUSTA;
            y = 1;
        }
    }

    /**
     * Asettaa kuninkaan (Lauta-)luokan oliomuuttujaan. Osaa tarkistaa
     * asetettavaan kuninkaan värin. Asettamisen jälkeen kuninkaan saa
     * käyttöönsä getValkoinenKuningas() tai getMustaKuningas() gettereiden
     * kautta.
     *
     * @param kuningas Asetettava kuningas.
     */
    public void setKuningas(Nappula kuningas) {
        if (kuningas.getVari() == VALKOINEN && kuningas.getArvo() == KUNINGAS) {
            this.valkoinenKuningas = kuningas;
        } else if (kuningas.getVari() == MUSTA && kuningas.getArvo() == KUNINGAS) {
            this.mustaKuningas = kuningas;
        }
    }

    public Nappula getValkoinenKuningas() {
        return this.valkoinenKuningas;
    }

    public Nappula getMustaKuningas() {
        return this.mustaKuningas;
    }

    /**
     * Palauttaa parametrina saadun nappulan vastustajan kuninkaan. Esim.
     * valkoinen sotilas palauttaa mustan kuninkaan.
     *
     * @param tarkistettava Nappula jonka vastustajan kuningas tahdotaan.
     * @return Palauttaa tarkistettavan nappulan vastapuolen kuninkaan.
     */
    public Nappula getVastustajanKuningas(Nappula tarkistettava) {
        if (tarkistettava.getVari() == MUSTA) {
            return this.valkoinenKuningas;
        }
        return this.mustaKuningas;
    }

    /**
     * Palauttaa listan aloituksessa olevista upseereista(eli kaikki paitsi
     * sotilaat) kyseiselle(parametrina annetulle) värille.
     *
     * @param vari Kumman väriset upseerit tahdotaan.
     * @return Kaikki valkoiset tai mustat (aloitus)upseerit.
     */
    public List<Nappula> getAloitusUpseerit(Vari vari) {
        List<Nappula> upseerit = new ArrayList<>();
        int y = 0;
        if (vari == VALKOINEN) {
            y = 7;
        }
        Nappula torni = new Nappula(0, y, TORNI, vari);
        Nappula torni2 = new Nappula(7, y, TORNI, vari);
        Nappula hevonen = new Nappula(1, y, HEVONEN, vari);
        Nappula hevonen2 = new Nappula(6, y, HEVONEN, vari);
        Nappula lahetti = new Nappula(2, y, LAHETTI, vari);
        Nappula lahetti2 = new Nappula(5, y, LAHETTI, vari);
        Nappula daami = new Nappula(3, y, KUNINGATAR, vari);
        Nappula kunkku = new Nappula(4, y, KUNINGAS, vari);
        upseerit.addAll(Arrays.asList(torni, torni2, hevonen, hevonen2, lahetti, lahetti2, daami, kunkku));
        return upseerit;
    }

    public List<Nappula> getNappulaLista() {
        List<Nappula> nappulatLaudalla = new ArrayList();
        for (Nappula nappula : nappulat) {
            if (nappula.onkoSyoty() == false) {
                nappulatLaudalla.add(nappula);
            }
        }
        this.nappulat = nappulatLaudalla;
        return nappulatLaudalla;
    }

}
