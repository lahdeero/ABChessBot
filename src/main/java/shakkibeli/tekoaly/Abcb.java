package shakkibeli.tekoaly;

import abcb.heurestic.Openings;
import abcb.algorithm.AlphaBeta;
import abcb.datastructure.MyRecord;
import abcb.simulate.Generator;
import abcb.simulate.Position;
import abcb.util.History;
import abcb.util.Randomizer;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import shakkibeli.logiikka.Pelilogiikka;
import shakkibeli.logiikka.Siirto;
import shakkibeli.nappula.Nappula;
import shakkibeli.nappula.Vari;
import static shakkibeli.nappula.Vari.MUSTA;
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
    private History history;
    private Position previous;
    private Openings openings;
    private Generator generator;
    private int moveNumber = 1;
    private boolean looping;
    private Randomizer randomizer;
    private boolean fail;

    public Abcb(Pelilogiikka pelo, List<Nappula> nappulat, Vari vari) throws IOException {
        this.pelo = pelo;
        this.nappulat = nappulat;
        this.vari = vari;
        this.ab = new AlphaBeta();
        this.depth = 5;
        this.history = new History();
//        this.history = new MyRecord<String>();
        this.openings = new Openings();
        this.generator = new Generator();
//        this.moveConverter = new MoveConverter();
        this.randomizer = new Randomizer();
    }

    public void suoritaSiirto(Vari vari) {
        System.out.println("\n\nMOVE NUMBER " + moveNumber++);
        System.out.println("ihan eka");
        Position currentPosition = new Position();
        for (Nappula nappi : nappulat) {
            if (nappi.onkoSyoty()) {
                continue;
            }
            currentPosition.board[nappi.getY()][nappi.getX()] = nappi.getIntegerValue();
        }

        // Gui doesnt add into history, so we have to do it here..
        if (!fail) {
            if (previous != null) {
                history.addToHistory(previous, currentPosition);
            } else if (this.vari == MUSTA) {
                history.addToHistory(generator.createStartingPosition(), currentPosition);
            }
        }

        /**
         * (OPENING / ALPHA-BETA) SEARCH *
         */
        Position nextPosition = figureOutNextPosition(currentPosition);
        /**
         * SEARCH OVER *
         */
        if (looping) {
            MyRecord<Position> nextPositions = generator.getNextPositions(currentPosition);
            nextPosition = nextPositions.get(randomizer.generateRandomInt(nextPositions.size() - 1));
            looping = false;
        }

        if (nextPosition != null) {
            history.addToHistory(currentPosition, nextPosition);
            Siirto siirto = new Siirto(currentPosition, nextPosition, nappulat);
            System.out.println("\n" + siirto.getNappula().toString());
            System.out.println(siirto.toString());
            if (!pelo.liikutaNappulaa(new Siirto(currentPosition, nextPosition, nappulat))) {
                whenAlgorithmFails();
            } else {
                previous = nextPosition;
            }
        } else {
            whenAlgorithmFails();
        }
    }

    private void whenAlgorithmFails() {
        fail = true;
        TekoalynAuttaja ta = new TekoalynAuttaja(pelo);
        if (!ta.suoritaSiirtoJosMahdollista(nappulat)) {
            System.out.println("Could not find next move!");
            if (pelo.shakkaako()) {
                pelo.setJatkuu(2);
            } else {
                pelo.setJatkuu(3);
            }
            System.out.println("Game over");
        }
    }

    public Position figureOutNextPosition(Position currentPosition) {
        if (history.getSize() < 20) {
            if (openings.search(history.historyToString())) {
                String moveStr = openings.getNextMove();
                Siirto siirto = getNextSiirto(currentPosition, moveStr);
                if (siirto != null) {
                    Position p = new Position();
                    p.clonePosition(currentPosition, siirto.getX(), siirto.getY());
                    p.board[siirto.getUusiY()][siirto.getUusiX()] = siirto.getNappula().getIntegerValue();
                    System.out.println("opening found! " + openings.getOpeningName());
                    return p;
                }
                System.out.println("could not find opening..");
            } else {
                System.out.println("Search failed");
            }
        }
        System.out.println("historyToString = " + history.historyToString());
        currentPosition.whitesMove = vari == VALKOINEN;
        return ab.calculateNextPosition(currentPosition, depth, vari == VALKOINEN);
    }

    private Siirto getNextSiirto(Position currentPosition, String search) {
        currentPosition.whitesMove = this.vari == VALKOINEN;
        MyRecord<Position> nextPositions = generator.getNextPositions(currentPosition);
        for (int i = 0; i < nextPositions.size(); i++) {
            Position nextPosition = nextPositions.get(i);
            Siirto siirto = new Siirto(currentPosition, nextPosition, nappulat);
            System.out.println("siirto[" + i + "] = " + siirto.toChessNotation());
            if (siirto.toChessNotation().equals(search)) {
                return siirto;
            }
        }
        return null;
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

    public void forceOutFromLoop() {
        this.looping = true;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
