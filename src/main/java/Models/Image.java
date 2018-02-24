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

    public Image() {
        ID = IDGenerator.generateIDWithLength(15);
        dateAdded = Instant.now().getEpochSecond();
        size = 0;
        data = new byte[0];
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
        obj.put("id",getID());
        obj.put("date", getDateAdded());
        obj.put("size", getSize());
        return new Gson().toJson(obj);
    }

}
