package Models;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class UserAdapter extends TypeAdapter<User> {
    @Override
    public void write(JsonWriter jsonWriter, User user) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("user_id").value(user.getUserID());
        jsonWriter.name("email").value(user.getEmail());
        jsonWriter.name("password").value(user.getPassword());
        jsonWriter.name("name").value(user.getName());
        jsonWriter.name("type").value(user.getType());
        jsonWriter.name("phone_number").value(user.getPhoneNumber());
        jsonWriter.endObject();
    }

    @Override
    public User read(JsonReader jsonReader) throws IOException {
        return null;
    }

}
