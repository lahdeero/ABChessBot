package shakkibeli.tekoaly;

import abcb.InputReader.Openings;
import abcb.algorithm.AlphaBeta;
import abcb.datastructure.MyRecord;
import abcb.simulate.Generator;
import abcb.simulate.Position;
import abcb.util.MoveConverter;
import java.io.IOException;
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
    private MoveConverter moveConverter;
    private MyRecord history;
    private Position previous;
    private Openings openings;
    private Generator generator;

    public Abcb(Pelilogiikka pelo, List<Nappula> nappulat, Vari vari) throws IOException {
        this.pelo = pelo;
        this.nappulat = nappulat;
        this.vari = vari;
        this.ab = new AlphaBeta();
        this.depth = 5;
        this.history = new MyRecord<String>();
        this.openings = new Openings();
        this.generator = new Generator();
    }

    public void suoritaSiirto(Vari vari) {
        Position currentPosition = new Position();
        for (Nappula nappi : nappulat) {
            if (nappi.onkoSyoty()) {
                continue;
            }
            currentPosition.board[nappi.getY()][nappi.getX()] = nappi.getIntegerValue();
        }

        // Gui doesnt add into history, so we have to do it here..
        if (previous != null && previous.whitesMove != currentPosition.whitesMove) {
            addToHistory(previous, currentPosition);
        }

        Position nextPosition = figureOutNextPosition(currentPosition);

        addToHistory(currentPosition, nextPosition);
        Siirto siirto = new Siirto(currentPosition, nextPosition, nappulat);
        System.out.println("\n" + siirto.getNappula().toString());
        System.out.println(siirto.toString());
        pelo.liikutaNappulaa(new Siirto(currentPosition, nextPosition, nappulat));
        previous = nextPosition;
    }

    public Position figureOutNextPosition(Position currentPosition) {
        if (openings.search(historyToString())) {
            String moveStr = openings.getNextMove();
            Siirto siirto = getNextSiirto(currentPosition, moveStr);
            Position p = new Position();
            p.clonePosition(currentPosition, siirto.getX(), siirto.getY());
            p.board[siirto.getUusiY()][siirto.getUusiX()] = siirto.getNappula().getIntegerValue();
            return p;
        }
        currentPosition.whitesMove = vari == VALKOINEN;
        return ab.calculateNextPosition(currentPosition, depth, vari == VALKOINEN);
    }

    private Siirto getNextSiirto(Position currentPosition, String search) {
        MyRecord<Position> nextPositions = generator.getNextPositions(currentPosition);
        for (int i = 0; i < nextPositions.size(); i++) {
            Position nextPosition = nextPositions.get(i);
            Siirto siirto = new Siirto(currentPosition, nextPosition, nappulat);
            if (siirto.toString().equals(search)) {
                return siirto;
            }
        }
        return null;
    }

    public void addToHistory(Position current, Position next) {
        history.add(moveConverter.positionsToChessNotation(current, next));
    }

    public String historyToString() {
        int k = 1;
        String his = "" + k + '.';
        for (int i = 0; i < history.size(); i++) {
            his += " " + history.get(i);
            if (i % 2 == 0 && i != 0) {
                his += k + '.';
            }
        }
        return his;
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
