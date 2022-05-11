package businessLogic.validators;
import model.Order;

/**
 * Checks that the quantity is positive and that there's enough products in stock for the order.
 *
 * @author ioana
 *
 *
 */
public class QuantityValidator implements Validator<Order> {

    @Override
    public void validate(Order order) {
        if(order.getQuantity()<=0){
            throw new IllegalArgumentException("Quantity cannot be negative or zero");
        }
        if(order.getQuantity()>order.getProduct().getStock()){
            throw new IllegalArgumentException("Product under-stock");
        }
    }
}
