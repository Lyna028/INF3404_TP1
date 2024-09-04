public class FormatIP extends Exception{

    private static final String IPV4_REGEX = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

    public boolean isValid(String IPValue) throws Exception {
        if (!IPValue.matches(IPV4_REGEX)) {
            throw new Exception("Invalid IP address: " + IPValue);
        }
        System.out.println("Valid IP address: " + IPValue);
        return IPValue.matches(IPV4_REGEX);
    }
}
