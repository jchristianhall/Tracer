import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Index {

    //code and comment are the ouput strings
    public ArrayList<String> code = new ArrayList<String>();
    public ArrayList<String> comment = new ArrayList<String>();
    
    public void trimming(File file) throws IOException {	
	BufferedReader inputFile = new BufferedReader(new FileReader(file));
	Pattern pattern = Pattern.compile("[a-zA-Z'/]+"); //regular expression
	String word,word2;
	
	while((word = inputFile.readLine()) != null){
	    if(!word.startsWith("package") && !word.startsWith("import")){
		if(word.contains("*")){
			Matcher match=pattern.matcher(word);
			while(match.find()){
				word2 = match.group();
				if(!word2.contains("/"))
				    comment.add(word2 + " ");
			}
		 }  
		else{
			Matcher match=pattern.matcher(word);
			while(match.find()){
				word2 = match.group();
				if(word2.contains("//")){
				    while(match.find()){
					word2 = match.group();
					comment.add(word2 + " ");
				    }
				     break;
				}				  		
				code.add(word2 + " ");
			}
		}
	    }		
	}
    }

}
