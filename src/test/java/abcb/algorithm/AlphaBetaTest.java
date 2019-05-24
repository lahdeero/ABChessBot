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
    public void whiteCheckmates() throws IOException {
        Position pos = mfr.fileToPosition();
        assertEquals("Rh8", ab.calculateNextMove(pos, 1, true));
    }

}
