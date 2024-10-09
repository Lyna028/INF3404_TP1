import java.io.File;

public class FileManager {
    private File currentDir;
    private File stockDir;

    public FileManager() {
        this.stockDir = new File(".");
        this.currentDir = new File(stockDir,"serverStockage");
        this.currentDir.mkdirs();
    }

    public String getCurrentDirectory() {
        return this.currentDir.getPath();
    }

    public String changeDirectory(String dirName) {
            if (dirName.equals("..")) {
                if(currentDir.getName().equals("serverStockage")){
                    return currentDir.getName();
                }
                else {
                    File parentDir = currentDir.getParentFile();
                    currentDir = parentDir;
                    return parentDir.getName();
                }
            } else {
                File newDir = new File(currentDir, dirName);
                if (newDir.exists()) {
                    currentDir = newDir;
                    return dirName;
                } else {
                    return "Directory " + dirName + " does not exist";

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
