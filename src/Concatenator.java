import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Concatenator {
    public void concatenate(List<File> files, String rootFolder) {
        try {
            File result = new File(rootFolder + "/resultFile.txt");
            FileWriter fileWriter = new FileWriter(result);
            for (var file :
                    files) {
                FileReader fr = new FileReader(file);
                BufferedReader reader = new BufferedReader(fr);
                String line = reader.readLine();
                while (line != null) {
                    fileWriter.write(line + "\n");
                    line = reader.readLine();
                }
                fr.close();
            }
            fileWriter.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }
}
