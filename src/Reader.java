import java.io.*;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Reader {
    public String getRootFolder() {
        return rootFolder;
    }

    private String rootFolder;


    public boolean readFolder() {
        System.out.println("Enter absolute root folder path:");
        String folder = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            folder = reader.readLine();
            reader.close();
            File file = new File(folder);
            if (!file.isDirectory()) {
                System.out.println("Incorrect folder name!");
                return false;
            } else {
                rootFolder = folder;
                return true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ExFile> generateExFiles() throws Exception {
        var files = makeFiles();
        return createExFiles(files);
    }

    private List<File> makeFiles() {
        List<File> files = new ArrayList<>();
        Path root = Paths.get(rootFolder);
        List<Path> paths;
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

    private List<ExFile> createExFiles(List<File> files) throws Exception {
        List<ExFile> exFiles = new ArrayList<>();
        for (var file :
                files) {
            exFiles.add(new ExFile(file, readFile(file, rootFolder)));
        }
        return exFiles;
    }

    private List<File> readFile(File file, String folder) throws Exception {
        List<File> requirements = new ArrayList<>();
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                if (line.startsWith("require ‘")) {
                    String[] tokens = line.split("‘");
                    String pathToFile = folder + tokens[1].split("’")[0];
                    Path path = Paths.get(pathToFile);
                    if (path.toFile().isFile()) {
                        requirements.add(path.toFile());
                    }else {
                        throw new NoSuchFileException("No such file"+ path);
                    }
                }
                line = reader.readLine();
            }
        return requirements;

    }
}
