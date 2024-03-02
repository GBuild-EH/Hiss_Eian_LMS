/*
 * Eian Hiss - CEN3024C - MAR 1, 2024
 * Class - Library
 * Text interface for program with user and file i/o.
 * Build and manage the records of the library's collection.
 */

package cen3024;

import java.nio.file.*;
import java.io.*;
import java.util.*;

public class Library {

	/*
	 * Method: main
	 * Menu interface and program control.
	 */
	/* public static void main(String[] args) {
		ArrayList<Book> collection = new ArrayList<Book>();
		boolean shutdown = false;
		int option = 0;
		int id = 0;
		String file = "";
		Scanner entry = new Scanner(System.in);
		while (!shutdown)
		{
			System.out.println("Library Management System");
			System.out.println("1: Add Records");
			System.out.println("2: Remove book");
			System.out.println("3: Display record");
			System.out.println("4: Display all records");
			System.out.println("5: Export collection");
			System.out.println("6: Exit");
			System.out.println("7: Search by title");
			System.out.println("8: Search by author");
			System.out.print("Select option: ");
			if (entry.hasNextInt()) {
				option = entry.nextInt();
				System.out.println();
				switch (option) {
				case 1:
					System.out.print("Import from file? (Y/N)");
					String answer = entry.next();
					entry.nextLine();
					if (answer.equalsIgnoreCase("y")) {
						System.out.print("Enter file name: ");
						file = entry.next();
						entry.nextLine();
						importRecords(file, collection);
					}
					else if (answer.equalsIgnoreCase("n")) 
						enterRecord(collection, entry);
					else
						System.out.println("Invalid selection. Try again.\n");
					break;
				case 2:
					System.out.print("Enter ID of book to remove: ");
					id = entry.nextInt();
					System.out.println();
					removeRecord(collection, id);
					break;
				case 3:
					System.out.print("Enter ID of book to display: ");
					id = entry.nextInt();
					System.out.println();
					displayRecordByID(collection, id);
					break;
				case 4:
					displayCollection(collection);
					break;
				case 5:
					System.out.print("Enter file name: ");
					file = entry.next();
					entry.nextLine();
					exportCollection(file, collection);
					break;
				case 6:
					shutdown = true;
					entry.close();
					break;
				case 7:
					System.out.print("Enter book title to search: ");
					String title = entry.next();
					entry.nextLine();
					System.out.println();
					displayRecordByTitle(collection, title);
					break;
				case 8:
					System.out.print("Enter author to search: ");
					String author = entry.next();
					entry.nextLine();
					System.out.println();
					displayRecordByAuthor(collection, author);
					break;
				default:
					System.out.println("Invalid option. Try again.");
					System.out.println();
				}
			}
			else {
				System.out.println("Invalid option. Try again");
				System.out.println();
				entry.nextLine();
			}
		}			
	} */
	
	/*
	 * Method - enterRecord
	 * Arguments - Target list and input scanner
	 * Manually enter records for testing purposes
	 */
	public static void enterRecord(ArrayList<Book> list, Scanner sc){
		System.out.print("Enter book ID: ");
		if (!sc.hasNextInt()) {
			System.out.println("Invalid ID. Restarting.");
			sc.nextLine();
		}
		else {
			int id = sc.nextInt();
			sc.nextLine();
			System.out.print("Enter book title: ");
			String ttl = sc.nextLine();
			System.out.print("Enter book author: ");
			String aut = sc.nextLine();
			System.out.print("Enter book genre: ");
			String g = sc.nextLine();
			
			addRecord(list, id, ttl, aut, g);
		}
	}
	
	/*
	 * Method - importRecords
	 * Enters records into the collection from a text file.
	 * Arguments - filename string and collection list
	 */	
	public static void importRecords (String file, ArrayList<Book> collection) {
		Path textFile = Paths.get(file + ".txt");
		String line = "";
		String[] tokens;
		try {
			Scanner readFile = new Scanner(textFile);
			while (readFile.hasNextLine()) {
				line = readFile.nextLine();
				tokens = line.split(",");
				if (!Boolean.parseBoolean(tokens[4])) {
					try {
						addRecord(collection, Integer.parseInt(tokens[0]), tokens[1], tokens[2], tokens[3]);
					}
					catch (NumberFormatException nfe) {
						System.out.println("Invalid record format - Check data in file.");
						nfe.printStackTrace();
					}
				}
				else {
					try {
						addRecord(collection, Integer.parseInt(tokens[0]), tokens[1], tokens[2], tokens[3],
								Boolean.parseBoolean(tokens[4]), Integer.parseInt(tokens[5]), 
								Integer.parseInt(tokens[6]), Integer.parseInt(tokens[7]));
					}
					catch (NumberFormatException nfe) {
						System.out.println("Invalid record format - Check data in file.");
						nfe.printStackTrace();
					}
				}
			}
			readFile.close();
		}
		catch (IOException ioe) {
			System.out.println("File not found - check file name and location");
			ioe.printStackTrace();
		}		
	}
	
	/*
	 * Method - addRecord
	 * Creates new Book records in the collection
	 * Arguments - collection list, ID number, title, author and genre of the book.
	 */
	private static void addRecord(ArrayList<Book> collection, int id, String ttl, String aut, String g) {
		collection.add(new Book(id, ttl, aut, g));
		System.out.println("Record added.");
	}
	
	
	private static void addRecord(ArrayList<Book> collection, int id, String ttl, String aut, String g,
			boolean chk, int y,	int m, int d) {
		collection.add(new Book(id, ttl, aut, g, chk, y, m, d));
		System.out.println("Record added.");
		System.out.println();
	}
	
	/*
	 * Method - removeRecord
	 * Removes a record from the collection
	 * Arguments - collection list and index number of book to be removed.
	 */
	public static void removeRecord(ArrayList<Book> collection, int index) {
		collection.remove(index);
		System.out.println("Record removed.");
	}
	
	/*
	 * Method - displayRecord
	 * Finds a record by ID number and displays it
	 * Arguments - collection list and ID number
	 */
	public static void displayRecord(Book record) {
		System.out.println(record);
	}
	
	/*
	 * Method - displayCollection
	 * Lists every record in the collection
	 * Arguments - collection list
	 */
	public static void displayCollection(ArrayList<Book> collection) {
		for (int x = 0; x < collection.size(); x++) {
			System.out.println(collection.get(x));
		}
		System.out.println();
	}
	
	/*
	 * Method - exportCollection
	 * Exports every record in the collection to a file.
	 * Arguments - file name and collection list
	 */
	public static void exportCollection(String file, ArrayList<Book> collection) {
		try (PrintWriter toWrite = new PrintWriter(Files.newOutputStream(Paths.get(file + ".txt")))) {
			for (int x = 0; x < collection.size(); x++) {
				toWrite.println(collection.get(x));
			} 
			System.out.println("Export complete.");
		}
		catch (IOException ioe) {
			System.out.println("Export failed.");
			ioe.printStackTrace();
		}		
		System.out.println();		
		}
	
	/*
	 * Method - searchByID
	 * Finds records by ID
	 * Arguments - ID number and collection list
	 */
	
	public static Book searchByID(ArrayList<Book> collection, int id) {
		for (int x = 0; x < collection.size(); x++) {
			if (collection.get(x).getID() == id) {
				//displayRecord(collection.get(x));
				return collection.get(x);
			}
		}
		System.out.println("No relevant records found.");
		System.out.println();
		return null;		
	}
	
	/*
	 * Method - indexByID
	 * Finds records with inventory ID - returns collection index
	 * Arguments - inventory ID and collection list
	 */
	
	public static int indexByID(ArrayList<Book> collection, int id) {
		for (int x = 0; x < collection.size(); x++) {
			if (collection.get(x).getID() == id) {
				//displayRecord(collection.get(x));
				return x;
			}
		}
		System.out.println("No relevant records found.");
		System.out.println();
		return -1;		
	}
	
	/*
	 * Method - searchByTitle
	 * Finds records by title
	 * Arguments - string to find in title and collection list
	 */
	public static Book searchByTitle(ArrayList<Book> collection, String title) {
		for (int x = 0; x < collection.size(); x++) {
			if (collection.get(x).getTitle().contains(title)) {
				//displayRecord(collection.get(x));
				return collection.get(x);
			}
		}
		System.out.println("No relevant records found.");
		System.out.println();
		return null;		
	}
	
	/*
	 * Method - indexByTitle
	 * Finds records by title and returns collection index
	 * Arguments - string to find in title and collection list
	 */
	public static int indexByTitle(ArrayList<Book> collection, String title) {
		for (int x = 0; x < collection.size(); x++) {
			if (collection.get(x).getTitle().contains(title)) {
				//displayRecord(collection.get(x));
				return x;
			}
		}
		System.out.println("No relevant records found.");
		System.out.println();
		return -1;		
	}
		
	/*
	 * Method - searchByAuthor
	 * Finds records by author and displays them
	 * Arguments - string to find in author's name and collection list
	 */
	public static Book searchByAuthor(ArrayList<Book> collection, String author) {
		for (int x = 0; x < collection.size(); x++) {
			if (collection.get(x).getAuthor().contains(author)) {
				//displayRecord(collection.get(x));
				return collection.get(x);
			}
		}
		System.out.println("No relevant records found.");
		System.out.println();
		return null;		
	}
	
	/*
	 * Method - searchByAuthor
	 * Finds records by author and displays them
	 * Arguments - string to find in author's name and collection list
	 */
	
	public static int indexByAuthor(ArrayList<Book> collection, String author) {
		for (int x = 0; x < collection.size(); x++) {
			if (collection.get(x).getAuthor().contains(author)) {
				//displayRecord(collection.get(x));
				return x;
			}
		}
		System.out.println("No relevant records found.");
		System.out.println();
		return -1;
	}
	
}
