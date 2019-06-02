package abcb;

import abcb.argsHandler.CliUi;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
       CliUi au = new CliUi();
       au.run(args);
    }
}
