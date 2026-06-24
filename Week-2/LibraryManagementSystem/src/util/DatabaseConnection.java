package util;
import java.sql.Connection; // for building a live link with db
import java.sql.DriverManager; // it knows how to open the portal to db
import java.sql.SQLException; // for handling db errors

public class DatabaseConnection{

    // jdbc = java database connectivity
    // where to build db?

    private static final String URL = "jdbc:sqlite:library.db";

    // open the portal and get the connection to library.db
    
    public static Connection getConnection() throws SQLException{
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite JDBC driver not found", e);
        }
        return DriverManager.getConnection(URL);
    }
}