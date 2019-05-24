package abcb.util;

import abcb.simulate.Position;
import static abcb.simulate.Position.*;

public class MoveConverter {

    public String positionsToChessNotation(Position startingPosition, Position nextPosition) {
        int piece = 0;
        int nx = -1;
        int ny = -1;
        System.out.println("startingPosition");
        startingPosition.print();
        System.out.println("\nnextPosition");
        nextPosition.print();
        System.out.println("");
        
        for (int y = 0; y < startingPosition.boardRows; y++) {
            for (int x = 0; x < startingPosition.boardCols; x++) {
                if (startingPosition.board[y][x] != nextPosition.board[y][x]) {
                    if (nextPosition.board[y][x] == 0) {
                        continue;
                    }
                    nx = x;
                    ny = y;
                    piece = nextPosition.board[ny][nx];
                }
            }
        }
        System.out.println("piece = " + piece);
        System.out.println("nx = " + nx);
        System.out.println("ny = " + ny);
        return cordinatesToChessNotation(piece, nx, ny);
    }

    private String cordinatesToChessNotation(int piece, int nx, int ny) {
        if (piece == whitePawn || piece == blackPawn) {
            return numberCordinatesToChessCordinates(nx, ny);
        } else if (piece == whiteRook || piece == blackRook) {
            return "R" + numberCordinatesToChessCordinates(nx, ny);
        } else if (piece == whiteKnight || piece == blackKnight) {
            return "N" + numberCordinatesToChessCordinates(nx, ny);
        } else if (piece == whiteBishop || piece == blackBishop) {
            return "B" + numberCordinatesToChessCordinates(nx, ny);
        } else if (piece == whiteQueen || piece == blackQueen) {
            return "Q" + numberCordinatesToChessCordinates(nx, ny);
        } else if (piece == whiteKing || piece == blackKing) {
            return "K" + numberCordinatesToChessCordinates(nx, ny);
        }
        return "Unknown move";
    }

    private String numberCordinatesToChessCordinates(int x, int y) {
        char c = (char)(x+'a');
        return "" + c + (8 - y);
    }
}
