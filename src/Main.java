import java.util.List;

public class Main {


    public static void main(String[] args) {
        try {
            Reader reader = new Reader();
            List<ExFile> exFiles;
            Graph graph = new Graph();
            if (reader.readFolder()) {
                exFiles = reader.generateExFiles();
                if (!graph.findCycle(exFiles)) {
                    var sortedFiles = graph.toOrderedList(exFiles);
                    Concatenator concatenator=new Concatenator();
                    concatenator.concatenate(sortedFiles,reader.getRootFolder());
                } else {
                    System.out.println("Cycle is found! \nNot possible to resolve " +
                            "automatically");
                }
            }
        }catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

    }


}