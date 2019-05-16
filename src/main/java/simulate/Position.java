package simulate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Position {

    public int board[][];
    public boolean whitesMove;

    public static int whiteKing = 10;
    public static int whiteQueen = 11;
    public static int whiteKnight = 12;
    public static int whiteBishop = 13;
    public static int whiteRook = 14;
    public static int whitePawn = 15;

    public static int blackKing = 20;
    public static int blackQueen = 21;
    public static int blackKnight = 22;
    public static int blackBishop = 23;
    public static int blackRook = 24;
    public static int blackPawn = 25;

    public Position() {
        this.board = new int[8][8];
        this.whitesMove = true;
    }

    public void setupStartingPosition() {
        for (int i = 0; i < 8; i++) {
            this.board[1][i] = blackPawn;
            this.board[6][i] = whitePawn;
        }
        this.board[0][0] = blackRook;
        this.board[0][1] = blackKnight;
        this.board[0][2] = blackBishop;
        this.board[0][3] = blackQueen;
        this.board[0][4] = blackKing;
        this.board[0][5] = blackBishop;
        this.board[0][6] = blackKnight;
        this.board[0][7] = blackRook;
        this.board[7][0] = whiteRook;
        this.board[7][1] = whiteKnight;
        this.board[7][2] = whiteBishop;
        this.board[7][3] = whiteQueen;
        this.board[7][4] = whiteKing;
        this.board[7][5] = whiteBishop;
        this.board[7][6] = whiteKnight;
        this.board[7][7] = whiteRook;
    }

    public List<Position> blackPawnMoves(int x, int y, List<Position> nextPositions) {
        boolean moves[] = new boolean[4];
        moves[0] = insideBoard(y + 1, x);
        moves[1] = y == 1 && this.board[y + 1][x] == 0 && this.board[y + 2][x] == 0;
        moves[2] = insideBoard(x - 1, y + 1) && isWhitePiece(this.board[y + 1][x - 1]);
        moves[3] = insideBoard(x + 1, y + 1) && isWhitePiece(this.board[y + 1][x + 1]);

        if (moves[0]) {
            Position p = new Position();
            p.clonePosition(this, x, y);
            p.board[y + 1][x] = blackPawn;
            nextPositions.add(p);
        }
        if (moves[1]) {
            Position p = new Position();
            p.clonePosition(this, x, y);
            p.board[y + 2][x] = blackPawn;
            nextPositions.add(p);
        }
        if (moves[2]) {
            Position p = new Position();
            p.clonePosition(this, x, y);
            p.board[y + 1][x - 1] = blackPawn;
            nextPositions.add(p);
        }
        if (moves[3]) {
            Position p = new Position();
            p.clonePosition(this, x, y);
            p.board[y + 1][x + 1] = blackPawn;
            nextPositions.add(p);
        }
        return nextPositions;
    }

    public List<Position> blackKnightMoves(int x, int y, List<Position> nextPositions) {
        int i = 0;
        for (int a = -2; a <= 2; a++) {
            for (int b = -2; b <= 2; b++) {
                if (a == b || !insideBoard(a, b)) {
                    continue;
                }
                Position p = new Position();
                p.clonePosition(this, x, y);
                p.board[y - 1][x] = blackKnight;
                nextPositions.add(p);
            }
        }
        return nextPositions;
    }

    public List<Position> whiteKnightMoves(int x, int y, List<Position> nextPositions) {
        int i = 0;
        for (int a = -2; a <= 2; a++) {
            for (int b = -2; b <= 2; b++) {
                if (a == b || !insideBoard(a, b)) {
                    continue;
                }
                Position p = new Position();
                p.clonePosition(this, x, y);
                p.board[y - 1][x] = whiteKnight;
                nextPositions.add(p);
            }
        }
        return nextPositions;
    }

    public List<Position> whitePawnMoves(int x, int y, List<Position> nextPositions) {
        boolean moves[] = new boolean[4];
        moves[0] = insideBoard(x, y - 1);
        moves[1] = y == 6 && this.board[y - 1][x] == 0 && this.board[y - 2][x] == 0;
        moves[2] = insideBoard(x + 1, y - 1) && isBlackPiece(this.board[y - 1][x + 1]);
        moves[3] = insideBoard(x - 1, y - 1) && isBlackPiece(this.board[y - 1][x - 1]);

        if (moves[0]) {
            Position p = new Position();
            p.clonePosition(this, x, y);
            p.board[y - 1][x] = whitePawn;
            nextPositions.add(p);
        }
        if (moves[1]) {
            Position p = new Position();
            p.clonePosition(this, x, y);
            p.board[y - 2][x] = whitePawn;
            nextPositions.add(p);
        }
        if (moves[2]) {
            Position p = new Position();
            p.clonePosition(this, x, y);
            p.board[y - 1][x - 1] = whitePawn;
            nextPositions.add(p);
        }
        if (moves[3]) {
            Position p = new Position();
            p.clonePosition(this, x, y);
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

    void clonePosition(Position previous, int cx, int cy) {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (y == cy && x == cx) {
                    this.board[y][x] = 0;
                    continue;
                }
                this.board[y][x] = previous.board[y][x];
            }
        }
        this.whitesMove = previous.whitesMove;
    }

    public void print() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                int piece = board[y][x];
                if (piece == 0) {
                    System.out.print(" ");
                } else if (piece >= 10 && piece < 20) {
                    if (piece == whitePawn) {
                        System.out.print("P");
                    } else if (piece == whiteRook) {
                        System.out.print("R");
                    } else if (piece == whiteBishop) {
                        System.out.print("B");
                    } else if (piece == whiteKnight) {
                        System.out.print("N");
                    } else if (piece == whiteQueen) {
                        System.out.print("Q");
                    } else if (piece == whiteKing) {
                        System.out.print("K");
                    }
                } else if (piece >= 20) {
                    if (piece == blackPawn) {
                        System.out.print("p");
                    } else if (piece == blackRook) {
                        System.out.print("r");
                    } else if (piece == blackBishop) {
                        System.out.print("b");
                    } else if (piece == blackKnight) {
                        System.out.print("n");
                    } else if (piece == blackQueen) {
                        System.out.print("q");
                    } else if (piece == blackKing) {
                        System.out.print("k");
                    }
                }
            }
            System.out.println("");
        }
    }

}
