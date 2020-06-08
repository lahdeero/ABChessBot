package shakkibeli.lauta;

import shakkibeli.logiikka.Siirto;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * Siirtohistoria-luokka.
 */
public class Historia {

    private Stack<Siirto> siirrot;

    /**
     * Konstruktori luo uuden pinon siirroille.
     */

    public Historia() {
        this.siirrot = new Stack<>();
    }

    /**
     * Palauttaa pinon siirroista.
     *
     * @param siirrot Lista siirrot.
     */
    public Historia(Stack<Siirto> siirrot) {
        this.siirrot = siirrot;
    }

    /**
     * Lisää siirron siirtohistoriaan.
     *
     * @param siirto Lisättävä siirto.
     */
    public void lisaaSiirto(Siirto siirto) {
        this.siirrot.add(siirto);
    }

    public int getHistoriaKoko() {
        return this.siirrot.size();
    }

    public Stack<Siirto> getHistoria() {
        return this.siirrot;
    }

    public Siirto getViimeinenSiirto() {
        return this.siirrot.peek();
    }
}
