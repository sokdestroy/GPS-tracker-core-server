import role.Driver;

public class TrackerCore {
    public static void main(String[] args) {
        Driver driver = new Driver(1,"Ivan Ivanov");
        Sender sender = new Sender();

        KMLReader reader = new KMLReader(sender, driver);
        reader.startSendCoordsOfDriver();
    }
}
