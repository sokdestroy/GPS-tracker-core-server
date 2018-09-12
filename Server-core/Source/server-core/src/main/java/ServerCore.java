import role.Driver;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Class where implements connection with driver's trackers. Every new driver throw into new thread with Catcher object
 * that can catch message from tracker and insert it into DB with Writer.
 */
public class ServerCore {
    // EVERY Catcher used a COMMON writer because H2 DB supporting connection just with ONE user.

    public static void main(String[] args) {
        Writer mainWriter = new Writer();
        Thread thread;
        try {
            ServerSocket servSock = new ServerSocket(5000);
            System.out.println("Init the server on port 5000.");
            while (true) {
                thread = new Thread(new Catcher(servSock.accept(),mainWriter));
                thread.start();
            }
        }
        catch(IOException e) {e.printStackTrace();}
    }
}
