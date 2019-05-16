package test.java.abcb.simulate;

import abcb.simulate.Generator;
import abcb.simulate.Position;
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
        System.out.println("nextMoves.size() = " + nextMoves.size());
        assertEquals(nextMoves.size(), 20);
    }

    @Test
    public void blackHas20StartingMoves() {
        Position blacksFirst = generator.getNextPositions(startingPosition).get(0);
        List<Position> blackOpenings = generator.getNextPositions(blacksFirst);
        assertEquals(blackOpenings.size(), 20);
    }

}
