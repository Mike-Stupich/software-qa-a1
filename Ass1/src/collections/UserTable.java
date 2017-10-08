package collections;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import models.User;
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
		for(int i=0;i<users.size();i++){
			String email=(users.get(i)).getUser();
			if(email.equalsIgnoreCase(string)){
				found=true;
			}
		}
		if(!found){
			User newuser=new User(string,string2,users.size());
			users.add(newuser);
			userid = users.size();
			logger.info(String.format("Operation:Create New User;User Info:[%s,%s];State:Success", string,string2));
		}else{
			logger.info(String.format("Operation:Create New User;User Info:[%s,%s];State:Fail;Reason:The User already existed.", string,string2));
		}
		return userid;	
	}
}
