package Utils;

import Models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Filterer {

    private ArrayList<Product> products;

    public Filterer(ArrayList<Product> p) {
        this.products = p;
    }

    /**
     * Retrieve products by params
     **/

    public ArrayList<Product> filter(String title, int price, String category) {
        List<Product> reqProds = (List<Product>) products.clone();
        if (title != null) {
            reqProds = products.stream().filter(
                    product -> product.getTitle().toLowerCase().contains(title.toLowerCase())).collect(Collectors.toList());
        }
        if (price != 0) {
            reqProds = reqProds.stream().filter(
                    product -> product.getPrice() <= price).collect(Collectors.toList());
        }
        if (category != null) {
            reqProds = reqProds.stream().filter(
                    product -> product.getCategory().toLowerCase().contains(category.toLowerCase())).collect(Collectors.toList());
        }
        return new ArrayList<>(reqProds);
    }
}
