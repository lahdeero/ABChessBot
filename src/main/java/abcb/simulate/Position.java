package abcb.simulate;

public class Position {

    public final static int boardRows = 8;
    public final static int boardCols = 8;

    public int value;
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

    public boolean whiteKingLives;
    public boolean blackKingLives;

    public boolean whiteCastleKingSide;
    public boolean whiteCastleQueenSide;
    public boolean blackCastleKingSide;
    public boolean blackCastleQueenSide;

    public String history;

    public Position() {
        this.board = new int[boardRows][boardCols];
        this.whitesMove = true;
    }

    public Position(int[][] board, boolean whitesMove) {
        this.board = board;
        this.whitesMove = whitesMove;
    }

    /**
     * Position to FEN string, still not fully implemented..
     * @return 
     */
    @Override
    public String toString() {
        String fen = "";
        for (int y = 0; y < boardRows; y++) {
            int k = 0;
            for (int x = 0; x < boardCols; x++) {
                if (this.board[y][x] == 0) {
                    k += 1;
                } else {
                    if (k > 0) {
                        fen += k;
                        k = 0;
                    }
                    fen += pieceToChar(this.board[y][x]);
                }
            }
            if (k > 0) {
                fen += k;
            }
            if (y < boardRows - 1) {
                fen += '/';
            }
        }
        fen += whitesMove ? " w" : " b";
        return fen;
    }

    /**
     * Clones previous position, except leaves moving piece as empty.
     *
     * @param previous
     * @param cx X-coordinate of moving(not cloned) piece
     * @param cy Y-coordinate of moving(not cloned) piece
     */
    public void clonePosition(Position previous, int cx, int cy) {
        this.parent = previous;
        this.whiteCastleKingSide = previous.whiteCastleKingSide;
        this.whiteCastleQueenSide = previous.whiteCastleQueenSide;
        this.blackCastleKingSide = previous.blackCastleKingSide;
        this.blackCastleQueenSide = previous.blackCastleQueenSide;
        for (int y = 0; y < boardRows; y++) {
            for (int x = 0; x < boardCols; x++) {
                if (y == cy && x == cx) {
                    this.board[y][x] = 0;
                } else {
                    this.board[y][x] = previous.board[y][x];
                }
            }
        }
        this.whitesMove = !previous.whitesMove;
    }

    /**
     * There is scenarios where algorithm wants to trade kings, anyone who's
     * familiar to rules of chess knows this isn't current meta. So we are using
     * this method to trying to prevent that from happening
     *
     * @param white
     * @return
     */
    public boolean kingLives(boolean white) {
        for (int y = 0; y < boardRows; y++) {
            for (int x = 0; x < boardCols; x++) {
                if (board[y][x] == 10 && white) {
                    return true;
                } else if (board[y][x] == 20 && !white) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * For command line for an exploratory testing use: Can print current
     * Position.
     */
    public void print() {
        for (int y = 0; y < 8; y++) {
            System.out.print(8 - y);
            for (int x = 0; x < 8; x++) {
                int piece = board[y][x];
                System.out.print(pieceToChar(piece));
            }
            System.out.println("");
        }
        System.out.println(" abcdefgh\n");
    }

    private char pieceToChar(int piece) {
        if (piece >= 20) {
            if (piece == blackPawn) {
                return 'p';
            } else if (piece == blackRook) {
                return 'r';
            } else if (piece == blackKnight) {
                return 'n';
            } else if (piece == blackBishop) {
                return 'b';
            } else if (piece == blackQueen) {
                return 'q';
            } else if (piece == blackKing) {
                return 'k';
            }
        } else if (piece >= 10 && piece < 20) {
            if (piece == whitePawn) {
                return 'P';
            } else if (piece == whiteRook) {
                return 'R';
            } else if (piece == whiteKnight) {
                return 'N';
            } else if (piece == whiteBishop) {
                return 'B';
            } else if (piece == whiteQueen) {
                return 'Q';
            } else if (piece == whiteKing) {
                return 'K';
            }
        }
        return '.';
    }
}
