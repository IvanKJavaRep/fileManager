import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Reader {
    private String rootFolder;


    public boolean readFolder() {
        System.out.println("Enter root folder path:");
        String folder = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            folder = reader.readLine();
            reader.close();
            File file = new File(folder);
            if (!file.isDirectory()) {
                System.out.println("Incorrect folder!");
                return false;
            } else {
                rootFolder = folder;
                return true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ExFile> generateExFiles() {
        var files = makeFiles();
        return createExFiles(files);
    }

    private List<File> makeFiles() {
        List<File> files = new ArrayList<>();
        Path root = Paths.get(rootFolder);
        List<Path> paths = null;
        try {
            paths = java.nio.file.Files.walk(root).toList();
            for (var path :
                    paths) {
                File file = path.toFile();
                if (file.isFile()) {
                    files.add(path.toFile());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return files;
    }

    private List<ExFile> createExFiles(List<File> files) {
        List<ExFile> exFiles = new ArrayList<>();
        for (var file :
                files) {
            System.out.println(file);
            exFiles.add(new ExFile(file, readFile(file, rootFolder)));
        }
        return exFiles;
    }

    private List<File> readFile(File file, String folder) {
        List<File> requirements = new ArrayList<>();
        try {
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                if (line.startsWith("require ‘")) {
                    String[] tokens = line.split("‘");
                    String pathToFile = folder + tokens[1].split("’")[0];
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
}
