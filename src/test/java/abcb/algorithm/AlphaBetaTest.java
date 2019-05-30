package abcb.algorithm;

import abcb.InputReader.MyFileReader;
import abcb.simulate.Generator;
import abcb.simulate.Position;
import static abcb.simulate.Position.blackKnight;
import static abcb.simulate.Position.whiteBishop;
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
    private String fileDirectory = "./src/test/resources/";

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
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void miniMaxiReturnsSameAsAB() {
        Position pos = new Position();
        pos.board[4][5] = blackKnight;
        pos.board[4][6] = blackKnight;
        pos.board[4][7] = blackKnight;
        pos.board[4][1] = blackKnight;
        pos.board[2][4] = whiteBishop;
        pos.board[3][4] = whiteBishop;
        pos.board[4][4] = whiteBishop;
        pos.board[5][4] = whiteBishop;
        Minimax mx = new Minimax(new Generator());
        mx.minimax(pos, 5, true);
        Position mxNextPos = mx.calculateNextPosition(pos, 5, true);
        Position abNextPos = ab.calculateNextPosition(pos, 5, true);
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

}
