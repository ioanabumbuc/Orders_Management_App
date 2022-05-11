package presentation;

import businessLogic.ClientBLL;
import businessLogic.OrderBLL;
import businessLogic.ProductBLL;
import model.Client;
import model.Order;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * creates the user interface for orders using Java Swing
 *
 * @author ioana
 */
public class OrderView extends JFrame {
    private final OrderBLL orderBLL;
    private final Font titleFont = new Font("Roboto", Font.BOLD,16);
    private final Font simpleFont = new Font("Roboto", Font.PLAIN,15);
    private final Font boldFont = new Font("Roboto", Font.BOLD,13);
    private final JLabel operations = new JLabel("Order Operations");
    private final JLabel orderIdLabel = new JLabel("Enter order id:");
    private final JLabel productIdLabel = new JLabel("Enter product id:");
    private final JLabel clientIdLabel = new JLabel("Enter client id:");
    private final JLabel quantityLabel = new JLabel("Enter quantity:");
    private final JTextField orderIdField = new JTextField();
    private final JTextField productIdField = new JTextField();
    private final JTextField clientIdField = new JTextField();
    private final JTextField quantityField = new JTextField();
    private final JButton viewOrders = new JButton("View Orders");
    private final JButton addOrder = new JButton("Add Order");
    private final JButton deleteOrder = new JButton("Delete Order");
    private final JButton editOrder = new JButton("Edit Order");

    public OrderView(){
        orderBLL = new OrderBLL();
        setSize(900,400);
        setLayout(null);
        setTitle("Client Operations");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        addComponents();
    }

    /**
     * <p>Adds components to interface. Provides functionality for buttons using OrderBLL.</p>
     */
    public void addComponents(){
        operations.setBounds(150,20,200,50);
        operations.setFont(titleFont);
        add(operations);
        orderIdLabel.setBounds(40,60,200,50);
        orderIdLabel.setFont(simpleFont);
        add(orderIdLabel);
        orderIdField.setBounds(170,75,30,20);
        orderIdField.setFont(simpleFont);
        add(orderIdField);
        viewOrders.setBounds(70,110,130,30);
        viewOrders.setFont(boldFont);
        viewOrders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showTable(orderBLL.getAllOrders());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(viewOrders);
        addOrder.setBounds(70,150,130,30);
        addOrder.setFont(boldFont);
        addOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientBLL clientBLL = new ClientBLL();
                ProductBLL productBLL = new ProductBLL();
                try {
                    Client client = clientBLL.findClientByID(getClientID());
                    Product product = productBLL.findProductByID(getProductID());
                    Order order = new Order(getOrderID(),product,client,getQuantity());
                    orderBLL.insertOrder(order);
                    BillCreator billCreator = new BillCreator(order);
                    billCreator.createBill();
                } catch (SQLException | IllegalAccessException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(addOrder);
        deleteOrder.setBounds(220,110,130,30);
        deleteOrder.setFont(boldFont);
        deleteOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    orderBLL.deleteOrder(orderBLL.findOrderByID(getOrderID()));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(deleteOrder);
        editOrder.setBounds(220,150,130,30);
        editOrder.setFont(boldFont);
        editOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientBLL clientBLL = new ClientBLL();
                ProductBLL productBLL = new ProductBLL();
                Client client = null;
                try {
                    client = clientBLL.findClientByID(getClientID());
                    Product product = productBLL.findProductByID(getProductID());
                    Order order = new Order(getOrderID(),product,client,getQuantity());
                    orderBLL.editOrder(order);
                } catch (SQLException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(editOrder);
        productIdLabel.setBounds(30,200,170,30);
        productIdLabel.setFont(simpleFont);
        add(productIdLabel);
        productIdField.setBounds(220,200,150,25);
        productIdField.setFont(simpleFont);
        add(productIdField);
        clientIdLabel.setBounds(30,240,170,30);
        clientIdLabel.setFont(simpleFont);
        add(clientIdLabel);
        clientIdField.setBounds(220,240,150,25);
        clientIdField.setFont(simpleFont);
        add(clientIdField);
        quantityLabel.setBounds(30,280,170,30);
        quantityLabel.setFont(simpleFont);
        add(quantityLabel);
        quantityField.setBounds(220,280,150,25);
        quantityField.setFont(simpleFont);
        add(quantityField);
    }

    /**
     * <p>Returns ID of order entered by user</p>
     * @return order ID
     */
    public int getOrderID(){
        return Integer.parseInt(orderIdField.getText());
    }

    /**
     * <p>Returns ID of product entered by user</p>
     * @return product ID
     */
    public int getProductID(){
        return Integer.parseInt(productIdField.getText());
    }

    /**
     * <p>Returns ID of client entered by user</p>
     * @return client ID
     */
    public int getClientID(){
        return Integer.parseInt(clientIdField.getText());
    }

    /**
     * <p>Returns quantity entered by user</p>
     * @return quantity
     */
    public int getQuantity(){
        return Integer.parseInt(quantityField.getText());
    }
    /**
     * <p>Displays the table when hitting the "View Orders" button. Calls createTable method on orderList</p>
     *
     * @param orderList list of all orders returned by OrderBLL
     */
    public void showTable(ArrayList<Order> orderList){
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(420,50,420,300);
        JTable orders = createTable(orderList);
        orders.setEnabled(true);
        orders.setVisible(true);
        scrollPane.setViewportView(orders);
        add(scrollPane);
    }
    /**
     * <p>Creates a JTable from a list of orders.</p>
     * @param orderList list of orders
     * @return a JTable with data from the list
     */
    public JTable createTable(ArrayList<Order> orderList){
        JTable table;
        String[] columnNames = {"id","client id","product id","quantity"};
        DefaultTableModel myModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        myModel.setColumnIdentifiers(columnNames);
        for(Order order:orderList){
            String[] row = new String[4];
            row[0]= String.valueOf(order.getId());
            row[1]= String.valueOf(order.getClient().getId());
            row[2]= String.valueOf(order.getProduct().getId());
            row[3]= String.valueOf(order.getQuantity());
            myModel.addRow(row);
        }
        table = new JTable(myModel);
        return table;
    }
}
