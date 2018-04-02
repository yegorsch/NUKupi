package Utils;

import org.apache.commons.lang.RandomStringUtils;

public class UniqueStringGenerator {

    private static UniqueStringGenerator ourInstance = new UniqueStringGenerator();

    public static final int DEFAULT_LENGTH = 15;

    public static UniqueStringGenerator getInstance() {
        return ourInstance;
    }

    private UniqueStringGenerator() { }

    public static String generateIDWithDefaultLength() {
        return RandomStringUtils.randomAlphanumeric(DEFAULT_LENGTH);
    }

    public static String generateIDWithLength(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public static String generatePasswordWithDefaultLength() { return RandomStringUtils.randomAlphanumeric(DEFAULT_LENGTH); }

    public static String generatePasswordWithLength(int length) { return RandomStringUtils.randomAlphanumeric(length); }
}
