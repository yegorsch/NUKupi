package Models;

import Utils.UniqueStringGenerator;
import com.google.gson.Gson;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

@JsonAdapter(UserAdapter.class)
public class User extends JsonModel{

    private String userID;
    private String email;
    private String password;
    private String name;
    private String type;
    private String phoneNumber;

    private static final String JSON_USER_ID = "user_id";
    private static final String JSON_EMAIL = "email";
    private static final String JSON_PASSWORD = "password";
    private static final String JSON_NAME = "name";
    private static final String JSON_TYPE = "type";
    private static final String JSON_PHONE_NUMBER = "phone_number";

    public User(String email, String name, String type, String phoneNumber) {
        this.userID = UniqueStringGenerator.generateIDWithDefaultLength();
        this.email = email;
        this.password = UniqueStringGenerator.generatePasswordWithDefaultLength();
        this.name = name;
        this.type = type;
        this.phoneNumber = phoneNumber;
    }

    public User(String userID, String email, String password, String name, String type, String phoneNumber) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.name = name;
        this.type = type;
        this.phoneNumber = phoneNumber;
    }

    public User() { emptyInit(); }

    public User(String JSONSString) {
        //emptyInit();
        initializeWith(JSONSString);
    }

    private void emptyInit() {
        this.userID = UniqueStringGenerator.generateIDWithDefaultLength();
        this.email = "";
        this.password = UniqueStringGenerator.generatePasswordWithDefaultLength();
        this.name = "";
        this.type = "User";
        this.phoneNumber = "";
    }

    protected void initializeWith(String JSONString) {
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> jsonObject = new Gson().fromJson(JSONString, type);
        this.userID = UniqueStringGenerator.generateIDWithDefaultLength();
        this.email = (String) jsonObject.get(JSON_EMAIL);
        this.password = UniqueStringGenerator.generatePasswordWithDefaultLength();
        this.name = (String) jsonObject.get(JSON_NAME);
        this.type = "User";
        this.phoneNumber = (String) jsonObject.get(JSON_PHONE_NUMBER);
    }

    public String toJSON() {
            return new Gson().toJson(this);
        }
    public void setUserID(String userID) { this.userID = userID; }
    public String getUserID() { return userID; }
    public void setEmail(String email) { this.email = email; }
    public String getEmail() { return email; }
    public void setPassword(String password) { this.password = password; }
    public String getPassword() { return password; }
    public void setName(String name) { this.name = name; }
    public String getName() { return name; }
    public void setType(String type) { this.type = type; }
    public String getType() { return type; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getPhoneNumber() { return phoneNumber; }

}
