package DB.QueryCreators;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductQueryCreator {

    private static ProductQueryCreator ourInstance = new ProductQueryCreator();
    private Connection conn;

    public static ProductQueryCreator getInstance() {
        return ourInstance;
    }

    private final static String PRODUCTS_BY_USER_QUERY = "select product_id, title, description, price, category, p_user_id, " +
                                                            "group_concat(image_id) as image_ids " +
                                                            "from product " +
                                                            "left join image on product.product_id = image.i_product_id " +
                                                            "where p_user_id=(?) " +
                                                            "group by product_id";

    private final static String DELETE_PRODUCT_QUERY = "delete from product where product_id=(?)";

    public PreparedStatement productsByUser(String p_user_id) {
        PreparedStatement p = null;
        try {
            p = conn.prepareStatement(PRODUCTS_BY_USER_QUERY);
            p.setString(1, p_user_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    public PreparedStatement deleteProduct(String product_id) {
        PreparedStatement p = null;
        try {
            p = conn.prepareStatement(DELETE_PRODUCT_QUERY);
            p.setString(1, product_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    private ProductQueryCreator() {
    }

    public void setConnection(Connection conn){
        this.conn = conn;
    }

}
