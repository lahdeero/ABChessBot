package abcb.algorithm;

import abcb.datastructure.MyRecord;
import abcb.heurestic.Evaluator;
import abcb.simulate.Generator;
import abcb.simulate.Position;
import abcb.util.MoveConverter;

public class AbOld {

    private Evaluator evaluator;
    private Generator generator;
    private MoveConverter moveConverter;
    public int value;

    public AbOld() {
        evaluator = new Evaluator();
        generator = new Generator();
        moveConverter = new MoveConverter();
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
        return moveConverter.positionsToChessNotation(currentPosition, calculateNextPosition(currentPosition, depth, maxPlayer));
    }

    /**
     * Calculates next Position using alpha-beta pruning and returns suggested
     * Position.
     *
     * @param currentPosition
     * @param depth
     * @param maxPlayer
     * @return
     */
    public Position calculateNextPosition(Position currentPosition, int depth, boolean maxPlayer) {
        currentPosition.whitesMove = maxPlayer;
        MyRecord<Position> nextPositions = generator.getNextPositions(currentPosition);
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        for (int i = 0; i < nextPositions.size(); i++) {
            int value = alphabeta(nextPositions.get(i), depth - 1, alpha, beta, !maxPlayer, maxPlayer);
            nextPositions.get(i).value = value;
        }
        quicksort(nextPositions, 0, nextPositions.size() - 1);
        if (maxPlayer) {
            value = nextPositions.get(nextPositions.size() -1).value;
            return nextPositions.get(nextPositions.size() - 1);
        }
        value = nextPositions.get(0).value;
        return nextPositions.get(0);
    }

    private int alphabeta(Position currentPosition, int depth, int alpha, int beta, boolean maxPlayer, boolean initialMaxPlayer) {
        if (depth == 0) {
            if (!currentPosition.kingLives(initialMaxPlayer)) {
                return initialMaxPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }
            return evaluator.evaluate(currentPosition);
        }

        if (maxPlayer) {
            int value = Integer.MIN_VALUE;
            MyRecord<Position> nextPositions = generator.getNextPositions(currentPosition);
            for (int i = 0; i < nextPositions.size(); i++) {
                int search = alphabeta(nextPositions.get(i), depth - 1, alpha, beta, false, initialMaxPlayer);
                value = value > search ? value : search;
                alpha = alpha > value ? alpha : value;
                if (alpha >= beta) {
                    break;
                }
            }
            return value;
        } else {
            int value = Integer.MAX_VALUE;
            MyRecord<Position> nextPositions = generator.getNextPositions(currentPosition);
            for (int i = 0; i < nextPositions.size(); i++) {
                int search = alphabeta(nextPositions.get(i), depth - 1, alpha, beta, true, initialMaxPlayer);
                value = value < search ? value : search;
                beta = beta < value ? beta : value;
                if (alpha >= beta) {
                    break;
                }
            }
            return value;
        }
    }

    private void quicksort(MyRecord<Position> A, int low, int high) {
        if (low < high) {
            int p = partition(A, low, high);
            quicksort(A, low, p - 1);
            quicksort(A, p + 1, high);
        }
    }

    private int partition(MyRecord<Position> A, int low, int high) {
        int pivot = A.get(high).value;
        int i = low;
        for (int j = low; j < high; j++) {
            if (A.get(j).value < pivot) {
                A.swap(i, j);
                i += 1;
            }
        }
        A.swap(i, high);
        return i;
    }
}
