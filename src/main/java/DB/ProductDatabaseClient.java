package DB;

import Models.Product;
import Models.ProductCollection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class ProductDatabaseClient extends DatabaseClient {

    public ProductDatabaseClient() {
        super();
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

//TODO: FIX SQL INJECTION
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

    //TODO: FIX SQL INJECTION
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
    //TODO: FIX SQL INJECTION
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

}
