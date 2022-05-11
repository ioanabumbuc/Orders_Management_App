package connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ConnectionFactory makes the connection to the database.
 *
 * @author ioana
 */
public class ConnectionFactory {

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DBURL = "jdbc:mysql://localhost:3306/warehouse";
    private static final String USER = "root";
    private static final String PASS = "keL8rift";

    private static ConnectionFactory singleInstance = new ConnectionFactory();

    /**
     * <p>Creates the connection to the database using the given url, user, password.
     * Throws an exception if connection is unsuccessful</p>
     * @return connection
     */
    private Connection createConnection(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(DBURL,USER,PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    /**
     * <p>calls createConnection</p>
     * @return connection if successful, throws exception otherwise
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }
    /**
     * <p>closes connection</p>
     * @param connection established connection
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the connection");
            }
        }
    }
    /**
     * <p>closes statement</p>
     * @param statement prepared statement in DAO
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the statement");
            }
        }
    }
    /**
     * <p>Closes result set</p>
     * @param resultSet result of query
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the ResultSet");
            }
        }
    }
}
