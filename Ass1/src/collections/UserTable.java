package collections;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import models.User;
import res.Strings;
import utilities.Trace;

public class UserTable {
	private Logger logger = Trace.getInstance().getLogger("operation_file");
	ArrayList<User> users = new ArrayList<User>();
	
	private static class UserList {
		private static final UserTable INSTANCE = new UserTable();
	}
	
	private UserTable() {
    	//set up the default list with some instances
    	String[] passwordList=new String[]{"Zhibo","Yu","Michelle","Kevin","Sun"};
    	String[] usernameList=new String[]{"Zhibo@carleton.ca","Yu@carleton.ca","Michelle@carleton.ca","Kevin@carleton.ca","Sun@carleton.ca"};
    	for(int i=0;i<usernameList.length;i++){
			User deuser=new User(usernameList[i],passwordList[i], i);
			users.add(deuser);
		}
    	logger.info(String.format("Operation:Initialize UserTable;UserTable: %s", users));
	}
	
    public static final UserTable getInstance() {
        return UserList.INSTANCE;
    }
    
	public Object addUser(String string, String string2) {
		int userid = -1;
		boolean found = false;
		
		for(int i=0 ;i<users.size(); ++i){
			if(users.get(i).getUser().equalsIgnoreCase(string)){
				found=true;
			}
		}
		
		if(!found){
			users.add(new User(string, string2, users.size()));
			userid = users.size();
			logger.info(String.format("Operation:Create New User;User Info:[%s,%s];State:Success", string,string2));
		}else{
			logger.info(String.format("Operation:Create New User;User Info:[%s,%s];State:Fail;Reason:The User already existed.", string,string2));
		}
		return userid;	
	}
	
	public Object removeUser(int userId) {
		if (userId > users.size() || userId < 0) {
			return -1;
		}
		Object f = FineTable.getInstance().getUserFines(userId);
		int loans = LoanTable.getInstance().checkUserLoans(userId);
		if (!f.equals(false)) {
			return Strings.USERMUSTPAYFINES;
		} else if (loans != 0) {
			return Strings.USERMUSTRETURNLOANS;
		}
		return users.remove(userId);
		
	}
	
	public Object findUser(String user) {
		for (int i = 0 ; i < users.size(); ++i) {
			if (users.get(i).getUser().equalsIgnoreCase(user)) {
				return users.get(i);
			}
		}
		return false;
	}
	
	public Object findUser(int id) {
		if (id >= 0 && id < users.size()) {
			return users.get(id);
		}
		return false;
	}
	
	public Object printUsers() {
		Object result = "";
		for (int i = 0 ; i < users.size(); ++i ) {
			result += users.get(i).toString() + ", ";
		}
		return result;
	}
	
	public boolean login(String user, String pass) {
		for (int i = 0 ; i < users.size(); ++i ) {
			if (user.equalsIgnoreCase(users.get(i).getUser()) && pass.equalsIgnoreCase(users.get(i).getPass())){
				return true;
			}
		}
		return false;
	}
}
