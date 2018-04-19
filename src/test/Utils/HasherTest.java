package Utils;

import junit.framework.TestCase;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class HasherTest extends TestCase {

    static private final String password = "12345";
    static private final String password2 = "123456";

    public void testHashing() {
        String hashed = Hasher.encodeSHA256(password);
        System.out.println(hashed);
        assertEquals("9999", hashed);
    }

    public void testHashingWrong() {
        String hashed = Hasher.encodeSHA256(password2);
        System.out.println(Hasher.encodeSHA256("arayka"));
        assertNotEquals(Hasher.encodeSHA256(password), hashed);
    }
}
