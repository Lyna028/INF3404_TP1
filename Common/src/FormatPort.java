public class FormatPort {
    public boolean isValid(Integer portValue) {

        return portValue >= 5000 && 5500 <= portValue;
    }
}
