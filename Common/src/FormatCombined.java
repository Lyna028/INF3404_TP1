public class FormatCombined {

    public boolean isValid(Integer portValue, String IPValue) {
        FormatIP ipObj = new FormatIP();
        FormatPort portObj = new FormatPort();
        Boolean ipValidity = ipObj.isValid(IPValue);
        Boolean portValidity = portObj.isValid(portValue);
        return  ipValidity && portValidity;
    }
}
