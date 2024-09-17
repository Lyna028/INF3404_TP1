import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private int clientNumber;

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

                // If the client sends "exit", close the connection
                if (clientMessage.equalsIgnoreCase("exit")) {
                    break;
                }

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