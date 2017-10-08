package inputoutput;

import res.Strings;

public class InputHandler {
	public static final int WAITING = 0;
	public static final int FINISHWAITING = 1;
    public static final int LIBRARIANLOGIN = 2;
    public static final int LIBRARIAN = 3;
    public static final int ADDUSER = 4;
    
    OutputHandler outputHandler=new OutputHandler();


	public ServerOutput processInput(String input, int state) {
		 String output = "";
		 Output o = new Output("",0);
		 ServerOutput oo = new ServerOutput(output,o.getState());
		 
        if (state == WAITING) {
            oo.setOutput(Strings.WHO);
            oo.setState(FINISHWAITING);
         }else if (state == FINISHWAITING) {
            if (input.equalsIgnoreCase("librarian")) {
                oo.setOutput(Strings.PASSPROMPT);
	            oo.setState(LIBRARIANLOGIN);
            } else {
            	oo.setOutput(Strings.INVALID + Strings.WHO);
            	oo.setState(FINISHWAITING);
            }
        }else if(state==LIBRARIANLOGIN){
        	o=outputHandler.librarianLogin(input);
    		oo.setOutput(o.getOutput());
            oo.setState(o.getState());
        }else if (state==LIBRARIAN) {
        	if (input.equalsIgnoreCase("add user")) {
        		oo.setOutput(Strings.ADDUSER);
        		oo.setState(ADDUSER);
        	}
        	else if (input.equalsIgnoreCase("menu")) {
        		oo.setOutput(Strings.LIBRARIANMENU);
        		oo.setState(LIBRARIAN);
        	}
        	else if (input.equalsIgnoreCase("log out")) {
        		oo.setOutput(Strings.LOGOUT);
        		oo.setState(WAITING);
        	} else {
	        	oo.setOutput(Strings.LIBRARIANMENU);
	        	oo.setState(LIBRARIAN);	
        	}
        } else if (state==ADDUSER) {
        	if (input.equalsIgnoreCase("menu")) {
        		oo.setOutput(Strings.LIBRARIANMENU);
        		oo.setState(LIBRARIAN);
        	}else if (input.equalsIgnoreCase("log out")) {
        		oo.setOutput(Strings.LOGOUT);
        		oo.setState(WAITING);
        	} else {
        		o = outputHandler.addUser(input);
        		oo.setOutput(o.getOutput());
        		oo.setState(o.getState());
        	}
        }
        return oo;
	}
}
