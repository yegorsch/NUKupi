package Models;

import com.google.gson.*;

import java.util.ArrayList;

public class ProductCollection extends ArrayList<Product> {
    public String toJson(){
        System.out.println(new Gson().toJson(this));
        return new Gson().toJson(this);
    }

}

