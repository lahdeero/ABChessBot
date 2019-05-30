package abcb;

import abcb.argshandler.ArgsUi;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
       ArgsUi au = new ArgsUi();
       au.run(args);
    }
}
