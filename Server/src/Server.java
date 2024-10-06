import java.awt.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class Server {
    private static ServerSocket Listener;
    private static Socket socket;
    // Application Serveur

    private static FileManager fm;



    public static void main(String[] args) throws Exception {
        // Compteur incrémenté à chaque connexion d'un client au serveur
        int clientNumber = 0;

        IdentifiantServeur idsServeur = new IdentifiantServeur();

        // Adress of the server ex: 127.0.0.1
        String serverAddress = idsServeur.getAdress();

        // Port of the server ex : 5000
        int serverPort = idsServeur.getPort();

        FormatCombined formatObj = new FormatCombined();
        try{
            formatObj.isValid(serverPort, serverAddress);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        // Création de la connexien pour communiquer ave les clients
        Listener = new ServerSocket();
        Listener.setReuseAddress(true);
        InetAddress serverIP = InetAddress.getByName(serverAddress);
        // Association de l'adresse et du port à la connexien
        Listener.bind(new InetSocketAddress(serverIP, serverPort));
        System.out.format("The server is running on %s:%d%n", serverAddress, serverPort);

        socket = new Socket(serverAddress, serverPort);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        try {
            // À chaque fois qu'un nouveau client se, connecte, on exécute la fonstion
            // run() de l'objet ClientHandler
            while (true) {
                // Important : la fonction accept() est bloquante: attend qu'un prochain client se connecte
                // Une nouvetle connection : on incémente le compteur clientNumber

                new ClientHandler(Listener.accept(), clientNumber++).start();

            }
        } finally {
            // Fermeture de la connexion
            Listener.close();

        }  }
} // pour traiter la demande de chaque client sur un socket particulier



