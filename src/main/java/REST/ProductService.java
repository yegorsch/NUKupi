package REST;

import Models.Product;
//import Utils.Filterer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashSet;

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

//    /**
//     * Filtering path
//     */
//
//    @GET
//    @Path("/filter")
//    public Response mainFilterer(@QueryParam("title") String title,
//                                      @QueryParam("category") String category,
//                                      @QueryParam("type") String type,
//                                      @QueryParam("price") double price,
//                                      @QueryParam("units") String units,
//                                      @QueryParam("email") String email) {
//        ArrayList<Product> reqProds = new ArrayList<Product>();
//        Filterer filterer = new Filterer(products);
//        // By title
//        reqProds.addAll(filterer.getProductByTitle(title));
//        // Category
//        reqProds.addAll(filterer.getProductByCategory(category));
//        // Type, price units
//        reqProds.addAll(filterer.getProductByPrice(type, price, units));
//        // Email
//        reqProds.addAll(filterer.getProductByUser(email));
//
//        ArrayList<Product> uniqueList = new ArrayList<>(new HashSet<Product>(reqProds));
//        return Response.ok(new Gson().toJson(uniqueList)).build();
//    }


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
        ArrayList<Product> reqProds = new ArrayList<>();
        if(title==null && category==null) {
            for (Product p : products) {
                if (p.getPrice()<=price) {
                    reqProds.add(p);
                }
            }
        } else if (title==null && category!=null) {
            for (Product p : products) {
                if (p.getPrice()<=price && p.getCategory().equals(category)) {
                    reqProds.add(p);
                }
            }
        } else if (title!=null && category==null) {
            for (Product p : products) {
                if (p.getPrice()<=price && p.getTitle().toLowerCase().contains(title.toLowerCase())) {
                    reqProds.add(p);
                }
            }
        } else {
            for (Product p : products) {
                if (p.getPrice()<=price && p.getCategory().equals(category) && p.getTitle().toLowerCase().contains(title.toLowerCase())) {
                    reqProds.add(p);
                }
            }
        }
        return Response.ok(new Gson().toJson(reqProds)).build();
    }

    /**
     * Retrieve products owned by particular user (change email for user id)
     **/

    @GET
    @Path("/myproducts")
    public Response getProductByUser(@QueryParam("email") String email) {
        ArrayList<Product> reqProds = new ArrayList<>();
        if (email != null) {
            for (Product p : products) {
                if (p.getAuthorEmail().equals(email)) {
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
