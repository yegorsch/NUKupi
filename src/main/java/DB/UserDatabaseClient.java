package DB;

import Models.User;
import Models.UserCollection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDatabaseClient extends DatabaseClient {

    public UserDatabaseClient() {
        super();
    }

    public boolean runQueryEmail(String email) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "select user_id " +
                            "from user " +
                            "where email='" + email + "'" + ";"
            );
            if (rs.next() == true) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String runQueryUserIdByEmail(String email) {
        String userId = "";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "select user_id " +
                            "from user " +
                            "where email='" + email + "'" + ";"
            );
            if (rs.next())
                userId = rs.getString("user_id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userId;
    }

    public String runQueryUserInfoById(String userId) {
        String info = "";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "select email, phone_number " +
                            "from user " +
                            "where user_id='" + userId + "'" + ";"
            );
            if (rs.next())
                info = rs.getString("email") + "," + rs.getString("phone_number");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    public String runQueryUserPasswordByEmail(String email) {
        String password = "";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "select password " +
                            "from user " +
                            "where email='" + email + "'" + ";"
            );
            if (rs.next())
                password = rs.getString("password");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }

    public boolean runQueryUpdateUserPassword(String userId, String newPassword) {
        try {
            Statement stmt = conn.createStatement();
            int count = stmt.executeUpdate(
                    "update user set password='" + newPassword + "'" +
                            " where user_id='" + userId + "';"
            );
            if (count > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean runQueryLogIn(String email, String password) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "select user_id " +
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

    public boolean runQueryCheckPassword(String userId, String password) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "select user_id " +
                            "from user " +
                            "where user_id='" + userId + "'" + " and password='" + password + "'" + ";"
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

    public UserCollection runQueryUsersAll() {
        UserCollection users = new UserCollection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "select * from user"
            );
            fillUsers(users, rs);
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public UserCollection runQueryUserById(String userId) {
        UserCollection users = new UserCollection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "select * from user where user_id='"+userId+"';"
            );
            fillUsers(users, rs);
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    private void fillUsers(UserCollection users, ResultSet rs) throws SQLException {
        while (rs.next()) {
            User u = new User(rs.getString("user_id"), rs.getString("email"),
                    rs.getString("password"), rs.getString("name"),
                    rs.getString("type"), rs.getString("phone_number"));
            users.add(u);
        }
    }

}
