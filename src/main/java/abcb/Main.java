package abcb;

import abcb.argsHandler.CliUi;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
//         Openings o = new Openings();
//         CliUi au = new CliUi();
//         au.run(args);

//       Generator generator = new Generator();
//       Position randomPosition = generator.generateRandomPosition(16);
//       AlphaBeta ab = new AlphaBeta();
//       Position nextPosition = ab.calculateNextPosition(randomPosition, 5, true);
//       nextPosition.print();
//        System.out.println(ab.value);
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
//        double times = 10;
//        double total = 0;
//        for (long i = 0; i < times; i++) {
//            total += tt.run(7);
//        }
//        
//        System.out.println("total = " + total);
//        System.out.println("average: " + total / times);
        TimeTest tt = new TimeTest();
        int depth = 7;
//        for (int j = 0; j < 4; j++) {
            int times = 3;
            int leafs = 0;
            for (int i = 0; i < times; i++) {
                leafs += tt.runLeafs(depth);
            }
            long average = (long) (leafs / times);

            System.out.println("depth = " + depth);
            System.out.println("total = " + leafs);
            System.out.println("average: " + average);
            depth += 1;
//        }
//
//        System.out.println("total diff = " + diff);
    }
}
