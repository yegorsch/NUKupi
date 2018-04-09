package REST;

import DB.LoggerDatabaseClient;
import Models.LogCollection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

// URL: http://localhost:8080/rest/logger
@Path("logger")
public class LoggerService {

    private LoggerDatabaseClient dlc;

    public LoggerService() {
        dlc = new LoggerDatabaseClient();
    }

    @GET
    @Path("/all")
    public Response getAllLogs() {
        LogCollection logs = dlc.runQueryLogsAll();
        return Response.ok(logs.toJson()).build();
    }

    @GET
    @Path("/filters")
    public Response getLogsByFilters(@QueryParam("searchWord") String searchWord,
                                     @QueryParam("loggerName") String loggerName) {
        LogCollection logs = dlc.runQueryLogsByFilter(searchWord, loggerName);
        return Response.ok(logs.toJson()).build();
    }

}
