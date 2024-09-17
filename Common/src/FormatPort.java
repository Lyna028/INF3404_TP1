public class FormatPort extends Exception{
    public boolean isValid(int portValue) throws Exception {
        if (portValue < 5000 || portValue > 5500) {
            throw new Exception("Port value " + portValue + " is out of the valid range (5000-5500).");
        }

        // If the port is within the valid range
        Boolean result = (portValue >= 5000) && (portValue <= 5500);
        System.out.println("PORT: " + portValue + " " + result);

        return result;
    }
}
