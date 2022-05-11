package businessLogic;

import businessLogic.validators.EmailValidator;
import businessLogic.validators.PhoneValidator;
import businessLogic.validators.Validator;
import dataAccess.ClientDAO;
import model.Client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;


/**
 * ClientBLL does operations on clients at business logic level.
 * Its purpose is to validate data and call functions for viewing, inserting, deleting and editing clients.
 *
 * @author ioana
 */

public class ClientBLL {

    private final ArrayList<Validator<Client>> clientValidators;
    private final ClientDAO clientDAO;

    public ClientBLL(){
        clientValidators = new ArrayList<>();
        clientValidators.add(new PhoneValidator());
        clientValidators.add(new EmailValidator());
        clientDAO = new ClientDAO();
    }
    /**
     * <p>The method calls getAllClients from the data access class for a client</p>
     * @return an array list of all the clients in the database
     * @throws SQLException if resultSet is null
     */
    public ArrayList<Client> getAllClients() throws SQLException {
        return clientDAO.getAllClients();
    }
    /**
     * <p>Given an existing id the method will return the client that has that id in the database.
     * If the client doesn't exist it throws an exception.</p>
     * @param id given id to search
     * @return the searched client with the given id
     * @throws SQLException if resultSet is null
     */
    public Client findClientByID(int id) throws SQLException {
        if(clientDAO.createClientList(clientDAO.findById(id)).size()==0){
            throw new NoSuchElementException("Client with id "+id+" not found");
        }
        return clientDAO.createClientList(clientDAO.findById(id)).get(0);
    }
    /**
     * <p>Given an object of class client, the method inserts that client into the database.
     * The client is validated with the list of validators</p>
     * @param client the given client to insert
     * @throws IllegalAccessException from insert method
     */
    public void insertClient(Client client) throws IllegalAccessException {
        for(Validator<Client> validator:clientValidators){
            validator.validate(client);
        }
        clientDAO.insert(client);
    }
    /**
     * <p>Deletes the given client from the database</p>
     * @param client to delete
     */
    public void deleteClient(Client client){
        clientDAO.delete(client,client.getId());
    }
    /**
     * <p>Edits an existing client with the data of a given client</p>
     * @param client the new client to add in the place of the old one
     * @throws IllegalAccessException from update method
     */
    public void editClient(Client client) throws IllegalAccessException {
        clientDAO.update(client,client.getId());
    }
}
