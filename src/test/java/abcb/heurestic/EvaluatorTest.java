package abcb.heurestic;

import abcb.simulate.Generator;
import abcb.simulate.Position;
import static abcb.simulate.Position.*;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EvaluatorTest {

    private Evaluator evaluator;
    private Generator generator;

    public EvaluatorTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        generator = new Generator();
        evaluator = new Evaluator();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void startingPositionHasEqualValue() {
        Position pos = generator.createStartingPosition();
        assertEquals(0, evaluator.evaluate(pos));
    }

    @Test
    public void whiteKingSoloValue() {
        Position pos = new Position();
        pos.board[5][5] = whiteKing;
        assertEquals(evaluator.kingValue, evaluator.evaluate(pos));
    }

    @Test
    public void blackKingAndQueenValue() {
        Position pos = new Position();
        pos.board[5][5] = blackKing;
        pos.board[5][4] = blackQueen;
        assertEquals(-evaluator.kingValue + -evaluator.queenValue, evaluator.evaluate(pos));
    }

    @Test
    public void blackChessmate() {
        Position pos = new Position();
        pos.board[0][0] = blackKing; 
        pos.board[1][0] = blackPawn;
        pos.board[1][1] = blackPawn;
        pos.board[7][7] = blackRook;
        
        pos.board[6][6] = whitePawn;
        pos.board[6][7] = whitePawn;

        int expected = -1*(evaluator.kingValue+2*evaluator.pawnValue+evaluator.rookValue);
        expected += 2*evaluator.pawnValue;
        assertEquals(expected, evaluator.evaluate(pos));
    }
}
