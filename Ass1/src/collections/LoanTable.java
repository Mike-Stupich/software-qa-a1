package collections;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import models.Loan;
import models.Title;
import models.User;
import res.Strings;
import utilities.Config;
import utilities.Trace;

public class LoanTable {
	private Logger logger = Trace.getInstance().getLogger("operation_file");
	ArrayList<Loan> loans = new ArrayList<Loan>();
	
    private static class LoanList {
        private static final LoanTable INSTANCE = new LoanTable();
    }
    
    private LoanTable(){
    	//set up the default list with some instances
    	Loan loan=new Loan(0,"9781442668584",new Date());
    	loans.add(loan);
    	logger.info(String.format("Operation:Initialize LoanTable;LoanTable: %s", loans));
    }
    
    public static final LoanTable getInstance() {
        return LoanList.INSTANCE;
    }
    
    // Add a new loan to the list of loans
	public Object addLoan(String user, String title) {
		Object result = -1;
		Object loanUser = UserTable.getInstance().findUser(user);
		Object loanTitle = TitleTable.getInstance().findTitle(title);
		Date now = new Date();
		logger.debug(String.format("Loan user: %s, Loan title: %s", loanUser, loanTitle));
		if (loanUser.equals(false)|| loanTitle.equals(-1)) {
			result = Strings.INVALIDLOANTITLE;
		} else {
			Title t = (Title) loanTitle;
			boolean found = false;	
			for (int i = 0; i < loans.size(); ++i) {
				Loan curr = loans.get(i);
				if (curr.getIsbn().equalsIgnoreCase(t.getISBN())){
					found = true;
					result = Strings.LOANEXISTS;
				}
			}
			if (!found) {
				User u = (User)loanUser;
				loans.add(new Loan(u.getUserId(), title, now));
				result = Strings.LOANADDED;	
			}
		}
		return result;
	}
	
	// Find loans by userId and return all their loans
	public ArrayList<Loan> findLoan(int userId) {
		ArrayList<Loan> userLoans = new ArrayList<Loan>();
		for (int i = 0; i < loans.size(); ++i) {
			if (loans.get(i).getUserId() == userId) {
				userLoans.add(loans.get(i));
			}
		}
		return userLoans;
	}
	
	// Find loan by title and return all loans of that title
	public ArrayList<Loan> findLoan(String title) {
		Title t = (Title)TitleTable.getInstance().findTitle(title);
		String isbn = t.getISBN();
		ArrayList<Loan> userLoans = new ArrayList<Loan>();
		for (int i = 0; i < loans.size(); ++i) {
			if (loans.get(i).getIsbn().equalsIgnoreCase(isbn)) {
				userLoans.add(loans.get(i));
			}
		}
		return userLoans;
	}
	
	private Loan findLoan(int user, String isbn) {
		for (int i = 0 ; i < loans.size(); ++i) {
			Loan l = loans.get(i);
			if (l.getUserId() == user && l.getIsbn().equals(isbn)) {
				return l;
			}
		}
		return null;
	}
	
	public Object returnLoan(int user, String isbn) {
		for (int i = 0 ; i < loans.size(); ++i) {
			Loan l = loans.get(i);
			if (l.getUserId() == user && l.getIsbn().equals(isbn)) {
				loans.remove(i);
				return l;
			}
		}
		return false;
	}
}
