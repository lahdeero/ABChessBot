package simulate;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GeneratorTest {
    
    public Generator generator;
    
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
        generator = new Generator();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void whiteHas20StartingMoves() {
        List<Position> nextMoves = generator.getNextPositions();
        
        assertEquals(nextMoves.size(), 20);
    }


}