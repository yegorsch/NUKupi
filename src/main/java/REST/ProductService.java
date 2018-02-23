package REST;

import Models.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;

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
    public String getItems(@QueryParam("first") int numberOfItems) {
        if (numberOfItems > 0) {
            return new Gson().toJson(products.subList(0, numberOfItems));
        }
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
    public Response addProduct(String jsonString) throws IOException {
        try {
            products.add(new Product(jsonString));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Consumes("application/json")
    public Response deleteProduct(String jsonString) {
        ArrayList<String> list = new Gson().fromJson(jsonString, new TypeToken<ArrayList<String>>() {
        }.getType());
        for (String id : list) {
            System.out.println("Item re");
            products.removeIf((Product p) -> p.getID().equals(id));
        }
        return Response.status(Response.Status.OK).build();
    }

}
