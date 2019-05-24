package abcb.algorithm;

import abcb.InputReader.MyFileReader;
import abcb.simulate.Generator;
import abcb.simulate.Position;
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

}
