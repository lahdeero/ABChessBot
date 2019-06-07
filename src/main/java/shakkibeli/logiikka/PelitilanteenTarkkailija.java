package shakkibeli.logiikka;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import shakkibeli.lauta.Lauta;
import static shakkibeli.nappula.Arvo.*;
import shakkibeli.nappula.Nappula;
import static shakkibeli.nappula.Vari.*;

public class PelitilanteenTarkkailija {

    private Pelilogiikka logiikka;
    private Lauta lauta;
    private List<Nappula> nappulat;

    /**
     * Konstruktori asettaa pelilogiikan oliomuuttujaan, hakee pelilogiikan
     * kautta laudan ja laudan kautta nappulat.
     *
     * @param logiikka käytettävä Pelilogiikka -olio.
     */
    public PelitilanteenTarkkailija(Pelilogiikka logiikka) {
        this.logiikka = logiikka;
        this.lauta = logiikka.getLauta();
        this.nappulat = lauta.getNappulaLista();
    }

    /**
     * 
     * @return true mikäli peli on tasapeli. 
     */
    public boolean tarkistaLooppi() {
        nappulat = logiikka.getNappulaLista();
        Stack<Siirto> siirrot = new Stack();
        siirrot = (Stack<Siirto>)logiikka.getLauta().getHistoria().getHistoria().clone();
        if (siirrot.size() < 12) {
            return false;
        }
        HashMap<Siirto,Integer> tarkastusmappi = new HashMap();
        
        for (int i = 0; i < 12; i++) {
            Siirto siirto = siirrot.pop();
            if (!tarkastusmappi.containsKey(siirto)) {
                tarkastusmappi.put(siirto,1);
            } else if (tarkastusmappi.get(siirto) == 1){
                tarkastusmappi.put(siirto, tarkastusmappi.get(siirto)+1);
            } else if (tarkastusmappi.get(siirto) == 2) {
                Logger.getLogger(PelitilanteenTarkkailija.class.getName()).log(Level.WARNING, null, "LOOPPAA!"); 
                return true;
            }
        }
        return false;
    }
    /**
     * Tarkistetaan nimenomaan ettei vuorossa oleva pelaaja voi altistaa omaa
     * kuningastaan uhatuksi. Metodia kutsutaan (pääsääntöisesti) Pelilogiikan
     * liikutaNappulaa metodissa Pelilogiikan testaaShakkiOmaanPaatyyn-metodin
     * kautta.
     *
     * @param siirto Tarkistettava siirto.
     * @return True mikäli liikkuvan päätyyn tulisi shakki, mikäli siirto
     * suoritetaan. Laillinen siirto palauttaa false.
     */
    public boolean tuleekoLiikkuvanPaatyynShakki(Siirto siirto) {
        Nappula mahdollisestiSyotava = null;
        Nappula testattava = siirto.getNappula();

        if (lauta.onkoNappulaaRuudussa(siirto.getUusiX(), siirto.getUusiY())) {
            mahdollisestiSyotava = lauta.getNappula(siirto.getUusiX(), siirto.getUusiY());
        }
        testattava.testiLiiku(siirto.getUusiX(), siirto.getUusiY());

        for (Nappula nappula : lauta.getNappulaLista()) {
            if (nappula.getVari() != testattava.getVari() && !nappula.equals(mahdollisestiSyotava) && tarkistaShakkaakoNappula(nappula) != null) {
                testattava.testiLiikuTakaisin();
                return true;
            }
        }
        testattava.testiLiikuTakaisin();
        return false;
    }

    /**
     * Tarkistetaan tuleeko siirron suorittaneen pelaajan vastustajan kuningas
     * uhatuksi mikäli siirto suoritetaan. Huomionarvoista on, että shakkaava
     * nappula ei välttämättä ole siirretty nappula!
     *
     * @param siirto Tarkistettava siirto.
     * @return True jos siirron jälkeen vastustajan kuningas on uhattuna, muuten
     * false.
     */
    public boolean tarkistaShakataanko(Siirto siirto) {
        Nappula liikkunut = siirto.getNappula();
        for (Nappula nappula : logiikka.getNappulaLista()) {
            if (liikkunut.getVari() == nappula.getVari() && tarkistaShakkaakoNappula(nappula) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tarkistetaan uhkaako parametrina annettu Nappula vastustajan Kuningasta.
     *
     * @param tarkistettava Se Nappula, jonka osalta tarkistetaan, uhkaako se
     * vastustajan kuningasta.
     * @return Null mikäli nappula ei uhkaa vastustajan kuningasta, muuten
     * palautetaan uhkaava(eli tarkistettava) Nappula.
     */
    public Nappula tarkistaShakkaakoNappula(Nappula tarkistettava) {
        Nappula vastustajanKuningas = lauta.getVastustajanKuningas(tarkistettava);

        if (null != tarkistettava.getArvo()) {
            switch (tarkistettava.getArvo()) {
                case SOTILAS:
                    return tarkistaShakkaakoSotilas(tarkistettava, vastustajanKuningas);
                case TORNI:
                    return tarkistaShakkaakoTorni(tarkistettava, vastustajanKuningas);
                case HEVONEN:
                    return tarkistaShakkaakoHevonen(tarkistettava, vastustajanKuningas);
                case LAHETTI:
                    return tarkistaShakkaakoLahetti(tarkistettava, vastustajanKuningas);
                case KUNINGATAR:
                    return tarkistaShakkaakoKuningatar(tarkistettava, vastustajanKuningas);
                case KUNINGAS:
                    return tarkistaShakkaakoKuningas(tarkistettava, vastustajanKuningas);
                default:
                    break;
            }
        }
        return null;
    }

    /**
     * Tarkistaa uhkaako tarkistettavana oleva sotilas vastustajansa kuningasta.
     *
     * @param tarkistettava sotilas.
     * @param vastustajanKuningas kuningas eri väriä kun tarkistettava.
     * @return sotilaan mikäli se shakkaa, muuten null(ei shakkaa).
     */
    public Nappula tarkistaShakkaakoSotilas(Nappula tarkistettava, Nappula vastustajanKuningas) {
        if (tarkistettava.getVari() == VALKOINEN) {
            return tarkistaShakkaakoValkoinenSotilas(tarkistettava, vastustajanKuningas);
        } else if (tarkistettava.getVari() == MUSTA) {
            return tarkistaShakkaakoMustaSotilas(tarkistettava, vastustajanKuningas);
        }
        return null;
    }

    private Nappula tarkistaShakkaakoValkoinenSotilas(Nappula tarkistettava, Nappula vastustajanKuningas) {
        if (vastustajanKuningas.getX() == tarkistettava.getX() - 1 && vastustajanKuningas.getY()
                == tarkistettava.getY() - 1 || vastustajanKuningas.getX() == tarkistettava.getX() + 1
                && vastustajanKuningas.getY() == tarkistettava.getY() - 1) {
            return tarkistettava;
        }
        return null;
    }

    private Nappula tarkistaShakkaakoMustaSotilas(Nappula tarkistettava, Nappula vastustajanKuningas) {
        if (vastustajanKuningas.getX() == tarkistettava.getX() - 1 && vastustajanKuningas.getY()
                == tarkistettava.getY() + 1 || vastustajanKuningas.getX() == tarkistettava.getX() + 1
                && vastustajanKuningas.getY() == tarkistettava.getY() + 1) {
            return tarkistettava;
        }
        return null;
    }

    /**
     * Tarkistaa shakkaako parametrina annettu torni vastustajansa kuningasta.
     *
     * @param tarkistettava Torni.
     * @param vastustajanKuningas Eri väriä kun tarkastettava torni.
     * @return Tarkistettava torni mikäli se shakkaa, muuten null(ei shakkaa).
     */
    public Nappula tarkistaShakkaakoTorni(Nappula tarkistettava, Nappula vastustajanKuningas) {
        if (tarkistettava.getX() == vastustajanKuningas.getX()
                || tarkistettava.getY() == vastustajanKuningas.getY()) {
            Siirto testattava = new Siirto(tarkistettava.getX(), tarkistettava.getY(), vastustajanKuningas.getX(), vastustajanKuningas.getY(), tarkistettava);
            if (logiikka.tarkistaPystyTaiVaakaLiikkuminen(testattava)) {
                return tarkistettava;
            }
        }
        return null;
    }

    /**
     * Tarkistaa shakkaako parametrina annettu heppa vastustajansa kuningasta.
     *
     * @param tarkistettava Hevonen.
     * @param vastustajanKuningas Eri väriä kun tarkastettava hevonen.
     * @return Tarkistettava hevonen mikäli se shakkaa, muuten null(ei shakkaa).
     */
    public Nappula tarkistaShakkaakoHevonen(Nappula tarkistettava, Nappula vastustajanKuningas) {
        if (Math.abs(tarkistettava.getX() - vastustajanKuningas.getX()) == 1 && Math.abs(tarkistettava.getY() - vastustajanKuningas.getY()) == 2
                || Math.abs(tarkistettava.getX() - vastustajanKuningas.getX()) == 2 && Math.abs(tarkistettava.getY() - vastustajanKuningas.getY()) == 1) {
            return tarkistettava;
        }
        return null;
    }

    /**
     * Tarkistaa shakkaako parametrina annettu lähetti vastustajansa kuningasta.
     *
     * @param tarkistettava Lähetti.
     * @param vastustajanKuningas Eri väriä kun tarkastettava lähetti.
     * @return Tarkistettava lähetti mikäli se shakkaa, muuten null(ei shakkaa).
     */
    public Nappula tarkistaShakkaakoLahetti(Nappula tarkistettava, Nappula vastustajanKuningas) {
        Siirto testattava = new Siirto(tarkistettava.getX(), tarkistettava.getY(), vastustajanKuningas.getX(), vastustajanKuningas.getY(), tarkistettava);
        if (logiikka.tarkistaRistiinLiikkuminen(testattava) == true) {
            return tarkistettava;
        }
        return null;
    }

    /**
     * Tarkistaa shakkaako parametrina annettu kuningatar vastustajansa
     * kuningasta.
     *
     * @param tarkistettava Kuningatar.
     * @param vastustajanKuningas Eri väriä kun tarkastettava kuningatar.
     * @return Tarkistettava kuningatar mikäli se shakkaa, muuten null(ei
     * shakkaa).
     */
    public Nappula tarkistaShakkaakoKuningatar(Nappula tarkistettava, Nappula vastustajanKuningas) {
        Siirto testattava = new Siirto(tarkistettava.getX(), tarkistettava.getY(), vastustajanKuningas.getX(), vastustajanKuningas.getY(), tarkistettava);
        if (logiikka.tarkistaRistiinLiikkuminen(testattava) == true) {
            return tarkistettava;
        } else if (logiikka.tarkistaPystyTaiVaakaLiikkuminen(testattava)) {
            return tarkistettava;
        }
        return null;

    }

    /**
     * Tarkistaa etteivät kuninkaat pääse viereisiin ruutuihin.
     *
     * @param tarkistettava Kuningas.
     * @param vastustajanKuningas Eri väriä kun tarkastettava kuningas.
     * @return Tarkistettava kuningas mikäli se shakkaa, muuten null(ei
     * shakkaa).
     */
    public Nappula tarkistaShakkaakoKuningas(Nappula tarkistettava, Nappula vastustajanKuningas) {
        if (Math.abs(tarkistettava.getX() - vastustajanKuningas.getX()) < 2 && Math.abs(tarkistettava.getY() - vastustajanKuningas.getY()) < 2) {
            return tarkistettava;
        }
        return null;
    }
}
