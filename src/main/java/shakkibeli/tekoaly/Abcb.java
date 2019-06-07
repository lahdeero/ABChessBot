package shakkibeli.tekoaly;

import abcb.algorithm.AlphaBeta;
import abcb.simulate.Position;
import java.util.List;
import shakkibeli.logiikka.Pelilogiikka;
import shakkibeli.logiikka.Siirto;
import shakkibeli.nappula.Nappula;
import shakkibeli.nappula.Vari;
import static shakkibeli.nappula.Vari.VALKOINEN;

/**
 * Yhistää uuden botin ja vanhan käyttöliittymän. Finenglish koska käyttis
 * suomeks ja botti enkuks.
 *
 * @author Eero
 */
public class Abcb {

    private Pelilogiikka pelo;
    private List<Nappula> nappulat;
    private Vari vari;
    private AlphaBeta ab;
    private int depth;

    public Abcb(Pelilogiikka pelo, List<Nappula> nappulat, Vari vari) {
        this.pelo = pelo;
        this.nappulat = nappulat;
        this.vari = vari;
        this.ab = new AlphaBeta();
        this.depth = 5;
    }

    public void suoritaSiirto(Vari vari) {
        Position currentPosition = new Position();
        for (Nappula nappi : nappulat) {
            if (nappi.onkoSyoty()) {
                continue;
            }
            currentPosition.board[nappi.getY()][nappi.getX()] = nappi.getIntegerValue();
        }
        currentPosition.print();
        currentPosition.whitesMove = vari == VALKOINEN;
        Position nextPosition = ab.calculateNextPosition(currentPosition, depth, vari == VALKOINEN);
        Siirto siirto = new Siirto(currentPosition, nextPosition, nappulat);
        System.out.println("\n" + siirto.getNappula().toString());
        System.out.println(siirto.toString());
        pelo.liikutaNappulaa(new Siirto(currentPosition, nextPosition, nappulat));
    }

    public Pelilogiikka getPelo() {
        return pelo;
    }

    public void setPelo(Pelilogiikka pelo) {
        this.pelo = pelo;
    }

    public List<Nappula> getNappulat() {
        return nappulat;
    }

    public void setNappulat(List<Nappula> nappulat) {
        this.nappulat = nappulat;
    }

    public Vari getVari() {
        return vari;
    }

    public void setVari(Vari vari) {
        this.vari = vari;
    }

}
