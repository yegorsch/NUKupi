package REST;

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

    ArrayList<Image> images;

    public ImageService() {
        images = new ArrayList<Image>();
    }

    @GET
    public Response download(@QueryParam("id") String id) {
        if (id == null) {
            System.out.println("KEY NULL");
            return Response.status(400).build();
        }
        System.out.println("REQUESTING IMAGE");
        for (Image i : images) {
            if (i.getID().equals(id)) {
                Response.ResponseBuilder response = Response.ok(i.getData());
                response.header("Content-Disposition", "attachment; filename=image.jpg");
                System.out.println("Image returned");
                return response.build();
            }
        }
        return Response.noContent().build();
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MEDIA_TYPE_WILDCARD)
    public Response uploadPhoto(InputStream stream) throws IOException {
        byte[] result = IOUtils.toByteArray(stream);
        if (result.length > 0) {
            Image i = new Image();
            i.setData(result);
            images.add(i);
            return Response.ok(i.toJSON()).build();
        } else {
            return Response.status(400).build();
        }
    }

    @DELETE
    @Consumes("application/json")
    public Response deleteProduct(String jsonString) {
        ArrayList<String> list = new Gson().fromJson(jsonString, new TypeToken<ArrayList<String>>() {
        }.getType());
        for (String id : list) {
            images.removeIf((Image p) -> p.getID().equals(id));
        }
        return Response.status(Response.Status.OK).build();
    }


}