import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;
// Application client

public class Client {
    private static Socket socket;
    public static void main(String[] args) throws Exception {
        // Adresse et port du serveur
        String serverAddress = "127.0.0.1";
        int port = 5000;
        // Création d'une nouvelle connexion aves le serveur
        socket = new Socket(serverAddress, port);
        System.out.format("Serveur lancé sur [%s:%d]", serverAddress, port);
        // Céatien d'un canal entrant pour recevoir les messages envoyés, par le serveur
//        DataInputStream in = new DataInputStream(socket.getInputStream());
        // Attente de la réception d'un message envoyé par le, server sur le canal
//        String helloMessageFromServer = in.readUTF();
//        System.out.println(helloMessageFromServer);

        OutputStream fileOut = socket.getOutputStream();
        String filename = "./Client/src/data.txt";
        FileInputStream fileIn = new FileInputStream(filename);
        byte[] buffer = new byte[1024];
        int bytesRead = 0;

        System.out.println("Sending file");
        while ((bytesRead = fileIn.read(buffer)) != -1) {
            fileOut.write(buffer, 0, bytesRead);
        }



        // fermeture de La connexion avec le serveur
        socket.close(); } }
