package REST;

import DB.ImageDatabaseClient;
import Models.Image;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

// URL: http://localhost:8080/rest/images
@Path("images")
public class ImageService {

    private ImageDatabaseClient dbc;

    public ImageService() {
        dbc = new ImageDatabaseClient();
    }

    /**
     * Takes id of the image and returns its byte data
     *
     * @param id
     * @return bytes[] of the image
     */
    @GET
    public Response download(@QueryParam("id") String id) {
        if (id == null) {
            System.out.println("KEY NULL");
            return Response.status(400).build();
        }
        Image i = dbc.runQueryGetImages(id);
        if (i != null && i.getID().equals(id)) {
            Response.ResponseBuilder response = Response.ok(i.getData());
            response.header("Content-Disposition", "attachment; filename=image.jpg");
            System.out.println("Image returned");
            return response.build();
        }
        return Response.status(400).build();
    }

    /**
     * Uploads image to the server
     *
     * @param stream byte stream of image
     * @param id     product id that is generated at the frontend
     * @return JSON of type {"date": unix_date,"size": byte_size_long,"id":"UNIQUE ID"}
     * @throws IOException
     */
    @POST
    @Path("{ id : [A-Za-z0-9_]+}")
    @Consumes(MediaType.MEDIA_TYPE_WILDCARD)
    public Response uploadPhoto(InputStream stream, @PathParam("id") String id) throws IOException {
        byte[] result = IOUtils.toByteArray(stream);
        if (result.length > 0) {
            Image image = new Image(id);
            image.setData(result);
            if (dbc.runQueryInsertImage(image)) {
                return Response.ok(image.toJSON()).build();
            }
            return Response.status(400).build();
        } else {
            return Response.status(400).build();
        }

    }

    /**
     * Deletes products from json array with ids
     *
     * @param jsonString array of ids
     * @return OK
     */
    @DELETE
    @Consumes("application/json")
    public Response deleteImages(String jsonString) {
        ArrayList<String> list = new Gson().fromJson(jsonString, new TypeToken<ArrayList<String>>() {
        }.getType());
        for (String id : list) {
            dbc.runQueryDeleteImage(id);
        }
        return Response.status(Response.Status.OK).build();
    }


}