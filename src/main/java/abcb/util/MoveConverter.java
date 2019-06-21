package abcb.util;

import abcb.simulate.Position;
import static abcb.simulate.Position.*;

public class MoveConverter {

    /**
     * Returns new position after move.
     *
     * @param position
     * @param x moving piece x-coordinate
     * @param y moving piece y-coordinate
     * @param nx moving piece new x-coordinate
     * @param ny moving piece new y-coordinate
     * @return
     */
    public Position move(Position position, int x, int y, int nx, int ny) {
        Position p = new Position();
        p.clonePosition(position, x, y);
        p.board[ny][nx] = position.board[y][x];
        p.board[y][x] = 0;
        System.out.println("x = " + x + " y = " + y);
        System.out.println(p.board[y][x]);
        return p;
    }

    /**
     * Returns move as Algebraic notation.
     *
     * @param currentPosition
     * @param nextPosition
     * @return
     */
    public String positionsToChessNotation(Position currentPosition, Position nextPosition) {
        int[] moveArr = moveToIntArr(currentPosition, nextPosition);
        int x = moveArr[0];
        int y = moveArr[1];
        int nx = moveArr[2];
        int ny = moveArr[3];
        int piece = moveArr[4];

        return cordinatesToChessNotation(piece, x, y, nx, ny, currentPosition);
    }

    /**
     * We could also use object here, moveArr has all information about move we
     * need here.
     *
     * @param currentPosition
     * @param nextPosition
     * @return
     */
    private int[] moveToIntArr(Position currentPosition, Position nextPosition) {
        int[] moveArr = new int[5];
        if (nextPosition == null) {
            System.out.println("Nextposition null..");
            return moveArr;
        }
        for (int y = 0; y < currentPosition.boardRows; y++) {
            for (int x = 0; x < currentPosition.boardCols; x++) {
                if (currentPosition.board[y][x] != nextPosition.board[y][x]) {
                    if (nextPosition.board[y][x] == 0) {
                        moveArr[0] = x;
                        moveArr[1] = y;
                    } else {
                        moveArr[2] = x;
                        moveArr[3] = y;
                        moveArr[4] = nextPosition.board[y][x];
                    }
                }
            }
        }
        return moveArr;
    }

    private String cordinatesToChessNotation(int piece, int x, int y, int nx, int ny, Position currentPosition) {
        System.out.println("nx = " + nx + " ny = " + ny);
        boolean eats = currentPosition.board[ny][nx] != 0;
        if (piece == whitePawn || piece == blackPawn) {
            if (eats) {
                return "" + (char) (x + 'a') + "x" + numberCordinatesToChessCordinates(nx, ny);
            } else {
                return numberCordinatesToChessCordinates(nx, ny);
            }
        } else if (piece == whiteRook || piece == blackRook) {
            return eats ? "Rx" + numberCordinatesToChessCordinates(nx, ny)
                    : "R" + numberCordinatesToChessCordinates(nx, ny);
        } else if (piece == whiteKnight || piece == blackKnight) {
            return eats ? "Nx" + numberCordinatesToChessCordinates(nx, ny)
                    : "N" + numberCordinatesToChessCordinates(nx, ny);
        } else if (piece == whiteBishop || piece == blackBishop) {
            return eats ? "Bx" + numberCordinatesToChessCordinates(nx, ny)
                    : "B" + numberCordinatesToChessCordinates(nx, ny);
        } else if (piece == whiteQueen || piece == blackQueen) {
            return eats ? "Qx" + numberCordinatesToChessCordinates(nx, ny)
                    : "Q" + numberCordinatesToChessCordinates(nx, ny);
        } else if (piece == whiteKing || piece == blackKing) {
            return eats ? "Kx" + numberCordinatesToChessCordinates(nx, ny)
                    : "K" + numberCordinatesToChessCordinates(nx, ny);
        }
        return "Unknown move";
    }

    private String numberCordinatesToChessCordinates(int x, int y) {
        char c = (char) (x + 'a');
        return "" + c + (8 - y);
    }
}
