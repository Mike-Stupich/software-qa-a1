package models;

public class User {
	private String user;
	private String pass;
	private int userId;
	
	public User(String user, String pass, int id) {
		this.user = user;
		this.pass = pass;
		this.userId = id;
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
