package helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class FileOperator {

    public static void writeFile(String content, String filename) {
        File file = new File("resources/" + filename);
        try {
            FileWriter dataf = new FileWriter(file);
            dataf.write(content);
            dataf.close();
        } catch(FileNotFoundException e){
            System.out.println("File not found!");
        } catch(IOException e){
            System.out.println("IO Error!");
        }
    }
}
