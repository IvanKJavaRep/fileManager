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
        Map<Integer, File> filesToIndex = generateMapWithIndex(l);
        fileWithIndexList = generateFilesWithIndex(l, filesToIndex);
        notVisited = generateSet(l);
        stack = new Stack<>();
        while (!notVisited.isEmpty() && !haveCycle) {
            recur(notVisited.iterator().next());
        }
        return haveCycle;
    }

    public Set<Integer> generateSet(List<ExFile> l) {
        Set<Integer> set = new HashSet<>();
        int index = 0;
        for (var file :
                l) {
            set.add(index);
            index++;
        }
        return set;
    }

    public Map<Integer, File> generateMapWithIndex(List<ExFile> l) {
        Map<Integer, File> filesToIndex = new HashMap<>();
        int index = 0;
        for (var file :
                l) {
            filesToIndex.put(index, file.getCurrent());
            index++;
        }
        return filesToIndex;
    }

    public List<ExFileWithIndex> generateFilesWithIndex(List<ExFile> l, Map<Integer, File> map) {
        List<ExFileWithIndex> fileWithIndexList = new ArrayList<>();
        for (var file :
                l) {
            List<Integer> indexes = new ArrayList<>();
            for (var dep :
                    file.getRequirements()) {
                for (Map.Entry<Integer, File> entry : map.entrySet()) {
                    if (Objects.equals(entry.getValue(), dep)) {
                        indexes.add(entry.getKey());
                    }
                }
            }
            fileWithIndexList.add(new ExFileWithIndex(file.getCurrent(), indexes));
        }
        return fileWithIndexList;
    }
}
