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

    public AlphaBeta() {
        evaluator = new Evaluator();
        generator = new Generator();
        moveConverter = new MoveConverter();
    }

//    private void setupInitialValues(int initialDepth) {
//        this.initialDepth = initialDepth;
//        this.bestMax = Integer.MIN_VALUE;
//        this.bestMin = Integer.MAX_VALUE;
//        bestMaxMove = null;
//        bestMinMove = null;
//    }
    /**
     * Calculates next move using alpha-beta pruning and returns chess notation.
     *
     * @param currentPosition
     * @param depth
     * @param maxPlayer
     * @return
     */
    public String calculateNextMove(Position currentPosition, int depth, boolean maxPlayer) {
        Position bestMove = null;
        int bestValue = maxPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (Position position : generator.getNextPositions(currentPosition)) {
            int value = alphabeta(position, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, !maxPlayer);
            if (maxPlayer && value > bestValue) {
                bestValue = value;
                bestMove = position;
            } else if (!maxPlayer && value < bestValue) {
                bestValue = value;
                bestMove = position;
            }
        }
        return moveConverter.positionsToChessNotation(currentPosition, bestMove);
    }

    /**
     * Similar to calculateNextMove, but returns Position.
     * @param currentPosition
     * @param depth
     * @param maxPlayer
     * @return 
     */
    public Position calculateNextPosition(Position currentPosition, int depth, boolean maxPlayer) {
        Position bestMove = null;
        int bestValue = maxPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (Position position : generator.getNextPositions(currentPosition)) {
            int value = alphabeta(position, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, !maxPlayer);
            if (maxPlayer && value > bestValue) {
                bestValue = value;
                bestMove = position;
            } else if (!maxPlayer && value < bestValue) {
                bestValue = value;
                bestMove = position;
            }
        }
        return bestMove;
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
                if (α >= β) {
                    break;
                }
            }
            return value;
        }
    }
}
