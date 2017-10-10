package models;

public class Fine {
	int userId;
	double fine;
	
	public Fine(int userId, double fine) {
		this.userId = userId;
		this.fine = fine;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public double getFine() {
		return fine;
	}

	public void setFine(double fine) {
		this.fine = fine;
	}
}
