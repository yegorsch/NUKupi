package REST;

import DB.ImageDatabaseClient;
import DB.ProductDatabaseClient;
import DB.UserDatabaseClient;
import Models.Product;
import Models.ProductCollection;
import Models.ProductInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

//import Utils.Filterer;

// URL: http://localhost:8080/f/rest/products
@Path("products")
public class ProductService {

    private ProductDatabaseClient dbc;
    private UserDatabaseClient dbu;
    private ImageDatabaseClient dbi;
    @Context private HttpServletRequest request;

    public ProductService() {
        dbc = new ProductDatabaseClient();
        dbu = new UserDatabaseClient();
        dbi = new ImageDatabaseClient();
    }

    /**
     * Retrieve all products
     * Assumption that pagenum is never null and all pages contain at least one product
     */

    @GET
    @Path("/all")
    public Response getAllProducts() {
        ProductCollection prod = dbc.runQueryProductsAll();
        Gson gson = new Gson();
        Response.ResponseBuilder b = Response.ok(gson.toJson(prod));
        return b.build();
    }

    /**
     * Filters
     * Assumption that pagenum is never null and all pages contain at least one product
     */

    @GET
    @Path("/filters")
    public Response getProductByFilters(@QueryParam("title") String title,
                                        @QueryParam("price") int price,
                                        @QueryParam("category") String category,
                                        @QueryParam("pagenum") int pagenum) {
        System.out.println(pagenum);
        ProductCollection result = dbc.runQueryProductsByFilter(title, price, category, 15*(pagenum-1));
        return Response.ok(result.toJson()).build();
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

    @POST
    @Consumes("application/json")
    public Response addProduct(String jsonString) {
        Product p;
        try {
            p = new Product(jsonString);
        } catch (Exception e) {
            return Response.status(400).build();
        }
        //TODO: SEND PRODUCT MODEL INSTEAD OF EACH PARAMETER
        if (dbc.runQueryInsertProduct(p.getID(), p.getTitle(), p.getDescription(), p.getPrice(), p.getCategory(), p.getAuthorID()))
            return Response.ok(p.getID()).build(); //NOT SURE
        else
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }

    @DELETE
    @Consumes("application/json")
    public Response deleteProduct(String jsonString) {
        Gson gson = new Gson();
        ProductInfo pi = gson.fromJson(jsonString, ProductInfo.class);
        HttpSession session = request.getSession();
        String test = dbu.runQueryUserIdByEmail(session.getAttribute("email").toString());
        String type = dbu.runQueryIfModerator(test);
        System.out.println(type);
        System.out.println(test);
        if(type.equals("Moderator") || test.equals(pi.getUserId())) {
            dbi.runQueryDeleteImage(pi.getProductId());
            if (dbc.runQueryDeleteProduct(pi.getProductId())) {
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            }
        } else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

}
