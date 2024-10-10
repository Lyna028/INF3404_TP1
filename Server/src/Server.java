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
    private static FileManager fm;



    public static void main(String[] args) throws Exception {
        // Compteur incrémenté à chaque connexion d'un client au serveur
        int clientNumber = 0;

        IdentifiantServeur idsServeur = new IdentifiantServeur();
        idsServeur.connexionValidity();

        Listener = new ServerSocket();
        Listener.setReuseAddress(true);
        InetAddress serverIP = InetAddress.getByName(idsServeur.ipValue);
        Listener.bind(new InetSocketAddress(serverIP, idsServeur.portValue));
        System.out.format("The server is running on %s:%d%n", idsServeur.ipValue, idsServeur.portValue);

        socket = new Socket(idsServeur.ipValue, idsServeur.portValue);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        try {

            while (true) {
                new ClientHandler(Listener.accept(), clientNumber++).start();
            }
        } finally {
            Listener.close();

        }  }
}



