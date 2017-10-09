package test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import client.LibClient;
import res.Strings;
import server.LibServer;
import utilities.Config;

public class TestCases {
	
	static LibServer ls = new LibServer(Config.DEFAULT_PORT);
	
	// Assert that the server has successfully started
	@BeforeClass
	public static void TestStartup() {
		assertNotNull(ls);
	}
	
	@Test
	public void addUser() {
		LibClient lc = new LibClient(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
		final int clientId = lc.getID();
		
		// Commands
		String lib = "librarian";
		String lib_pass = "admin";
		String create_user = "Add User";
		String old_user = "Zhibo@carleton.ca,Zhibo";
		String new_user = "Mike@carleton.ca,Mike";
		
		// Login as librarian
		ls.handle(clientId, "hi");
		ls.handle(clientId, lib);
		ls.handle(clientId, lib_pass);
		
		// Try to add existing user
		ls.handle(clientId, create_user);
		assertEquals(Strings.USEREXISTS, ls.handle(clientId, old_user).trim());
		
		// Add new user
		ls.handle(clientId, create_user);
		assertNotEquals(Strings.USEREXISTS, ls.handle(clientId, new_user).trim());
		ls.handle(clientId, "Exit");
	}
	
	@Test
	public void addTitle() {
		LibClient lc = new LibClient(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
		final int clientId = lc.getID();
		
		// Commands
		String lib = "librarian";
		String lib_pass = "admin";
		String add_title = "Add Title";
		String old_title = "9781442668584,By the grace of God";
		String new_title = "9999999999999,Mike's fantastic book";
		
		// Login as librarian
		ls.handle(clientId, "hi");
		ls.handle(clientId, lib);
		ls.handle(clientId, lib_pass);
		
		// Try to add existing title
		ls.handle(clientId, add_title);
		assertEquals(Strings.TITLEEXISTS, ls.handle(clientId, old_title).trim());
		
		// Add new title
		ls.handle(clientId, add_title);
		assertNotEquals(Strings.TITLEEXISTS, ls.handle(clientId, new_title).trim());
		ls.handle(clientId, "Exit");
	}
	
	@Test
	public void addItem() {
		LibClient lc = new LibClient(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
		final int clientId = lc.getID();
		
		// Commands
		String lib = "librarian";
		String lib_pass = "admin";
		String find_title = "Find Title";
		String new_title = "New Title";
		String titleToAdd = "New Title,9999999999998";
		String existing_title = "By the grace of God";
		
		
		// Login as librarian
		ls.handle(clientId, "hi");
		ls.handle(clientId, lib);
		ls.handle(clientId, lib_pass);
		
		// Assert title is returned
		ls.handle(clientId, find_title);
		assertNotEquals("-1", ls.handle(clientId, existing_title));
		
		// Assert new title is added
		ls.handle(clientId, find_title);
		ls.handle(clientId, new_title);
		assertNotEquals("-1", ls.handle(clientId, titleToAdd));
		
		// 
		ls.handle(clientId, find_title);
		assertEquals("9999999999998, New Title", ls.handle(clientId, new_title).trim());
		ls.handle(clientId, "Exit");
	}
	
	@Test
	public void removeTitle() {
		LibClient lc = new LibClient(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
		final int clientId = lc.getID();

		// Commands
		String lib = "librarian";
		String lib_pass = "admin";
		String remove_title = "Remove Title";
		String existing_title = "By the grace of God";
		String fake_title = "This title isn't saved";
		
		// Login as librarian
		ls.handle(clientId, "hi");
		ls.handle(clientId, lib);
		ls.handle(clientId, lib_pass);
		
		ls.handle(clientId, remove_title);
		assertNotEquals("-1", ls.handle(clientId, existing_title));
		
		ls.handle(clientId, remove_title);
		assertEquals(Strings.TITLENOTFOUND, ls.handle(clientId, fake_title).trim());
		ls.handle(clientId, "Exit");
	}
	
	@Test
	public void removeItem() {
		LibClient lc = new LibClient(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
		final int clientId = lc.getID();

		// Commands
		String lib = "librarian";
		String lib_pass = "admin";
		String remove_item = "Remove Item";
		String existing_item = "Dante's lyric poetry";
		String fake_title = "This title isn't saved";
		
		// Login as librarian
		ls.handle(clientId, "hi");
		ls.handle(clientId, lib);
		ls.handle(clientId, lib_pass);
		
		ls.handle(clientId, remove_item);
		assertEquals(Strings.TITLENOTFOUND, ls.handle(clientId, fake_title).trim());
		
		ls.handle(clientId, remove_item);
		ls.handle(clientId, existing_item);
		assertNotEquals("-1", ls.handle(clientId, existing_item));
		
		
		ls.handle(clientId, "Exit");
	}
}
