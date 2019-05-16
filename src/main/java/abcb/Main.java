package abcb;

import java.util.List;
import abcb.simulate.Position;
import abcb.simulate.Generator;


public class Main {
    public static void main(String[] args) {
        Generator gen = new Generator();
        Position start = gen.createStartingPosition();
//        System.out.println(start.whitesMove);
        List<Position> nextPos = gen.getNextPositions(start);
//        System.out.println(nextPos.size());
        List<Position> blackOpenings = gen.getNextPositions(nextPos.get(0));
        System.out.println(blackOpenings.size());
        
        int i = 1;
        for (Position p : blackOpenings) {
            System.out.println("i: " + i++);
            p.print();
            System.out.println("");
        }
        
//        nextPos.get(1).print();
//        System.out.println(nextPos.get(1).whitesMove);
//        nextPos.get(2).print();
//        System.out.println(nextPos.get(2).whitesMove);
    }

}
