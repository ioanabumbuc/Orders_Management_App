package businessLogic;

import businessLogic.validators.PriceValidator;
import businessLogic.validators.StockValidator;
import businessLogic.validators.Validator;
import dataAccess.ProductDAO;
import model.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * ProductBLL does operations on products at business logic level.
 * Its purpose is to validate data and call functions for viewing, inserting, deleting and editing products.
 *
 * @author ioana
 */
public class ProductBLL {

    private final ArrayList<Validator<Product>> productValidators;
    private final ProductDAO productDAO;

    public ProductBLL(){
        productValidators = new ArrayList<>();
        productValidators.add(new PriceValidator());
        productValidators.add(new StockValidator());
        productDAO = new ProductDAO();
    }
    /**
     * <p>The method calls getAllProducts from the data access class for a product</p>
     * @return an array list of all the products in the database
     * @throws SQLException if resultSet of query is null
     */
    public ArrayList<Product> getAllProducts() throws SQLException {
        return productDAO.getAllProducts();
    }
    /**
     * <p>Given an existing id the method will return the product that has that id in the database.
     * If the product doesn't exist, throws an exception</p>
     * @param id given id to search
     * @return the searched product with the given id
     * @throws SQLException if resultSet is null
     */
    public Product findProductByID(int id) throws SQLException {
        if(productDAO.createProductList(productDAO.findById(id)).size()==0){
            throw new NoSuchElementException("Product with id "+id+" not found");
        }
        return productDAO.toProduct(productDAO.findById(id));
    }
    /**
     * <p>Given an object of class product, the method inserts that product into the database.
     * The product is validated with the list of validators</p>
     * @param product the given product to insert
     * @throws IllegalAccessException from insert method
     */
    public void insertProduct(Product product) throws IllegalAccessException {
        for(Validator<Product> validator:productValidators){
            validator.validate(product);
        }
        productDAO.insert(product);
    }
    /**
     * <p>Deletes the given product from the database</p>
     * @param product to delete
     */
    public void deleteProduct(Product product){
        productDAO.delete(product,product.getId());
    }
    /**
     * <p>Edits an existing product with the data of a given product</p>
     * @param product the new product to add in the place of the old one
     * @throws IllegalAccessException from update method
     */
    public void editProduct(Product product) throws IllegalAccessException {
        productDAO.update(product,product.getId());
    }

}

