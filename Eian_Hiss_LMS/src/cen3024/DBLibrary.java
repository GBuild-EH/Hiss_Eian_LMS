/**
 * Eian Hiss - CEN3024C - APR 7, 2024
 * Class - DBLibrary
 * Connects to and manipulates MySQL database.
 * Manages records of the library's collection.
 */

package cen3024;

import java.util.*;
import java.sql.*;
import java.sql.Date;

/**
 * Logic for MySQL Library manipulation
 */
public class DBLibrary {
	
	/**
	 * Connects to the database
	 */
	private static Connection dbConn;
	/**
	 * Used to reference the current date for check-out purposes
	 */
	private static GregorianCalendar today = new GregorianCalendar();
	
	
	/**
	 * Method - connect()
	 * Connects program to MySQL database
	 * @throws SQLException Database errors
	 */

	public static void connect() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // fetching database driver
		} catch (ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		dbConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms_EH", "java", "cop2805");					
	} //Local database, Linux-based MySQL instance. Probably needs to be adjusted for run environment.
	
	/**
	 * Method - refresh()
	 * Retrieves full database
	 * @return ResultSet of full table
	 * @throws SQLException Database errors
	 */
	public static ResultSet refresh() throws SQLException {
		return dbConn.createStatement().executeQuery("SELECT * FROM Books");
	}
	
	/**
	 * Method - checkID(ID)
	 * Retrieves records corresponding to ID number.
	 * @param ID Barcode number of book
	 * @return ResultSet of books that match ID
	 * @throws SQLException Database errors
	 */
	public static ResultSet checkID (int ID) throws SQLException {
		return dbConn.createStatement().executeQuery("SELECT * FROM Books WHERE ID = " + ID);
	}
	
	/**
	 * Method - checkTitle(title)
	 * Retrieves record corresponding to book title.
	 * @param title Title of book
	 * @return ResultSet of books that match title.
	 * @throws SQLException Database errors
	 */

	public static ResultSet checkTitle (String title) throws SQLException {
		return dbConn.createStatement().executeQuery("SELECT * FROM Books WHERE Title = \'" 
				+ title + "\'");
	}
	
	/**
	 * Method - removeID(ID)
	 * Removes record with specified ID from database.
	 * @param ID ID of book to remove
	 * @throws SQLException Database errors
	 */
	public static void removeID (int ID) throws SQLException {
		dbConn.createStatement().executeUpdate("DELETE FROM Books WHERE ID = " + ID);
	}
	
	/**
	 * Method - removeTitle(title)
	 * Removes record with specified title from database.
	 * @param title Title of book to remove
	 * @throws SQLException Database errors
	 */
	public static void removeTitle (String title) throws SQLException {
		dbConn.createStatement().executeUpdate("DELETE FROM Books WHERE Title = \'" + title + "\'");
	}
	
	/**
	 * Method - checkOut(title)
	 * Marks record with specified title as checked out.
	 * @param title Title of book to check out
	 * @throws SQLException Database errors
	 */
	public static void checkOut(String title) throws SQLException {
		GregorianCalendar dueCal = (GregorianCalendar) today.clone();
		dueCal.add(GregorianCalendar.DATE, 28);
		Date dueDate = new Date(dueCal.getTimeInMillis());
		dbConn.createStatement().executeUpdate("UPDATE Books SET Status = 1, DueDate = \'"
				+ dueDate.toString() + "\' WHERE Title = \'" + title + "\'");
	}
	
	/**
	 * Method - checkIn(title)
	 * Marks record with specified title as checked in.
	 * @param title Title of book to check in.
	 * @throws SQLException Database errors
	 */
	public static void checkIn(String title) throws SQLException {
		dbConn.createStatement().executeUpdate("UPDATE Books SET Status = 0, DueDate = null WHERE Title = \'"
				+ title + "\'");
	}
	
	/**
	 * Method - close()
	 * Closes database connection.
	 * @throws SQLException Database errors
	 */
	public static void close() throws SQLException {
		dbConn.close();
	}

}
