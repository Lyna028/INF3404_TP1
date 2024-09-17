import java.io.*;

public class FileIOHandler {
    public FileIOHandler() { }

    public boolean writeFile(File file, InputStream readStream) {
        if (file.isDirectory()) {
            System.out.println("File passed is a directory");
            return false;
        }

        byte[] buffer = new byte[UploadConstants.BUFFER_SIZE];
        int len;

        try (// Try-with-resources
             FileOutputStream fileToWrite = new FileOutputStream(file)) {
            len = readStream.read(buffer, 0, UploadConstants.BUFFER_SIZE);
            fileToWrite.write(buffer, 0, len);
        } catch (IOException e) {
            System.out.printf("Error during transfer to file %s : %s", file.getName(), e.toString());
            return false;
        }

        return true;
    }

    public boolean readFile(File file, OutputStream readStream) {
        if (file.isDirectory()) {
            System.out.println("File passed is a directory");
            return false;
        }

        byte[] buffer = new byte[UploadConstants.BUFFER_SIZE];
        int len;

        try (// Try-with-resources
             FileInputStream fileToRead = new FileInputStream(file)) {
            len = fileToRead.read(buffer, 0, UploadConstants.BUFFER_SIZE);
            readStream.write(buffer, 0, len);
        } catch (IOException e) {
            System.out.printf("Error during transfer from file %s : %s", file.getName(), e.toString());
            return false;
        }

        return true;
    }
}
