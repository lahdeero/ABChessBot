package abcb;

import abcb.argsHandler.CliUi;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
//         Openings o = new Openings();
         CliUi au = new CliUi();
         au.run(args);

//        AbOld ab = new AbOld();
//        MyFileReader mfr = new MyFileReader();
//        String fileDirectory = "./src/test/resources/";
        // Openings openings = new Openings();
        // openings.search("1. Nh3 d5 2. g3 e5 3. f4 Bxh3 4. Bxh3");
        // System.out.println(openings.getNextMove());
        // Randomizer randomizer = new Randomizer();
        // System.out.println(randomizer.generateRandomInt(100));

        // Position pos = mfr.fileToPosition(fileDirectory + "plsnonull.txt");
        // pos.whitesMove = true;
        // Position p = ab.calculateNextPosition(pos, 5, true);
        // p.print();
        // assertNotNull(p);

        // Position pos = mfr.fileToPosition(fileDirectory + "outofbounds.txt");
        // Position p = ab.calculateNextPosition(pos, 5, true);
        // p.print();

//        TimeTest tt = new TimeTest();
//        int diff = 0;
//        for (int i = 0; i < 100; i++) {
//            tt.run();
//            if (tt.values[0] > tt.values[2] && tt.maxPlayer) {
//                diff += 1;
//            } else if (tt.values[0] < tt.values[2] && !tt.maxPlayer) {
//                diff += 1;
//            }
//        }
//
//        System.out.println("total diff = " + diff);

    }
}
