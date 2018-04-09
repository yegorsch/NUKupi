package Utils;

import Models.ProductCollection;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FiltererTest extends TestCase {

    ProductCollection products;

    public void setUp() throws Exception {
        super.setUp();
        products = getModels("src/test/Utils/json.txt");

    }

    private ProductCollection getModels(String fromFile) throws IOException {
        File file = new File(fromFile);
        String text = "";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                text += line;
            }
        }
        return new Gson().fromJson(text, new TypeToken<ProductCollection>() {
        }.getType());
    }

    public void testFilterEmpty() {
        try {
            ProductCollection empty = getModels("src/test/Utils/jsonEmpty.txt");
            assertEquals(empty, new Filterer(products).filter("TITLE_NONE", 0, ""));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testFilterByCategoryAndName() {
        try {
            ProductCollection test = getModels("src/test/Utils/jsonFilteredByCategoryAndName.txt");
            System.out.println();
            assertEquals(test, new Filterer(products).filter("k", 0, "OTHER"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testFilterByCategoryBooks() {
        try {
            ProductCollection test = getModels("src/test/Utils/jsonFilteredByCategoryBooks.txt");
            System.out.println();
            assertEquals(test, new Filterer(products).filter(null, 0, "BOOKS"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testFilterByCategoryOther() {
        try {
            ProductCollection test = getModels("src/test/Utils/jsonFilteredByCategoryOther.txt");
            assertEquals(test, new Filterer(products).filter(null, 0, "OTHER"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testFilterByTitle() {
        try {
            ProductCollection test = getModels("src/test/Utils/jsonFilteredByTitle_Electronic.txt");
            assertEquals(test, new Filterer(products).filter("Electonic", 0, null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testFilterByPrice() {
        try {
            ProductCollection test = getModels("src/test/Utils/jsonFilteredByPrice.txt");
            assertEquals(test, new Filterer(products).filter(null, 500, null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}