package dataAccess;

import model.Client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * ClientDAO extends the AbstractDAO class and defines methods specifically for clients.
 *
 * @author ioana
 */
public class ClientDAO extends AbstractDAO<Client> {

    public ClientDAO() {
        super();
    }

    /**
     * <p>Creates an array list of all the clients in the database, by calling the findAll function.
     * It iterates through the result set and adds clients to the list.</p>
     * @return an arraylist of clients
     * @throws SQLException from resultSet.next()
     */
    public ArrayList<Client> getAllClients() throws SQLException {
        ArrayList <Client> clientList = new ArrayList<>();
        ResultSet resultSet = this.findAll();
        while(resultSet.next()){
            Client client = new Client(resultSet.getInt("id"),resultSet.getString("name"),
                    resultSet.getString("email"),resultSet.getString("phoneNb"));
            clientList.add(client);
        }
        return clientList;
    }

    /**
     * <p>Creates an array list of clients from a result set obtained from a previous query.</p>
     * @param resultSet the result of a query
     * @return an array list of clients
     * @throws SQLException from resultSet.next()
     */
    public ArrayList<Client> createClientList(ResultSet resultSet) throws SQLException {
        ArrayList<Client> clientList = new ArrayList<>();
        while(resultSet.next()){
            Client client = new Client(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("phoneNb"));
            clientList.add(client);
        }
        return clientList;
    }

    /**
     * <p>Creates a Client from a result set. This method is used when a single client is a result of the query,
     * that is when searching by id. The method calls createClientList then returns the first element of that list.
     * </p>
     * @param resultSet result of a query
     * @return a client
     * @throws SQLException from createClientList
     */
    public Client toClient(ResultSet resultSet) throws SQLException {
        ArrayList<Client> clients = createClientList(resultSet);
        return clients.get(0);
    }
}
