package REST;

import DB.UserDatabaseClient;
import Models.User;
import Models.UserCollection;
import Utils.SendMail;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

// URL: http://localhost:8080/rest/users
@Path("users")
public class UserService {
    private UserDatabaseClient dbu;
    @Context private HttpServletRequest request;

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

    @GET
    @Path("/getuserid")
    public Response getUserId() {
        HttpSession session = request.getSession();
        if(session.getAttribute("email")==null) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } else {
            System.out.println(session.getAttribute("email").toString());
            String userId = dbu.runQueryUserIdByEmail(session.getAttribute("email").toString());
            return Response.ok(userId).build();
        }
    }


    @GET
    @Path("/getuserinfo")
    public Response getUserEmail(@QueryParam("userid") String userId) {
        String email = dbu.runQueryUserInfoById(userId);
        return Response.ok(email).build();
    }

    // TODO: Idk do something
    @GET
    @Path("/login")
    public Response login(@QueryParam("email") String email, @QueryParam("password") String password) {
        if(dbu.runQueryLogIn(email, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("email", email);
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
        System.out.println(jsonString);
        User u;
        try {
            u = new User(jsonString);
        } catch (Exception e) {
            return Response.status(400).build();
        }
        if (dbu.runQueryEmail(u.getEmail())) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } else {
            if (dbu.runQueryInsertUser(u.getUserID(), u.getEmail(), u.getPassword(), u.getName(), u.getType(), u.getPhoneNumber())) {
                SendMail.send(u.getEmail(), u.getPassword());
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            }
        }
    }

}
