package inputoutput;

import collections.FineTable;
import collections.LoanTable;
import collections.TitleTable;
import collections.UserTable;
import models.Fine;
import models.Loan;
import models.Title;
import models.User;
import res.States;
import res.Strings;
import utilities.Config;

public class OutputHandler {

	public Output librarianLogin(String input) {
		Output output=new Output("",0);
		if(input.equalsIgnoreCase(Config.LIBRARIAN_PASSWORD)){
			output.setOutput(Strings.LIBRARIANMENU);
        	output.setState(States.LIBRARIAN);
		}else{
			output.setOutput(Strings.INCPASS);
        	output.setState(States.LIBRARIANLOGIN);
		}
		return output;
	}
	
	public Output borrowerLogin(String input) {
		Output o = new Output("",0);
		String[] str = input.split(",");
		if (str.length != 2) {
			o.setOutput(Strings.INVALIDADDUSER);
			o.setState(States.BORROWERLOGIN);
		} else {
			boolean result = false;
			result = UserTable.getInstance().login(str[0], str[1]);
			if (result) {
				o.setOutput(Strings.BORROWERMENU);
				o.setState(States.BORROWER);
			} else {
				o.setOutput(Strings.LOGINFAILED);
				o.setState(States.BORROWERLOGIN);
			}
		}
		return o;
	}
	
	public Output addUser(String user) {
		Output o = new Output("",0);
		String[] str = user.split(",");
		boolean isEmail = str[0].contains("@");
		Object result = -1;
		if (str.length!=2 || !isEmail) {
			o.setOutput(Strings.INVALIDADDUSER);
			o.setState(States.ADDUSER);
		} else {
			result = UserTable.getInstance().addUser(str[0],str[1]);
			if (!result.equals(-1)) {
				o.setOutput(String.format("User added! UserId = %s", result.toString()));
			} else {
				o.setOutput(Strings.USEREXISTS);
			}
			o.setState(States.LIBRARIAN);
		}
		return o;
	}
	
	public Output removeUser(String user) {
		Output o = new Output("", 0);
		Object result = -1;
		String[] usr = user.split("@");
		
		if (usr.length != 2) {
			o.setOutput(Strings.INVALIDUSEREMAIL);
			o.setState(States.REMOVEUSER);
		} else {
			Object userObj = UserTable.getInstance().findUser(user);
			if (userObj.equals(false)) {
				o.setOutput(Strings.INVALIDUSEREMAIL);
			} else {
				User u = (User) userObj;
				result = UserTable.getInstance().removeUser(u.getUserId());
				if (result.equals(-1)) {
					o.setOutput(Strings.INVALIDUSEREMAIL);
				} else {
					result = (User) result;
					o.setOutput(Strings.USERREMOVED);
				}
			}
			
			o.setState(States.LIBRARIAN);
		}
		return o;
	}
	
	public Output addTitle(String title) {
		Output o = new Output("",0);
		String[] str = title.split(",");
		Object result = -1;
		if (str.length != 2) {
			o.setOutput(Strings.INVALIDADDTITLE);
			o.setState(States.ADDTITLE);
		} else {
			result = TitleTable.getInstance().addTitle(str[0], str[1]);
			if (!result.equals(-1)) {
				o.setOutput(String.format("Title added! TitleId = %s", result.toString()));
			} else {
				o.setOutput(Strings.TITLEEXISTS);
			}
			o.setState(States.LIBRARIAN);
		}
		return o;
	}
	
	public Output findTitle(String input) {
		Output o = new Output("",0);
		Object result = -1;
		if (input.isEmpty()) {
			o.setOutput(Strings.INVALIDFINDTITLE);
			o.setState(States.FINDTITLE);
		} else {
			result = TitleTable.getInstance().findTitle(input);
			if (!result.equals(-1)) {
				Title t = (Title) result;
				o.setOutput(String.format("%s, %s",t.getTitle(), t.getISBN()));
				o.setState(States.LIBRARIAN);
			} else {
				o.setOutput(Strings.ADDTITLE);
				o.setState(States.ADDTITLE);
			}
		}
		return o;
	}
	
	public Output removeTitle(String input) {
		Output o = new Output("", 0);
		Object result = -1;
		if (input.isEmpty()) {
			o.setOutput(Strings.INVALIDFINDTITLE);
			o.setState(States.REMOVETITLE);
		} else {
			result = TitleTable.getInstance().removeTitle(input);
			if (!result.equals(-1)) {
				o.setOutput(Strings.TITLEREMOVED);
			} else {
				o.setOutput(Strings.TITLENOTFOUND);
			}
			o.setState(States.REMOVETITLE);
		}
		return o;
	}
	
	public Output removeItem(String input) {
		Output o = new Output("",0);
		Object result = -1;
		if (input.isEmpty()) {
			o.setOutput(Strings.INVALIDFINDTITLE);
			o.setState(States.REMOVEITEM);
		} else {
			result = TitleTable.getInstance().findTitle(input);
			if (result.equals(-1)) {
				o.setOutput(Strings.TITLENOTFOUND);
				o.setState(States.LIBRARIAN);
			} else {
				Title t = (Title)result;
				o.setOutput(String.format("%s, %s\n%s", t.getTitle().toString(), t.getISBN().toString(), Strings.REMOVETITLE));
				o.setState(States.REMOVETITLE);
			}
		}
		return o;
	}
	
	public Output loanItem(String input) {
		Output o = new Output("",0);
		Object result = -1;
		String[] str = input.split(",");
		String[] usr = str[0].split("@");
		if (str.length != 2) {
			o.setOutput(Strings.INVALIDLOANTITLE);
			o.setState(States.LOANITEM);
		} else if (usr.length != 2) {
			o.setOutput(Strings.INVALIDUSEREMAIL);
			o.setState(States.LOANITEM);
		} else {
			result = LoanTable.getInstance().addLoan(str[0], str[1]);
			o.setOutput(result.toString());
			o.setState(States.LIBRARIAN);
		}
		return o;
	}
	
	public Output returnLoan(String input) {
		Output o = new Output("", 0);
		Object result = -1;
		String[] str = input.split(",");
		String[] usr = str[0].split("@");
		if (str.length != 2) {
			o.setOutput(Strings.INVALIDLOANID);
			o.setState(States.LOANRETURN);
		} else if (usr.length != 2) {
			o.setOutput(Strings.INVALIDUSEREMAIL);
			o.setState(States.LOANRETURN);
		} else {
			User u = (User)UserTable.getInstance().findUser(str[0]);
			Title t = (Title)TitleTable.getInstance().findTitle(str[1]);
			result = LoanTable.getInstance().returnLoan(u.getUserId(), t.getISBN());
			if (result.equals(false)) {
				o.setOutput(Strings.LOANRETURNED);
				o.setState(States.LIBRARIAN);
			} else {
				o.setOutput(Strings.INVALIDLOANID);
				o.setState(States.LOANRETURN);
			}
		}
		return o;
	}
	
	public Output renewLoan(String input) {
		Output o = new Output("", 0);
		Object result = -1;
		String[] str = input.split(",");
		String[] usr = str[0].split("@");
		if (str.length != 2) {
			o.setOutput(Strings.INVALIDLOANID);
			o.setState(States.LOANRENEW);
		} else if (usr.length != 2) {
			o.setOutput(Strings.INVALIDUSEREMAIL);
			o.setState(States.LOANRENEW);
		} else {
			User u = (User)UserTable.getInstance().findUser(str[0]);
			Title t = (Title)TitleTable.getInstance().findTitle(str[1]);
			result = LoanTable.getInstance().returnLoan(u.getUserId(), t.getISBN());
			if (result.equals(false)) {
				o.setOutput(Strings.LOANRENEWED);
				o.setState(States.LIBRARIAN);
			} else {
				o.setOutput(Strings.INVALIDLOANID);
				o.setState(States.LOANRENEW);
			}
		}
		return o;
	}
	
	public Output payFines(String input) {
		Output o = new Output("", 0);
		Object result = -1;
		String[] usr = input.split("@");
		if (usr.length != 2) {
			o.setOutput(Strings.INVALIDUSEREMAIL);
			o.setState(States.PAYFINES);
		} else {
			User u = (User) UserTable.getInstance().findUser(input);
			result = FineTable.getInstance().payFine(u.getUserId());
			o.setOutput(result.toString());
			o.setState(States.LIBRARIAN);
		}
		return o;
	}
	
	public Output monitorSystem() {
		Output o = new Output("",0);
		String result = "";
		String users;
		String titles;
		users = UserTable.getInstance().printUsers().toString();
		titles = TitleTable.getInstance().printTitles().toString();
		result = String.format("Users: %s, Titles: %s", users, titles);
		o.setOutput(result.toString());
		o.setState(States.LIBRARIAN);
		return o;
	}
	
	public Output findUser(String user) {
		Output o = new Output("",0);
		Object result = -1;
		String[] str = user.split("@");
		if (str.length != 2) {
			o.setOutput(Strings.INVALIDUSEREMAIL);
			o.setState(States.FINDUSER);
		} else {
			User u  =(User) UserTable.getInstance().findUser(user);
			result = u.toString();
			o.setOutput(result.toString());
			o.setState(States.BORROWER);
		}
		return o;
	}
}
