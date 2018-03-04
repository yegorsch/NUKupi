package REST;

import DB.DatabaseClient;
import DB.ImageDatabaseClient;
import Models.Image;
import Utils.IDGenerator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("id")
public class IDService {

    private DatabaseClient dbc;

    public IDService() {
        dbc = new DatabaseClient();
    }

    @GET
    public Response generate(@QueryParam("id") String id) {
        return Response.ok(IDGenerator.generateIDWithDefaultLength()).build();
    }

}
