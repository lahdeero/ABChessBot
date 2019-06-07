package abcb.InputReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Openings {

    public Openings() throws IOException {

    }

    public void generateOpenings() throws IOException {
        System.out.println("eka");
        String fileName = "openings.txt";
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        System.out.println("File Found : " + file.exists());
        String content = new String(Files.readAllBytes(file.toPath()));
        System.out.println(content);;

        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        int y = 0;
        while ((st = br.readLine()) != null) {
            System.out.println(st);
        }
    }
}
