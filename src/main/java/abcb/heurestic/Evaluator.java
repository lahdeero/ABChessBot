package abcb.heurestic;

import abcb.simulate.Position;
import static abcb.simulate.Position.*;

public class Evaluator {
    public int evaluate(Position p) {
        int value = 0;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (p.board[y][x] == 0) continue;
                int piece = p.board[y][x];
                if (piece == whitePawn) {
                    value += 1;
                } else if (piece == whiteRook) {
                    value += 5;
                } else if (piece == whiteKnight) {
                    value += 3;
                } else if (piece == whiteBishop) {
                    value += 3;
                } else if (piece == whiteQueen) {
                    value += 9;
                } else if (piece == whiteKing) {
                    value += 1000;
                } else if (piece == blackPawn) {
                    value -= 1;
                } else if (piece == blackRook) {
                    value -= 5;
                } else if (piece == blackKnight) {
                    value -= 3;
                } else if (piece == blackBishop) {
                    value -= 3;
                } else if (piece == blackQueen) {
                    value -= 9;
                } else if (piece == blackKing) {
                    value -= 1000;
                }
            }
        }
        return value;
    }
}
