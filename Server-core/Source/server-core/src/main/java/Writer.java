import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import role.Driver;

/**
 * Class implements work with built-in H2 database. This take Driver and paste it into table.
 */
public class Writer {
    /**
     * Query that implements INSERT data into table.
     */
    private String insertQuery = "INSERT INTO driversPositions(idDriver,driverName,latitude,longitude) VALUES" +
            "(?,?,?,?);";

    /**
     * Querry that implements creating of table if it not exist.
     */
    private String createTableQuery = "CREATE TABLE IF NOT EXISTS driversPositions(idDriver INTEGER, driverName VARCHAR(50), " +
            "latitude FLOAT, longitude FLOAT);";


    /**
     * Variable that take connection with DB.
     */
    private Connection connection;

    /**
     * Variable that take do the query
     */
    private Statement statement;

    /**
     * Vatiable that take do the query
     */
    private PreparedStatement prStatement;

    /**
     * Constructor. Do connection with DB and create table driversPositions if it not exists.
     */
    public Writer() {
        try {
            Class.forName("org.h2.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:h2:./Drivers","sa","");
            System.out.println("Work with 'Drivers' DB.");

            statement = connection.createStatement();
            statement.execute(createTableQuery);
        }
        catch(SQLException e) {e.printStackTrace();}
        catch(Exception e) {e.printStackTrace();}
    }

    /**
     * Method that do insert drivers data into database.
     * @param d driver that need to paste.
     */
    public synchronized void insertIntoDB(Driver d) {
        try {
            System.out.print("Insert into DB: ");

            prStatement = connection.prepareStatement(insertQuery);

            prStatement.setInt(1,d.getDriverId());
            prStatement.setString(2,d.getDriverName());
            prStatement.setDouble(3,d.getLatitude());
            prStatement.setDouble(4,d.getLongitude());
            prStatement.execute();

            System.out.println("OK");
        }
        catch(SQLException e) {
            System.out.println("NO");
            e.printStackTrace();
        }
    }


    /**
     * Getter for statement
     * @return statement
     */
    public Statement getStat() {
        return statement;
    }
}
