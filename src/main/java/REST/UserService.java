package REST;

import DB.UserDatabaseClient;
import Models.User;
import Models.UserCollection;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

// URL: http://localhost:8080/rest/users
@Path("users")
public class UserService {
    private UserDatabaseClient dbu;

    public UserService() {
        dbu = new UserDatabaseClient();
    }

    @GET
    @Path("/all")
    public Response getAllUsers() {
        UserCollection usr = dbu.runQueryUsersAll();
        Gson gson = new Gson();
        Response.ResponseBuilder b = Response.ok(gson.toJson(usr));
        return b.build();
    }

    /*
     * TODO: Make filterer
     * https://gist.github.com/neolitec/8953607
     * https://www.javatpoint.com/servlet-http-session-login-and-logout-example
     */

    // TODO: Idk do something
    @GET
    @Path("/login")
    public Response login(@QueryParam("email") String email, @QueryParam("password") String password) {
        if(dbu.runQueryLogIn(email, password)) {

            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    // TODO: Logout
//        HttpSession session=request.getSession();
//        session.invalidate();

    // TODO: My info (search by email? if yes why do we need userid?)
    @GET
    @Path("/myinfo")
    public Response myInfo(@QueryParam("email") String email) {
        UserCollection usr = dbu.runQueryUserByEmail(email);
        Gson gson = new Gson();
        Response.ResponseBuilder b = Response.ok(gson.toJson(usr));
        return b.build();
    }

    // TODO: Change password


    @POST
    @Consumes("application/json")
    public Response addUser(String jsonString) {
        User u;
        try {
            u = new User(jsonString);
        } catch (Exception e) {
            return Response.status(400).build();
        }
        if (dbu.runQueryInsertUser(u.getUserID(), u.getEmail(), u.getPassword(), u.getName(), u.getType(), u.getPhoneNumber())) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

}
