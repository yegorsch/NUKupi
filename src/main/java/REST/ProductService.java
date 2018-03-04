package REST;

import DB.DatabaseClient;
import DB.ProductDatabaseClient;
import Models.Product;
import Models.ProductCollection;
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

    private ProductDatabaseClient dbc;
    private int start;
    private int end;
    private int prodsize;

    public ProductService() {
        dbc = new ProductDatabaseClient();
    }

    @GET
    public String getItems(@QueryParam("first") int numberOfItems) {
    //TODO: ADD PAGINATION,
        return "NOT YEt";
    }

    /**
     * Retrieve all products
     * Assumption that pagenum is never null and all pages contain at least one product
     */

    @SuppressWarnings("Duplicates")
    @GET
    @Path("/all")
    public Response getAllProducts(@QueryParam("pagenum") int pagenum) {
        ProductCollection prodpaged = new ProductCollection();
        start = (pagenum-1)*15;
        end = pagenum*15;
        ProductCollection prod = dbc.runQueryProductsAll();
        prodsize = prod.size();
        if(prodsize<end) {
            for(int i=start; i<prodsize; i++) {
                prodpaged.add(prod.get(i));
            }
        } else {
            for(int i=start; i<end; i++) {
                prodpaged.add(prod.get(i));
            }
        }
        Gson gson = new Gson();
        Response.ResponseBuilder b = Response.ok("{\"products\":" + gson.toJson(prodpaged) + ", \"size\":" + prodpaged.size()+"}");
        return b.build();
    }

    /**
     * Filters
     * Assumption that pagenum is never null and all pages contain at least one product
     */

    @SuppressWarnings("Duplicates")
    @GET
    @Path("/filters")
    public Response getProductByFilters(@QueryParam("title") String title,
                                        @QueryParam("price") int price,
                                        @QueryParam("category") String category,
                                        @QueryParam("pagenum") int pagenum) {
        //TODO: USE SQL INSTEAD OF FILTERER, SEE https://stackoverflow.com/questions/10185638/optional-arguments-in-where-clause
        //ProductCollection result = dbc.runQueryProductsAll();
        //result = new Filterer(result).filter(title, price, category);
        //return Response.ok(result.toJson()).build();
        ProductCollection prodpaged = new ProductCollection();
        start = (pagenum-1)*15;
        end = pagenum*15;
        ProductCollection result = dbc.runQueryProductsByFilter(title, price, category);
        prodsize = result.size();
        if(prodsize<end) {
            for(int i=start; i<prodsize; i++) {
                prodpaged.add(result.get(i));
            }
        } else {
            for(int i=start; i<end; i++) {
                prodpaged.add(result.get(i));
            }
        }
        Gson gson = new Gson();
        Response.ResponseBuilder b = Response.ok("{\"products\":" + gson.toJson(prodpaged) + ", \"size\":" + prodpaged.size()+"}");
        return b.build();
    }

    /**
     * Retrieve products owned by particular user (change email for user id)
     **/

    @GET
    @Path("/myproducts")
    public Response getProductByUser(@QueryParam("id") String id) {
        ProductCollection reqProds = null;
        if (id != null) {
            reqProds = dbc.runQueryProductsByUser(id);
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
        Product p;
        try {
            p = new Product(jsonString);
        } catch (Exception e){
            return Response.status(400).build();
        }
        //TODO: SEND PRODUCT MODEL INSTEAD OF EACH PARAMETER
        if (dbc.runQueryInsertProduct(p.getID(), p.getTitle(), p.getDescription(),p.getPrice(), p.getCategory(), p.getAuthorID()))
            return Response.status(Response.Status.OK).build();
        else
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }

    @DELETE
    @Consumes("application/json")
    public Response deleteProduct(String jsonString) {
        ArrayList<String> list = new Gson().fromJson(jsonString, new TypeToken<ArrayList<String>>() {
        }.getType());
        for (String id : list) {
            if (!dbc.runQueryDeleteProduct(id))
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
        return Response.status(Response.Status.OK).build();
    }

}
