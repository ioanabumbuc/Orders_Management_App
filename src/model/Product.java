package model;

/**
 * Product class defines a product by its attributes: id, name, stock and price.
 *
 * @author ioana
 */
public class Product {
    private int id;
    private String name;
    private int stock;
    private int price;

    public Product(int id, int price, int stock, String name) {
        this.id = id;
        this.price = price;
        this.stock = stock;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getName() {
        return name;
    }

    public void decrementStock(int amount){
        this.stock -= amount;
    }

}
