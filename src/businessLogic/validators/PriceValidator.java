package businessLogic.validators;

import model.Product;
/**
 * Checks that the price is positive.
 * @author ioana
 *
 *
 */
public class PriceValidator implements Validator<Product>{
    @Override
    public void validate(Product product) {
        if(product.getPrice()<=0){
            throw new IllegalArgumentException("Price cannot be negative or free");
        }
    }
}
