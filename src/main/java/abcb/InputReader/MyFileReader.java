package abcb.InputReader;

import abcb.simulate.Position;
import static abcb.simulate.Position.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MyFileReader {

    public Position fileToPosition(String location) throws IOException {
        Position position = new Position();
        File file = new File(location);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        int y = 0;
        while ((st = br.readLine()) != null) {
            for (int x = 0; x < 8; x++) {
                position.board[y][x] = characterToPiece(st.charAt(x));
            }
            y++;
        }
        return position;
    }

    private int characterToPiece(char c) {
        switch (c) {
            case 'K':
                return whiteKing;
            case 'Q':
                return whiteQueen;
            case 'B':
                return whiteBishop;
            case 'N':
                return whiteKnight;
            case 'R':
                return whiteRook;
            case 'P':
                return whitePawn;
            case 'k':
                return blackKing;
            case 'q':
                return blackQueen;
            case 'b':
                return blackBishop;
            case 'n':
                return blackKnight;
            case 'r':
                return blackRook;
            case 'p':
                return blackPawn;
        }
        return 0;
    }
}
