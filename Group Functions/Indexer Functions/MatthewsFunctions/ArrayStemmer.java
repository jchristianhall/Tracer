import java.util.*;

/**
 * 
 * @author Matthew Wood
 *
 * @about The ArrayStemmer class uses the Stemmer class (Porter Stemmer(C)) to take
 * an ArrayList of Strings, stem them, and then return the 
 * results in an ArrayList. Also any repeated words are 
 * removed such that there is only one instance of 
 * each word. Call the method stem(ArrayList<String>) to stem an array. The main
 * function at the end tests the functionality of the class.
 */

public class ArrayStemmer {
	
	private Stemmer s;
	private ArrayList<String> myComments;
	
	public ArrayStemmer(){
		s = new Stemmer();
		myComments = new ArrayList<String>();
		
	}
	
	/**
	 * @about Takes an ArrayList of type string and returns an  
	 * ArrayList with the stemmed results of type string.
	 * @return type ArrayList<String>
	 */
	
	public ArrayList<String> stem(ArrayList<String> myArray){//change
		 
		for(int i=0;i<myArray.size();i++){
		    	
		    	for(int j=0;j<myArray.get(i).length();j++){
					char letter = myArray.get(i).charAt(j);
					if (letter != '/' && letter != '\''){						////line added
					s.add(letter);  //Add characters one at a time to 
					}				//the stemmer to build the word
				}					
		    	
		    	s.stem();//stem the word
		    	
		    	//stop double words
		    	if(myComments.size()>0){
		    		
		    		boolean isDouble = checkWord(); //call checkWord() method.
		    										//Returns true if word is 
		    										//found
		    		//add word if not already there
		    		if(isDouble != true){
		    			addWord();  //call addWord() method
		    		}
		    	}
		    	
		    	//add word if the stemmed words list is empty
		    	else{
		    		addWord();  //call addWord() method
		    	}
		    	
		    	
		    }
		//Next commented line is used when returning an array
		//String[] stemResults = myComments.toArray(new String[myComments.size()]);
		return myComments;
	}
	
	/* Checks to see if the stemmed word already exists */
	
	private boolean checkWord(){
		
		//iterate through listArray to see if stemmed word
		//is already there
		for(int k=0;k<myComments.size();k++){
    		String doubleWord = myComments.get(k).toString();
    		String compare = s.toString();
    		
    		if(compare.equals(doubleWord)){
    			return true; //Return true if word is already 
    		}				 //in ArrayList
		}
    	return false;		 //Return false if word is not
	}						 //in ArrayList
	
	/* Adds stemmed word to the list */
	
	private void addWord(){
		String mystemm = s.toString();
    	myComments.add(mystemm);
	}
	
	/* This main function is only for testing this 
	 * class and should not be included in the 
	 * final code*/
	
	public static void main(String[] args) {
		
		//array only used to make selecting words
		//easier. These words are then inserted
		//into an ArrayList to pass to ArrayStemmer
		String[] myArray = {"ping", "ping's", 
							"pinging", "pines",
							"pine", "pine'r", 
							"trea/sure", "treasuring",
							"treasures"
		};
		
		//ArrayList for passing to class
		ArrayList<String> myArrayList = new ArrayList<String>();
		
		//ArrayList to receive returned ArrayList
		ArrayList<String> myArrayResult = new ArrayList<String>();
		
		for(int i=0;i<myArray.length;i++){	//Build ArrayList from array
			myArrayList.add(myArray[i]);	//for testing only
		}
		
		ArrayStemmer a = new ArrayStemmer();  //Create a new instance of my Class
		myArrayResult = a.stem(myArrayList);  //Call .stem() to stem and catch returned
											  //results ArrayList
		//print to screen the results for debugging only
		for(int i=0;i<myArrayResult.size();i++){
	    	System.out.println(myArrayResult.get(i));
	    }
	}
}
