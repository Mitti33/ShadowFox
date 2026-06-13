public class Product{

    // setting fields 
    // using access specifier = private
    // for proper encapsulation
    private int id;
    private String name;
    private String category;
    private double price;
    private int quantity;

    // making a parameterized constructor
    public Product(int id, String name, String category, double price, int quantity){
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;

        // in case quantity = -ve, we must store quantity = 0
        // this prevents negative stock from ever being stored
        this.quantity = Math.max(0, quantity);
    }

    // making getters
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getCategory(){
        return category;
    }
    public double getPrice(){
        return price;
    }
    public int getQuantity(){
        return quantity;
    }
    public double getTotalValue(){
        return price * quantity;
    }

    // making setters
    public void setName(String name){
        this.name = name;
    }
    public void setCategory(String category){
        this.category = category;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public void setQuantity(int quantity){
        // if negative, throw exception
        if(quantity < 0){
            throw new IllegalArgumentException(
                "Quantity cannot be negative. You entered: " + quantity
            );
        }
        this.quantity = quantity;
    }

    // overriding toString() 
    @Override
    public String toString(){
        return String.format("Product{id=%d, name='%s', qty=%d, price=%.2f}", id, name, quantity, price);
    }
}