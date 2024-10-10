import java.io.File;

/**
 * Class that manages file and directory operations in a specified storage directory.
 * It allows navigating between directories, creating new directories, listing contents, and deleting files.
 */
public class FileManager {
    private File currentDir;
    private File stockDir;

    private String rootName = "serverStockage";

    /**
     * Constructs a FileManager instance. The root directory is initialized to
     * the current working directory (".") and a subdirectory "serverStockage" which represents
     * the project root is created.
     */
    public FileManager() {
        this.stockDir = new File(".");
        this.currentDir = new File(stockDir,rootName);
        this.currentDir.mkdirs();
    }

    /**
     * Returns the path of the current working directory.
     * @return a string representing the path of the current directory.
     */
    public String getCurrentDirectory() {
        return this.currentDir.getPath();
    }

    /**
     * Changes the current directory to the specified directory name or moves
     * to the parent directory if ".." is provided.
     * @param dirName name of the directory to switch or '..' to switch to parent directory
     * @return The name of the new current directory, or an error message if the directory does not exist
     */
    public String changeDirectory(String dirName) {
            if (dirName.equals("..")) {
                if(currentDir.getName().equals(rootName)){
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
     * Creates a new directory inside the current directory with the specified name
     * @param dirName name of the directory to create
     * @return true if the directory was successfully created, false otherwise
     */
    public boolean createDirectory(String dirName) {
        File newDir = new File(currentDir, dirName);
        return newDir.mkdir();
    }

    /**
     * lists all files and folders in the current directory
     * @return a formatted string of currentDir's content. Each entry is specified by [File] or [Folder]
     * depending on its type. It also indicates if the directory is empty
     */
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

    /**
     * Deletes a file or a folder from the current directory. In the case of a folder, the function
     * recursively deletes all its content.
     * @param file The file/folder to delete.
     * @return true if the file was successfully deleted, false otherwise.
     */
    private boolean deleteFile(File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                deleteFile(f);
            }
        }
        return file.delete();
    }

    /**
     * Calls the private recursive methode deleteFile()
     * @param fileName The name of the file/folder to delete
     * @return true if the file was successfully deleted, false otherwise.
     */
    public boolean deleteFile(String fileName) {
        File file = new File(currentDir, fileName);
        return deleteFile(file);
    }

}
