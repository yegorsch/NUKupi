package Models;

import Utils.IDGenerator;
import com.google.gson.Gson;

import javax.ws.rs.FormParam;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

// TODO: complete this and hook it with ImageService (maybe)
public class Image extends JsonModel {

    private String ID;
    private long dateAdded; // Unix time in seconds
    private double size; //in bytes
    private byte[] data;
    private String productID;

    private static final String JSON_ID = "id";
    private static final String JSON_DATE = "date";
    private static final String JSON_SIZE = "size";

    public Image(String productID) {
        ID = IDGenerator.generateIDWithLength(15);
        dateAdded = Instant.now().getEpochSecond();
        size = 0;
        data = new byte[0];
        this.productID = productID;
    }

    public Image(String ID, long dateAdded, double size, byte[] data) {
        this.ID = ID;
        this.dateAdded = dateAdded;
        this.size = size;
        this.data = data;
    }

    public Image(String ID, long dateAdded, double size, byte[] data, String product_id) {
        this.ID = ID;
        this.dateAdded = dateAdded;
        this.size = size;
        this.data = data;
        this.productID = product_id;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
        this.size = data.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(ID, image.ID);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ID);
    }

    @Override
    void initializeWith(String JSONString) {

    }

    @Override
    public String toJSON() {
        HashMap<String, Object> obj = new HashMap<>();
        obj.put(JSON_ID,getID());
        obj.put(JSON_DATE, getDateAdded());
        obj.put(JSON_SIZE, getSize());
        return new Gson().toJson(obj);
    }

    public String getProductID() {
        return productID;
    }
}
