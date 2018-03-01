package Models;

import Utils.IDGenerator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

//enum PaymentType {
//    FREE,
//    EXCHANGE,
//    REGULAR
//}

public class Product extends JsonModel {

    private String title;
    private String description;
    // TODO: change it to user reference when we have it.
    private String authorID;
    private ArrayList<String> images;
    // private PaymentType paymentType;
    private String category;
    // Price could be like "2 chocolates"
    private int price;
    // private String units;
    private String ID;


    private static final String JSON_TITLE = "title";
    private static final String JSON_DESC = "description";
    private static final String JSON_EMAIL = "email";
    private static final String JSON_PRICE = "price";

    public Product(String title, String description, String authorID, int price, String category) {
        this.title = title;
        this.description = description;
        this.authorID = authorID;
        //this.paymentType = paymentType;
        this.price = price;
        //this.units = units;
        images = new ArrayList<String>();
        this.category = category;
        ID = IDGenerator.generateIDWithDefaultLength();
    }

    public Product(String ID, String title, String description, String authorID, int price, String category) {
        this.title = title;
        this.description = description;
        this.authorID = authorID;
        //this.paymentType = paymentType;
        this.price = price;
        //this.units = units;
        images = new ArrayList<String>();
        this.ID = ID;
        this.category = category;
    }

    public Product() {
        emptyInit();
    }


    public Product(String JSONSString) {
        emptyInit();
        initializeWith(JSONSString);
    }

    private void emptyInit() {
        this.title = "";
        this.description = "";
        this.authorID = "";
        //this.paymentType = PaymentType.REGULAR;
        this.category = "OTHER";
        this.price = 0;
        //this.units = "тг.";
        images = new ArrayList<String>();
        ID = IDGenerator.generateIDWithDefaultLength();
    }

    protected void initializeWith(String JSONString) {
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> jsonObject = new Gson().fromJson(JSONString, type);
        this.title = (String) jsonObject.get(JSON_TITLE);
        this.description = (String) jsonObject.get(JSON_DESC);
        this.authorID = (String) jsonObject.get(JSON_EMAIL);
        // Images - ?
        //this.paymentType = PaymentType.valueOf((String) jsonObject.get(JSON_TYPE));
        this.price = (Integer) jsonObject.get(JSON_PRICE);
        //this.units = (String) jsonObject.get(JSON_UNITS);
    }

    public String toJSON() {
        return new Gson().toJson(this);
    }

    public void addImage(String imageID) {
        images.add(imageID);
    }

    public boolean removeImage(String imageID) {
        return images.remove(imageID);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(ID, product.ID);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ID);
    }

}
