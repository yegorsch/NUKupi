package Models;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class UserCollection extends ArrayList<User> {
    public String toJson(){
        System.out.println(new Gson().toJson(this));
        return new Gson().toJson(this);
    }

}

