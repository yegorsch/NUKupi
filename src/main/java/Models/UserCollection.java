package Models;

import com.google.gson.Gson;

import java.util.ArrayList;

public class UserCollection extends ArrayList<User> {
    public String toJson(){
        System.out.println(new Gson().toJson(this));
        return new Gson().toJson(this);
    }

}

