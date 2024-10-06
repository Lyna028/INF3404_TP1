import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private int clientNumber;
    private FileManager fileManager;
    private FileIOHandler fileIOHandler;

    public ClientHandler(Socket socket, int clientNumber) {
        this.socket = socket;
        this.clientNumber = clientNumber;
    }

    public void run(){ // Création de thread qui envoi un message à un client
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            System.out.println("Streams successfully opened for client#" + clientNumber);

            while (true) {
                // Read the message from the client
                String clientMessage = in.readUTF();
                String[] commandParts = clientMessage.split(" ", 2);
                String cmdName = commandParts[0];
                String arg = (commandParts.length > 1) ? commandParts[1] : null;

                switch (cmdName) {
                    case "ls":
                        out.writeUTF(fileManager.listDirectory());
                        break;

                    case "cd":
                        fileManager.changeDirectory(arg);
                        out.writeUTF("Vous etes dans le dossier " + arg);
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

                // Send a response back to the client
                out.writeUTF("Server received: " + clientMessage);
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
}