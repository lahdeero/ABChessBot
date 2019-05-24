package abcb.simulate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static abcb.simulate.Position.*;

public class Generator {

    public Position currentPosition;

    /**
     * @return Classic chess starting position
     */
    public Position createStartingPosition() {
        int[][] board = new int[8][8];
        for (int i = 0; i < 8; i++) {
            board[1][i] = blackPawn;
            board[6][i] = whitePawn;
        }
        board[0][0] = blackRook;
        board[0][1] = blackKnight;
        board[0][2] = blackBishop;
        board[0][3] = blackQueen;
        board[0][4] = blackKing;
        board[0][5] = blackBishop;
        board[0][6] = blackKnight;
        board[0][7] = blackRook;
        board[7][0] = whiteRook;
        board[7][1] = whiteKnight;
        board[7][2] = whiteBishop;
        board[7][3] = whiteQueen;
        board[7][4] = whiteKing;
        board[7][5] = whiteBishop;
        board[7][6] = whiteKnight;
        board[7][7] = whiteRook;

        return new Position(board, true);
    }

    /**
     * Generates all possible positions after current position.
     *
     * @param currentPosition
     * @return List<Position>
     */
    public List<Position> getNextPositions(Position currentPosition) {
        this.currentPosition = currentPosition;
        List<Position> nextPositions = Collections.synchronizedList(new ArrayList());
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                int piece = currentPosition.board[y][x];
                if (piece == 0) {
                    continue;
                }
                if (!currentPosition.whitesMove) { // BLACK
                    if (piece == blackPawn) {
                        blackPawnMoves(x, y, nextPositions);
                    } else if (piece == blackRook) {
                        nextPositions.addAll(rookMoves(x, y, blackRook));
                    } else if (piece == blackKnight) {
                        nextPositions.addAll(knightMoves(x, y, blackKnight));
                    } else if (piece == blackBishop) {
                        nextPositions.addAll(bishopMoves(x, y, blackBishop));
                    } else if (piece == blackQueen) {
                        nextPositions.addAll(queenMoves(x, y, blackQueen));
                    } else if (piece == blackKing) {
                        nextPositions.addAll(kingMoves(x, y, blackKing));
                    }
                } else {  // WHITE
                    if (piece == whitePawn) {
                        whitePawnMoves(x, y, nextPositions);
                    } else if (piece == whiteRook) {
                        nextPositions.addAll(rookMoves(x, y, whiteRook));
                    } else if (piece == whiteKnight) {
                        nextPositions.addAll(knightMoves(x, y, whiteKnight));
                    } else if (piece == whiteBishop) {
                        nextPositions.addAll(bishopMoves(x, y, whiteBishop));
                    } else if (piece == whiteQueen) {
                        nextPositions.addAll(queenMoves(x, y, whiteQueen));
                    } else if (piece == whiteKing) {
                        nextPositions.addAll(kingMoves(x, y, whiteKing));
                    }
                }
            }
        }
        return nextPositions;
    }

    private List<Position> rookMoves(int x, int y, int piece) {
        List<Position> nextRooks = new ArrayList();
        boolean[] blocked = {false, false, false, false};
        for (int i = 1; i < 8; i++) {
            if (!blocked[0] && insideBoard(x - i, y) && !occupied(x - i, y, piece)) {
                if (eats(x - i, y, piece)) {
                    blocked[0] = true;
                }
                nextRooks.add(createPosition(x, y, x - i, y, piece));
            } else {
                blocked[0] = true;
            }
            if (!blocked[1] && insideBoard(x + i, y) && !occupied(x + i, y, piece)) {
                if (eats(x + i, y, piece)) {
                    blocked[1] = true;
                }
                nextRooks.add(createPosition(x, y, x + i, y, piece));
            } else {
                blocked[1] = true;
            }
            if (!blocked[2] && insideBoard(x, y - i) && !occupied(x, y - i, piece)) {
                if (eats(x, y - i, piece)) {
                    blocked[2] = true;
                }
                nextRooks.add(createPosition(x, y, x, y - i, piece));
            } else {
                blocked[2] = true;
            }
            if (!blocked[3] && insideBoard(x, y + i) && !occupied(x, y + i, piece)) {
                if (eats(x, y + i, piece)) {
                    blocked[3] = true;
                }
                nextRooks.add(createPosition(x, y, x, y + i, piece));
            } else {
                blocked[3] = true;
            }
        }
        return nextRooks;
    }

    private List<Position> bishopMoves(int x, int y, int piece) {
        List<Position> nextBishops = new ArrayList();
        int k = -1;
        int e = -1;
        for (int j = 0; j < 4; j++) {
            int i = 1;
            while (insideBoard(x + (k * i), y + (e * i)) && !occupied(x + (k * i), y + (e * i), piece)) {
                boolean eating = false;
                if (eats(x + k * i, y + e * i, piece)) {
                    eating = true;
                }
                Position p = new Position();
                p.clonePosition(currentPosition, x, y);
                p.board[y + e * i][x + k * i] = piece;
                nextBishops.add(p);
                if (eating) {
                    break;
                }
                i++;
            }
            if (k == -1 && e == -1) {
                e = 1;
            } else if (k == -1 && e == 1) {
                k = 1;
                e = -1;
            } else if (k == 1 && e == -1) {
                e = 1;
            }
        }
        return nextBishops;
    }

    private List<Position> queenMoves(int x, int y, int piece) {
        List<Position> queenMoves = new ArrayList();
        queenMoves.addAll(rookMoves(x, y, piece));
        queenMoves.addAll(bishopMoves(x, y, piece));
        return queenMoves;
    }

    private List<Position> kingMoves(int x, int y, int piece) {
        List<Position> nextKings = new ArrayList();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                if (insideBoard(x + j, y + i) && !occupied(x + j, y + i, piece)) {
                    Position p = new Position();
                    p.clonePosition(currentPosition, x, y);
                    p.board[y + i][x + j] = piece;
                    nextKings.add(p);
                }
            }
        }
        return nextKings;
    }

    private List<Position> knightMoves(int x, int y, int piece) {
        List<Position> nextKnights = new ArrayList();
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                if (Math.abs(i) + Math.abs(j) == 3 && insideBoard(x + i, y + j) && !occupied(x + i, y + j, piece)) {
                    Position p = new Position();
                    p.clonePosition(currentPosition, x, y);
                    p.board[y + j][x + i] = piece < 20 ? whiteKnight : blackKnight;
                    nextKnights.add(p);
                }
            }
        }
        return nextKnights;
    }

    private List<Position> blackPawnMoves(int x, int y, List<Position> nextPositions) {
        boolean moves[] = new boolean[4];
        moves[0] = insideBoard(y + 1, x);
        moves[1] = y == 1 && currentPosition.board[y + 1][x] == 0 && currentPosition.board[y + 2][x] == 0;
        moves[2] = insideBoard(x - 1, y + 1) && isWhitePiece(currentPosition.board[y + 1][x - 1]);
        moves[3] = insideBoard(x + 1, y + 1) && isWhitePiece(currentPosition.board[y + 1][x + 1]);

        if (moves[0]) {
            Position p = new Position();
            p.clonePosition(currentPosition, x, y);
            p.board[y + 1][x] = blackPawn;
            nextPositions.add(p);
        }
        if (moves[1]) {
            Position p = new Position();
            p.clonePosition(currentPosition, x, y);
            p.board[y + 2][x] = blackPawn;
            nextPositions.add(p);
        }
        if (moves[2]) {
            Position p = new Position();
            p.clonePosition(currentPosition, x, y);
            p.board[y + 1][x - 1] = blackPawn;
            nextPositions.add(p);
        }
        if (moves[3]) {
            Position p = new Position();
            p.clonePosition(currentPosition, x, y);
            p.board[y + 1][x + 1] = blackPawn;
            nextPositions.add(p);
        }
        return nextPositions;
    }

    private List<Position> whitePawnMoves(int x, int y, List<Position> nextPositions) {
        boolean moves[] = new boolean[4];
        moves[0] = insideBoard(x, y - 1);
        moves[1] = y == 6 && currentPosition.board[y - 1][x] == 0 && currentPosition.board[y - 2][x] == 0;
        moves[2] = insideBoard(x - 1, y - 1) && isBlackPiece(currentPosition.board[y - 1][x - 1]);
        moves[3] = insideBoard(x + 1, y - 1) && isBlackPiece(currentPosition.board[y - 1][x + 1]);

        if (moves[0]) {
            Position p = new Position();
            p.clonePosition(currentPosition, x, y);
            p.board[y - 1][x] = whitePawn;
            nextPositions.add(p);
        }
        if (moves[1]) {
            Position p = new Position();
            p.clonePosition(currentPosition, x, y);
            p.board[y - 2][x] = whitePawn;
            nextPositions.add(p);
        }
        if (moves[2]) {
            Position p = new Position();
            p.clonePosition(currentPosition, x, y);
            p.board[y - 1][x - 1] = whitePawn;
            nextPositions.add(p);
        }
        if (moves[3]) {
            Position p = new Position();
            p.clonePosition(currentPosition, x, y);
            p.board[y - 1][x + 1] = whitePawn;
            nextPositions.add(p);
        }
        return nextPositions;
    }

    private boolean occupied(int x, int y, int piece) {
        if (currentPosition.board[y][x] == 0) {
            return false;
        }
        if (currentPosition.board[y][x] >= 20 && piece >= 20) {
            return true;
        } else if (currentPosition.board[y][x] < 20 && piece < 20) {
            return true;
        }
        return false;
    }

    private boolean eats(int x, int y, int piece) {
        if (currentPosition.board[y][x] == 0) {
            return false;
        }
        return currentPosition.board[y][x] < 20 && piece >= 20
                || currentPosition.board[y][x] >= 20 && piece < 20;
    }

    private Position createPosition(int x, int y, int nx, int ny, int piece) {
        Position p = new Position();
        p.clonePosition(currentPosition, x, y);
        p.board[ny][nx] = piece;
        return p;
    }

    private boolean insideBoard(int x, int y) {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }

    private boolean isWhitePiece(int piece) {
        return piece >= 10 && piece <= 15;
    }

    private boolean isBlackPiece(int piece) {
        return piece >= 20 && piece <= 25;
    }

}
