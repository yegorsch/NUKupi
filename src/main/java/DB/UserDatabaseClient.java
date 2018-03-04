package DB;

import java.sql.ResultSet;
import java.sql.Statement;

public class UserDatabaseClient extends DatabaseClient {

    public boolean runQueryLogIn(String email, String password) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "select user_id, password " +
                            "from user " +
                            "where email='" + email + "'" + " and password='" + password + "'" + ";"
            );
            if (rs.next() == true) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean runQueryInsertUser(String text, String text1, String text2, String text3, String text4, String text6) {
        try {
            Statement stmt = conn.createStatement();
            int count = stmt.executeUpdate("INSERT INTO user(user_id, email, password, name, type, phone_number)"
                    + "VALUES ('" + text + "','" + text1 + "','" + text2 + "','" + text3 + "','" + text4 + "','" + text6 + "');");
            if (count > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
