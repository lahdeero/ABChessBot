package abcb.simulate;

import static abcb.simulate.Position.whitePawn;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PositionTest {

    private Generator generator;
    private Position startingPosition;

    public PositionTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.generator = new Generator();
        this.startingPosition = generator.createStartingPosition();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void toStringMatchesStartingPosition() {
        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w", startingPosition.toString());
    }

    @Test
    public void toStringMatchesAfterOpeningMove() {
        startingPosition.board[6][4] = 0;
        startingPosition.board[4][4] = whitePawn;
        startingPosition.whitesMove = false;
        assertEquals("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b", startingPosition.toString());
    }
}
