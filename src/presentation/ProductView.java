package presentation;

import businessLogic.ProductBLL;
import dataAccess.ProductDAO;
import model.Client;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * ProductView creates the user interface for operations on products.
 *
 * @author ioana
 */
public class ProductView extends JFrame {

    private final ProductBLL productBLL;

    private final Font titleFont = new Font("Roboto", Font.BOLD,16);
    private final Font simpleFont = new Font("Roboto", Font.PLAIN,15);
    private final Font boldFont = new Font("Roboto", Font.BOLD,13);

    private final JLabel operations = new JLabel("Product Operations");
    private final JLabel idLabel = new JLabel("Enter searched id:");
    private final JLabel nameLabel = new JLabel("Enter name of product: ");
    private final JLabel priceLabel = new JLabel("Enter price per unit: ");
    private final JLabel stockLabel = new JLabel("Enter amount in stock: ");
    private final JTextField idField = new JTextField();
    private final JTextField nameField = new JTextField();
    private final JTextField priceField = new JTextField();
    private final JTextField stockField = new JTextField();
    private final JButton viewProducts = new JButton("View Products");
    private final JButton addProduct = new JButton("Add Product");
    private final JButton deleteProduct = new JButton("Delete Product");
    private final JButton editProduct = new JButton("Edit Product");


    public ProductView(){
        productBLL = new ProductBLL();
        setSize(900,400);
        setLayout(null);
        setTitle("Client Operations");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        addComponents();
    }
    /**
     * <p>Adds components to the application window.
     * Adds functionality for buttons by calling methods from ProductBLL</p>
     */
    public void addComponents(){
        operations.setBounds(150,20,200,50);
        operations.setFont(titleFont);
        add(operations);
        idLabel.setBounds(40,60,200,50);
        idLabel.setFont(simpleFont);
        add(idLabel);
        idField.setBounds(170,75,30,20);
        idField.setFont(simpleFont);
        add(idField);
        viewProducts.setBounds(70,110,130,30);
        viewProducts.setFont(boldFont);
        viewProducts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showTable(productBLL.getAllProducts());
                } catch (SQLException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(viewProducts);
        addProduct.setBounds(70,150,130,30);
        addProduct.setFont(boldFont);
        addProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    productBLL.insertProduct(new Product(getSearchedID(), getEnteredPrice(),
                            getEnteredStock(),getEnteredName()));
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(addProduct);
        deleteProduct.setBounds(220,110,130,30);
        deleteProduct.setFont(boldFont);
        deleteProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    productBLL.deleteProduct(productBLL.findProductByID(getSearchedID()));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(deleteProduct);
        editProduct.setBounds(220,150,130,30);
        editProduct.setFont(boldFont);
        editProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    productBLL.editProduct(new Product(getSearchedID(),getEnteredPrice(),getEnteredStock(),getEnteredName()));
                } catch (IllegalAccessException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        add(editProduct);
        nameLabel.setBounds(30,200,170,30);
        nameLabel.setFont(simpleFont);
        add(nameLabel);
        nameField.setBounds(220,200,150,25);
        nameField.setFont(simpleFont);
        add(nameField);
        priceLabel.setBounds(30,240,170,30);
        priceLabel.setFont(simpleFont);
        add(priceLabel);
        priceField.setBounds(220,240,150,25);
        priceField.setFont(simpleFont);
        add(priceField);
        stockLabel.setBounds(30,280,170,30);
        stockLabel.setFont(simpleFont);
        add(stockLabel);
        stockField.setBounds(220,280,150,25);
        stockField.setFont(simpleFont);
        add(stockField);
    }

    /**
     * <p>Returns id entered by user</p>
     * @return id of product
     */
    public int getSearchedID(){
        return Integer.parseInt(idField.getText());
    }

    /**
     * <p>Returns name entered by user</p>
     * @return product name as String
     */
    public String getEnteredName(){
        return nameField.getText();
    }
    /**
     * <p>Returns price entered by user</p>
     * @return price of product
     */
    public int getEnteredPrice(){
        return Integer.parseInt(priceField.getText());
    }
    /**
     * <p>Returns stock entered by user</p>
     * @return number of products in stock
     */
    public int getEnteredStock(){
        return Integer.parseInt(stockField.getText());
    }
    /**
     * <p>Displays the table when hitting the "View Products" button. Calls createTable method on productList</p>
     *
     * @param productList list of all products returned by ProductBLL
     */
    public void showTable(ArrayList<Product> productList) throws IllegalAccessException {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(420,50,420,300);
        JTable products = productBLL.createTable(productList);
        products.setEnabled(true);
        products.setVisible(true);
        scrollPane.setViewportView(products);
        add(scrollPane);
    }
//    /**
//     * <p>Creates a JTable from a list of products</p>
//     * @param productList list of products
//     * @return a JTable with data from the list
//     */
//    public JTable createTable(ArrayList<Product> productList){
//        JTable table;
//        String[] columnNames = {"id","price","stock","name"};
//        DefaultTableModel myModel = new DefaultTableModel() {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return false;
//            }
//        };
//        myModel.setColumnIdentifiers(columnNames);
//        for(Product product:productList){
//            String[] row = new String[4];
//            row[0]= String.valueOf(product.getId());
//            row[1]= String.valueOf(product.getPrice());
//            row[2]= String.valueOf(product.getStock());
//            row[3]= product.getName();
//            myModel.addRow(row);
//        }
//        table = new JTable(myModel);
//        return table;
//    }

}
