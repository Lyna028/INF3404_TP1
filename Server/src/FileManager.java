import java.io.File;

public class FileManager {
    File currentDir = new File(".");

    public void changeDirectory(dirName) {
        File newDir = new File();
            if (dirName = "..") {
                newDir = currentDir.getParentFile();
            }
            else {
                newDir = new File(currentDir, dirName);
            }
        
            return newDir;
        }

    public void listDirectory() {
         return return dir.listFiles()
    }
}
