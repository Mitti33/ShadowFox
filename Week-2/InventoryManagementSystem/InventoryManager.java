import java.util.*;
import java.util.stream.Collectors;

public class InventoryManager{

    //__ SINGLETON MACHINERY __________________________________________________________________
    
    private static InventoryManager instance;

    // creating an unchangeable list of products
    private final List<Product> products;

    // auto-incrementing ID counter
    private int nextId;

    // private constructor
    private InventoryManager(){
        products = new ArrayList<>();
        nextId = 1;
        seedData();
    }

    // creating the only door in
    // first call: creates new instance
    // every call after: uses already existing instance
    // returns an instance of type InventoryManager
    public static InventoryManager getInstance(){
        if(instance == null)
            instance = new InventoryManager();

        return instance;
    }

    // __ SEEDING DATA ___________________________________________________________________________

    // seeding some random data for initial realistic demo
    private void seedData(){
        addProduct(new Product(0, "Pencil", "Stationery", 4.0, 32));
        addProduct(new Product(0, "Pen", "Stationery", 6.0, 35));
        addProduct(new Product(0, "Egg", "Food", 7.0, 3)); // low!
        addProduct(new Product(0, "Coffee pack", "Beverages", 60.0,  2)); // low!
        addProduct(new Product(0, "Tea pack", "Beverages", 20.0,  12));
        addProduct(new Product(0, "Chocolate cake", "Food", 10.0, 4)); // low!
        addProduct(new Product(0, "Notebook A4", "Stationery", 70.0, 40));
        addProduct(new Product(0, "Soap", "Accessories", 5.0, 103));
        addProduct(new Product(0, "Stapler", "Stationery", 250.0, 1)); // low!
        addProduct(new Product(0, "Toothbrush", "Accessories", 30.0, 6));
        addProduct(new Product(0, "Rice pack", "Food", 100.0, 14));
        addProduct(new Product(0, "Milk pack", "Food", 35.0, 0)); // out of stock!
    }

    // __ CREATE ___________________________________________________________________________________

    // creating a product
    // returns saved copy with real ID
    public Product addProduct(Product p){
        Product saved = new Product(
            nextId++,
            p.getName(),
            p.getCategory(),
            p.getPrice(),
            p.getQuantity()
        );
        products.add(saved);
        return saved;
    }

    // __ READ ___________________________________________________________________________________

    // reading all the products
    // returns a copy of the list of products
    // so that nobody can affect the actual list directly!
    public List<Product> getAllProducts(){
        return new ArrayList<>(products);
    }

    // reading a certain product by barcode id
    // by using simple linear search - O(n)
    public Product findById(int id){

        for(Product p : products)
            if(p.getId() == id)
                return p;
        
        return null;
    }

    // __ UPDATE ___________________________________________________________________________________

    // updating a certain product by id
    // returns true if found and updated, false otherwise
    public boolean updateProduct(int id, String name, String category, double price, int quantity){
        
        // find it
        Product p = findById(id);

        // if not found, return false
        if(p == null)
            return false;

        // else, update it and return true
        p.setName(name);
        p.setCategory(category);
        p.setPrice(price);
        p.setQuantity(quantity);
        return true;

    }

    // __ DELETE ___________________________________________________________________________________

    // deleting a certaing product by id
    // returns true if found and deleted, false otherwise
    public boolean deleteProduct(int id){
        return products.removeIf(p -> p.getId() == id);
    }

    // __ SEARCH (BARCODE + NAME) ___________________________________________________________________________________

    // creating a Barcode + Name Search system
    // O(n) filter
    // returns list of products having the given id
    public List<Product> search(String query){
        
        // if query is empty, return all products
        if(query == null || query.trim().isEmpty())
            return getAllProducts();

        // normalise query to lowercase
        // for case-insensitivity
        final String q = query.toLowerCase().trim();

        // the filter
        return products.stream()
            .filter(p -> 
                p.getName().toLowerCase().contains(q)
                || String.valueOf(p.getId()).contains(q)
            )
            .collect(Collectors.toList());
    }

    // __ LOW-STOCK FILTER ___________________________________________________________________________________

    // creating low-stock filter system
    // returns all products with qty < given threshold
    public List<Product> getLowStock(int threshold){
        return products.stream()
            .filter(p -> p.getQuantity() < threshold)
            .collect(Collectors.toList());
    }

    // __ AGGREGATE STATS ___________________________________
    
    // how many products left in inventory?
    public int getTotalProducts(){
        return products.size();
    }

    // how much does the whole inventory cost?
    public double getTotalInventoryValue(){
        return products.stream()
            .mapToDouble(Product::getTotalValue)
            .sum();
    }

    // how many products are suffering from low stock?
    public long getLowStockCount(int threshold){
        return products.stream()
            .filter(p -> p.getQuantity() < threshold)
            .count();
    }
}