package abcb.heurestic;

import abcb.InputReader.MyFileReader;
import abcb.datastructure.MyRecord;
import abcb.util.Randomizer;

/**
 * Its hard to calculate moves at start of the chess game. This class provides
 * usage of opening books to make games more interesting.
 *
 * @author Eero
 */
public class Openings {

    private Randomizer randomizer;
    private MyRecord<String> openingNames;
    private MyRecord<String> openings;
    private MyRecord<String> nextMoves;
    private Integer indexSelected;

    public Openings() {
        this.randomizer = new Randomizer();
        MyFileReader mfr = new MyFileReader();
        this.openings = mfr.readOpeningsFromFile("openings.txt");
        this.openingNames = mfr.getOpeningNames();
    }

    /**
     * Search if there is opening available.
     *
     * @param history
     * @return
     */
    public boolean search(String history) {
        boolean ret = false;
        this.nextMoves = new MyRecord<String>();
        for (int i = 0; i < openings.size(); i++) {
            String opening = openings.get(i);
            if (myContains(opening, history) && opening.length() > history.length() + 2) {
                this.nextMoves.add(resolveNextMove(history, opening));
                ret = true;
            }
        }
        return ret;
    }

    private boolean myContains(String str2, String search2) {
        int k = 0;
        int f = 0;
        String str = str2.toLowerCase();
        String search = search2.toLowerCase();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == search.charAt(k)) {
                k += 1;
                f += 1;
                if (f == search.length()) {
                    return true;
                }
            } else {
                k = 0;
                f = 0;
            }
        }
        return false;
    }

    private String resolveNextMove(String history, String opening) {
        String move = "";
        System.out.println("history = " + history);
        System.out.println("opening = " + opening);
        int i = history.length();
        while (!Character.isLetter(opening.charAt(i)) && opening.length() > i) {
            i += 1;
        }
        while (Character.isLetter(opening.charAt(i)) && opening.length() > i) {
            move += opening.charAt(i++);
        }
        move += opening.charAt(i);
        return move;
    }

    /**
     * Returns next move as Algebraic notation. NOTE THAT: search-method should
     * return true before calling this!
     *
     * @return
     */
    public String getNextMove() {
        int randomIndex = randomizer.generateRandomInt(this.nextMoves.size() - 1);
        System.out.println("random = " + randomIndex + ", max = " + (nextMoves.size() - 1));
        this.indexSelected = randomIndex;
        return this.nextMoves.get(randomIndex);
    }

    public String getOpeningName() {
        if (openingNames.size() > indexSelected) {
            return openingNames.get(indexSelected);
        }
        System.out.println("index not inside myRecord size!");
        return "Unknown opening";
    }
}
