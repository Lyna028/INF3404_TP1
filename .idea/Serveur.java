import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Serveur {
    private static ServerSocket Listener;
    // Application Serveur

    public static void main(String[] args) throws Exception {
        // Compteur incrémenté à chaque connexion d'un client au serveur
        int clientNumber = 0;
        // Adresse et port du serveur
        // String serverAddress = "127.0.0.1";
        // int serverPort = 5000;
        // Création de la connexien pour communiquer ave les,
        clients Listener = new ServerSocket();
        Listener.setReuseAddress(true);
        InetAddress serverIP = InetAddress.getByName(serverAddress);
        // Association de l'adresse et du port à la connexien
        Listener.bind(new InetSocketAddress(serverIP, serverPort));
        System.out.format("The server is running on %s:%d%n", serverAddress, serverPort);
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
        }  }  } // pour traiter la demande de chaque client sur un socket particulier


public class ClientHandler extends Thread {
    private Socket socket;
    private int clientNumber;
    public ClientHandler(Socket socket, int clientNumber) {
        this.socket = socket;
        this.clientNumber = clientNumber;
        System.out.println("New connection with client#" + clientNumber + " at" + socket);
    }


    public void run() { // Création de thread qui envoi un message à un client
         try {
             DataOutputStream out = new DataOutputStream(socket.getOutputStream()); // création de canal d’envoi
             out.writeUTF("Hello from server - you are client#" + clientNumber); // envoi de message
             } catch (IOException e) {
                System.out.println("Error handling client# " + clientNumber + ": " + e); }
         finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Couldn't close a socket, what's going on?");
                }
             System.out.println("Connection with client# " + clientNumber + " closed");
         }



