package abcb.util;

public class Randomizer {

    /**
     * Return "random" integer between 0 and max
     *
     * @param max
     * @return
     */
    public int generateRandomInt(int max) {
//        if (max <= 0) {
//            return 0;
//        }
//        int random = (int) (System.currentTimeMillis() % (max+1));
//        return random;
        return (int)(Math.random() * ((max - 0) + 1)) + 0;
    }
}
