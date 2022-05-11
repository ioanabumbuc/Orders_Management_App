package presentation;
import model.Order;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * BillCreator creates a txt file with the bill for an order.
 *
 * @author ioana
 */
public class BillCreator {
    private final Order order;

    public BillCreator(Order order) throws IOException {
        this.order=order;
    }
    /**
     * <p>Creates a bill with a random name containing the details of an order.</p>
     * @throws IOException from writing in a file
     */
    public void createBill() throws IOException {
        String fileName;
        Random random = new Random();
        int r = random.nextInt();
        while(r < 0){
            r = random.nextInt();
        }
        fileName = "order"+r+".txt";
        BufferedWriter outputFile = new BufferedWriter(new FileWriter(fileName));
        outputFile.write("Order number "+r+":\nClient: "+order.getClient().getName()+"\nProduct: "+
                order.getProduct().getName()+"\nQuantity: "+order.getQuantity()+"\nTotal Price: $"
                +order.getOrderPrice());
        outputFile.close();
    }
}

