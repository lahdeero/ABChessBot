package abcb;

import abcb.InputReader.MyFileReader;
import abcb.algorithm.AlphaBeta;
import abcb.simulate.Position;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        MyFileReader mfr = new MyFileReader();
        Position pos = mfr.fileToPosition();
        System.out.println("");

        AlphaBeta ab = new AlphaBeta();
        System.out.println(ab.calculateNextMove(pos, 1, true));
    }
}
