package REST;

import Models.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.ws.rs.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

// URL: http://localhost:8080/rest/products
@Path("products")
public class ProductService {

    ArrayList<Product> products;

    public ProductService() {
        products = new ArrayList<Product>();
        for (int i = 0; i < 10; i++) {
            Product p = new Product();
            p.setTitle("Product" + String.valueOf(i));
            products.add(p);
        }
    }

    @GET
    public String getItems() {
        return new Gson().toJson(products);
    }

    @GET
    @Path("{ id : [A-Za-z0-9_]+}")
    public String getProduct(@PathParam("id") String id) {
        System.out.println(id);
        for (Product p : products) {
            if (p.getID().equals(id)) {
                return p.toJSON();
            }
        }
        return "";
    }

    @POST
    @Consumes("application/json")
    public void addProduct(String jsonString) throws IOException {
        try {
            products.add(new Product(jsonString));
        } catch (Exception e) {
            // crash and burn
            System.out.println(e.getMessage());
            throw new IOException("Error parsing JSON request string");
        }
    }

}
