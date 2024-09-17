import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;
// Application client

public class Client {
    private static Socket socket;
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in); 

        // Adress of the server  
        System.out.println("Enter server adress : ");
        String serverAddress = scanner.nextLine();  

        // Port of the server 
        System.out.println("Enter server port : ");
        String serverPortString = scanner.nextLine();  
        int serverPort = Integer.parseInt(serverPortString);

        // New cnnection with the server
        socket = new Socket(serverAddress, serverPort);
        System.out.format("Serveur lancé sur [%s:%d]", serverAddress, serverPort);

        // Input and output streams to recieve and send datas to the server 
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        while (true) {
            System.out.println("Enter command : ");
            String command = scanner.nextLine();

            // Sending the command to the server 
            //if (command.startsWith("mkdir"))
            out.writeUTF(command);
            out.flush();
            
            if(command == "exit") {
                break;
            }
            String helloMessageFromServer = in.readUTF();
            System.out.println(helloMessageFromServer);
        }
        
        // closing the connection with the server 
        socket.close(); 
    } 
}