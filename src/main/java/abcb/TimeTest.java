package abcb;

import abcb.algorithm.AlphaBeta;
import abcb.algorithm.Minimax;
import abcb.simulate.Generator;
import abcb.simulate.Position;
import abcb.util.MoveConverter;

public class TimeTest {

    MoveConverter moveConverter;
    public long[] times = new long[3];
    public long[] values = new long[3];
    public String[] moves = new String[3];
    public boolean maxPlayer;
    private Minimax minimax;
    private Generator generator;
    private AlphaBeta ab;

    public TimeTest() {
        this.moveConverter = new MoveConverter();
        generator = new Generator();
        minimax = new Minimax(generator);

    }

    public double run(int depth) {
        maxPlayer = true;
        Position position = generator.generateRandomPosition(16);
        position.whitesMove = maxPlayer;
//        position.print();

        long start = System.nanoTime();
        Position abpos = ab.calculateNextPosition(position, depth, maxPlayer);
        long end = System.nanoTime();
//        abpos.print();

        return (double) (end - start) / 1000000000;
    }

    public int runLeafs(int depth) {
        AlphaBeta ab = new AlphaBeta();
        maxPlayer = true;
        Position position = generator.generateRandomPosition(16);
        position.whitesMove = maxPlayer;
//        position.print();
        Position abpos = ab.calculateNextPosition(position, depth, maxPlayer);
        return ab.leafs;
    }

    private void printMoves() {
        System.out.println("----------");
        System.out.println("moves:");
        for (int i = 0; i < 3; i++) {
            System.out.println(moves[i]);
        }
        System.out.println("------------");
    }

    private void printValues() {
        System.out.println("----------");
        System.out.println("values:");
        for (int i = 0; i < 3; i++) {
            System.out.println(values[i]);
        }
        System.out.println("------------");
    }

    private void printTimes() {
        System.out.println("----------");
        System.out.println("times:");
        for (int i = 0; i < 3; i++) {
            System.out.println(times[i]);
        }
        System.out.println("------------");
    }

    private void match(Position mxpos, Position abpos) {
        boolean match = true;
        for (int y = 0; y < Position.boardRows; y++) {
            for (int x = 0; x < Position.boardCols; x++) {
                if (mxpos.board[y][x] != abpos.board[y][x]) {
                    match = false;
                }
            }
        }
        System.out.println("match: " + match);
    }

    private void printPositions(Position mxpos, Position abpos, Position oldpos) {
        mxpos.print();
        System.out.println("------");
        abpos.print();
        System.out.println("------");
        oldpos.print();
        System.out.println("------");
    }
}
