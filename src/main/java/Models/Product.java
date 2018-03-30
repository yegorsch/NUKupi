package Models;

import Utils.UniqueStringGenerator;
import com.google.gson.*;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import jdk.nashorn.internal.parser.JSONParser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

@JsonAdapter(ProductAdapter.class)
public class Product extends JsonModel {

    private String title;
    private String description;
    // TODO: change it to user reference when we have it.
    private String authorID;
    public ArrayList<String> images;
    // private PaymentType paymentType;
    private String category;
    // Price could be like "2 chocolates"
    private int price;
    // private String units;
    private String ID;


    private static final String JSON_TITLE = "title";
    private static final String JSON_DESC = "description";
    private static final String JSON_PRODUCT_ID = "ID";
    private static final String JSON_IMAGES = "images";
    private static final String JSON_PRICE = "price";
    private static final String JSON_CATEGORY = "category";

    public Product(String title, String description, String authorID, int price, String category) {
        this.title = title;
        this.description = description;
        this.authorID = authorID;
        //this.paymentType = paymentType;
        this.price = price;
        //this.units = units;
        images = new ArrayList<String>();
        this.category = category;
        ID = UniqueStringGenerator.generateIDWithDefaultLength();
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
        // TODO: FOR GODS SAKE PLS FIX THIS BULLSHIT
        this.authorID = "1";
        this.category = "OTHER";
        this.price = 0;
        images = new ArrayList<String>();
        //ID = UniqueStringGenerator.generateIDWithDefaultLength();
    }

    protected void initializeWith(String JSONString) {
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> jsonObject = new Gson().fromJson(JSONString, type);
        this.ID = UniqueStringGenerator.generateIDWithDefaultLength();
        this.title = (String) jsonObject.get(JSON_TITLE);
        this.description = (String) jsonObject.get(JSON_DESC);
        this.category = (String) jsonObject.get(JSON_CATEGORY);
        Double price = (Double) jsonObject.get(JSON_PRICE);
        this.price = price.intValue();
        this.authorID = "1";
        this.images = (ArrayList<String>) jsonObject.get(JSON_IMAGES);
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

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public void addImageID(String id){
        this.images.add(id);
    }

}
