import java.util.Scanner;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Scanner;

public class IdentifiantServeur {
    private Scanner scanner = new Scanner(System.in);
    public String getAdress (){
        // Address of the server ex: 127.0.0.1
        System.out.print("Enter server address : ");
        return (scanner.nextLine());
    }

    public int getPort(){
        // Port of the server ex : 5000
        System.out.print("Enter server port : ");
        String serverPortString = scanner.nextLine();
        int serverPort = Integer.parseInt(serverPortString);
        return(serverPort);
    }


}
