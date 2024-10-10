import java.util.Scanner;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Scanner;

/**
 * The IdentifiantServeur class is responsible for handling server
 * connection details including the server's IP address and port number.
 * It validates the input values to ensure they conform to expected
 * formats and ranges.
 */
public class IdentifiantServeur {
    private Scanner scanner = new Scanner(System.in);
    public int portValue;
    public String ipValue;
    private static final String IPV4_REGEX = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

    /**
     * Requests the user to enter the server address (ipvalue).
     * The method continues to prompt until a non-empty value is provided.
     * The ipValue is stored in the class.
     */
    public void getAdress (){
        // Address of the server ex: 127.0.0.1
            System.out.print("Enter server address : ");
            this.ipValue = scanner.nextLine();
            while(this.ipValue.matches("")){
                System.out.print("Enter server address : ");
                this.ipValue = scanner.nextLine();
            }
    }

    /**
     * Requests the user to enter the server port (portValue).
     * The method continues to prompt until a non-empty value is provided.
     * The value is then parsed to an integer and stored in the clas attribute portValue.
     */
    public void getPort(){
        // Port of the server ex : 5000
        System.out.print("Enter server port : ");
        String serverPortString = scanner.nextLine();
        while(serverPortString.matches("")){
            System.out.print("Enter server port : ");
            serverPortString = scanner.nextLine();
        }
        this.portValue= Integer.parseInt(serverPortString);



    }

    /**
     * Validates the format of the IP address using the regular expression of the class that respect IPv4 format.
     * @return true if the IP address is valid, false otherwise.
     */
    public boolean isIPValid() {
        boolean result = this.ipValue.matches(IPV4_REGEX);
        if(!result){
            System.out.println("Invalid IP address");
        }
        return result;
    }

    /**
     * Validates the port number to ensure it falls within the valid range (5000-5500).
     * @return true if the port number is valid, false otherwise.
     */
    public boolean isPortValid(){
        // If the port is within the valid range
        Boolean result = (this.portValue >= 5000) && (this.portValue <= 5500);
        if(!result){
            System.out.println("Invalid port value");
        }
        return result;
    }

    /**
     * Validates both the IP address and the port number.
     * @return true if both are valid, false otherwise.
     */
    public boolean isIdValid() {
        Boolean ipValidity = this.isIPValid();
        Boolean portValidity = this.isPortValid();
        return  ipValidity && portValidity;
    }

    /**
     * Manages the process of obtaining valid server connection details.
     * It requests the user for the address and port until both are valid.
     */
    public void connexionValidity(){
        this.getAdress();
        this.getPort();
        while(!this.isIdValid()){
            System.out.println("Please try again");
            this.getAdress();
            this.getPort();
        }
    }

}
