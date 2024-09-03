import java.io.DataInputStream;
import java.net.Socket;
import java.util.Scanner;
// Application client

public class Client {
    private static Socket socket;
    public static void main(String[] args) throws Exception {
        // Adress of the server 
        Scanner serverAddressScanner = new Scanner(System.in);  
        System.out.println("Enter server adress : ");
        String serverAddress = serverAddressScanner.nextLine();  

        // Port of the server 
        Scanner portScanner = new Scanner(System.in); 
        System.out.println("Enter server port : ");
        String serverPortString = portScanner.nextLine();  
        int serverPort = Integer.parseInt(serverPortString);

        // Création d'une nouvelle connexion aves le serveur
        socket = new Socket(serverAddress, serverPort);
        System.out.format("Serveur lancé sur [%s:%d]", serverAddress, serverPort);
        // Céatien d'un canal entrant pour recevoir les messages envoyés, par le serveur
        DataInputStream in = new DataInputStream(socket.getInputStream());
        // Attente de la réception d'un message envoyé par le, server sur le canal
        String helloMessageFromServer = in.readUTF();
        System.out.println(helloMessageFromServer);
        // fermeture de La connexion avec le serveur
        socket.close(); } }
