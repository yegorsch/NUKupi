package DB.QueryCreators;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserQueryCreator {

    private static UserQueryCreator ourInstance = new UserQueryCreator();
    private Connection conn;

    public static UserQueryCreator getInstance() {
        return ourInstance;
    }

    private final static String USER_BY_EMAIL_QUERY =  "select user_id " +
                                                        "from user " +
                                                         "where email=(?)";

    private final static String INFO_BY_ID_QUERY = "select email, phone_number " +
                                                    "from user " +
                                                    "where user_id=(?)";

    private final static String PASSWORD_BY_EMAIL_QUERY = "select password " +
                                                          "from user " +
                                                          "where email=(?)";

    private final static String UPDATE_PASSWORD_QUERY =  "update user set password=(?)" +
                                                         "where user_id=(?)";

    private final static String LOGIN_QUERY  =  "select user_id " +
                                                "from user " +
                                                "where email=(?) and password=(?)";

    private final static String CHECK_PASSWORD_QUERY  = "select user_id " +
                                                        "from user " +
                                                        "where user_id=(?) and password=(?)";

    private final static String INSER_USER_QUERY = "insert into user values (?, ?, ?, ?, ?, ?)";


    private final static String ALL_USERS_QUERY = "select * from user";

    private final static String USER_BY_ID_QUERY = "select * from user where user_id=(?)";

    private final static String USER_TYPE_QUERY =  "select type " +
                                                    "from user " +
                                                    "where user_id=(?)";
    private UserQueryCreator() {
    }

    public void setConnection(Connection conn){
        this.conn = conn;
    }

    public PreparedStatement getUserByEmail(String email) {
        PreparedStatement p = null;
        try {
            p = conn.prepareStatement(USER_BY_EMAIL_QUERY);
            p.setString(1, email);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    public PreparedStatement QueryUserInfoById(String userId) {
        PreparedStatement p = null;
        try {
            p = conn.prepareStatement(INFO_BY_ID_QUERY);
            p.setString(1, userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }
}
