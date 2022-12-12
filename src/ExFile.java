import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExFile {
    private File current;
    private List<File> requirements = new ArrayList<>();

    public ExFile(File current, List<File> requirements) {
        this.current = current;
        this.requirements = requirements;
    }

    public File getCurrent() {
        return current;
    }

    public List<File> getRequirements() {
        return requirements;
    }
}
