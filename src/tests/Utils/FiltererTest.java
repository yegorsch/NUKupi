package Utils;

import Models.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FiltererTest extends TestCase {

    ArrayList<Product> products;

    public void setUp() throws Exception {
        super.setUp();
        products = getModels("src/tests/Utils/json.txt");

    }

    private ArrayList<Product> getModels(String fromFile) throws IOException {
        File file = new File(fromFile);
        String text = "";
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                text += line;
            }
        }
        return new Gson().fromJson(text, new TypeToken<ArrayList<Product>>() {
        }.getType());
    }

    public void testFilterEmpty() {
        try {
            ArrayList<Product> empty = getModels("src/tests/Utils/jsonEmpty.txt");
            assertEquals(empty, new Filterer(products).filter("TITLE_NONE", 0, ""));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testFilterByCategoryAndName() {
        try {
            ArrayList<Product> test = getModels("src/tests/Utils/jsonFilteredByCategoryAndName.txt");
            System.out.println();
            assertEquals(test, new Filterer(products).filter("k", 0, "OTHER"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testFilterByCategoryBooks() {
        try {
            ArrayList<Product> test = getModels("src/tests/Utils/jsonFilteredByCategoryBooks.txt");
            System.out.println();
            assertEquals(test, new Filterer(products).filter(null, 0, "BOOKS"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testFilterByCategoryOther() {
        try {
            ArrayList<Product> test = getModels("src/tests/Utils/jsonFilteredByCategoryOther.txt");
            assertEquals(test, new Filterer(products).filter(null, 0, "OTHER"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testFilterByTitle() {
        try {
            ArrayList<Product> test = getModels("src/tests/Utils/jsonFilteredByTitle_Electronic.txt");
            assertEquals(test, new Filterer(products).filter("Electonic", 0, null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}