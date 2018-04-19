package Models;

public class ProductInfo {

    public String product_id;
    public String user_id;

    public ProductInfo(String product_id, String user_id) {
        this.product_id = product_id;
        this.user_id = user_id;
    }

    public String getProductId() {
        return product_id;
    }

    public String getUserId() {
        return user_id;
    }

}
