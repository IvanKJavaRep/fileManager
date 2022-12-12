import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static List<File> readFile(File file, String folder) {
        List<File> requirements = new ArrayList<>();
        try {
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                //System.out.println(line);
                if (line.startsWith("require ‘")) {
                    String[] tokens = line.split("‘");
                    String pathToFile = folder+tokens[1].split("’")[0];
                    Path path = Paths.get(pathToFile);
                    requirements.add(path.toFile());
                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return requirements;

    }

    public static void main(String[] args) {
        String folder = "C:\\Users\\Иван\\IdeaProjects\\untitled6\\r";
        Path root = Paths.get(folder);
        List<File> files = new ArrayList<>();
        List<ExFile> exFiles = new ArrayList<>();
        Graph graph = new Graph();
        try {
            List<Path> paths = java.nio.file.Files.walk(root).toList();
            for (var path :
                    paths) {
                File file = path.toFile();
                if (file.isFile()) {
                    files.add(path.toFile());
                }
            }
            for (var file :
                    files) {
                System.out.println(file);
                exFiles.add(new ExFile(file, readFile(file, folder)));
            }
            System.out.println(graph.findCycle(exFiles));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}