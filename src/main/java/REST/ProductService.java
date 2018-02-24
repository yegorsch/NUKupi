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

//    @GET
//    public String getItems(@QueryParam("first") int numberOfItems) {
//        if (numberOfItems > 0) {
//            return new Gson().toJson(products.subList(0, numberOfItems));
//        }
//        return new Gson().toJson(products);
//    }

    /** Retrieve all products **/

    @GET
    @Path("/all")
    public Response getAllProducts() {
        Gson gson = new Gson();
        Response.ResponseBuilder b = Response.ok(gson.toJson(products));
        return b.build();
    }

    /**Retrieve products by title **/

    @GET
    @Path("/title")
    public Response getProductByTitle(@QueryParam("thetitle") String title) {
        ArrayList<Product> reqProds = new ArrayList<Product>();
        if (!title.equals(null)) {
            for (Product p : products) {
                if (p.getTitle().toLowerCase().contains(title.toLowerCase()) || p.getDescription().toLowerCase().contains(title.toLowerCase())) {
                    reqProds.add(p);
                }
            }
        }
        if (reqProds.size()!=0) {
            Gson gson = new Gson();
            Response.ResponseBuilder b = Response.ok(gson.toJson(reqProds));
            return b.build();
        } else {
            Response.ResponseBuilder b = Response.ok("No products with such a title.");
            return b.build();
        }
    }

    /** Retrieve products by categories **/

    @GET
    @Path("/categories")
    public Response getProductByCategory(@QueryParam("category") String category) {
        ArrayList<Product> reqProds = new ArrayList<Product>();
        if (!category.equals(null)) {
            for (Product p : products) {
                if (p.getCategory().equals(category)) {
                    reqProds.add(p);
                }
            }
        }
        if (reqProds.size()!=0) {
            Gson gson = new Gson();
            Response.ResponseBuilder b = Response.ok(gson.toJson(reqProds));
            return b.build();
        } else {
            Response.ResponseBuilder b = Response.ok("No products in this category.");
            return b.build();
        }
    }

    /** Retrieve products by price filters **/

    @GET
    @Path("/payment")
    public Response getProductByPrice(@QueryParam("type") String type, @QueryParam("price") double price, @QueryParam("units") String units) {
        ArrayList<Product> reqProds = new ArrayList<Product>();
        if (type.equals("FREE")) {
            for (Product p : products) {
                if (p.getPaymentType().equals("FREE")) {
                    reqProds.add(p);
                }
            }
        } else if (type.equals("EXCHANGE")) {
            for (Product p : products) {
                if (p.getPaymentType().equals("EXCHANGE")) {
                    reqProds.add(p);
                }
            }
        } else if (type.equals("REGULAR") && price>=0 && !units.equals(null)) {
            for (Product p : products) {
                if (p.getPaymentType().equals("REGULAR") && p.getPrice()<=price && p.getUnits().equals(units)) {
                    reqProds.add(p);
                }
            }
        }
        if(reqProds.size()!=0) {
            Gson gson = new Gson();
            Response.ResponseBuilder b = Response.ok(gson.toJson(reqProds));
            b.header("header-name", "value");
            return b.build();
        } else {
            Response.ResponseBuilder b = Response.ok("No matching result found.");
            return b.build();
        }
    }

    /** Retrieve products owned by particular user (change email for user id) **/

    @GET
    @Path("/author")
    public Response getProductByUser(@QueryParam("authoremail") String authoremail) {
        System.out.println(authoremail);
        ArrayList<Product> reqProds = new ArrayList<Product>();
        if (!authoremail.equals(null)) {
            for (Product p : products) {
                if (p.getAuthorEmail().equals(authoremail)) {
                    reqProds.add(p);
                }
            }
        }
        if (reqProds.size()!=0) {
            Gson gson = new Gson();
            Response.ResponseBuilder b = Response.ok(gson.toJson(reqProds));
            return b.build();
        } else {
            Response.ResponseBuilder b = Response.ok("User does not own any products.");
            return b.build();
        }
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
