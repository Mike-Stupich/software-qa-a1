package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.apache.log4j.Logger;
import org.junit.Test;

import client.LibClient;
import collections.LoanTable;
import collections.UserTable;
import res.Strings;
import server.LibServer;
import utilities.Config;
import utilities.Trace;

public class UserStories {
	private Logger logger = Trace.getInstance().getLogger("user_stories_file");
	static LibServer ls = new LibServer(Config.DEFAULT_PORT);
	// Generic commands
	String hi = "hi";
	String lib = "librarian";
	String lib_pass = "admin";
	String bor = "borrower";
	String exit = "exit";
	
	// Librarian adds and removes a user
	@Test
	public void addRemoveUsers() {
		LibClient lc = new LibClient(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
		final int id = lc.getID();
		String newUser = "Mike@carleton.ca";
		String newPass = "Mike";
		
		String add_user = "Add user";
		String remove_user = "Remove user";
		String res;
		
		ls.handle(id, hi);
		ls.handle(id, lib);
		ls.handle(id, lib_pass);
		
		logger.info(String.format("Users: %s",UserTable.getInstance().printUsers()));
		
		ls.handle(id, add_user);
		res = ls.handle(id, String.format("%s,%s", newUser,newPass));
		logger.info(String.format("User: %s|Operation: %s|Result:%s", newUser, add_user, res));
		
		logger.info(String.format("Users: %s",UserTable.getInstance().printUsers()));
		
		ls.handle(id, remove_user);
		res = ls.handle(id, String.format("%s,%s", newUser,newPass));
		logger.info(String.format("User: %s|Operation: %s|Result:%s", newUser, remove_user, res));
		
		logger.info(String.format("Users: %s",UserTable.getInstance().printUsers()));
		
		ls.handle(id, exit);
	}

	
	// Multiple clients try to borrow same book
	@Test
	public void multiLoan() {
		LibClient lc1 = new LibClient(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
		LibClient lc2 = new LibClient(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
		final int id1 = lc1.getID();
		final int id2 = lc2.getID();
		String user1 = "Yu@carleton.ca";
		String pass1 = "Yu";
		
		String user2 = "Michelle@carleton.ca";
		String pass2 = "Michelle";
		
		String loan_item = "loan item";
		String book = "Courtesy lost";
		
		logger.info(String.format("Multiple loans of same book at same time"));
		ls.handle(id1, hi);
		ls.handle(id1, bor);
		ls.handle(id1, String.format("%s,%s", user1, pass1));
		ls.handle(id1, loan_item);
		
		ls.handle(id2, hi);
		ls.handle(id2, bor);
		ls.handle(id2, String.format("%s,%s", user2, pass2));
		ls.handle(id2,loan_item);
		
		String res1 = ls.handle(id1, String.format("%s,%s", user1, book)).trim();
		String res2 = ls.handle(id2, String.format("%s,%s", user2, book)).trim(); 
		
		logger.info(String.format("User: %s|Operation: %s|Result:%s", user1, loan_item, res1));
		logger.info(String.format("User: %s|Operation: %s|Result:%s", user2, loan_item, res2));
		assertEquals(Strings.LOANADDED, res1);
		assertEquals(Strings.LOANEXISTS, res2);
		
		ls.handle(id1, exit);
		ls.handle(id2, exit);
	}
}
