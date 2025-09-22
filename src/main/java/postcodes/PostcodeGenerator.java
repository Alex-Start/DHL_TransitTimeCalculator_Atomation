package postcodes;

public interface PostcodeGenerator {
    String generateValid();
    String generateInvalid();
    boolean isValid(String postcode);
}
