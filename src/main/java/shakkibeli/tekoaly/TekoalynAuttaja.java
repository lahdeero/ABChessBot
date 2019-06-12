package shakkibeli.tekoaly;

import java.util.List;
import shakkibeli.logiikka.Pelilogiikka;
import shakkibeli.logiikka.Siirto;
import shakkibeli.nappula.Nappula;

/**
 * Yhteisiä metodeja tekoälyluokille.
 *
 * @author Eero
 */
public class TekoalynAuttaja {

    private Pelilogiikka pelo;

    /**
     * Luo uuden TekoalynAuttajan, luokan tarkoitus on kerätä tekoälyille
     * yhteisiä metodeja.
     *
     * @param pelo Käytössä oleva pelilogiikka.
     */
    public TekoalynAuttaja(Pelilogiikka pelo) {
        this.pelo = pelo;
    }

    /**
     * Tarkistaa jokaisen annetussa listassa olevan nappulan kaikilla x- ja
     * y-koordinaattien yhdistelmillä.
     *
     * @param lapikaytavat Läpikäytävä nappulalista.
     * @return true mikäli saadaan siirrettyä vielä jotain nappia.
     */
    public boolean suoritaSiirtoJosMahdollista(List<Nappula> lapikaytavat) { // tätä vois testata hieman
        for (Nappula kokeiltava : lapikaytavat) {
            if (kokeiltava.onkoSyoty()) {
                continue;
            }
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    Siirto lapikaynti = new Siirto(kokeiltava.getX(), kokeiltava.getY(), x, y, kokeiltava);
                    if (pelo.liikutaNappulaa(lapikaynti)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
