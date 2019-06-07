package abcb.InputReader;

import abcb.datastructure.MyRecord;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

public class Openings {

    private MyRecord<String> openingNames;
    private MyRecord<String> openings;
    private String nextMove;
    private int indexFound;

    public Openings() throws IOException {
        openings = new MyRecord<String>();
        openingNames = new MyRecord<String>();
        String fileName = "openings.txt";
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        System.out.println("File Found : " + file.exists());
        String content = new String(Files.readAllBytes(file.toPath()));
        System.out.println(content);;

        BufferedReader br = new BufferedReader(new FileReader(file));
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
    }

    public boolean search(String history) {
        for (int i = 0; i < openings.size(); i++) {
            String opening = openings.get(i);
            if (opening.contains(history)) {
                indexFound = i;
                resolveNextMove(history, opening);
                return true;
            }
        }
        return false;
    }
    
    private void resolveNextMove(String history, String opening) {
        String move = "";
        int i = history.length();
        while (!Character.isLetter(opening.charAt(i)) && opening.length() > i) {
            i += 1;
        }
        while (Character.isLetter(opening.charAt(i)) && opening.length() > i) {
            move += opening.charAt(i);
        }
        this.nextMove = move;
    }

    public String getNextMove() {
        return this.nextMove;
    }

    public String getOpeningName() {
        if (openingNames.size() > indexFound) {
            return openingNames.get(indexFound);
        }
        System.out.println("index not inside myRecord size!");
        return "Unknown opening";
    }
}
