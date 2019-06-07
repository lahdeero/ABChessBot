package abcb;

import abcb.argsHandler.CliUi;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
//        Openings o = new Openings();
        
        CliUi au = new CliUi();
        au.run(args);

//        Generator gen = new Generator();
//        Position p = gen.createStartingPosition();
//        p.toString();
//        p.board[6][4] = 0;
//        p.board[4][4] = whitePawn;
//        p.whitesMove = false;
//        System.out.println(p.toString());
//        System.out.println(p.board[4][6]);
    }
}
