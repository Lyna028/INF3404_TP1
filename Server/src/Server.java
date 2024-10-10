import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Listens for client connections and handles multiple clients using threads.
 * Each client connection is managed by a `ClientHandler` that processes the commands sent by each client.
 */
public class Server {
    private static ServerSocket Listener;
    private static Socket socket;
    private static FileManager fm;


    /**
     * Initializes the server with a specific IP address and port, and listens for incoming client connections
     * For each new client, a separate `ClientHandler` thread is created to manage the client's requests.
     *
     * @param args arguments
     * @throws Exception If an error occurs during server initialization or socket communication.
     */
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



