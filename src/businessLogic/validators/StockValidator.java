package businessLogic.validators;
import model.Product;

/**
 * Checks that stock is positive.
 *
 * @author ioana
 *
 *
 */

public class StockValidator implements Validator<Product>{
    @Override
    public void validate(Product product) {
        if(product.getStock()<0){
            throw new IllegalArgumentException("Stock cannot be negative");
        }
    }
}
