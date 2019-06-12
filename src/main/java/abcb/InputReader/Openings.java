package abcb.InputReader;

import abcb.datastructure.MyRecord;
import abcb.util.Randomizer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Openings {

    private Randomizer randomizer;
    private MyRecord<String> openingNames;
    private MyRecord<String> openings;
    private MyRecord<String> nextMoves;
    private MyRecord<Integer> indexFound;
    private Integer indexSelected;
    private int previousRandom;

    public Openings() throws IOException {
        this.randomizer = new Randomizer();
        previousRandom = 1;
        openings = new MyRecord<String>();
        openingNames = new MyRecord<String>();
        String fileName = "openings.txt";
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
//        System.out.println("File Found : " + file.exists());
//        String content = new String(Files.readAllBytes(file.toPath()));
//        System.out.println(content);;

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
        boolean ret = false;
        this.indexFound = new MyRecord();
        this.nextMoves = new MyRecord<String>();
        for (int i = 0; i < openings.size(); i++) {
            String opening = openings.get(i);
            if (opening.contains(history) && opening.length() > history.length() + 2) {
                this.nextMoves.add(resolveNextMove(history, opening));
                ret = true;
            }
        }
        return ret;
    }

    private String resolveNextMove(String history, String opening) {
        String move = "";
        System.out.println("history = " + history);
        System.out.println("opening = " + opening);
        int i = history.length();
        while (!Character.isLetter(opening.charAt(i)) && opening.length() > i) {
            i += 1;
        }
        while (Character.isLetter(opening.charAt(i)) && opening.length() > i) {
            move += opening.charAt(i++);
        }
        move += opening.charAt(i);
        return move;
    }

    public String getNextMove() {
        int randomIndex = randomizer.generateRandomInt(this.nextMoves.size());
        System.out.println("random = " + randomIndex + ", max = " + nextMoves.size());
        this.indexSelected = randomIndex;
        return this.nextMoves.get(randomIndex);
    }

    public String getOpeningName() {
        if (openingNames.size() > indexSelected) {
            return openingNames.get(indexSelected);
        }
        System.out.println("index not inside myRecord size!");
        return "Unknown opening";
    }
}
