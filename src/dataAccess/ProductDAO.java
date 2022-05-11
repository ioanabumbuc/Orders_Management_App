package dataAccess;
import model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * ProductDAO extends AbstractDAO and defines methods for products.
 *
 * @author ioana
 */
public class ProductDAO extends AbstractDAO<Product>{

    public ProductDAO() {
        super();
    }
    /**
     * <p>Creates an array list of all the products in the database, by calling the findAll function.
     * It iterates through the result set and adds products to the list.</p>
     * @return an arraylist of products
     * @throws SQLException from resultSet.next()
     */
    public ArrayList<Product> getAllProducts() throws SQLException {
        ArrayList <Product> productList = new ArrayList<>();
        ResultSet resultSet = this.findAll();
        while(resultSet.next()){
            Product product = new Product(resultSet.getInt("id"),resultSet.getInt("price"),
                    resultSet.getInt("stock"),resultSet.getString("name"));
            productList.add(product);
        }
        return productList;
    }
    /**
     * <p>Creates an array list of products from a result set obtained from a previous query.</p>
     * @param resultSet the result of a query
     * @return an array list of products
     * @throws SQLException from resultSet.next()
     */
    public ArrayList<Product> createProductList(ResultSet resultSet) throws SQLException {
        ArrayList<Product> products= new ArrayList<>();
        while(resultSet.next()){
            Product product = new Product(resultSet.getInt("id"),resultSet.getInt("price"),
                    resultSet.getInt("stock"),resultSet.getString("name"));
            products.add(product);
        }
        return products;
    }
    /**
     * <p>Creates a Product from a result set. This method is used when a single product is a result of the query,
     * that is when searching by id. The method calls createProductList then returns the first element of that list.</p>
     * @param resultSet result of a query
     * @return a product
     * @throws SQLException from createProductList
     */
    public Product toProduct(ResultSet resultSet) throws SQLException {
        ArrayList<Product> products = createProductList(resultSet);
        return products.get(0);
    }


}
