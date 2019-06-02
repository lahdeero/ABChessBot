package abcb.algorithm;

import abcb.datastructure.MyRecord;
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

    public Position calculateNextPosition(Position currentPosition, int depth, boolean maxPlayer) {
        Position bestMove = null;
        int bestValue = maxPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        MyRecord myRecord = generator.getNextPositions(currentPosition);
        for (int i = 0; i < myRecord.size(); i++) {
            Position position = (Position) myRecord.get(i);
            int value = minimax(position, depth - 1, !maxPlayer);
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

    public int minimax(Position currentPosition, int depth, boolean maxPlayer) {
        if (depth == 0) {
            return evaluator.evaluate(currentPosition);
        }

        if (maxPlayer) {
            int value = Integer.MIN_VALUE;
            MyRecord myRecord = generator.getNextPositions(currentPosition);
            for (int i = 0; i < myRecord.size(); i++) {
                Position p = (Position) myRecord.get(i);
                value = Math.max(value, minimax(p, depth - 1, false));
            }
            return value;
        }
        int value = Integer.MAX_VALUE;
        MyRecord myRecord = generator.getNextPositions(currentPosition);
        for (int i = 0; i < myRecord.size(); i++) {
            Position p = (Position) myRecord.get(i);
            value = Math.min(value, minimax(p, depth - 1, true));

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
