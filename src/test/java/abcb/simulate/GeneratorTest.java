package abcb.simulate;

import abcb.simulate.Generator;
import abcb.simulate.Position;
import static abcb.simulate.Position.blackKing;
import static abcb.simulate.Position.blackKnight;
import static abcb.simulate.Position.blackQueen;
import static abcb.simulate.Position.blackRook;
import static abcb.simulate.Position.whiteBishop;
import static abcb.simulate.Position.whiteKing;
import static abcb.simulate.Position.whiteKnight;
import static abcb.simulate.Position.whiteRook;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GeneratorTest {

    private Generator generator;
    private Position startingPosition;

    public GeneratorTest() {
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
    public void whiteHas20StartingMoves() {
        List<Position> nextMoves = generator.getNextPositions(startingPosition);
        assertEquals(20, nextMoves.size());
    }

    @Test
    public void blackHas20StartingMoves() {
        Position blacksFirst = generator.getNextPositions(startingPosition).get(0);
        List<Position> blackOpenings = generator.getNextPositions(blacksFirst);
        assertEquals(20, blackOpenings.size());
    }

    @Test
    public void rookHas14MovesMax() {
        Position p = new Position(new int[8][8], false);
        p.board[3][3] = blackRook;
        List<Position> lonelyRook = generator.getNextPositions(p);
        assertEquals(14, lonelyRook.size());
    }

    @Test
    public void knightHas8MovesMax() {
        Position p = new Position(new int[8][8], true);
        p.board[3][3] = whiteKnight;
        List<Position> lonelyKnight = generator.getNextPositions(p);
        assertEquals(8, lonelyKnight.size());
    }

    @Test
    public void bishopHas13MovesMax() {
        Position p = new Position(new int[8][8], true);
        p.board[3][3] = whiteBishop;
        List<Position> lonelyBishop = generator.getNextPositions(p);
        assertEquals(13, lonelyBishop.size());
    }

    @Test
    public void queenHas27MovesMax() {
        Position p = new Position(new int[8][8], false);
        p.board[3][3] = blackQueen;
        List<Position> lonelyQueen = generator.getNextPositions(p);
        assertEquals(27, lonelyQueen.size());
    }

    @Test
    public void kingHas8MovesMax() {
        Position p = new Position(new int[8][8], false);
        p.board[3][3] = blackKing;
        List<Position> lonelyKing = generator.getNextPositions(p);
        assertEquals(8, lonelyKing.size());
    }

    @Test
    public void corneredKingHas3Moves() {
        Position p = new Position(new int[8][8], true);
        p.board[0][0] = whiteKing;
        List<Position> lonelyKing = generator.getNextPositions(p);
        assertEquals(3, lonelyKing.size());
    }

    @Test
    public void rookCanMove7plusEatOne() {
        Position p = new Position(new int[8][8], true);
        p.board[7][0] = whiteRook;
        p.board[7][1] = blackKnight;
        List<Position> hungryRook = generator.getNextPositions(p);
        assertEquals(7 + 1, hungryRook.size());
    }

    @Test
    public void bishopCanMove3PlusEatOne() {
        Position p = new Position(new int[8][8], true);
        p.board[6][1] = whiteBishop;
        p.board[5][2] = blackKnight;
        List<Position> hungryBishop = generator.getNextPositions(p);
        assertEquals(3 + 1, hungryBishop.size());
    }

    @Test
    public void queenCanMove3OrEat3() {
        Position p = new Position(new int[8][8], false);
        p.board[0][7] = blackQueen;
        p.board[2][7] = whiteKnight;
        p.board[2][6] = whiteKnight;
        p.board[2][5] = whiteKnight;
        p.board[1][5] = whiteKnight;
        p.board[0][5] = whiteKnight;
        List<Position> hungryQueen = generator.getNextPositions(p);
        assertEquals(3 + 3, hungryQueen.size());
    }
}
