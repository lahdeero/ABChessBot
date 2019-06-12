package abcb.util;

public class Randomizer {

    public int generateRandomInt(int max) {
        int random = (int) (System.currentTimeMillis() % max);
        return random;
    }
}
