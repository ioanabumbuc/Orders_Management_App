package presentation;
import businessLogic.ClientBLL;
import model.Client;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * ClientView creates the user interface for client operations, using java swing
 *
 * @author ioana
 */
public class ClientView extends JFrame {

    private final ClientBLL clientBLL;

    private final Font titleFont = new Font("Roboto", Font.BOLD,16);
    private final Font simpleFont = new Font("Roboto", Font.PLAIN,15);
    private final Font boldFont = new Font("Roboto", Font.BOLD,13);
    private final JLabel operations = new JLabel("Client Operations");
    private final JLabel idLabel = new JLabel("Enter searched id:");
    private final JLabel nameLabel = new JLabel("Enter full name: ");
    private final JLabel emailLabel = new JLabel("Enter email address: ");
    private final JLabel phoneLabel = new JLabel("Enter phone number: ");
    private final JTextField idField = new JTextField();
    private final JTextField nameField = new JTextField();
    private final JTextField emailField = new JTextField();
    private final JTextField phoneField = new JTextField();
    private final JButton viewClients = new JButton("View Clients");
    private final JButton addClient = new JButton("Add Client");
    private final JButton deleteClient = new JButton("Delete Client");
    private final JButton editClient = new JButton("Edit Client");

    public ClientView() throws SQLException {
        clientBLL = new ClientBLL();
        setSize(900,400);
        setLayout(null);
        setTitle("Client Operations");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        addComponents();
    }

    /**
     * <p>Adds components to the application window.
     * Adds functionality for buttons by calling methods from ClientBLL</p>
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
        nameLabel.setBounds(30,200,150,30);
        nameLabel.setFont(simpleFont);
        add(nameLabel);
        nameField.setBounds(200,200,150,25);
        nameField.setFont(simpleFont);
        add(nameField);
        emailLabel.setBounds(30,240,150,30);
        emailLabel.setFont(simpleFont);
        add(emailLabel);
        emailField.setBounds(200,240,150,25);
        emailField.setFont(simpleFont);
        add(emailField);
        phoneLabel.setBounds(30,280,150,30);
        phoneLabel.setFont(simpleFont);
        add(phoneLabel);
        phoneField.setBounds(200,280,150,25);
        phoneField.setFont(simpleFont);
        add(phoneField);
        viewClients.setBounds(70,110,130,30);
        viewClients.setFont(boldFont);
        viewClients.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showTable(clientBLL.getAllClients());
                } catch (SQLException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(viewClients);
        addClient.setBounds(70,150,130,30);
        addClient.setFont(boldFont);
        addClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clientBLL.insertClient(new Client(getSearchedID(),getEnteredName(),getEnteredEmail(),getEnteredPhoneNb()));
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(addClient);
        deleteClient.setBounds(220,110,130,30);
        deleteClient.setFont(boldFont);
        deleteClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clientBLL.deleteClient(clientBLL.findClientByID(getSearchedID()));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(deleteClient);
        editClient.setBounds(220,150,130,30);
        editClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clientBLL.editClient(new Client(getSearchedID(),getEnteredName(),getEnteredEmail(),getEnteredPhoneNb()));
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        });
        editClient.setFont(boldFont);
        add(editClient);
    }

    /**
     * <p>Retrieves id entered by user</p>
     * @return id
     */
    public int getSearchedID(){
        return Integer.parseInt(idField.getText());
    }

    /**
     * <p>Retrieves client name entered by user</p>
     * @return name of client
     */
    public String getEnteredName(){
        return nameField.getText();
    }

    /**
     * <p>Retrieves email entered by user</p>
     * @return email of client
     */
    public String getEnteredEmail(){
        return emailField.getText();
    }

    /**
     * <p>Retrieves phone number entered by user</p>
     * @return phone number as a String
     */
    public String getEnteredPhoneNb(){
        return phoneField.getText();
    }

    /**
     * <p>Displays the table when hitting the "View Clients" button. Calls createTable method on clientList</p>
     *
     * @param clientList list of all clients returned by ClientBLL
     */
    public void showTable(ArrayList<Client> clientList) throws IllegalAccessException {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(420,50,420,300);
        //JTable clients = createTable(clientList);
        JTable clients = clientBLL.createTable(clientList);
        clients.setEnabled(true);
        clients.setVisible(true);
        scrollPane.setViewportView(clients);
        add(scrollPane);
    }
//    /**
//     * <p>Creates a JTable from a list of clients. It adds to each row the values from a client.</p>
//     * @param clientList list of clients
//     * @return a JTable with data from the list
//     */
//    public JTable createTable(ArrayList<Client> clientList){
//        JTable table;
//        String[] columnNames = {"id","name","email","phoneNb"};
//        DefaultTableModel myModel = new DefaultTableModel() {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return false;
//            }
//        };
//        myModel.setColumnIdentifiers(columnNames);
//        for(Client client:clientList){
//            String[] row = new String[4];
//            row[0]= String.valueOf(client.getId());
//            row[1]=client.getName();
//            row[2]=client.getEmail();
//            row[3]=client.getPhoneNb();
//            myModel.addRow(row);
//        }
//        table = new JTable(myModel);
//        return table;
//    }
}

