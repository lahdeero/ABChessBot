package abcb.util;

import abcb.datastructure.MyRecord;
import abcb.simulate.Position;

public class History {
    private MyRecord history;
    private MoveConverter moveConverter;

    public History() {
        this.history = new MyRecord<String>();
        this.moveConverter = new MoveConverter();
    }

    public void addToHistory(Position current, Position next) {
        String s = moveConverter.positionsToChessNotation(current, next);
        System.out.println("s = " + s);
        history.add(s);
    }

    public String historyToString() {
        int k = 1;
        String ret = "";
        for (int i = 0; i < history.size(); i += 2) {
            if (k > 1) {
                ret += " ";
            }
            if (i + 1 < history.size()) {
                ret += "" + k + ". " + history.get(i) + " " + history.get(i + 1);
            } else {
                ret += k + ". " + history.get(i);
            }
            k += 1;
        }
        return ret;
    }
    
    public int getSize() {
        return this.history.size();
    }
}
