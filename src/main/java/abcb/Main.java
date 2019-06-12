package abcb;

import abcb.argsHandler.CliUi;
import abcb.simulate.Generator;
import abcb.simulate.Position;
import abcb.util.History;
import abcb.util.MoveConverter;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class Main {

    public static void main(String[] args) throws IOException {
//        Openings o = new Openings();
        CliUi au = new CliUi();
        au.run(args);

//        Generator generator = new Generator();
//        History history = new History();
//        MoveConverter moveConverter = new MoveConverter();
//        Position startingPosition = generator.createStartingPosition();
//        Position p = moveConverter.move(startingPosition, 4, 6, 4, 4);
//        history.addToHistory(startingPosition, p);
//        Position p2 = moveConverter.move(p, 4, 1, 4, 3);
//        history.addToHistory(p, p2);
//        Position p3 = moveConverter.move(p2, 6, 7, 5, 5);
//        history.addToHistory(p2, p3);
//        Position p4 = moveConverter.move(p3, 3, 1, 3, 2);
//        history.addToHistory(p3,p4);
//        Position p5 = moveConverter.move(p4, 3, 6, 3, 4);
//        history.addToHistory(p4, p5);
//        Position p6 = moveConverter.move(p5, 4, 3, 3, 4);
//        history.addToHistory(p5, p6);
//        System.out.println(history.historyToString());
        
//    Openings openings = new Openings();
//    openings.search("1. Nh3 d5 2. g3 e5 3. f4 Bxh3 4. Bxh3");
//        System.out.println(openings.getNextMove());
    }
}
