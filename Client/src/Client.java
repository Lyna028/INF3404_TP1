import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
// Application client

/**
 * Client that connects to a server, sends commands, and processes the server's responses.
 * It allows users to interact with a remote file system, supporting commands such as
 * uploading files, downloading files, changing directories, listing files, creating directories,
 * and deleting files. The client runs in a loop, continuously accepting user input until the user types "exit".
 */
public class Client {
    private static Socket socket;
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        int clientNumber = 0;

        IdentifiantServeur idsServeur = new IdentifiantServeur();
        idsServeur.connexionValidity();

        // New cnnection with the server
        socket = new Socket(idsServeur.ipValue, idsServeur.portValue);
        System.out.format("Serveur lancé sur [%s:%d]\n", idsServeur.ipValue, idsServeur.portValue);

        // Input and output streams to recieve and send datas to the server
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        while (true) {
            System.out.print("Enter command : ");
            String command = scanner.nextLine();

            if(command.equalsIgnoreCase("exit")) {
                break;
            }

            String[] commandParts = command.split(" ");
            FileIOHandler fileIOHandler = new FileIOHandler();

            switch (commandParts[0]) {
                case "upload":
                    File fileToUpload = new File(commandParts[1]);
                    if (!fileToUpload.exists() || !fileToUpload.isFile()) {
                        System.out.println("File does not exist or is a folder.");
                        continue;
                    }

                    out.writeUTF(commandParts[0] + " " + fileToUpload.getName());

                    if (!fileIOHandler.readFile(fileToUpload, out))
                        continue;

                    waitServerRes(in);
                    break;

                case "download":
                    File fileToDownload = new File(commandParts[1]);
                    out.writeUTF(commandParts[0] + " " + fileToDownload.getName());

                    if (!fileIOHandler.writeFile(fileToDownload, in))
                        continue;

                    waitServerRes(in);
                    break;

                case "cd":
                case "ls":
                case "mkdir":
                case "delete":
                    // Sending the command to the server
                    out.writeUTF(command);
                    out.flush();
                    waitServerRes(in);
                    break;

                default:
                    System.out.println("Commande invalide");
            }
        }

        // closing the connection with the server
        socket.close();
    }

    /**
     * Waits for server response after sending the command
     * @param in Stream of the socket connected to the server
     */
    private static void waitServerRes(DataInputStream in) {
        try {
            System.out.println("Command sent. Waiting for response...");
            String serverResponse = in.readUTF();
            System.out.println(serverResponse);
        } catch (IOException e) {
            System.out.println("Error sending command: " + e);
        }
    }

}