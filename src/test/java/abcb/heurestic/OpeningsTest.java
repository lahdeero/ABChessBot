package abcb.heurestic;

import abcb.simulate.Generator;
import abcb.simulate.Position;
import abcb.util.History;
import abcb.util.MoveConverter;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class OpeningsTest {

    Openings openings;
    Generator generator;
    MoveConverter moveConverter;
    History history;

    public OpeningsTest() {
    }

    @Before
    public void setUp() throws IOException {
        openings = new Openings();
        generator = new Generator();
        moveConverter = new MoveConverter();
        history = new History();
    }

    @Test
    public void shouldFindBasicOpening() {
        assertEquals(true, openings.search("b4"));
    }

    @Test
    public void shouldFindBasicOpening2() {
        openings.search("b4");
        assertTrue(openings.getNextMove().length() >= 2);
    }

    @Test
    public void shouldOpeningAfterPiecesEaten() {
        assertEquals(true, openings.search("1. Nh3 d5 2. g3 e5 3. f4 Bxh3 4. Bxh3"));
    }

    @Test
    public void shouldOpeningAfterPiecesEaten2() {
        openings.search("1. Nh3 d5 2. g3 e5 3. f4 Bxh3 4. Bxh3");
        assertTrue(openings.getNextMove().length() >= 2);
    }

    // C41 Philidor, Berger variatio
    @Test // 1. e4 e5 2. Nf3 d6 3. d4 exd4 4. Nxd4 Nf6 5. Nc3 Be7 6. Be2 O-O 7. O-O c5
    public void openingNotations() {
        generator = new Generator();
        Position startingPosition = generator.createStartingPosition();
        Position p = moveConverter.move(startingPosition, 4, 6, 4, 4);
        history.addToHistory(startingPosition, p);
        Position p2 = moveConverter.move(p, 4, 1, 4, 3);
        history.addToHistory(p, p2);
        Position p3 = moveConverter.move(p2, 6, 7, 5, 5);
        history.addToHistory(p2, p3);
        Position p4 = moveConverter.move(p3, 3, 1, 3, 2);
        history.addToHistory(p3,p4);
        Position p5 = moveConverter.move(p4, 3, 6, 3, 4);
        history.addToHistory(p4, p5);
        Position p6 = moveConverter.move(p5, 4, 3, 3, 4);
        history.addToHistory(p5, p6);
        assertEquals("1. e4 e5 2. Nf3 d6 3. d4 exd4", history.historyToString());
    }

}
