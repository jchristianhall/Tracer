import java.util.ArrayList;

/**
 *
 * @author Seth Holland
 */
public class Indexer{
    private ArrayList<String> s;
    
    /**
     * creates a new Indexer object from an ArrayList of words
     * @param words 
     */
    public Indexer(ArrayList<String> words){s = words;}
    
    /**
     * returns the list of words in an ArrayList
     * @return the list of words
     */
    public ArrayList<String> getWords(){return s;}
    
    /**
     * uses the existing list of words (ArrayList s)
     * splits camelCase Strings into separate words and
     * places them in a new ArrayList
     */
    public void split()
    {
        ArrayList<String> tempAL = new ArrayList();
        char letter;
        for(String x : s)
        {
            for(int i = 1; i < x.length(); i++)
            {
                letter = x.charAt(i);
                if(letter >= 'A' && letter <= 'Z')
                {
                    tempAL.add(x.substring(0, i).toLowerCase());
                    x = x.substring(i);
                    i = 0;
                }
            }
            tempAL.add(x.toLowerCase());
        }
        s = tempAL;
    }
}
