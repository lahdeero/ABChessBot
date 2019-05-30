package abcb.datastructure;

import abcb.simulate.Generator;
import abcb.simulate.Position;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class MyRecordTest {

    Generator generator;
    MyRecord myRecord;

    @Before
    public void setUp() {
        generator = new Generator();
        myRecord = new MyRecord();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void canAdd() {
        Position pos = generator.createStartingPosition();
        myRecord.add(pos);
        assertEquals(1, myRecord.size());
    }

    @Test
    public void canGet() {
        Position pos = generator.createStartingPosition();
        Position pos2 = new Position();
        myRecord.add(pos);
        myRecord.add(pos2);
        Position getPos = (Position) myRecord.get(0);
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (pos.board[y][x] != getPos.board[y][x]) {
                    assertTrue("Getted Position differs from orginal", false);
                    break;
                }
            }
        }
        assertTrue(true);
    }
}
