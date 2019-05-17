package abcb;

import abcb.algorithm.Minimax;
import abcb.simulate.Position;
import abcb.simulate.Generator;


public class Main {
    public static void main(String[] args) {
        Generator generator = new Generator();
        Position p = generator.createStartingPosition();
        Minimax mx = new Minimax(generator);
        
        System.out.println(mx.calc(p, 5, true));

    }
}
