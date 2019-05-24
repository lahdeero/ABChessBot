package abcb.simulate;

public class Position {
    public final static int boardRows = 8;
    public final static int boardCols = 8;
    
    public static int whiteKing = 10;
    public static int whiteQueen = 11;
    public static int whiteBishop = 12;
    public static int whiteKnight = 13;
    public static int whiteRook = 14;
    public static int whitePawn = 15;

    public static int blackKing = 20;
    public static int blackQueen = 21;
    public static int blackBishop = 22;
    public static int blackKnight = 23;
    public static int blackRook = 24;
    public static int blackPawn = 25;

    public int board[][];
    public boolean whitesMove;
    public Position parent;

    public Position() {
        this.board = new int[boardRows][boardCols];
        this.whitesMove = true;
    }
    
    public Position(Position parent) {
        this.board = new int[boardRows][boardCols];
        this.parent = parent;
    } 

    public Position(int[][] board, boolean whitesMove) {
        this.board = board;
        this.whitesMove = whitesMove;
    }

    /**
     * Clones position without moving piece.
     * @param previous
     * @param cx X-coordinate of removed(moving) piece
     * @param cy Y-coordinate of removed(moving) piece
     */
    void clonePosition(Position previous, int cx, int cy) {
        this.parent = previous;
        for (int y = 0; y < boardRows; y++) {
            for (int x = 0; x < boardCols; x++) {
                if (y == cy && x == cx) {
                    this.board[y][x] = 0;
                    continue;
                }
                this.board[y][x] = previous.board[y][x];
            }
        }
        this.whitesMove = !previous.whitesMove;
    }

    /**
     * For command line for an exploratory testing use: Can print current Position.
     */
    public void print() {
        for (int y = 0; y < 8; y++) {
            System.out.print(8-y);
            for (int x = 0; x < 8; x++) {
                int piece = board[y][x];
                if (piece == 0) {
                    System.out.print(".");
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
        System.out.println(" abcdefgh");
    }

}
