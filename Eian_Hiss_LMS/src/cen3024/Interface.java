/*
 * Eian Hiss - CEN3024C - MAR 1, 2024
 * Class - Interface
 * Control over the LMS
 */

package cen3024;

import java.util.*;

public class Interface {

	public static void main(String[] args) {
			ArrayList<Book> collection = new ArrayList<Book>();
			List<Integer> results = new ArrayList<Integer>();
			boolean shutdown = false;
			int option = 0;
			int id = 0;
			int index = 0;
			String file = "";
			String title = "";
			Book book = null;
			Scanner entry = new Scanner(System.in);
			
			System.out.println("Library Management System online.");
			System.out.println("Importing from file:");
			System.out.print("Enter file name: ");
			file = entry.next();
			entry.nextLine();
			try {
				Library.importRecords(file, collection);
			}
			catch (Exception ioe) {
				ioe.printStackTrace();
			}
			
			System.out.println();
			Library.displayCollection(collection);
			
			
			while (!shutdown)
			{
				System.out.println("Library Management System");
				System.out.println("1: Manually Add Records");
				System.out.println("2: Remove book (ID)");
				System.out.println("3: Remove book (Title)");
				System.out.println("4: Check out book");
				System.out.println("5: Check in book");
				System.out.println("7: Display all records");
				System.out.println("8: Export collection");
				System.out.println("9: Exit");
				System.out.print("Select option: ");
				
				if (entry.hasNextInt()) {
					option = entry.nextInt();
					System.out.println();
					
					switch (option) {
					case 1:
						Library.enterRecord(collection, entry);
						break;
					case 2:
						System.out.print("Enter ID of book to remove: ");
						id = entry.nextInt();
						System.out.println();
						Library.removeByID(collection, id);
						System.out.println();
						Library.displayCollection(collection);
						System.out.println();
						break;
					case 3:
						System.out.print("Enter Title of book to remove: ");
						title = entry.next();
						entry.nextLine();
						System.out.println();
						Library.removeByTitle(collection, entry, title);
						System.out.println();
						results.clear();
						Library.displayCollection(collection);
						System.out.println();
						break;
					case 4:
						System.out.print("Enter title of book to check out: ");
						title = entry.next();
						entry.nextLine();
						System.out.println();
						results = Library.indexByTitle(collection, title);
						if (results.isEmpty()) //Nothing found.
							System.out.println();
						else if (results.size() == 1) //Single record found.
							book = collection.get(results.get(0));
						else {
							System.out.println("Multiple records found."); //Multiple records found.
							for (int x = 0; x < results.size(); x++)
								Library.displayRecord(collection.get(results.get(x)));
							System.out.print("Enter ID of book to check out: ");
							id = entry.nextInt();
							System.out.println();
							index = Library.indexByID(collection, id);
							if (index != -1)
								book = collection.get(index);
							else
								System.out.println();
						}
						Library.checkOut(book);
						System.out.println();
						results.clear();
						book = null;
						Library.displayCollection(collection);
						System.out.println();
						break;
					case 5:
						System.out.print("Enter title of book to check in: ");
						title = entry.next();
						entry.nextLine();
						System.out.println();
						results = Library.indexByTitle(collection, title);
						if (results.isEmpty())
							System.out.println();
						else if (results.size() == 1)
							book = collection.get(results.get(0));
						else {
							System.out.println("Multiple records found.");
							for (int x = 0; x < results.size(); x++)
								Library.displayRecord(collection.get(results.get(x)));
							System.out.print("Enter ID of book to check in: ");
							id = entry.nextInt();
							System.out.println();
							index = Library.indexByID(collection, id);
							if (index != -1)
								book = collection.get(index);
							else
								System.out.println();
						}
						Library.checkIn(book);
						System.out.println();
						results.clear();
						book = null;
						Library.displayCollection(collection);
						System.out.println();
						break;
					case 7:
						Library.displayCollection(collection);
						break;
					case 8:
						System.out.print("Enter file name: ");
						file = entry.next();
						entry.nextLine();
						Library.exportCollection(file, collection);
						break;
					case 9:
						shutdown = true;
						entry.close();
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

	}

}
