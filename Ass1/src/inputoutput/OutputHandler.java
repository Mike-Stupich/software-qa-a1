package inputoutput;

import collections.TitleTable;
import collections.UserTable;
import models.Title;
import res.Strings;
import utilities.Config;

public class OutputHandler {
	public static final int WAITING = 0;
	public static final int FINISHWAITING = 1;
    public static final int LIBRARIANLOGIN = 2;
    public static final int LIBRARIAN = 3;
    public static final int ADDUSER = 4;
    public static final int ADDTITLE = 5;
    public static final int FINDTITLE =6;

	public Output librarianLogin(String input) {
		Output output=new Output("",0);
		if(input.equalsIgnoreCase(Config.LIBRARIAN_PASSWORD)){
			output.setOutput(Strings.LIBRARIANMENU);
        	output.setState(LIBRARIAN);
		}else{
			output.setOutput(Strings.INCPASS);
        	output.setState(LIBRARIANLOGIN);
		}
		return output;
	}
	
	public Output addUser(String user) {
		Output o = new Output("",0);
		String[] str = user.split(",");
		boolean isEmail = str[0].contains("@");
		Object result = -1;
		if (str.length!=2 || !isEmail) {
			o.setOutput(Strings.INVALIDADDUSER);
			o.setState(ADDUSER);
		} else {
			result = UserTable.getInstance().addUser(str[0],str[1]);
			if (!result.equals(-1)) {
				o.setOutput("User added! UserId = " + result.toString());
			} else {
				o.setOutput(Strings.USEREXISTS);
			}
			o.setState(LIBRARIAN);
		}
		return o;
	}
	
	public Output addTitle(String title) {
		Output o = new Output("",0);
		String[] str = title.split(",");
		Object result = -1;
		if (str.length != 2) {
			o.setOutput(Strings.INVALIDADDTITLE);
			o.setState(ADDTITLE);
		} else {
			result = TitleTable.getInstance().addTitle(str[0], str[1]);
			if (!result.equals(-1)) {
				o.setOutput("Title added! TitleId = " + result.toString());
			} else {
				o.setOutput(Strings.TITLEEXISTS);
			}
			o.setState(LIBRARIAN);
		}
		return o;
	}
	
	public Output findTitle(String input) {
		Output o = new Output("",0);
		Object result = -1;
		if (input.isEmpty()) {
			o.setOutput(Strings.INVALIDFINDTITLE);
			o.setState(FINDTITLE);
		} else {
			result = TitleTable.getInstance().findTitle(input);
			if (!result.equals(-1)) {
				Title t = (Title) result;
				o.setOutput(t.getTitle() + "," +t.getISBN());
				o.setState(LIBRARIAN);
			} else {
				o.setOutput(Strings.ADDTITLE);
				o.setState(ADDTITLE);
			}
		}
		return o;
	}
}
