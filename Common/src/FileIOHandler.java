import java.io.*;

public class FileIOHandler {
    public FileIOHandler() { }

    public boolean writeFile(File file, DataInputStream socketStream) {
        if (file.isDirectory()) {
            System.out.println("File passed is a directory");
            return false;
        }

        long fileSize = 0;
        long totalRead = 0;

        try {
            fileSize = socketStream.readLong(); // Read size that was sent before data
        } catch (IOException e) {
            System.out.printf("Error during reading of size of file %s : %s", file.getName(), e.toString());
            return false;
        }

        byte[] buffer = new byte[UploadConstants.BUFFER_SIZE];
        int len;

        try (// Try-with-resources
             FileOutputStream fileToWrite = new FileOutputStream(file)) {
            do {
                len = socketStream.read(buffer, 0, Math.min(UploadConstants.BUFFER_SIZE, (int)(fileSize - totalRead)));
                fileToWrite.write(buffer, 0, len);

                totalRead += len;
            } while (totalRead < fileSize && len > 0);

        } catch (IOException e) {
            System.out.printf("Error during transfer to file %s : %s", file.getName(), e.toString());
            return false;
        }

        return true;
    }

    public boolean readFile(File file, DataOutputStream socketStream) {
        if (file.isDirectory()) {
            System.out.println("File passed is a directory");
            return false;
        }

        byte[] buffer = new byte[UploadConstants.BUFFER_SIZE];
        int len;

        long fileSize = file.length();
        try {
            socketStream.writeLong(fileSize);
        } catch (IOException e) {
            System.out.printf("Error during writing of size of file %s : %s", file.getName(), e.toString());
            return false;
        }

        try (// Try-with-resources
             FileInputStream fileToRead = new FileInputStream(file)) {
            do {
                len = fileToRead.read(buffer, 0, UploadConstants.BUFFER_SIZE);
                socketStream.write(buffer, 0, len);
            } while (len > 0);

        } catch (IOException e) {
            System.out.printf("Error during transfer from file %s : %s", file.getName(), e.toString());
            return false;
        }

        return true;
    }
}
