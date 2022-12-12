import java.io.File;
import java.util.*;

public class Graph {
    private boolean haveCycle = false;
    private Stack<Integer> stack;
    private Set<Integer> notVisited;
    List<ExFileWithIndex> fileWithIndexList;

    private void recur(Integer node) {
        if (stack.contains(node)) {
            haveCycle = true;
            return;
        }
        stack.push(node);
        if (notVisited.contains(node)) {
            notVisited.remove(node);
        }
        for (var d :
                fileWithIndexList.get(node).getIndexes()) {
            recur(d);
            if (haveCycle) {
                return;
            }
        }
        stack.pop();
    }

    public boolean findCycle(List<ExFile> l) {
        Map<File, Integer> filesToIndex = generateMapWithIndex(l);
        fileWithIndexList = generateFilesWithIndex(l, filesToIndex);
        notVisited = generateSet(fileWithIndexList);
        stack = new Stack<>();
        while (!notVisited.isEmpty() && !haveCycle) {
            recur(notVisited.iterator().next());
        }
        return haveCycle;
    }

    private Set<Integer> generateSet(List<ExFileWithIndex> l) {
        Set<Integer> set = new HashSet<>();
        int index = 0;
        for (var file :
                l) {
            set.add(index);
            index++;
        }
        return set;
    }

    private Map<File, Integer> generateMapWithIndex(List<ExFile> l) {
        Map<File, Integer> filesToIndex = new HashMap<>();
        int index = 0;
        for (var file :
                l) {
            filesToIndex.put(file.getCurrent(), index);
            index++;
        }
        return filesToIndex;
    }

    private List<ExFileWithIndex> generateFilesWithIndex(List<ExFile> l, Map<File, Integer> map) {
        List<ExFileWithIndex> fileWithIndexList = new ArrayList<>();
        for (var file :
                l) {
            List<Integer> indexes = new ArrayList<>();
            for (var dep :
                    file.getRequirements()) {
                for (Map.Entry<File, Integer> entry : map.entrySet()) {
                    if (Objects.equals(entry.getKey(), dep)) {
                        indexes.add(entry.getValue());
                    }
                }
            }
            fileWithIndexList.add(new ExFileWithIndex(file.getCurrent(), indexes));
        }
        return fileWithIndexList;
    }

    private int[][] createTable(int size) {
        int[][] table = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                table[i][j] = 0;
            }
        }
        return table;
    }

    private void fillTable(int[][] table, Map<File, Integer> fileToIndex) {
        int i = 0;
        for (var file :
                fileWithIndexList) {
            i = fileToIndex.get(file.getCurrent());
            for (var el :
                    file.getIndexes()) {
                table[i][el] = 1;
            }
        }
    }

    public List<File> toOrderedList(List<ExFile> l) {
        int[][] table = createTable(l.size());
        Map<File, Integer> fileToIndex = generateMapWithIndex(l);
        List<File> result = new ArrayList<>();
        fileWithIndexList = generateFilesWithIndex(l, fileToIndex);
        int i = 0;
        fillTable(table, fileToIndex);
        Set<Integer> notUsed = generateSet(fileWithIndexList);
        while (!notUsed.isEmpty()) {
            for (var x :
                    notUsed) {
                boolean empty = true;
                for (int j = 0; j < fileWithIndexList.size(); j++) {
                    if (table[x][j] == 1) {
                        empty = false;
                        break;
                    }
                }
                if (empty) {
                    i = x;
                    break;
                }
            }
            notUsed.remove(i);
            for (int j = 0; j < fileWithIndexList.size(); j++) {
                table[j][i] = 0;
            }
            result.add(fileWithIndexList.get(i).getCurrent());
        }
        return result;

    }
}
