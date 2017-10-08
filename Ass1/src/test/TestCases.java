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
	
	// Asser that the server has successfully started
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
		ls.handle(clientId, "hi");
		ls.handle(clientId, lib);
		ls.handle(clientId, lib_pass);
		ls.handle(clientId, create_user);

		assertEquals(Strings.USEREXISTS, ls.handle(clientId, old_user).trim());
		
		ls.handle(clientId, create_user);
		
		assertNotEquals(Strings.USEREXISTS, ls.handle(clientId, new_user).trim());
		
		
	}
}
