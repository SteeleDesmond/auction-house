package ah.bank;

/**
 * Generate a random string sequence for use by the Auction House and Agent bank accounts
 * Relevant information: https://dzone.com/articles/generate-random-alpha-numeric
 */
public class KeyGenerator {

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private String key;

    public KeyGenerator() {
        key = generateKey(10);
    }

    private static String generateKey(int count) {

        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public String getKey() {
        return key;
    }
}
