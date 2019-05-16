package simulate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static simulate.Position.*;
import static simulate.Position.*;

public class Generator {
    Position currentPosition;
    
    public Generator() {
        this.currentPosition = new Position();
        this.currentPosition.setupStartingPosition();
    }

    public List<Position> getNextPositions() {
        List<Position> nextPositions = Collections.synchronizedList(new ArrayList());
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                int piece = currentPosition.board[y][x];
                if (piece == 0) {
                    continue;
                }
                if (!currentPosition.whitesMove) {
                    if (piece == blackPawn) {
                        currentPosition.blackPawnMoves(x, y, nextPositions);
                    } else if (piece == blackKnight) {
                        currentPosition.blackKnightMoves(x, y, nextPositions);
                    }
                } else {
                    if (piece == whitePawn) {
                        currentPosition.whitePawnMoves(x, y, nextPositions);
                    } else if (piece == whiteKnight) {
                        currentPosition.whiteKnightMoves(x, y, nextPositions);
                    }
                }
            }
        }
        return nextPositions;
    }
}
