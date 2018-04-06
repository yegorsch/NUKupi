package Models;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ProductAdapter extends TypeAdapter<Product> {
    @Override
    public void write(JsonWriter jsonWriter, Product product) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("ID").value(product.getID());
        jsonWriter.name("title").value(product.getTitle());
        jsonWriter.name("description").value(product.getDescription());
        jsonWriter.name("category").value(product.getCategory());
        jsonWriter.name("price").value(product.getPrice());
        jsonWriter.name("authorID").value(product.getAuthorID());
        jsonWriter.name("images").jsonValue(new Gson().toJson(product.getImages()));
        jsonWriter.endObject();
    }

    @Override
    public Product read(JsonReader jsonReader) throws IOException {
        return null;
    }

}
