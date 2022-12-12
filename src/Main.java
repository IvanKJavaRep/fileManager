import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {


    public static void main(String[] args) {
        Reader reader = new Reader();
        List<ExFile> exFiles;
        Graph graph = new Graph();
        if (reader.readFolder()) {
            exFiles = reader.generateExFiles();
        }
    }


}