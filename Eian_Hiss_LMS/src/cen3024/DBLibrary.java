/*
 * Eian Hiss - CEN3024C - APR 7, 2024
 * Class - DBLibrary
 * Connects to and manipulates MySQL database.
 * Manages records of the library's collection.
 */

package cen3024;

import java.util.*;
import java.sql.*;
import java.sql.Date;

public class DBLibrary {
	
	private static Connection dbConn;
	private static GregorianCalendar today = new GregorianCalendar();
	
	
	/*
	 * Method - connect()
	 * No arguments
	 * Connects program to MySQL database
	 */
	public static void connect() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // fetching database driver
		} catch (ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		dbConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lms_EH", "java", "cop2805");					
	} //Local database, Linux-based MySQL instance. Probably needs to be adjusted for run environment.
	
	/*
	 * Method - refresh()
	 * No arguments
	 * Retrieves full database
	 */
	public static ResultSet refresh() throws SQLException {
		return dbConn.createStatement().executeQuery("SELECT * FROM Books");
	}
	
	/*
	 * Method - checkID(ID)
	 * Argument - Book ID number
	 * Retrieves records corresponding to ID number.
	 */
	public static ResultSet checkID (int ID) throws SQLException {
		return dbConn.createStatement().executeQuery("SELECT * FROM Books WHERE ID = " + ID);
	}
	
	/*
	 * Method - checkTitle(title)
	 * Argument - Book title
	 * Retrieves record corresponding to book title.
	 */
	public static ResultSet checkTitle (String title) throws SQLException {
		return dbConn.createStatement().executeQuery("SELECT * FROM Books WHERE Title = \'" 
				+ title + "\'");
	}
	
	/*
	 * Method - removeID(ID)
	 * Argument - Book ID number
	 * Removes record with specified ID from database.
	 */
	public static void removeID (int ID) throws SQLException {
		dbConn.createStatement().executeUpdate("DELETE FROM Books WHERE ID = " + ID);
	}
	
	/*
	 * Method - removeTitle(title)
	 * Argument - Book title
	 * Removes record with specified title from database.
	 */
	public static void removeTitle (String title) throws SQLException {
		dbConn.createStatement().executeUpdate("DELETE FROM Books WHERE Title = \'" + title + "\'");
	}
	
	/*
	 * Method - checkOut(title)
	 * Argument - Book title
	 * Marks record with specified title as checked out.
	 */
	public static void checkOut(String title) throws SQLException {
		GregorianCalendar dueCal = (GregorianCalendar) today.clone();
		dueCal.add(GregorianCalendar.DATE, 28);
		Date dueDate = new Date(dueCal.getTimeInMillis());
		dbConn.createStatement().executeUpdate("UPDATE Books SET Status = 1, DueDate = \'"
				+ dueDate.toString() + "\' WHERE Title = \'" + title + "\'");
	}
	
	/*
	 * Method - checkIn(title)
	 * Argument - Book title
	 * Marks record with specified title as checked in.
	 */
	public static void checkIn(String title) throws SQLException {
		dbConn.createStatement().executeUpdate("UPDATE Books SET Status = 0, DueDate = null WHERE Title = \'"
				+ title + "\'");
	}
	
	/*
	 * Method - close()
	 * No arguments
	 * Closes database connection.
	 */
	public static void close() throws SQLException {
		dbConn.close();
	}

}
