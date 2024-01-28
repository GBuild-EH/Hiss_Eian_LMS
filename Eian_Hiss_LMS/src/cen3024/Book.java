/*
 * Eian Hiss - CEN3024C - Jan 26, 2024
 * Class - Book
 * Individual records of books in the library.
 * Contains an ID number, the title, and the author of the book.
 */

package cen3024;

public class Book {
	
	private int idNum;
	private String title;
	private String author;
	
	public Book(int id, String ttl, String aut) {		
		idNum = id;
		title = ttl;
		author = aut;		
	}
	
	/*
	 * Method: toString
	 * Make a Book record readable for printing.
	 * No arguments - returns a String containing the attributes of a Book.
	 */	
	public String toString() {	
		return idNum + "," + title + "," + author;		
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
}
