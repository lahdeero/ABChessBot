package abcb;

import abcb.algorithm.Search;
import abcb.simulate.Position;
import abcb.simulate.Generator;


public class Main {
    public static void main(String[] args) {
        Generator generator = new Generator();
        Position p = generator.createStartingPosition();
        Search mx = new Search(generator);
        
        mx.minimax(p, 5, true);
        Position max = mx.getBestMaxPosition();
        max.print();
        
        while (max.parent != null) {
            System.out.println("");
            max = max.parent;
            max.print();
        }

    }
}
