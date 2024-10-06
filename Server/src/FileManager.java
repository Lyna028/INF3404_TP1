import java.io.File;

public class FileManager {
    private File currentDir;

    public FileManager() {
        this.currentDir = new File(".");
    }

    public String getCurrentDirectory() {
        return this.currentDir.getPath();
    }

    public void changeDirectory(String dirName) {
        if (dirName.equals("..")) {
            File parentDir = currentDir.getParentFile();
            currentDir = parentDir;
        } else {
            File newDir = new File(currentDir, dirName);
            if (newDir.exists()) {
                currentDir = newDir;
            } else {
                System.out.println("Directory does not exist: " + dirName);
            }
        }
    }

    /**
     *
     * @param dirName
     * @return
     */
    public boolean createDirectory(String dirName) {
        File newDir = new File(currentDir, dirName);
        return newDir.mkdir();
    }

    public String listDirectory() {
        File[] fileList = currentDir.listFiles();
        if (fileList == null || fileList.length == 0) {
            return "Directory is empty.";
        }

        StringBuilder files = new StringBuilder();
        for (File file : fileList) {
            if (file.isDirectory()) {
                files.append("[Folder] ");
                files.append(file.getName());
            } else {
                files.append("[File] ");
                files.append(file.getName());
            }
            files.append("\n");
        }
        return files.toString();
    }

    public boolean deleteFile(String fileName) {
        File file = new File(currentDir, fileName);
        return file.delete();
    }
}
