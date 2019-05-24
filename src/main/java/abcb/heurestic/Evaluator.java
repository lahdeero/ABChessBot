package abcb.heurestic;

import abcb.simulate.Position;
import static abcb.simulate.Position.*;

public class Evaluator {

    public final int pawnValue;
    public final int rookValue;
    public final int knightValue;
    public final int bishopValue;
    public final int queenValue;
    public final int kingValue;

    public Evaluator() {
        pawnValue = 1;
        rookValue = 5;
        knightValue = 3;
        bishopValue = 3;
        queenValue = 9;
        kingValue = 1000;
    }

    public int evaluate(Position p) {
        int value = 0;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (p.board[y][x] == 0) {
                    continue;
                }
                int piece = p.board[y][x];
                if (piece == whitePawn) {
                    value += pawnValue;
                } else if (piece == whiteRook) {
                    value += rookValue;
                } else if (piece == whiteKnight) {
                    value += knightValue;
                } else if (piece == whiteBishop) {
                    value += bishopValue;
                } else if (piece == whiteQueen) {
                    value += queenValue;
                } else if (piece == whiteKing) {
                    value += kingValue;
                } else if (piece == blackPawn) {
                    value -= pawnValue;
                } else if (piece == blackRook) {
                    value -= rookValue;
                } else if (piece == blackKnight) {
                    value -= knightValue;
                } else if (piece == blackBishop) {
                    value -= bishopValue;
                } else if (piece == blackQueen) {
                    value -= queenValue;
                } else if (piece == blackKing) {
                    value -= kingValue;
                }
            }
        }
        return value;
    }
}
