package models;

public class Title {
	private String ISBN;
	private String title;
	
	public Title(String isbn, String title) {
		this.ISBN = isbn;
		this.title = title;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
