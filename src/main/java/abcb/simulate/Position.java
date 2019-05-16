package abcb.simulate;

public class Position {

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

    public int board[][];
    public boolean whitesMove;

    public Position() {
        this.board = new int[8][8];
    }

    public Position(int[][] board, boolean whitesMove) {
        this.board = board;
        this.whitesMove = whitesMove;
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
        this.whitesMove = !previous.whitesMove;
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
