package Utils;

import DB.DatabaseClient;
import DB.QueryCreators.UserQueryCreator;
import junit.framework.TestCase;

public class UserQueryTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
        UserQueryCreator.getInstance().setConnection(new DatabaseClient().getConnection());
    }

    public void testUserByEmailQuery() {
        //PreparedStatement p = UserQueryCreator.getInstance().getUserByEmail("test@mail.com");
//        String sql = "select user_id from user where email=('test@mail.com')";
//        assertEquals(p.toString().split(": ")[1], sql);
    }

}