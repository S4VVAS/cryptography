import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils_copy {


    public static String readFileContent(String path, boolean trim) {
        Path pathObj;
        String root = "./"; // assume relative path
        String result;
        if (path.startsWith("/")) {
            root = "/";
        }
        pathObj = FileSystems.getDefault().getPath(root, path);
        try {
            result = new String(Files.readAllBytes(pathObj));
            return (trim) ? result.trim() : result;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    public static void writeFileContent(String path, String content) {
        Path pathObj;
        String root = "./";
        if (path.startsWith("/")) {
            root = "/";
        }
        pathObj = FileSystems.getDefault().getPath(root, path);
        try {
            Files.write(pathObj, content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFileContent(String path) {
        return readFileContent(path, false);
    }
}
