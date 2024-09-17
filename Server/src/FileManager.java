import java.io.File;

public class FileManager {
    File currentDir = new File(".");

    public static File changeDirectory(File currentDir, String dirName) {
        File newDir;
        if (dirName.equals("..")) {
            newDir = currentDir.getParentFile();
        }
        else {
            newDir = new File(currentDir, dirName);
        }
        return newDir;
    }

    public void listDirectory(File currentFile) {
        File[] fileList = currentFile.listFiles();
        for (File file : fileList) {

        }
    }
}
