package DB;

import Models.Image;
import Models.Product;
import Models.ProductCollection;
import com.sun.org.apache.xerces.internal.xs.StringList;

import java.sql.*;
import java.util.*;

public class DatabaseClient {

    private String userName = "o.bakhtiyar";
    private String password = "C1Q1VT02";
    private String dbms = "mysql";
    private String serverName = "46.101.171.158";
    private String portNumber = "80";
    private String dbname = "ospanov_bakhtiyar";

    protected Connection conn = null;

    public DatabaseClient() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {

        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.userName);
        connectionProps.put("password", this.password);

        if (this.dbms.equals("mysql")) {
            conn = DriverManager.getConnection(
                    "jdbc:" + this.dbms + "://" +
                            this.serverName +
                            ":" + this.portNumber + "/" + this.dbname,
                    connectionProps);
        }
        System.out.println("Connected to database");
        return conn;
    }



}
