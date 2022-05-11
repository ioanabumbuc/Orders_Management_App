package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * View class creates the main window of the application.
 * It lets the user choose on what they want to perform operations: clients, products or orders.
 *
 * @author ioana
 */
public class View extends JFrame {
    private final Font titleFont = new Font("Roboto", Font.BOLD,17);
    private final Font boldFont = new Font("Roboto", Font.BOLD,15);

    private final JLabel chooseLabel = new JLabel("Choose table to manage");
    private final JButton client = new JButton("Clients");
    private final JButton product = new JButton("Products");
    private final JButton order = new JButton("Orders");

    public View(){
        setSize(350,350);
        setLayout(null);
        setTitle("Orders Management");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addComponents();
    }

    /**
     * <p>Displays components on the window. Provides functionality to buttons.
     * Opens ClientView, OrderView or ProductView, depending on what button is pressed.</p>
     *
     */
    public void addComponents(){
        chooseLabel.setBounds(65,30,200,30);
        chooseLabel.setFont(titleFont);
        add(chooseLabel);
        client.setBounds(90,80,150,40);
        client.setFont(boldFont);
        client.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientView clientView = null;
                try {
                    clientView = new ClientView();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                clientView.setVisible(true);
            }
        });
        add(client);
        product.setBounds(90,140,150,40);
        product.setFont(boldFont);
        product.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductView productView = new ProductView();
                productView.setVisible(true);
            }
        });
        add(product);
        order.setBounds(90,200,150,40);
        order.setFont(boldFont);
        order.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderView orderView = new OrderView();
                orderView.setVisible(true);
            }
        });
        add(order);
    }
}
