package abcb;

import abcb.algorithm.AlphaBeta;
import abcb.algorithm.AbOld;
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

    public TimeTest() {
        this.moveConverter = new MoveConverter();
    }

    public void run() {
        Generator generator = new Generator();
        Minimax minimax = new Minimax(generator);
        AlphaBeta ab = new AlphaBeta();
        AbOld old = new AbOld();

        maxPlayer = true;
        int depth = 4;

        Position position = generator.generateRandomPosition(16);
        position.whitesMove = maxPlayer;
        position.print();

        long start;
        long end;

        start = System.nanoTime();
        Position mxpos = minimax.calculateNextPosition(position, depth, maxPlayer);
        end = System.nanoTime();
        times[0] = end - start;
        values[0] = minimax.value;
        moves[0] = moveConverter.positionsToChessNotation(position, mxpos);
        System.out.println("minimax valmis");

        start = System.nanoTime();
        Position abpos = ab.calculateNextPosition(position, depth, maxPlayer);
        end = System.nanoTime();
        times[1] = end - start;
        values[1] = ab.value;
        moves[1] = moveConverter.positionsToChessNotation(position, abpos);
        System.out.println("ab valmis");

        start = System.nanoTime();
        Position oldpos = old.calculateNextPosition(position, depth, maxPlayer);
        end = System.nanoTime();
        times[2] = end - start;
        values[2] = old.value;
        moves[2] = moveConverter.positionsToChessNotation(position, oldpos);
        System.out.println("vanha valmis");

        printPositions(mxpos, abpos, oldpos);
        printMoves();
        printValues();
        printTimes();

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
