package inputoutput;

import res.Strings;
import utilities.Config;

public class OutputHandler {
	public static final int WAITING = 0;
	public static final int FINISHWAITING=1;
    public static final int LIBRARIANLOGIN = 2;
    public static final int LIBRARIAN = 3;

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
	
}
