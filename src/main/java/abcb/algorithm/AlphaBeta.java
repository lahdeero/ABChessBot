package abcb.algorithm;

import abcb.heurestic.Evaluator;
import abcb.simulate.Generator;
import abcb.simulate.Position;
import abcb.util.MoveConverter;

public class AlphaBeta {

    private Evaluator evaluator;
    private Generator generator;
    private MoveConverter moveConverter;
    private Position bestMaxMove;
    private Position bestMinMove;
    private int initialDepth;
    private int bestMin;
    private int bestMax;

    public AlphaBeta() {
        evaluator = new Evaluator();
        generator = new Generator();
        moveConverter = new MoveConverter();
    }

    private void setupInitialValues(int initialDepth) {
        this.initialDepth = initialDepth;
        this.bestMax = Integer.MIN_VALUE;
        this.bestMin = Integer.MAX_VALUE;
        bestMaxMove = null;
        bestMinMove = null;
    }

    /**
     * Calculates next move using alpha-beta pruning and returns chess notation.
     *
     * @param currentPosition
     * @param depth
     * @param maxPlayer
     * @return
     */
    public String calculateNextMove(Position currentPosition, int depth, boolean maxPlayer) {
        setupInitialValues(depth);
        alphabeta(currentPosition, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, maxPlayer);
        if (maxPlayer) {
            return moveConverter.positionsToChessNotation(currentPosition, bestMaxMove);
        } else {
            return moveConverter.positionsToChessNotation(currentPosition, bestMinMove);
        }
    }

    public Position calculateNextPosition(Position currentPosition, int depth, boolean maxPlayer) {
        setupInitialValues(depth);
        alphabeta(currentPosition, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, maxPlayer);
        return maxPlayer ? bestMaxMove : bestMinMove;
    }

    private int alphabeta(Position currentPosition, int depth, int α, int β, boolean maxPlayer) {
        if (depth == 0) {
//            int value = evaluator.evaluate(currentPosition);
//            System.out.println("value = " + value);
//            currentPosition.print();
//            System.out.println("");
            return evaluator.evaluate(currentPosition);
        }

        if (maxPlayer) {
            int value = Integer.MIN_VALUE;
            for (Position nextPosition : generator.getNextPositions(currentPosition)) {
                nextPosition.whitesMove = !currentPosition.whitesMove;
                value = Math.max(value, alphabeta(nextPosition, depth - 1, α, β, false));
                α = Math.max(α, value);
                if (depth == initialDepth && α > bestMax) {
                    bestMax = α;
                    bestMaxMove = nextPosition;
                }
                if (α >= β) {
                    break;
                }
            }
            return value;
        } else {
            int value = Integer.MAX_VALUE;
            for (Position nextPosition : generator.getNextPositions(currentPosition)) {
                nextPosition.whitesMove = !currentPosition.whitesMove;
                value = Math.min(value, alphabeta(nextPosition, depth - 1, α, β, true));
                β = Math.min(β, value);
                if (depth == initialDepth && β < bestMin) {
                    bestMin = β;
                    bestMinMove = nextPosition;
                }
                if (α >= β) {
                    break;
                }
            }
            return value;
        }
    }
}
