import de.micromata.opengis.kml.v_2_2_0.*;
import role.Driver;
import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * Class that imitates work of GPS-tracker. This read the KML file, change poles of Driver object
 * and send it to the main server with use Sender.
 */

public class KMLReader {
    /**
     * Driver, that use tracker.
     */
    private Driver driver;

    /**
     * The Sender, that used to send driver to the main server.
     */
    private Sender sender;

    /**
     * Work with kml data.
     */
    private Kml kml;

    /**
     * Speed of auto.
     */
    private double speed = 44.28; //km per h

    /**
     * Count of dots, that registered on all distance
     */
    private int dots = 29673;

    /**
     * Distance in km, that driver was drive.
     */
    private double distance = 7371.94;//km

    /**
     * Time out of the tracker for imitation of work.
     */
    private int timeOutForDots;

    /**
     * Constructor. Take the Sender and Driver objects, init of kml and numeric of waiting time (time out).
     */
    public KMLReader(Sender s, Driver d) {
        driver = d;
        sender = s;
        try {
            //переменная описывает с каким периодом необходимо осуществлять отправку
            timeOutForDots = (int) (1 / (((dots/distance)*speed)/3600))*1000;
            System.out.println(timeOutForDots);
            //ClassLoader cl = this.getClass().getClassLoader();
            //File kmlFile = new File(cl.getResource("17741.kml").toURI());

            InputStream is = this.getClass().getResourceAsStream("17741.kml");

            kml = Kml.unmarshal(is);
        }
        catch(Exception e) {e.printStackTrace();}
    }


    /**
     * Thit method takes the coords from kml file and send it to the main server.
     *
     */
    public void startSendCoordsOfDriver() {
        Folder folder = (Folder) kml.getFeature();
        Placemark placemark = (Placemark) folder.getFeature().get(0);
        LineString lineString = (LineString) placemark.getGeometry();
        List<Coordinate> coordsList = lineString.getCoordinates();

        for (Coordinate coord : coordsList) {
            driver.setLongitude(coord.getLongitude());
            driver.setLatitude(coord.getLatitude());
            sender.sendDriver(driver);
            try {
                Thread.sleep(timeOutForDots);
            }
            catch (InterruptedException e) {
                System.out.println("=======Error in thread part=======");
                e.printStackTrace();
            }

        }
    }

}
