import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClientHandler extends Thread {
    private Socket socket;
    private int clientNumber;
    private FileManager fileManager;
    private FileIOHandler fileIOHandler;

    public ClientHandler(Socket socket, int clientNumber) {
        this.socket = socket;
        this.clientNumber = clientNumber;
        this.fileManager = new FileManager();
    }

    public void run(){ // Création de thread qui envoi un message à un client
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            System.out.println("Streams successfully opened for client#" + clientNumber);

            while (true) {
                // Read the message from the client
                String clientMessage = in.readUTF();
                printUserCommand(clientMessage);

                String[] commandParts = clientMessage.split(" ",2);
                String cmdName = commandParts[0];
                String arg = (commandParts.length > 1) ? commandParts[1] : null;

                switch (cmdName) {
                    case "ls":
                        out.writeUTF(fileManager.listDirectory());
                        break;

                    case "cd":
                        if(arg != null) {
                            String result = fileManager.changeDirectory(arg);
                            out.writeUTF("Vous etes dans le dossier " + result);
                        }
                        else{
                            out.writeUTF("Spécifiez le dossier");
                        }
                        break;

                    case "mkdir":
                        boolean created = fileManager.createDirectory(arg);
                        out.writeUTF("Le dossier " + arg + " a été crée.");
                        break;

                    case "upload":
                        File fileToUpload = new File(fileManager.getCurrentDirectory(), arg);
                        fileIOHandler.writeFile(fileToUpload, in);
                        break;

                    case "download":
                        File fileToDownload = new File(fileManager.getCurrentDirectory(), arg);
                        fileIOHandler.readFile(fileToDownload, out);
                        break;

                    case "delete":
                        boolean deleted = fileManager.deleteFile(arg);
                        out.writeUTF("Le fichier " + arg + " a bien été supprimé");
                        break;
                }
                out.flush();

                System.out.println("Response sent to client#" + clientNumber);

            }

        } catch (IOException e) {
            System.out.println("Error handling client# " + clientNumber + ": " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Couldn't close a socket, what's going on?");
            }
            System.out.println("Connection with client# " + clientNumber + " closed");
        }
    }

    public void printUserCommand(String clientMessage) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd@HH:mm:ss");

        String formattedDateTime = now.format(formatter);

        System.out.printf(
                "[%s:%d - %s] : %s\n",
                socket.getInetAddress().getHostAddress(),
                socket.getPort(),
                now.format(formatter),
                clientMessage
        );
    }
}