package inputoutput;

import collections.TitleTable;
import collections.UserTable;
import models.Title;
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
}
