package DB;

import Models.Image;
import Models.Product;
import javassist.bytecode.ByteArray;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DatabaseClient {

    String userName = "o.bakhtiyar";
    String password = "C1Q1VT02";
    String dbms = "mysql";
    String serverName = "46.101.171.158";
    String portNumber = "80";
    String dbname = "ospanov_bakhtiyar";

    Connection conn = null;

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

    public boolean runQueryInsertProduct(String product_id, String title, String description, int price, String category, String p_user_id) {
        try {
            Statement stmt = conn.createStatement();
            int count = stmt.executeUpdate(
                    "insert into product values ('" + product_id + "','"
                            + title + "','" + description + "',"
                            + price + ",'" + category
                            + "','" + p_user_id + "');"
            );
            if (count > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Product> runQueryProductsAll() {
        ArrayList<Product> products = new ArrayList<Product>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "select product_id, title, description, price, category, p_user_id " +
                            "from product;"
            );

            while (rs.next()) {
                products.add(new Product(rs.getString("product_id"), rs.getString("title"),
                        rs.getString("description"), rs.getString("p_user_id"),
                        rs.getInt("price"), rs.getString("category")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public ArrayList<Product> runQueryProductsByUser(String p_user_id) {
        ArrayList<Product> products = new ArrayList<Product>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "select product_id, title, description, price, category, p_user_id " +
                            "from product " + "where p_user_id='" + p_user_id + "'" + ";"
            );

            while (rs.next()) {
                products.add(new Product(rs.getString("product_id"), rs.getString("title"),
                        rs.getString("description"), rs.getString("p_user_id"),
                        rs.getInt("price"), rs.getString("category")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public ArrayList<Product> getAllProducts(String title, int price, String category) {
        ArrayList<Product> products = new ArrayList<Product>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = null;
            if (category == null)
                rs = stmt.executeQuery(
                        "select product_id, title, description, price, category, p_user_id " +
                                "from product " + "where title like '%" + title + "%' and price <= "
                                + price + ";");
            else if (title == null)
                rs = stmt.executeQuery(
                        "select product_id, title, description, price, category, p_user_id " +
                                "from product " + "where category like '%" + category + "%' and price <= "
                                + price + ";");
            else if (title != null && category != null)
                rs = stmt.executeQuery(
                        "select product_id, title, description, price, category, p_user_id " +
                                "from product " + "where title like '%" + title + "%' and category like '%" +
                                category + "%' and price <= "
                                + price + ";");

            while (rs.next()) {
                products.add(new Product(rs.getString("product_id"), rs.getString("title"),
                        rs.getString("description"), rs.getString("p_user_id"),
                        rs.getInt("price"), rs.getString("category")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public ArrayList<Product> runQueryProductsByFilter(String title, int price, String category) {
        ArrayList<Product> products = new ArrayList<Product>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = null;
            if (category == null)
                rs = stmt.executeQuery(
                        "select product_id, title, description, price, category, p_user_id " +
                                "from product " + "where title like '%" + title + "%' and price <= "
                                + price + ";");
            else if (title == null)
                rs = stmt.executeQuery(
                        "select product_id, title, description, price, category, p_user_id " +
                                "from product " + "where category like '%" + category + "%' and price <= "
                                + price + ";");
            else if (title != null && category != null)
                rs = stmt.executeQuery(
                        "select product_id, title, description, price, category, p_user_id " +
                                "from product " + "where title like '%" + title + "%' and category like '%" +
                                category + "%' and price <= "
                                + price + ";");

            while (rs.next()) {
                products.add(new Product(rs.getString("product_id"), rs.getString("title"),
                        rs.getString("description"), rs.getString("p_user_id"),
                        rs.getInt("price"), rs.getString("category")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public boolean runQueryDeleteProduct(String product_id) {
        try {
            Statement stmt = conn.createStatement();
            int count = stmt.executeUpdate(
                    "delete from product where product_id='" + product_id + "';"
            );
            if (count > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public boolean runQueryInsertImage(String image_id, Double size, Long date, byte[] bytes, String i_product_id) {
        try {
            Statement stmt = conn.createStatement();
            int count = stmt.executeUpdate(
                    "insert into image values ('" + image_id + "',"
                            + size + "," + date + ","
                            + bytes + ",'" + i_product_id
                            + "');"
            );
            if (count > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Image> runQueryGetImages(String image_id) {
        ArrayList<Image> images = new ArrayList<Image>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "select image_id, size, date, bytes, i_product_id " +
                            "from image " + "where image_id='" + image_id + "'" + ";"
            );

            while (rs.next()) {
                images.add(new Image(rs.getString("image_id"), rs.getLong("date"),
                        rs.getDouble("size"), rs.getBytes("bytes"),
                        rs.getString("product_id")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }

    public boolean runQueryDeleteImage(String image_id) {
        try {
            Statement stmt = conn.createStatement();
            int count = stmt.executeUpdate(
                    "delete from image where image_id='" + image_id + "';"
            );
            if (count > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }
}
