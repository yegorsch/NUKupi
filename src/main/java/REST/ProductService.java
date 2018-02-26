package REST;

import Models.Product;
import Utils.Filterer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

//import Utils.Filterer;

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

    /**
     * Retrieve all products
     **/

    @GET
    @Path("/all")
    public Response getAllProducts() {
        Gson gson = new Gson();
        Response.ResponseBuilder b = Response.ok(gson.toJson(products));
        return b.build();
    }
    
    /**
     * Filters
     */

    @GET
    @Path("/filters")
    public Response getProductByFilters(@QueryParam("title") String title,
                                        @QueryParam("price") double price,
                                        @QueryParam("category") String category) {
        System.out.println(title);
        System.out.println(price);
        System.out.println(category);
        ArrayList<Product> result = new Filterer(products).filter(title, price, category);

        return Response.ok(new Gson().toJson(result)).build();
    }

    /**
     * Retrieve products owned by particular user (change email for user id)
     **/

    @GET
    @Path("/myproducts")
    public Response getProductByUser(@QueryParam("id") String id) {
        ArrayList<Product> reqProds = new ArrayList<>();
        if (id != null) {
            for (Product p : products) {
                if (p.getAuthorID().equals(id)) {
                    reqProds.add(p);
                }
            }
        }
        return Response.ok(new Gson().toJson(reqProds)).build();
    }

//    @GET
//    @Path("{ id : [A-Za-z0-9_]+}")
//    public String getProduct(@PathParam("id") String id) {
//        System.out.println(id);
//        for (Product p : products) {
//            if (p.getID().equals(id)) {
//                return p.toJSON();
//            }
//        }
//        return "";
//    }

    @POST
    @Consumes("application/json")
    public Response addProduct(String jsonString) {
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
            products.removeIf((Product p) -> p.getID().equals(id));
        }
        return Response.status(Response.Status.OK).build();
    }

}
