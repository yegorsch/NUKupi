package DB;

import Models.Product;
import Models.ProductCollection;
import Models.User;
import Models.UserCollection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class UserDatabaseClient extends DatabaseClient {

    public UserDatabaseClient() {
        super();
    }

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

    public boolean runQueryInsertUser(String user_id, String email, String password, String name, String type, String phone_number) {
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into user values (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, user_id);
            stmt.setString(2, email);
            stmt.setString(3, password);
            stmt.setString(4, name);
            stmt.setString(5, type);
            stmt.setString(6, phone_number);
            int count = stmt.executeUpdate();
            if (count > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // TODO: Please check it????
    // TODO: Fix SQL Injection
    public UserCollection runQueryUsersAll() {
        UserCollection users = new UserCollection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "select * from user"
            );
            fillProducts(users, rs);
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public UserCollection runQueryUserByEmail(String email) {
        UserCollection users = new UserCollection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "select * from user where email='"+email+"';"
            );
            fillProducts(users, rs);
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    private void fillProducts(UserCollection users, ResultSet rs) throws SQLException {
        while (rs.next()) {
            User u = new User(rs.getString("user_id"), rs.getString("email"),
                    rs.getString("password"), rs.getString("name"),
                    rs.getString("type"), rs.getString("phone_number"));
            users.add(u);
        }
    }

}
