package dataAccess;

import connection.ConnectionFactory;
import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * Abstract Class for data access operations.
 * @param <T> generic parameter that can be a client, product or order
 * @author ioana
 */
public class AbstractDAO<T> {
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    /**
     * <p>Creates a query to select all data in a table.</p>
     * @return a string representing the query
     */
    public String createSelectAllQuery(){
        return "SELECT * FROM `" + type.getSimpleName()+"`";
    }
    /**
     * <p>Creates a query to select an entry depending on given id.</p>
     * @param id searched id
     * @return a string representing the query
     */
    public String createSelectQuery(int id){
        return "SELECT * FROM `" + type.getSimpleName() + "` WHERE ID = " + id;
    }
    /**
     * <p>Creates a query for deleting an entry depending on given id</p>
     * @param id id of entry to delete
     * @return a string representing the query
     */
    public String createDeleteQuery(int id){
        return "DELETE FROM `" + type.getSimpleName() + "` WHERE ID = "+id;

    }
    /**
     * <p>Creates a query for inserting the given object in the database.
     * Uses reflection technique to extract the object properties in order to receive the data to enter in the table.
     * It uses field.get(t) to get the information on that object. The name of the field is not needed in this case.
     * </p>
     * @param t generic object to insert
     * @return a string representing the insert query
     * @throws IllegalAccessException for accessing a field
     */
    public String createInsertQuery(T t) throws IllegalAccessException {
        String s = "INSERT INTO `" + type.getSimpleName() + "` VALUES (";
        int length = t.getClass().getDeclaredFields().length;
        int i=0;
        for(Field field: t.getClass().getDeclaredFields()){
            field.setAccessible(true);
            if(i!=length-1) {
                //not the last field
                s = s.concat("'"+field.get(t)+"'"+ ",");
            }else{
                //last field - close bracket
                s = s.concat("'"+field.get(t)+"'"+");");
            }
            i++;
        }
        return s;
    }

    /**
     * <p>Creates a query for updating the given object in the database, having its id
     * Uses reflection technique to extract the object properties in order to receive the data to enter in the table.
     * It uses field.get(t) to get the information on that object and field.getName() to get the name of the property,
     * corresponding to a column in a table.
     * </p>
     * @param t object to update
     * @param id of given object
     * @return a string representing a query
     * @throws IllegalAccessException for accessing a field
     */
    public String createUpdateQuery(T t, int id) throws IllegalAccessException {
        String s = "UPDATE `"+type.getSimpleName()+"`\n"+"SET ";
        int length = t.getClass().getDeclaredFields().length;
        int i=0;
        for(Field field: t.getClass().getDeclaredFields()){
            field.setAccessible(true);
            if(i!=length-1) {
                //not the last field
                s = s.concat(field.getName()+" = ");
                s = s.concat("'"+field.get(t)+ "',");
            }else{
                //last field
                s = s.concat(field.getName()+" = ");
                s = s.concat("'"+field.get(t)+ "'");
            }
            i++;
        }
        s = s.concat("WHERE id = "+id);
        return s;
    }

    /**
     * <p>Finds all entries of a table. Calls createSelectAllQuery method to get the query, then executes it.</p>
     * @return a ResultSet containing the result of the query
     */

    public ResultSet findAll(){
        ResultSet resultSet;
        Connection connection = null;
        PreparedStatement preparedStatement=null;
        String query = createSelectAllQuery();
        try{
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <p>Finds an entry in the table at the given id.
     * Calls createSelectQuery method to get the query, then executes it.</p>
     * @param id the searched id
     * @return result of select query
     */
    public ResultSet findById(int id){
        ResultSet resultSet;
        Connection connection = null;
        PreparedStatement preparedStatement=null;
        String query = createSelectQuery(id);
        try{
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            //if(resultSet.
            return resultSet;
        } catch (SQLException e) {
            System.out.println("id not found");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <p>Inserts a given object in a table. Calls createInsertQuery method on the given object.
     * Executes the query.</p>
     * @param t given object to insert
     * @throws IllegalAccessException from createInsertQuery()
     */
    public void insert(T t) throws IllegalAccessException {
        String query = createInsertQuery(t);
        Connection connection = null;
        PreparedStatement preparedStatement=null;
        try{
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
    }
    /**
     * <p>Deletes a given object from a table. Calls createDeleteQuery method on the given object.
     * Executes the query.</p>
     * @param t given object to delete
     * @param id the id of the given object
     */
    public void delete(T t, int id){
        String query = createDeleteQuery(id);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * <p>Updates an object in the database with the given id. It is replaced by the given object.</p>
     * @param t object to update
     * @param id of object
     * @throws IllegalAccessException from createUpdateQuery
     */
    public void update(T t, int id) throws IllegalAccessException {
        String query = createUpdateQuery(t,id);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            ConnectionFactory.close(preparedStatement);
            ConnectionFactory.close(connection);
        }
    }

    public JTable createTable(ArrayList<T> list) throws IllegalAccessException {
        JTable table;
        String[] columnNames = new String[list.get(0).getClass().getDeclaredFields().length];
        DefaultTableModel myModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        int i=0;
        for(Field field:list.get(0).getClass().getDeclaredFields()){
            columnNames[i] = field.getName();
            i++;
        }
        myModel.setColumnIdentifiers(columnNames);
        for(T t:list){
            String[] row = new String[4];
            i=0;
            for(Field field: t.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                row[i] = field.get(t).toString();
                i++;
            }
            myModel.addRow(row);
        }
        table = new JTable(myModel);
        return table;
    }


}
