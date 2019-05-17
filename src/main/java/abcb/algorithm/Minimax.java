package abcb.algorithm;

import abcb.heurestic.Evaluator;
import abcb.simulate.Generator;
import abcb.simulate.Position;

public class Minimax {
    private Generator generator;
    private Evaluator evaluator;
    private Position bestMaxPosition;
    private Position bestMinPosition;
    
    public Minimax(Generator generator) {
        this.generator = generator;
        this.evaluator = new Evaluator();
    }
    
    public int calc(Position currentPosition, int depth, boolean maxPlayer) {
        if (depth == 0) {
            return evaluator.evaluate(currentPosition);
        } else if (maxPlayer) {
            int value = Integer.MIN_VALUE;
            int max = Integer.MIN_VALUE;
            for (Position p : generator.getNextPositions(currentPosition)) {
                value = Math.max(value, calc(p, depth-1, false));
                if (value > max) {
                    bestMaxPosition = p;
                    max = value;
                }
            }
            return value;
        } 
        int value = Integer.MAX_VALUE;
        int min = Integer.MAX_VALUE;
        for (Position p : generator.getNextPositions(currentPosition)) {
            value = Math.min(value, calc(p, depth-1, true));
            if (value < min) {
                bestMinPosition = p;
                min = value;
            }
        }
        return value;
    }
    public Position getBestMaxPosition() {
        return this.bestMaxPosition;
    }
    public Position getBestMinPosition() {
        return this.bestMinPosition;
    }
}
