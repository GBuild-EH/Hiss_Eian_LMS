/*
 * Eian Hiss - CEN3024C - MAR 1, 2024
 * Class - Book
 * Individual records of books in the library.
 * Contains an ID number, the title, and the author of the book.
 * New additions: Checkout status and due date.
 */

package cen3024;
import java.util.*;

public class Book {
	
	private int idNum;
	private String title;
	private String author;
	private String genre;
	private boolean checkedOut = false;
	private GregorianCalendar dueDate = null;
	private static GregorianCalendar today = new GregorianCalendar();
	
	public Book(int id, String ttl, String aut, String g) {		
		idNum = id;
		title = ttl;
		author = aut;
		genre = g;
	}
	
	public Book(int id, String ttl, String aut, String g, boolean ck, int y, int m, int d) {
		this(id, ttl, aut, g);
		checkedOut = ck;
		dueDate = new GregorianCalendar(y, m-1, d);
	}
	
	/*
	 * Method: toString
	 * Make a Book record readable for printing.
	 * No arguments - returns a String containing the attributes of a Book.
	 */	
	public String toString() {	
		if (checkedOut)
			return getID() + "," + getTitle() + "," + getAuthor() + "," +
				getGenre() + "," + Boolean.toString(checkedOut) + "," + getDateString();		
		else
			return getID() + "," + getTitle() + "," + getAuthor() + "," +
				getGenre() + "," + Boolean.toString(checkedOut) + ",,,";
	}
	
	public int getID(){
		return idNum;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getGenre() {
		return genre;
	}

	public boolean getCheckedStatus() {
		return checkedOut;
	}
	
	public String getDateString() {
		if (checkedOut) {
			String dateString = Integer.toString(dueDate.get(GregorianCalendar.YEAR)) + "," +
					Integer.toString(dueDate.get(GregorianCalendar.MONTH) + 1) + "," +
					Integer.toString(dueDate.get(GregorianCalendar.DATE));
			return dateString;
		}
		else
			return "Not Checked Out";
	}
	
	public GregorianCalendar getDueDate() {
		return dueDate;
	}
	
	public void toggleCheck() {
		if (checkedOut) {
			checkedOut = false;
			clearDue();
		}
		else {
			checkedOut = true;
			assignDue();
		}
	}
	
	public void clearDue() {
		dueDate = null;
	}
	
	public void assignDue() {
		dueDate = (GregorianCalendar) today.clone();
		dueDate.add(GregorianCalendar.DATE, 28);
	}
	
}
