import presentation.View;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Main class - runs the application by creating a new object of class View and setting it visible.
 */
public class Main {
    public static void main(String[] args){
        View view = new View();
        view.setVisible(true);
    }
}
