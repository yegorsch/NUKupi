package Utils;

import junit.framework.TestCase;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class HasherTest extends TestCase {

    static private final String password = "12345";
    static private final String password2 = "123456";

    public void testHashing() {
        String hashed = Hasher.encodeSHA256(password);
        System.out.println(hashed);
        assertEquals("5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5", hashed);
    }

    public void testHashingWrong() {
        String hashed = Hasher.encodeSHA256(password2);
        assertNotEquals(Hasher.encodeSHA256(password), hashed);
    }
}
