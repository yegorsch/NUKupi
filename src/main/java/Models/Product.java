package Models;

import Utils.IDGenerator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.ref.SoftReference;
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
    private String authorEmail;

    private ArrayList<String> images;
    // private PaymentType paymentType;
    private String category;
    // Price could be like "2 chocolates"
    private double price;
    // private String units;
    private String ID;


    private static final String JSON_TITLE = "title";
    private static final String JSON_DESC = "description";
    private static final String JSON_EMAIL = "email";
    //private static final String JSON_TYPE = "paymentType";
    private static final String JSON_PRICE = "price";
    //private static final String JSON_UNITS = "units";

    public Product(String title, String description, String authorEmail, double price) { //PaymentType paymentType, String units) {
        this.title = title;
        this.description = description;
        this.authorEmail = authorEmail;
        //this.paymentType = paymentType;
        this.price = price;
        //this.units = units;
        images = new ArrayList<String>();
        ID = IDGenerator.generateIDWithDefaultLength();
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
        this.authorEmail = "";
        //this.paymentType = PaymentType.REGULAR;
        this.category = "OTHER";
        this.price = 0;
        //this.units = "тг.";
        images = new ArrayList<String>();
        ID = IDGenerator.generateIDWithDefaultLength();
    }

    protected void initializeWith(String JSONString) {
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> jsonObject =  new Gson().fromJson(JSONString, type);
        this.title = (String) jsonObject.get(JSON_TITLE);
        this.description = (String) jsonObject.get(JSON_DESC);
        this.authorEmail = (String) jsonObject.get(JSON_EMAIL);
        // Images - ?
        //this.paymentType = PaymentType.valueOf((String) jsonObject.get(JSON_TYPE));
        this.price = (Double) jsonObject.get(JSON_PRICE);
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

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

//    public String getPaymentType() {
//        return paymentType.name();
//    }
//
//    public void setPaymentType(PaymentType paymentType) {
//        this.paymentType = paymentType;
//    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getID() {
        return ID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

//    public String getUnits() {
//        return units;
//    }
//
//    public void setUnits(String units) {
//        this.units = units;
//    }

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
