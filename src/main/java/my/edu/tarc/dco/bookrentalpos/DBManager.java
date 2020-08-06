package my.edu.tarc.dco.bookrentalpos;

/**
 * @author Looz
 */

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class used to execute queries for database
 *
 * @author Looz
 * @version 1.0
 */
public class DBManager {

    private String lastErrorMessage;
    private Connection conn;
    private final String dbPath = System.getenv("APPDATA") + "\\BookRentalPos\\";
    private final String dbName = "data.db";

    /**
     * Only call once throughout the project and pass the reference around
     * thanks
     */
    public DBManager() {
        conn = null;
        connectDB();
        prepareTable();
    }

    /**
     * Connect to Database
     */
    private void connectDB() {
        // make sure directory exist
        new File(dbPath).mkdir();
        String url = "jdbc:sqlite:" + dbPath + dbName;

        // Connecting to the database
        try {
            conn = DriverManager.getConnection(url);
            if (conn == null) {
                throw new SQLException();
            }
        } catch (SQLException err) {
            System.out.println("Failed to connect to database.\nMissing APPDATA env ?");
            System.out.println(err.getMessage());
            this.lastErrorMessage = err.getMessage();
            System.exit(1);
        }
    }

    /**
     * Set up database schema
     */
    private void prepareTable() {
        String tab1 = "CREATE TABLE IF NOT EXISTS staff (\n"
                + "	id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "	date datetime default current_timestamp,\n"
                + "	name TEXT NOT NULL UNIQUE,\n"
                + "	password TEXT NOT NULL\n"
                + ");";
        String tab2 = "CREATE TABLE IF NOT EXISTS member (\n"
                + "	id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "	date datetime default current_timestamp,\n"
                + "	name TEXT NOT NULL,\n"
                + "	phoneNo TEXT,\n"
                + "	email TEXT,\n"
                + "	IC TEXT NOT NULL\n"
                + ");";
        String tab3 = "CREATE TABLE IF NOT EXISTS book (\n" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "date datetime default current_timestamp,\n" +
                "title TEXT NOT NULL,\n" +
                "author TEXT,\n" +
                "rentalPrice DOUBLE NOT NULL,\n" +
                "lastRentedBy INTEGER,\n" +
                "lastReservedBy INTEGER,\n" +
                "isRented INTEGER,\n" +
                "isReserved INTEGER,\n" +
                "FOREIGN KEY (lastRentedBy) REFERENCES member(id),\n" +
                "FOREIGN KEY (lastReservedBy) REFERENCES member(id)\n" +
                ");";

        String tab4 = "CREATE TABLE IF NOT EXISTS transactions (\n"
                + "	id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "	date datetime default current_timestamp,\n"
                + "	rentDurationInDays INTEGER,\n"
                + "	type TEXT,\n"
                + "	staffHandled INTEGER,\n"
                + "	memberInvolved INTEGER,\n"
                + "	bookInvolved INTEGER,\n"
                + "	FOREIGN KEY (staffHandled) REFERENCES staff(id),\n"
                + "	FOREIGN KEY (memberInvolved) REFERENCES member(id),\n"
                + "	FOREIGN KEY (bookInvolved) REFERENCES book(id)\n"
                + ");";

        try {
            Statement stmt = conn.createStatement();
            stmt.addBatch(tab1);
            stmt.addBatch(tab2);
            stmt.addBatch(tab3);
            stmt.addBatch(tab4);
            stmt.executeBatch();
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            this.lastErrorMessage = err.getMessage();
        }
    }

    /**
     * Execute update Query
     *
     * @param q SQLITE query
     * @return rows are affected, return -1 if SQLException thrown
     */
    public int updateQuery(String q) {
        try {
            Statement stmt = conn.createStatement();
            return stmt.executeUpdate(q);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            this.lastErrorMessage = err.getMessage();
            return -1;
        }
    }

    /**
     * Execute query
     *
     * @param q SQLITE Query
     * @return true if the query returned a ResultSet
     */
    public boolean execQuery(String q) {
        try {
            Statement stmt = conn.createStatement();
            return stmt.execute(q);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            this.lastErrorMessage = err.getMessage();
            return false;
        }
    }

    /**
     * Execute query that returns rows of data
     *
     * @param q SQLITE query
     * @return ResultSet of the query, will return null if SQLException thrown
     */
    public ResultSet resultQuery(String q) {
        try {
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(q);
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            this.lastErrorMessage = err.getMessage();
            return null;
        }
    }

    /**
     * @return Last error message that is logged when using this class
     */
    public String getLastErrorMsg() {
        return this.lastErrorMessage;
    }
}
//CREATE TABLE IF NOT EXISTS staff (
//	id INTEGER PRIMARY KEY AUTOINCREMENT,
//	date datetime default current_timestamp,
//	name TEXT NOT NULL UNIQUE,
//	password TEXT NOT NULL
//);
//
//CREATE TABLE IF NOT EXISTS member (
//	id INTEGER PRIMARY KEY AUTOINCREMENT,
//	date datetime default current_timestamp,
//	name TEXT NOT NULL,
//	phoneNo TEXT,
//	email TEXT,
//	IC TEXT NOT NULL UNIQUE
//);
//
//CREATE TABLE IF NOT EXISTS book (
//	id INTEGER PRIMARY KEY AUTOINCREMENT,
//	date datetime default current_timestamp,
//	title TEXT NOT NULL,
//  author TEXT,
//	rentalPrice DOUBLE NOT NULL,
//	lastRentedBy INTEGER,
//	lastReservedBy INTEGER,
//      isRented INTEGER,
//      isReserved INTEGER,
//	FOREIGN KEY (lastRentedBy) REFERENCES member(id),
//	FOREIGN KEY (lastReservedBy) REFERENCES member(id)
//);
//
//CREATE TABLE IF NOT EXISTS transactions (
//	id INTEGER PRIMARY KEY AUTOINCREMENT,
//	date datetime default current_timestamp,
//	rentDurationInDays INTEGER,
//	type TEXT,
//	staffHandled INTEGER,
//	memberInvolved INTEGER,
//	bookInvolved INTEGER,
//	FOREIGN KEY (staffHandled) REFERENCES staff(id),
//	FOREIGN KEY (memberInvolved) REFERENCES member(id),
//	FOREIGN KEY (bookInvolved) REFERENCES book(id)
//);

