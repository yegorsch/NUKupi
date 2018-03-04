package Models;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductCollection extends ArrayList<Product> {
    public String toJson(){
        ArrayList<String> list = new ArrayList<>();
        for (Product p: this){
            list.add(new Gson().toJson(p, Product.class));
        }
        return new Gson().toJson(list);
    }

    public void fillImageArraysFrom(HashMap<Integer, String > map) {
        map.forEach((k,v) -> this.get(k).addImageID(v));
    }

}
