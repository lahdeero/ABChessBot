
import simulate.Position;
import java.util.List;
import simulate.Generator;

public class Main {
    public static void main(String[] args) {
        Generator gen = new Generator();
        Position pos = new Position();
        pos.setupStartingPosition();
        List<Position> nextPos = gen.getNextPositions();
        System.out.println(nextPos.size());
        
        nextPos.get(1).print();
        nextPos.get(2).print();
    }

}
