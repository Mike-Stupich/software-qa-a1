package collections;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import models.Title;
import utilities.Trace;

public class TitleTable {
	private Logger logger = Trace.getInstance().getLogger("operation_file");
	ArrayList<Title> titles=new ArrayList<Title>();
	
    private static class TitleList {
        private static final TitleTable INSTANCE = new TitleTable();
    }
    
    private TitleTable(){
    	//set up the default list with some instances
    	String[] ISBNList=new String[]{"9781442668584","9781442616899","9781442667181","9781611687910","9781317594277"};
    	String[] titlenameList=new String[]{"By the grace of God","Dante's lyric poetry","Courtesy lost","Writing for justice","The act in context"};
    	for(int i=0;i<ISBNList.length;i++){
    		Title detitle=new Title(ISBNList[i],titlenameList[i]);
    		titles.add(detitle);
		}
    	logger.info(String.format("Operation:Initialize TitleTable;TitleTable: %s", titles));
    }
    
    public static final TitleTable getInstance() {
        return TitleList.INSTANCE;
    }
    
    public Object addTitle(String isbn, String title) {
    	int titleId = -1;
    	boolean found = false;
    	
    	for (int i=0; i < titles.size(); ++i) {
    		if(titles.get(i).getISBN().equals(isbn) || titles.get(i).getTitle().equalsIgnoreCase(title)) {
    			found = true;
    		}
    	}
    	
    	if (!found) {
    		titles.add(new Title(isbn, title));
    		titleId = titles.size();
    		logger.info(String.format("Operation:Create New Title;Title Info:[%s,%s];State:Success", isbn,title));
    	} else {
    		logger.info(String.format("Operation:Create New Title;Title Info:[%s,%s];State:Fail;Reason:The ISBN already existed.", isbn,title));
    	}
    	return titleId;
    }
    
    public Object findTitle(String input) {
    	int titleId = -1;
    	boolean found = false;
    	Object result = titleId;
    	for (int i=0; i< titles.size(); ++i) {
    		if (titles.get(i).getISBN().equalsIgnoreCase(input) || titles.get(i).getTitle().equalsIgnoreCase(input)){
    			found = true;
    			titleId = i;
    		}
    	}
    	if (!found) {
    		logger.info(String.format("Operation:Find Title;Title Info:[%s];State:Fail;Reason:Title not in titls", input));
    	} else {
    		result = titles.get(titleId);
    		logger.info(String.format("Operation:Find Title;Title Info:[%s];State:Success", input));
    	}
    	return result;
    }
    
    public Object removeTitle(String input) {
    	int titleId = -1;
    	boolean found = false;
    	for (int i = 0; i < titles.size(); ++i) {
    		if (titles.get(i).getISBN().equalsIgnoreCase(input) || titles.get(i).getTitle().equalsIgnoreCase(input)){
    			titles.remove(i);
    			found = true;
    			titleId = i;
    		}
    	}
    	
    	if (!found) {
    		logger.info(String.format("Operation:Find Title;Title Info:[%s];State:Fail;Reason:Title not in titls", input));
    	} else {
    		logger.info(String.format("Operation:Find Title;Title Info:[%s];State:Success", input));
    	}
    	return titleId;
    }
    	
}
