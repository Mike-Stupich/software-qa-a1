package collections;

import java.util.Date;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import models.Fine;
import models.Loan;
import models.User;
import res.Strings;
import utilities.Config;
import utilities.Trace;

public class FineTable {
	private Logger logger = Trace.getInstance().getLogger("operation_file");
	ArrayList<Fine> fines=new ArrayList<Fine>();
    private static class fineList {
        private static final FineTable INSTANCE = new FineTable();
    }
    private FineTable(){
    	Fine fine=new Fine(0,5);
    	fines.add(fine);
    	applyFines();
    	logger.info(String.format("Operation:Initialize FeeTable;FeeTable: %s", fines));
    }
    public static final FineTable getInstance() {
        return fineList.INSTANCE;
    }
    
    public Object payFine(int userId) {
    	boolean found = false;
    	ArrayList<User> users = UserTable.getInstance().users;
    	if (userId > users.size() || userId < 0) {
    		return Strings.INVALIDUSERID;
    	}
    	
    	for (int i = 0; i < fines.size(); ++i) {
    		Fine f = fines.get(i);
    		if (f.getUserId() == userId) {
    			f.setFine(0);
    			found = true;
    		}
    	}
    	if (found) {
    		return Strings.FINEPAID;
    	}
    	return Strings.NOFINEONUSER;    	
    }
    
    public boolean applyFines() {
    	ArrayList<Loan> loans = LoanTable.getInstance().loans;
    	Date now = new Date();
    	int daysOverdue;
    	
    	for (int i =0 ; i < loans.size(); ++i) {
    		Loan currLoan = loans.get(i);
    		daysOverdue = (int)((now.getTime() - currLoan.getDate().getTime()) / Config.TODAYS);
    		if (daysOverdue > 0) {
    			int id = loans.get(i).getUserId();
    			fines.get(id).setFine(fines.get(id).getFine() + daysOverdue * Config.FINEPERDAY);
    		}	
    	}
    	return true;
    }
    
    public Object getUserFines(int userId) {
    	for (int i = 0; i < fines.size(); ++i ) {
    		Fine f = fines.get(i);
    		if (f.getUserId() == userId) {
    			return f;
    		}
    	}
    	return false;
    }
    
    public void logFines() {
    	for (int i = 0 ; i < fines.size(); ++i ) {
    		logger.debug(fines.get(i).toString());
    	}
    }
}
