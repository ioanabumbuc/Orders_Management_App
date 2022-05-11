package dataAccess;

import model.Client;
import model.Order;
import model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * OrderDAO extends AbstractDAO and defines methods specifically for orders
 */
public class OrderDAO extends AbstractDAO<Order>{
    ProductDAO productDAO = new ProductDAO();
    ClientDAO clientDAO = new ClientDAO();
    public OrderDAO() {
        super();
    }
    /**
     * <p>Creates an array list of all the orders in the database, by calling the findAll function.
     * It iterates through the result set and adds orders to the list.</p>
     * @return an arraylist of orders
     * @throws SQLException from resultSet.next()
     */
    public ArrayList<Order> getAllOrders() throws SQLException {
        ArrayList <Order> orderList = new ArrayList<>();
        ResultSet resultSet = this.findAll();
        while(resultSet.next()){
            Product product = productDAO.toProduct(productDAO.findById(resultSet.getInt("product")));
            Client client = clientDAO.toClient(clientDAO.findById(resultSet.getInt("client")));
            Order order = new Order(resultSet.getInt("id"),product,client,
                    resultSet.getInt("quantity"));
            orderList.add(order);
        }
        return orderList;
    }
    /**
     * <p>Creates an array list of orders from a result set obtained from a previous query.</p>
     * @param resultSet the result of a query
     * @return an array list of orders
     * @throws SQLException from resultSet.next()
     */
    public ArrayList<Order> createOrderList(ResultSet resultSet) throws SQLException {
        ArrayList <Order> orderList = new ArrayList<>();
        while(resultSet.next()){
            Product product = productDAO.toProduct(productDAO.findById(resultSet.getInt("product")));
            Client client = clientDAO.toClient(clientDAO.findById(resultSet.getInt("client")));
            Order order = new Order(resultSet.getInt("id"),product,client,
                    resultSet.getInt("quantity"));
            orderList.add(order);
        }
        return orderList;
    }
    /**
     * <p>Creates an Order from a result set. This method is used when a single order is a result of the query,
     * that is when searching by id. The method calls createOrderList then returns the first element of that list.
     * </p>
     * @param resultSet result of a query
     * @return an order
     * @throws SQLException from createOrderList
     */
    public Order toOrder(ResultSet resultSet) throws SQLException{
        ArrayList<Order> orders = createOrderList(resultSet);
        return orders.get(0);
    }

    /**
     * <p>Creates an insert query. The class Order has as fields a Product and a Client.
     * Thus, the abstract method for inserting does not work, because we want to insert the
     * id of the product, not the product itself.</p>
     * @param order order to insert
     * @return a string representing the query
     */
    public String createInsertQuery(Order order){
        return "INSERT INTO `order` VALUES (" + order.getId()+","+order.getClient().getId()+
                ","+order.getProduct().getId()+","+order.getQuantity()+");";
    }
    /**
     * <p>Creates an update query</p>
     * @param order order to update
     * @return a string representing the query
     */
    public String createUpdateQuery(Order order, int id){
        return "UPDATE `order`\nSET id = " + order.getId() + ", client = "+order.getClient().getId()+
                ", product = "+order.getProduct().getId()+", quantity = "+order.getQuantity()+
                " WHERE id = "+ id + ";";
    }
    /**
     * <p>Decrements the stock of a product after an order is inserted. It calls the update method on that product.</p>
     * @param order inserted ordered
     * @throws IllegalAccessException from updating the product
     */
    public void decrementStock(Order order) throws IllegalAccessException {
        Product product = order.getProduct();
        product.decrementStock(order.getQuantity());
        ProductDAO productDAO = new ProductDAO();
        productDAO.update(product,product.getId());
    }
}
