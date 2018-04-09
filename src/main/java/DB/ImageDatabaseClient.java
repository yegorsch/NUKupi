package DB;

import Models.Image;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ImageDatabaseClient extends DatabaseClient {

    public boolean runQueryInsertImage(Image image) {
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into image values (?,?,?,?,?)");
            stmt.setString(1, image.getID());
            stmt.setString(2, String.valueOf(image.getSize()));
            stmt.setString(3, String.valueOf(image.getDateAdded()));
            stmt.setBytes(4, image.getData());
            stmt.setString(5, image.getProductID());
            int count = stmt.executeUpdate();
            if (count > 0) {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public Image runQueryGetImages(String image_id) {
        ArrayList<Image> images = new ArrayList<Image>();
        try {
            PreparedStatement stmt = conn.prepareStatement("select image_id, size, date, bytes, i_product_id " +
                    "from image where image_id = (?)");
            stmt.setString(1, image_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return new Image(rs.getString("image_id"), rs.getLong("date"),
                        rs.getDouble("size"), rs.getBytes("bytes"),
                        rs.getString("i_product_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean runQueryDeleteImage(String product_id) {
        try {
            PreparedStatement stmt = conn.prepareStatement("delete from image where i_product_id = (?);");
            stmt.setString(1, product_id);
            int status = stmt.executeUpdate();
            if (status == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
