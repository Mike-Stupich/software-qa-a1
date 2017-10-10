package inputoutput;

import res.States;
import res.Strings;

public class InputHandler {
	
    OutputHandler outputHandler=new OutputHandler();

	public ServerOutput processInput(String input, int state) {
		 String output = "";
		 Output o = new Output("",0);
		 ServerOutput oo = new ServerOutput(output,o.getState());
		 
		 if(input.equalsIgnoreCase("log out")) {
			 oo.setOutput(Strings.LOGOUT);
			 oo.setState(States.WAITING);
		 } else if (input.equalsIgnoreCase("menu") || input.equalsIgnoreCase("main menu")) {
     		oo.setOutput(Strings.LIBRARIANMENU);
     		oo.setState(States.LIBRARIAN);
		 } else {
		 
	        if (state == States.WAITING) {
	            oo.setOutput(Strings.WHO);
	            oo.setState(States.FINISHWAITING);
	         }else if (state == States.FINISHWAITING) {
	            if (input.equalsIgnoreCase("librarian")) {
	                oo.setOutput(Strings.PASSPROMPT);
		            oo.setState(States.LIBRARIANLOGIN);
	            } else if (input.equalsIgnoreCase("borrower")) {
	            	oo.setOutput(Strings.BORROWERLOGIN);
	            	oo.setState(States.BORROWERLOGIN);
	            } else {
	            	oo.setOutput(Strings.INVALID + Strings.WHO);
	            	oo.setState(States.FINISHWAITING);
	            }
	        } else if (state == States.BORROWERLOGIN) {
	        	o = outputHandler.borrowerLogin(input);
	        	oo.setOutput(o.getOutput());
	        	oo.setState(o.getState());
	        } else if(state == States.BORROWER){
	        	if (input.equalsIgnoreCase("find user")) {
	        		oo.setOutput(Strings.FINDUSER);
	        		oo.setState(States.FINDUSER);
	        	} else if (input.equalsIgnoreCase("return loan")) {
	        		oo.setOutput(Strings.LOANRETURN);
	        		oo.setState(States.LOANRETURN);
	        	} else if (input.equalsIgnoreCase("loan item")) {
	        		oo.setOutput(Strings.LOANADD);
	        		oo.setState(States.LOANITEM);
	        	} else if (input.equalsIgnoreCase("find title")) {
	        		oo.setOutput(Strings.FINDTITLE);
	        		oo.setState(States.FINDTITLE);
	        	} else {
	        		oo.setOutput(Strings.BORROWERMENU);
	        		oo.setState(States.BORROWER);
	        	}
	        } else if(state==States.LIBRARIANLOGIN){
	        	o=outputHandler.librarianLogin(input);
	    		oo.setOutput(o.getOutput());
	            oo.setState(o.getState());
	        } else if (state==States.LIBRARIAN) {
	        	if (input.equalsIgnoreCase("add user")) {
	        		oo.setOutput(Strings.ADDUSER);
	        		oo.setState(States.ADDUSER);
	        	}else if (input.equalsIgnoreCase("add item")) {
	        		oo.setOutput(Strings.FINDTITLE);
	        		oo.setState(States.FINDTITLE);
	        	}else if (input.equalsIgnoreCase("remove title")) {
	        		oo.setOutput(Strings.REMOVETITLE);
	        		oo.setState(States.REMOVETITLE);
	        	}else if (input.equalsIgnoreCase("remove item")) {
	        		oo.setOutput(Strings.REMOVETITLE);
	        		oo.setState(States.REMOVEITEM);
	        	}else if (input.equalsIgnoreCase("loan item")) {
	        		oo.setOutput(Strings.LOANADD);
	        		oo.setState(States.LOANITEM);
	        	} else if (input.equalsIgnoreCase("return item")) {
	        		oo.setOutput(Strings.LOANRETURN);
	        		oo.setState(States.LOANRETURN);
	        	} else if (input.equalsIgnoreCase("renew item")) {
	        		oo.setOutput(Strings.LOANRENEW);
	        		oo.setState(States.LOANRENEW);
	        	} else if (input.equalsIgnoreCase("pay fines")) {
	        		oo.setOutput(Strings.PAYFINES);
	        		oo.setState(States.PAYFINES);
	        	} else if (input.equalsIgnoreCase("remove user")) {
	        		oo.setOutput(Strings.REMOVEUSER);
	        		oo.setState(States.REMOVEUSER);
	        	} else if (input.equalsIgnoreCase("monitor system")) {
	        		o = outputHandler.monitorSystem();
	        		oo.setOutput(o.getOutput());
	        		oo.setState(o.getState());
	        	}
	        	else if (input.equalsIgnoreCase("add title")) {
	        		oo.setOutput(Strings.ADDTITLE);
	        		oo.setState(States.ADDTITLE);
	        	}else {
		        	oo.setOutput(Strings.LIBRARIANMENU);
		        	oo.setState(States.LIBRARIAN);	
	        	}
	        } else if (state == States.ADDUSER) {
        		o = outputHandler.addUser(input);
        		oo.setOutput(o.getOutput());
        		oo.setState(o.getState());
	        } else if (state == States.ADDTITLE) {
		 		o = outputHandler.addTitle(input);
		 		oo.setOutput(o.getOutput());
		 		oo.setState(o.getState());
	        } else if (state == States.FINDTITLE) {
	        	o = outputHandler.findTitle(input);
	        	oo.setOutput(o.getOutput());
	        	oo.setState(o.getState());
	        } else if (state == States.REMOVETITLE) {
	        	o = outputHandler.removeTitle(input);
	        	oo.setOutput(o.getOutput());
	        	oo.setState(o.getState());
	        } else if (state == States.REMOVEITEM) {
	        	o = outputHandler.removeItem(input);
	        	oo.setOutput(o.getOutput());
	        	oo.setState(o.getState());
	        } else if (state == States.LOANITEM) {
	        	o = outputHandler.loanItem(input);
	        	oo.setOutput(o.getOutput());
	        	oo.setState(o.getState());
	        } else if (state == States.LOANRETURN) {
	        	o = outputHandler.returnLoan(input);
	        	oo.setOutput(o.getOutput());
	        	oo.setState(o.getState());
	        } else if (state == States.LOANRENEW) {
	        	o = outputHandler.renewLoan(input);
	        	oo.setOutput(o.getOutput());
	        	oo.setState(o.getState());
	        } else if (state == States.PAYFINES) {
	        	o = outputHandler.payFines(input);
	        	oo.setOutput(o.getOutput());
	        	oo.setState(o.getState());
	        } else if (state == States.REMOVEUSER) {
	        	o = outputHandler.removeUser(input);
	        	oo.setOutput(o.getOutput());
	        	oo.setState(o.getState());
	        } else if (state == States.FINDUSER) {
	        	o = outputHandler.findUser(input);
	        	oo.setOutput(o.getOutput());
	        	oo.setState(o.getState());
	        }
		 }
	 return oo;
	}

}
