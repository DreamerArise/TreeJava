import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
//ici on a la classe principale 
public class DirectoryTree {
// la methode main prend en argument le chemin du repertoire apres le java DirectoryTree 
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java DirectoryTree <directory_path>");
            return;
        }

        String directoryPath = args[0];
        Path path = Paths.get(directoryPath);
        // si le repertoire existe pas on lui dit que c invalide
        if (!Files.exists(path) || !Files.isDirectory(path)) {
            System.out.println("Invalid directory path.");
            return;
        }
        // dans le cas echeant il affiche l'arborescence avec la methode printDirectoryTree
        try {
            System.out.println("\n Directory Tree of " + path.toAbsolutePath().toString());
            printDirectoryTree(path, 0, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
// la methode printDirectorytree prend en params le chemin du rep le niveau de profondeur et le prefix pour l'indentation
    // elle utilise Files.list(path) pour obtenir touls elements dossier fichiers sous-fichiers
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
// cette methode prends en params le level le prefis et isLast(dernier element) elle imprime l'indentation 
    //en fonction du level(niveau profondeur)
    private static void printIndent(int level, String prefix, boolean isLast) {
        System.out.print(prefix);
        if (level > 0) {
            System.out.print(isLast ? "└── " : "├── ");
        }
    }
}
