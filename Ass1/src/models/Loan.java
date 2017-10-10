package models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Loan {
	int userId;
	String isbn;
	Date date;
	
	public Loan(int userid, String isbn, Date date) {
		this.userId = userid;
		this.isbn = isbn;
		this.date = date;
	}
	
	DateFormat format = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
	
	public String toString() {
		return String.format("%s, %s, %s", this.userId, this.isbn, format.format(this.date));
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
	
}
