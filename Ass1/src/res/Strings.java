package res;

public class Strings {
	// Connection Strings
	public final static String INITCONN = "Connecting to the server. Please wait ...";
	public final static String CONNFAIL = "Failed to connect to the server, please try later!";
	public final static String SERVERSUCCESS = "Server stated successfully!";
	
	public final static String WELCOME = "Welcome To Mike's Library!";
	public final static String WHO = "Who are you?";
	public final static String LIBRARIANLOGIN = "What can I do for you?";
	public final static String PASSPROMPT = "Please Input the password";
	public final static String INCPASS = "Wrong Password! Please Input The Password:";
	public final static String LOGOUT = "Successfully Logged Out!";
	
	public final static String LIBRARIANMENU = "Menu: Add User/Title/Item, Remove Title/Item, Loan Item";
	
	public final static String ADDUSER = "Enter the user name to add";
	public final static String INVALIDADDUSER = "Your input should be username@email,password";
	public final static String USEREXISTS = "The user entered already exists!";
	
	public final static String ADDTITLE = "Enter the title name to add";
	public final static String INVALIDADDTITLE = "Your input should be titlename,isbn";
	public final static String TITLEEXISTS = "The title entered already exists!";	
	
	public final static String FINDTITLE = "Enter the title or ISBN to search for";
	public final static String TITLENOTFOUNDADD = "Those do not exist, would you like to add the title?";
	public final static String INVALIDFINDTITLE = "Enter a valid title or isbn!";
	
	public final static String REMOVETITLE = "Enter the title or ISBN to remove";
	public final static String TITLEREMOVED = "The title has been removed!";
	public final static String TITLENOTFOUND = "The title is not stored.";
	
	public final static String INVALIDLOANTITLE = "No title was found with that input. Your input should be userid,title/isbn";
	public final static String LOANITEM = "Enter the loan to be added";
	public final static String LOANEXISTS = "The loan entered already exists.";
	public final static String LOANADDED = "The loan was successfully added";
	public final static String LOANRETURNED="The loan was successfully returend";
	public final static String LOANRENEWED="The loan was successfully renewed";
	
	public final static String FINEPAID = "The fine was successfully paid";
	public final static String NOFINEONUSER = "There is no fine for this users";
	
	public final static String INVALIDFINEID = "The fine id was invalid";
	public final static String INVALIDUSERID = "The user id was invalid";
	public final static String INVALIDTITLEID = "The title id was invalid";
	public final static String INVALIDLOANID = "The loan id was invalid";
	public final static String INVALIDUSEREMAIL = "Your user must be user@email";
	
	
	
	public final static String INVALID = "Sorry, that command doesn't work\n";
}
