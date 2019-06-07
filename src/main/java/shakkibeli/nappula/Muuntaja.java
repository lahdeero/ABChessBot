package shakkibeli.nappula;

import java.util.ArrayList;
import java.util.List;
import shakkibeli.logiikka.Siirto;
import static shakkibeli.nappula.Arvo.*;
import static shakkibeli.nappula.Vari.MUSTA;
import static shakkibeli.nappula.Vari.VALKOINEN;

/**
 * Luokan tehtävänä on muuntaa koordinaatit normaaliksi shakkikieleksi.
 *
 * @author Eero
 */
public class Muuntaja {

    /**
     * Toteuttamatta oleva metodi jonka tarkoituksena olisi muuttaa
     * shakkikoordinaatit numeromuotoon.
     *
     * @param rivi Shakkikoordinaatit kahdelle siirrolle(valkoisen ja mustan siirrot).
     * @return Listana kaksi Siirto-oliota.
     */
    public List<Siirto> muunnaKoordinaateiksi(String rivi) {
        List<Siirto> palautettava = new ArrayList<>();

        String[] karsinnat = rivi.split(".");
        String[] siirrot = karsinnat[1].split(" ");
        Siirto eka = muunnaSiirroksi(siirrot[0], VALKOINEN);
        Siirto toka = muunnaSiirroksi(siirrot[1], MUSTA);

        return palautettava;
    }

    private Siirto muunnaSiirroksi(String siirto, Vari vari) {
        int x;
        int y;
        int nx;
        int ny;

        if (siirto.length() == 2 && vari == VALKOINEN) {
            x = muutaKirjainKoordinaatiksi(siirto.charAt(0)) - 2;
        }

        return null;
    }

    private int muutaKirjainKoordinaatiksi(char merkki) {
        if (merkki == 'a') {
            return 0;
        } else if (merkki == 'b') {
            return 1;
        } else if (merkki == 'c') {
            return 2;
        } else if (merkki == 'd') {
            return 3;
        } else if (merkki == 'e') {
            return 4;
        } else if (merkki == 'f') {
            return 5;
        } else if (merkki == 'g') {
            return 6;
        } else if (merkki == 'h') {
            return 7;
        }
        return 0;
    }

    /**
     * Muuntaa shakkibelin koordinaatit shakkikielelle.
     *
     * @param nappula Nappula jonka koordinaatteja käytetään muuntamisessa.
     * @return Shakissa käytettävä koordinaattimuoto esim Na2(hevonen ruudussa
     * a2).
     */
    public String muunna(Nappula nappula) {
        String muunnos = "";

        int x = nappula.getX();
        int y = nappula.getY();

        muunnos = muunnaArvo(muunnos, nappula.getArvo());
        muunnos = muunnaKoordinaatit(muunnos, nappula.getX(), nappula.getY());

        return muunnos;
    }
    
    public String muunnaSiirtoShakkimuotoon(int x, int y, int ux, int uy) {
        String palautettava = "";
        palautettava = muunnaKoordinaatit(palautettava, x,y);
        palautettava += "->";
        palautettava = muunnaKoordinaatit(palautettava, ux, uy);
        return palautettava;
    }

    private String muunnaKoordinaatit(String muunnos, int x, int y) {
        if (x == 0) {
            muunnos += 'a';
        } else if (x == 1) {
            muunnos += 'b';
        } else if (x == 2) {
            muunnos += 'c';
        } else if (x == 3) {
            muunnos += 'd';
        } else if (x == 4) {
            muunnos += 'e';
        } else if (x == 5) {
            muunnos += 'f';
        } else if (x == 6) {
            muunnos += 'g';
        } else if (x == 7) {
            muunnos += 'h';
        }

        muunnos += 8 - y;
        return muunnos;
    }

    private String muunnaArvo(String muunnos, Arvo arvo) {
        if (arvo == KUNINGAS) {
            muunnos += 'K';
        } else if (arvo == KUNINGATAR) {
            muunnos += 'Q';
        } else if (arvo == HEVONEN) {
            muunnos += 'N';
        } else if (arvo == LAHETTI) {
            muunnos += 'B';
        } else if (arvo == TORNI) {
            muunnos += 'R';
        }
        return muunnos;
    }
}
