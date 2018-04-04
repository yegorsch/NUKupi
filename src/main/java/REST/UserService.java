package REST;

import DB.UserDatabaseClient;
import Models.User;
import Models.UserCollection;
import Models.UserInfo;
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
        System.out.println("HERE AT GETUSERID");
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
        String info = dbu.runQueryUserInfoById(userId);
        return Response.ok(info).build();
    }

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

    @GET
    @Path("/logout")
    public Response logout() {
        HttpSession session = request.getSession();
        session.invalidate();
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Path("/myinfo")
    public Response myInfo(@QueryParam("userid") String userId) {
        UserCollection usr = dbu.runQueryUserById(userId);
        Gson gson = new Gson();
        Response.ResponseBuilder b = Response.ok(gson.toJson(usr));
        return b.build();
    }

    // TODO: Forgot password
    @GET
    @Path("/forgotpassword")
    public Response forgotPassword(@QueryParam("email") String email) {
        String password = dbu.runQueryUserPasswordByEmail(email);
        if(!password.equals("")) {
            SendMail.send(email, password, false);
            return Response.ok(password).build();
        } else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build(); // if empty, write smth like no such user exists
        }
    }

    // email, oldPassword, newPassword
    @PUT
    @Consumes("application/json")
    public Response changePassword(String jsonString) {
        Gson gson = new Gson();
        UserInfo ui = gson.fromJson(jsonString, UserInfo.class);
        if(dbu.runQueryCheckPassword(ui.getUserId(), ui.getOldPassword())) {
            if(dbu.runQueryUpdateUserPassword(ui.getUserId(), ui.getNewPassword())) {
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            }
        } else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

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
                SendMail.send(u.getEmail(), u.getPassword(), true);
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(Response.Status.NOT_ACCEPTABLE).build();
            }
        }
    }

}
