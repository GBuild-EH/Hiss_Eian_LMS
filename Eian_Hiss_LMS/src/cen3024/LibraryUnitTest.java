package cen3024;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.*;

class LibraryUnitTest {

	static ArrayList<Book> inventory;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		inventory = new ArrayList<Book>();
		Library.addRecord(inventory, 505, "Fool Moon", "Jim Butcher", "Fantasy");
		Library.addRecord(inventory, 622, "A Princess of Mars", "Edgar Rice Burroughs", "Science Fiction");
		Library.addRecord(inventory, 772, "That Hideous Strength", "C.S. Lewis", "Religious");
		Library.addRecord(inventory, 800, "The Wizard of Oz", "L. Frank Baum", "Fantasy", true, 2024, 3, 17);
	}

	@Test
	void testAddRecord() {
		Library.addRecord(inventory, 999, "Sakamoto Days", "Yuto Suzuki", "Manga");
		assertFalse(Library.indexByID(inventory, 999) == -1);
	}

	@Test
	void testRemoveRecord() {
		Library.removeByID(inventory, 505);
		assertEquals(-1, Library.indexByID(inventory, 505));
	}

	@Test
	void testRemoveByTitle() {
		Library.removeByTitle(inventory, new Scanner(System.in) , "Princess");
		assertEquals(-1, Library.indexByID(inventory, 622));
	}

	@Test
	void testCheckOut() {
		Library.checkOut(Library.searchByID(inventory, 772));
		assertNotNull(Library.searchByID(inventory, 772).getDueDate());
	}

	@Test
	void testCheckIn() {
		Library.checkIn(Library.searchByID(inventory, 800));
		assertNull(Library.searchByID(inventory, 800).getDueDate());
	}

}
