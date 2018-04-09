package Models;

import com.google.gson.Gson;

import java.util.ArrayList;

public class LogCollection extends ArrayList<Log> {
    public String toJson(){
        System.out.println(new Gson().toJson(this));
        return new Gson().toJson(this);
    }
}

