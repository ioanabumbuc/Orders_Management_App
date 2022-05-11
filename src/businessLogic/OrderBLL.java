package businessLogic;

import businessLogic.validators.*;
import dataAccess.OrderDAO;
import model.Order;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * OrderBLL does operations on orders at business logic level.
 * Its purpose is to validate data and call functions for viewing, inserting, deleting and editing orders.
 *
 * @author ioana
 */

public class OrderBLL {
    private final Validator<Order> orderValidator;
    private final OrderDAO orderDAO;

    public OrderBLL(){
        orderValidator = new QuantityValidator();
        orderDAO = new OrderDAO();
    }
    /**
     * <p>The method calls getAllOrders from the data access class for an order</p>
     * @return an array list of all the orders in the database
     * @throws SQLException if resultSet is null
     */
    public ArrayList<Order> getAllOrders() throws SQLException {
        return orderDAO.getAllOrders();
    }
    /**
     * <p>Given an existing id the method will return the order that has that id in the database</p>
     * @param id given id to search
     * @return the searched order with the given id
     * @throws SQLException if resultSet is null
     */
    public Order findOrderByID(int id) throws SQLException {
        Order order = orderDAO.toOrder(orderDAO.findById(id));
        if(order == null){
            throw new NoSuchElementException("Client with id "+id+" not found");
        }
        return order;
    }
    /**
     * <p>Given an object of class order, the method inserts that order into the database.
     * The order is validated with quantity validator</p>
     * @param order the given order to insert
     * @throws IllegalAccessException from insert method
     */
    public void insertOrder(Order order) throws IllegalAccessException {
        orderValidator.validate(order);
        orderDAO.insert(order);
        orderDAO.decrementStock(order);
    }
    /**
     * <p>Deletes the given order from the database</p>
     * @param order to delete
     */
    public void deleteOrder(Order order){
        orderDAO.delete(order,order.getId());
    }
    /**
     * <p>Edits an existing order with the data of the given order</p>
     * @param order the new order to add in the place of the old one
     * @throws IllegalAccessException from update method
     */
    public void editOrder(Order order) throws IllegalAccessException {
        orderDAO.update(order,order.getId());
    }
}
