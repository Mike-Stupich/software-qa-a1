package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import client.LibClient;
import res.Strings;
import server.LibServer;
import utilities.Config;

public class TestCases {
	
	static LibServer ls = new LibServer(Config.DEFAULT_PORT);
	// Generic commands
	String hi = "hi";
	String lib = "librarian";
	String lib_pass = "admin";
	String exit = "exit";
	
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

		String create_user = "Add User";
		String old_user = "Zhibo@carleton.ca,Zhibo";
		String new_user = "Mike@carleton.ca,Mike";
		
		// Login as librarian
		ls.handle(clientId, hi);
		ls.handle(clientId, lib);
		ls.handle(clientId, lib_pass);
		
		// Try to add existing user
		ls.handle(clientId, create_user);
		assertEquals(Strings.USEREXISTS, ls.handle(clientId, old_user).trim());
		
		// Add new user
		ls.handle(clientId, create_user);
		assertNotEquals(Strings.USEREXISTS, ls.handle(clientId, new_user).trim());
		ls.handle(clientId, exit);
	}
	
	@Test
	public void addTitle() {
		LibClient lc = new LibClient(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
		final int clientId = lc.getID();
		
		// Commands\
		String add_title = "Add Title";
		String old_title = "9781442668584,By the grace of God";
		String new_title = "9999999999999,Mike's fantastic book";
		
		// Login as librarian
		ls.handle(clientId, hi);
		ls.handle(clientId, lib);
		ls.handle(clientId, lib_pass);
		
		// Try to add existing title
		ls.handle(clientId, add_title);
		assertEquals(Strings.TITLEEXISTS, ls.handle(clientId, old_title).trim());
		
		// Add new title
		ls.handle(clientId, add_title);
		assertNotEquals(Strings.TITLEEXISTS, ls.handle(clientId, new_title).trim());
		ls.handle(clientId, exit);
	}
	
	@Test
	public void addItem() {
		LibClient lc = new LibClient(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
		final int clientId = lc.getID();
		
		// Commands
		String add_item = "Add Item";
		String new_title = "New Title";
		String titleToAdd = "New Title,9999999999998";
		String existing_title = "By the grace of God";
		
		
		// Login as librarian
		ls.handle(clientId, hi);
		ls.handle(clientId, lib);
		ls.handle(clientId, lib_pass);
		
		// Assert title is returned
		ls.handle(clientId, add_item);
		assertNotEquals("-1", ls.handle(clientId, existing_title));
		
		// Assert new title is added
		ls.handle(clientId, add_item);
		ls.handle(clientId, new_title);
		assertNotEquals("-1", ls.handle(clientId, titleToAdd));
		
		// 
		ls.handle(clientId, add_item);
		assertEquals("9999999999998, New Title", ls.handle(clientId, new_title).trim());
		ls.handle(clientId, exit);
	}
	
	@Test
	public void removeTitle() {
		LibClient lc = new LibClient(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
		final int clientId = lc.getID();

		// Commands
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
	
	@Test
	public void loanBook() {
		LibClient lc = new LibClient(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
		final int clientId = lc.getID();
		long twodays = 1000 * 60 * 60 * 24 * 2;
		Date date = new Date();
		date.setTime(date.getTime() + twodays);

		// Commands
		String loan_title = "Loan Item";
		// Loan Format: useremail, book title
		String valid_loan = "Yu@carleton.ca,Courtesy lost";
		String invalid_loan = "Yu@carleton.ca,xyz,";
		String invalid_user = "mike,By the grace of God";
		
		// Login as librarian
		ls.handle(clientId, "hi");
		ls.handle(clientId, lib);
		ls.handle(clientId, lib_pass);
		
		ls.handle(clientId, loan_title);
		assertEquals(Strings.LOANADDED, ls.handle(clientId, valid_loan).trim());
		
		ls.handle(clientId, loan_title);
		assertEquals(Strings.INVALIDLOANTITLE, ls.handle(clientId, invalid_loan).trim());
		
		ls.handle(clientId, loan_title);
		assertEquals(Strings.INVALIDUSEREMAIL, ls.handle(clientId, invalid_user).trim());
		
		ls.handle(clientId, "Exit");
	}
	
	@Test
	public void returnLoan() {
		LibClient lc = new LibClient(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
		final int clientId = lc.getID();
		long twodays = 1000 * 60 * 60 * 24 * 2;
		Date date = new Date();
		date.setTime(date.getTime() + twodays);

		// Commands
		String loan_title = "Loan Item";
		String return_title = "Return Item";
		// Loan Format: useremail, book title
		String valid_loan = "Yu@carleton.ca,Courtesy lost";
		
		// Login as librarian
		ls.handle(clientId, "hi");
		ls.handle(clientId, lib);
		ls.handle(clientId, lib_pass);
		
		ls.handle(clientId, loan_title);
		assertEquals(Strings.LOANADDED, ls.handle(clientId, valid_loan).trim());
		
		ls.handle(clientId, return_title);
		assertEquals(Strings.LOANRETURNED, ls.handle(clientId, valid_loan).trim());
	}
	
	@Test
	public void renewLoan() {
		LibClient lc = new LibClient(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
		final int clientId = lc.getID();
		long twodays = 1000 * 60 * 60 * 24 * 2;
		Date date = new Date();
		date.setTime(date.getTime() + twodays);

		// Commands
		String loan_title = "Loan Item";
		String renew_item= "Renew Item";
		// Loan Format: useremail, book title
		String valid_loan = "Yu@carleton.ca,Courtesy lost";
		
		// Login as librarian
		ls.handle(clientId, "hi");
		ls.handle(clientId, lib);
		ls.handle(clientId, lib_pass);
		
		ls.handle(clientId, loan_title);
		assertEquals(Strings.LOANADDED, ls.handle(clientId, valid_loan).trim());
		
		ls.handle(clientId, renew_item);
		assertEquals(Strings.LOANRENEWED, ls.handle(clientId, valid_loan).trim());
		
		ls.handle(clientId, "Exit");
	}
	
	@Test
	public void collectFine() {
		LibClient lc = new LibClient(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
		final int clientId = lc.getID();
		long twodays = 1000 * 60 * 60 * 24 * 2;
		Date date = new Date();
		date.setTime(date.getTime() + twodays);

		// Commands
		String loan_title = "Loan Item";
		String pay_fines = "Pay Fines";
		// Loan Format: useremail, book title
		String valid_loan = "Zhibo@carleton.ca,Courtesy lost";
		String owing_fines = "Zhibo@carleton.ca";
		
		// Login as librarian
		ls.handle(clientId, "hi");
		ls.handle(clientId, lib);
		ls.handle(clientId, lib_pass);
		
		ls.handle(clientId, loan_title);
		assertEquals(Strings.USERMUSTPAYFINES, ls.handle(clientId, valid_loan).trim());
		
		ls.handle(clientId, pay_fines);
		assertEquals(Strings.FINEPAID, ls.handle(clientId, owing_fines).trim());
		
		ls.handle(clientId, "Exit");
	}
	
	@Test
	public void removeUser() {
		LibClient lc = new LibClient(Config.DEFAULT_HOST, Config.DEFAULT_PORT);	
		final int clientId = lc.getID();
		
		String add_user = "Add User";
		String remove_user = "Remove user";
		String useradd = "mikestupich@carleton.ca,mike";
		String userremove = "mikestupich@carleton.ca";
		// Login as librarian
		ls.handle(clientId, "hi");
		ls.handle(clientId, lib);
		ls.handle(clientId, lib_pass);
		
		ls.handle(clientId, add_user);
		ls.handle(clientId, useradd);
		
		ls.handle(clientId, remove_user);
		assertEquals(Strings.USERREMOVED,ls.handle(clientId, userremove).trim());
		
		ls.handle(clientId, "Exit");
	}
	
	@Test
	public void monitorSystem() {
		LibClient lc = new LibClient(Config.DEFAULT_HOST, Config.DEFAULT_PORT);	
		final int clientId = lc.getID();
		
		String monitor_system = "monitor system";
		
		// Login as librarian
		ls.handle(clientId, "hi");
		ls.handle(clientId, lib);
		ls.handle(clientId, lib_pass);
		
		//Assert the titles and users were added
		assertNotEquals("Users: , Titles:", ls.handle(clientId, monitor_system));
		
		ls.handle(clientId, "Exit");
	}
	

	
}
