import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class DirectoryTree {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java DirectoryTree <directory_path>");
            return;
        }

        String directoryPath = args[0];
        Path path = Paths.get(directoryPath);
        
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            System.out.println("Invalid directory path.");
            return;
        }
        
        try {
            System.out.println("\n Directory Tree of " + path.toAbsolutePath().toString());
            printDirectoryTree(path, 0, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printDirectoryTree(Path path, int level, String prefix) throws IOException {
        try (Stream<Path> paths = Files.list(path)) {
            var iterator = paths.sorted().iterator();
            while (iterator.hasNext()) {
                Path p = iterator.next();
                boolean isLast = !iterator.hasNext();
                printIndent(level, prefix, isLast);
                if (Files.isDirectory(p)) {
                    System.out.println("DIR  " + p.getFileName());
                    printDirectoryTree(p, level + 1, prefix + (isLast ? "    " : "│   "));
                } else {
                    System.out.println("FILE " + p.getFileName());
                }
            }
        }
    }

    private static void printIndent(int level, String prefix, boolean isLast) {
        System.out.print(prefix);
        if (level > 0) {
            System.out.print(isLast ? "└── " : "├── ");
        }
    }
}
