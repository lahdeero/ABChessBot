package abcb.simulate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static abcb.simulate.Position.*;

public class Generator {

    public Position currentPosition;

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

    public List<Position> getNextPositions(Position currentPosition) {
        this.currentPosition = currentPosition;
        List<Position> nextPositions = Collections.synchronizedList(new ArrayList());
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                int piece = currentPosition.board[y][x];
                if (piece == 0) {
                    continue;
                }
                if (!currentPosition.whitesMove) {
                    if (piece == blackPawn) {
                        blackPawnMoves(x, y, nextPositions);
                    } else if (piece == blackKnight) {
                        nextPositions.addAll(knightMoves(x, y, blackKnight));
                    }
                } else {
                    if (piece == whitePawn) {
                        whitePawnMoves(x, y, nextPositions);
                    } else if (piece == whiteKnight) {
                        nextPositions.addAll(knightMoves(x, y, whiteKnight));
                    }
                }
            }
        }
        return nextPositions;
    }

    public List<Position> rookMoves(int x, int y, int piece) {
        List<Position> nextRooks = new ArrayList();

        return nextRooks;
    }

    public List<Position> knightMoves(int x, int y, int piece) {
        List<Position> nextKnights = new ArrayList();
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                if (Math.abs(i) + Math.abs(j) == 3 && insideBoard(x+i, y+j) && !occupied(x+i, y+j, piece)) {
                    System.out.println("i = " + i);
                    System.out.println("j = " + j);
                    System.out.println("");
                    Position p = new Position();
                    p.clonePosition(currentPosition, x, y);
                    p.board[y+j][x+i] = piece < 20 ? whiteKnight : blackKnight;
                    nextKnights.add(p);
                    p.print();
                }
            }
        }
        return nextKnights;
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

    public List<Position> blackPawnMoves(int x, int y, List<Position> nextPositions) {
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

    public List<Position> whitePawnMoves(int x, int y, List<Position> nextPositions) {
        boolean moves[] = new boolean[4];
        moves[0] = insideBoard(x, y - 1);
        moves[1] = y == 6 && currentPosition.board[y - 1][x] == 0 && currentPosition.board[y - 2][x] == 0;
        moves[2] = insideBoard(x + 1, y - 1) && isBlackPiece(currentPosition.board[y - 1][x + 1]);
        moves[3] = insideBoard(x - 1, y - 1) && isBlackPiece(currentPosition.board[y - 1][x - 1]);

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
