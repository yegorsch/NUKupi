//package Utils;
//
//import Models.Product;
//
//import java.util.ArrayList;
//
//public class Filterer {
//
//    private ArrayList<Product> products;
//
//    public Filterer(ArrayList<Product> p) {
//        this.products = p;
//    }
//
//    /**
//     * Retrieve products by categories
//     **/
//
//    public ArrayList<Product> getProductByCategory(String category) {
//        ArrayList<Product> reqProds = new ArrayList<Product>();
//        if (category != null) {
//            for (Product p : products) {
//                if (p.getCategory().equals(category)) {
//                    reqProds.add(p);
//                }
//            }
//        }
//        return reqProds;
//    }
//
//    /**
//     * Retrieve products by title
//     **/
//
//    public ArrayList<Product> getProductByTitle(String title) {
//        ArrayList<Product> reqProds = new ArrayList<Product>();
//        if (title != null) {
//            for (Product p : products) {
//                if (p.getTitle().toLowerCase().contains(title.toLowerCase()) || p.getDescription().toLowerCase().contains(title.toLowerCase())) {
//                    reqProds.add(p);
//                }
//            }
//        }
//        return reqProds;
//    }
//
//    /**
//     * Retrieve products by price filters
//     **/
//
//    public ArrayList<Product> getProductByPrice(String type, double price, String units) {
//        ArrayList<Product> reqProds = new ArrayList<Product>();
//        if (type == null && units == null) {
//            return new ArrayList<>();
//        }
//        if (type.equals("FREE")) {
//            for (Product p : products) {
//                if (p.getPaymentType().equals("FREE")) {
//                    reqProds.add(p);
//                }
//            }
//        } else if (type.equals("EXCHANGE")) {
//            for (Product p : products) {
//                if (p.getPaymentType().equals("EXCHANGE")) {
//                    reqProds.add(p);
//                }
//            }
//        } else if (type.equals("REGULAR") && price >= 0 && units != null) {
//            for (Product p : products) {
//                if (p.getPaymentType().equals("REGULAR") && p.getPrice() <= price && p.getUnits().equals(units)) {
//                    reqProds.add(p);
//                }
//            }
//        }
//        return reqProds;
//    }
//
//    /**
//     * Retrieve products owned by particular user (change email for user id)
//     **/
//
//    public ArrayList<Product> getProductByUser(String email) {
//        ArrayList<Product> reqProds = new ArrayList<>();
//        if (email != null) {
//            for (Product p : products) {
//                if (p.getAuthorEmail().equals(email)) {
//                    reqProds.add(p);
//                }
//            }
//        }
//        return reqProds;
//    }
//}
