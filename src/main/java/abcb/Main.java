package abcb;

import abcb.InputReader.MyFileReader;
import abcb.algorithm.AlphaBeta;
import abcb.algorithm.Minimax;
import abcb.simulate.Generator;
import abcb.simulate.Position;
import static abcb.simulate.Position.blackKnight;
import static abcb.simulate.Position.whiteBishop;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        AlphaBeta ab = new AlphaBeta();
        Position pos = new Position();
        pos.board[4][5] = blackKnight;
        pos.board[4][6] = blackKnight;
        pos.board[4][7] = blackKnight;
        pos.board[4][1] = blackKnight;
        pos.board[2][4] = whiteBishop;
        pos.board[3][4] = whiteBishop;
        pos.board[4][4] = whiteBishop;
        pos.board[5][4] = whiteBishop;
        Minimax mx = new Minimax(new Generator());
        mx.minimax(pos, 3, true);
        Position mxNextPos = mx.calculateNextPosition(pos, 3, true);
        Position abNextPos = ab.calculateNextPosition(pos, 3, true);
        if (mxNextPos == null) {
            System.out.println("mxNull");
        }
        if (abNextPos == null) {
            System.out.println(ab.calculateNextMove(pos, 3, true));
            System.out.println("ABnull");
        }
        boolean same = true;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (mxNextPos.board[y][x] != abNextPos.board[y][x]) {
                    same = false;
                    break;
                }
            }
        }
    }
}
