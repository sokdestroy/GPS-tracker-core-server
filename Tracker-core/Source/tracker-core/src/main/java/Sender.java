import com.google.gson.Gson;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import role.Driver;

/**
 * Class sends data to the server, that have address:
 * Adress = serverAdress:port
 */
public class Sender {
    /**
     * Address of main server
     */
    private final String serverAddress = "127.0.0.1";

    /**
     * Used port by main server
     */
    private final int serverPort = 5000;

    /**
     * Socket used for connection with main server
     */
    private Socket senderSocket;

    /**
     * Output stream for work with socket
     */
    private OutputStream oStream;

    /**
     * Datastream for work with output stream :)
     */
    private DataOutputStream dataOStream;

    /**
     * Convert into JSON to send
     */
    private Gson gson;

    /**
     * Constructor for init socket, streams and gson
     * */
    public Sender() {
        try {
            senderSocket = new Socket(InetAddress.getByName(serverAddress), serverPort);
            oStream = senderSocket.getOutputStream();
            dataOStream = new DataOutputStream(oStream);
            System.out.println("Connect is OK.");

            gson = new Gson();
        }
        catch(UnknownHostException e) {
            System.out.println("========Unknown host========");
            System.out.println("In init");
            e.printStackTrace();
            System.out.println("========Unknown host========");
        }
        catch (IOException e) {
            System.out.println("========IO Exception========");
            System.out.println("In init");
            e.printStackTrace();
            System.out.println("========IO Exception========");
        }
    }

    /**
     * Send the Driver object like JSON string with sockets
     * @param d Sent driver
     */
    public void sendDriver(Driver d) {
        try {
            String driver = gson.toJson(d, Driver.class);
            dataOStream.writeUTF(driver);
        }
        catch(IOException e) {
            System.out.println("========IO Exception========");
            System.out.println("In sendDriver");
            e.printStackTrace();
            System.out.println("========IO Exception========");
        }
    }
}
