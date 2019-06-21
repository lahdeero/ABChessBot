package abcb.InputReader;

import abcb.datastructure.MyRecord;
import abcb.simulate.Position;
import static abcb.simulate.Position.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public class MyFileReader {

    private MyRecord<String> openingNames;

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

    public MyRecord<String> readOpeningsFromFile(String filename) {
        MyRecord<String> openings = new MyRecord<String>();
        openingNames = new MyRecord<String>();
//        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
//        File file = new File(classLoader.getResource(filename).getFile());
//        File file = new File("src/main/resources/ " + filename);
        try {
            //            File file = Paths.get(getClass().getClassLoader()
//            .
//            getResource("openings.txt").toURI()
//            ).toFile();
//        System.out.println("File Found : " + file.exists());
//        String content = new String(Files.readAllBytes(file.toPath()));
//        System.out.println(content);;
            File file = new File(filename);
            BufferedReader br = new BufferedReader(new FileReader(file));

//            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                if (st.length() > 0 && st.charAt(0) == '1' && st.charAt(1) == '.') {
                    openings.add(st);
                } else if (st.length() > 3) {
                    openingNames.add(st);
                }
            }
            System.out.println("total openings = " + openings.size());
            System.out.println("total openingNames = " + openingNames.size());
            return openings;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new MyRecord<String>();
    }

    public MyRecord<String> getOpeningNames() {
        return this.openingNames;
    }
}
