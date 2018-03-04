package DB;

import Models.Image;
import Models.Product;
import Models.ProductCollection;
import com.sun.org.apache.xerces.internal.xs.StringList;

import java.sql.*;
import java.util.*;

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
            PreparedStatement stmt = conn.prepareStatement("insert into product values (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, product_id);
            stmt.setString(2, title);
            stmt.setString(3, description);
            stmt.setString(4, String.valueOf(price));
            stmt.setString(5, category);
            stmt.setString(6, p_user_id);
            int count = stmt.executeUpdate();
            if (count > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ProductCollection runQueryProductsAll() {
        ProductCollection products = new ProductCollection();
        try {
            Statement stmt = conn.createStatement();
            Statement imageStmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "select product_id, title, description, price, category, p_user_id, group_concat(image_id) as image_ids" +
                            " from product " +
                            "left join image on product.product_id = image.i_product_id " +
                            "group by product_id"
            );
            while (rs.next()) {
                Product p = new Product(rs.getString("product_id"), rs.getString("title"),
                        rs.getString("description"), rs.getString("p_user_id"),
                        rs.getInt("price"), rs.getString("category"));
                ArrayList<String> imageIDs = new ArrayList<>();
                String imageID = rs.getString("image_ids");
                if (imageID != null) {
                   imageIDs = new ArrayList<String>(Arrays.asList(imageID.split(",")));
                }
                p.setImages(imageIDs);
                products.add(p);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public ProductCollection runQueryProductsByUser(String p_user_id) {
        ProductCollection products = new ProductCollection();

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


    public ProductCollection runQueryProductsByFilter(String title, int price, String category) {
        ProductCollection products = new ProductCollection();

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

    public boolean runQueryInsertImage(Image image) {
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into image values (?,?,?,?,?)");
            stmt.setString(1, image.getID());
            stmt.setString(2, String.valueOf(image.getSize()));
            stmt.setString(3, String.valueOf(image.getDateAdded()));
            stmt.setBytes(4, image.getData());
            stmt.setString(5, image.getProductID());
            int count = stmt.executeUpdate();
            if (count > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public Image runQueryGetImages(String image_id) {
        ArrayList<Image> images = new ArrayList<Image>();
        try {
            PreparedStatement stmt = conn.prepareStatement("select image_id, size, date, bytes, i_product_id " +
                    "from image where image_id = (?)");
            stmt.setString(1, image_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return new Image(rs.getString("image_id"), rs.getLong("date"),
                        rs.getDouble("size"), rs.getBytes("bytes"),
                        rs.getString("i_product_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean runQueryDeleteImage(String image_id) {
        try {
            PreparedStatement stmt = conn.prepareStatement("delete from image where image_id = (?);");
            stmt.setString(1, image_id);
            int status = stmt.executeUpdate();
            if (status == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
