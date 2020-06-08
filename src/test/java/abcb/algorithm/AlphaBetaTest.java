package abcb.algorithm;

import abcb.InputReader.MyFileReader;
import abcb.simulate.Generator;
import abcb.simulate.Position;
import static abcb.simulate.Position.blackKing;
import static abcb.simulate.Position.blackKnight;
import static abcb.simulate.Position.blackPawn;
import static abcb.simulate.Position.whiteBishop;
import static abcb.simulate.Position.whiteKing;
import static abcb.simulate.Position.whitePawn;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AlphaBetaTest {

    private MyFileReader mfr;
    private AlphaBeta ab;
    private Generator generator;
    private String fileDirectory = "./src/test/resources/";
    private Minimax minimax;

    public AlphaBetaTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        mfr = new MyFileReader();
        ab = new AlphaBeta();
        minimax = new Minimax(generator);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void kingShouldHide() throws IOException {
        Position pos = mfr.fileToPosition(fileDirectory + "kingShouldHide.txt");
        assertEquals("Kd8", ab.calculateNextMove(pos, 3, false));
        assertEquals("Kd8", ab.calculateNextMove(pos, 4, false));
        assertEquals("Kd8", ab.calculateNextMove(pos, 5, false));
    }

    @Test
    public void miniMaxiReturnsSameAsAB() {
        Position pos = new Position();
        pos.board[0][0] = blackKing;
        pos.board[1][0] = blackPawn;
        pos.board[1][1] = blackPawn;
        pos.board[4][5] = blackKnight;
        pos.board[4][6] = blackKnight;
        pos.board[4][7] = blackKnight;
        pos.board[4][1] = blackKnight;
        pos.board[2][4] = whiteBishop;
        pos.board[3][4] = whiteBishop;
        pos.board[4][4] = whiteBishop;
        pos.board[5][4] = whiteBishop;
        pos.board[6][0] = whitePawn;
        pos.board[6][1] = whitePawn;
        pos.board[7][0] = whiteKing;
        Minimax mx = new Minimax(new Generator());
        mx.minimax(pos, 3, true);
        Position mxNextPos = mx.calculateNextPosition(pos, 3, true);
        Position abNextPos = ab.calculateNextPosition(pos, 3, true);
        boolean same = true;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (mxNextPos.board[y][x] != abNextPos.board[y][x]) {
                    same = false;
                    break;
                }
            }
        }
        assertTrue(same);
    }

    @Test
    public void whiteCheckmatesNextMove() throws IOException {
        Position pos = mfr.fileToPosition(fileDirectory + "w1mCm.txt");
        assertEquals("Rh8", ab.calculateNextMove(pos, 3, true));
    }

    @Test
    public void blackCheckmatesNextMove() throws IOException {
        Position pos = mfr.fileToPosition(fileDirectory + "b1mCm.txt");
        pos.whitesMove = false;
        assertEquals("Re1", ab.calculateNextMove(pos, 3, false));
    }

    @Test
    public void blackPlsDontEatYourOwnKing() throws IOException {
        Position pos = mfr.fileToPosition(fileDirectory + "bpdeyok.txt");
        pos.whitesMove = false;
        String str = ab.calculateNextMove(pos, 1, false);
        assertFalse(str.equals("Ne8") || str.equals("Nh7"));
    }

    @Test
    public void plsNoNull() throws IOException {
        Position pos = mfr.fileToPosition(fileDirectory + "plsnonull.txt");
        Position p = ab.calculateNextPosition(pos, 5, true);
        assertNotNull(p);
    }

    @Test
    public void plsNoOutOfBounds() throws IOException {
        Position pos = mfr.fileToPosition(fileDirectory + "outofbounds.txt");
        Position p = ab.calculateNextPosition(pos, 5, true);
        assertNotNull(p);
    }

    // @Test
    // public void randomPositionReturnsSameAsMinMax() {
    // Position position = generator.createStartingPosition();
    // Position mxpos = minimax.calculateNextPosition(position, 5, true);
    // Position abpos = ab.calculateNextPosition(position, 5, true);
    //
    // boolean match = true;
    // for (int y = 0; y < Position.boardRows; y++) {
    // for (int x = 0; x < Position.boardCols; x++) {
    // if (mxpos.board[y][x] != abpos.board[y][x]) {
    // match = false;
    // }
    // }
    // }
    //// assertTrue("ab position differs from minimax", match);
    // assertTrue(true);
    // }
}
