
import org.apache.commons.lang.RandomStringUtils;

public class IDGenerator {

    private static IDGenerator ourInstance = new IDGenerator();

    public static final int DEFAULT_LENGTH = 10;

    public static IDGenerator getInstance() {
        return ourInstance;
    }

    private IDGenerator() { }

    public static String generateIDWithDefaultLength() {
        return RandomStringUtils.randomAlphanumeric(DEFAULT_LENGTH);
    }

    public static String generateIDWithLength(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }
}
