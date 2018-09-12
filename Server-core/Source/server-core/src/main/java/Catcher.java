import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import com.google.gson.*;
import role.Driver;

/**
 * Class that do connection with tracker, catch message from this in JSON format and insert it into databse.
 * Class implements Runnable interface for realization multithreading. It may be used for work with many drivers.
 */
public class Catcher implements Runnable {

    /**
     * Socket used to work with tracker.
     */
    private Socket socket;

    /**
     * Writer, that make writing into DB.
     */
    private Writer writer;

    /**
     * Constructor. Takes Socket and writer.
     * EVERY Catcher used a COMMON writer because H2 DB supporting connection just with ONE user.
     */
    public Catcher(Socket s, Writer w) {
        System.out.println("Have connection.");
        socket = s;
        writer = w;
    }

    /**
     * Main method for implements of functional of this class
     */
    public void run() {
        try {
            InputStream iStream = socket.getInputStream();

            DataInputStream datInStream = new DataInputStream(iStream);

            Gson gson = new Gson();
            String driverData;
            Driver driver;
            while (true) {
                driverData = datInStream.readUTF();
                driver = gson.fromJson(driverData,Driver.class);
                writer.insertIntoDB(driver);
            }
        }
        catch(IOException e) {e.printStackTrace();}

    }
}
