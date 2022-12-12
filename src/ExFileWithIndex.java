import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExFileWithIndex {
    private File current;
    private List<Integer> indexes = new ArrayList<>();

    public ExFileWithIndex(File current, List<Integer> indexes) {
        this.current = current;
        this.indexes = indexes;
    }

    public File getCurrent() {
        return current;
    }

    public List<Integer> getIndexes() {
        return indexes;
    }
}
