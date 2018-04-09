package Utils;

import DB.DatabaseClient;
import DB.QueryCreators.ProductQueryCreator;
import junit.framework.TestCase;

import java.sql.PreparedStatement;

public class ProductQueryTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
        ProductQueryCreator.getInstance().setConnection(new DatabaseClient().getConnection());
    }

    public void testProductsByUserQuery() {
        PreparedStatement p = ProductQueryCreator.getInstance().productsByUser("12345");
        String sql = "select product_id, title, description, price, category, p_user_id from product where p_user_id=('12345')";
        assertEquals(p.toString().split(":")[1], sql);
    }

    public void testDeleteProduct() {
        PreparedStatement p = ProductQueryCreator.getInstance().deleteProduct("12345");
        String sql = "delete from product where product_id=('12345')";
        assertEquals(p.toString().split(":")[1], sql);
    }

}
