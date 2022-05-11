package model;

/**
 * Order class defines an order by its fields: id, product, client and quantity.
 *
 * @author ioana
 */
public class Order {
    private int id;
    private Product product;
    private Client client;
    private int quantity;

    public Order(int id, Product product, Client client, int quantity) {
        this.id = id;
        this.product = product;
        this.client = client;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Client getClient() {
        return client;
    }

    public int getQuantity() {
        return quantity;
    }

    /**
     * Calculates the price of an order by multiplying the price of one product with the number of products (quantity).
     * @return the price of one order
     */
    public int getOrderPrice(){
       return this.getProduct().getPrice()*this.getQuantity();
    }
}
