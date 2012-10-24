//add package
import java.lang.Comparable;
/**
 *
 * @author Seth Holland
 */
public class WordOccurrence implements Comparable<WordOccurrence>{
    private String word_;
    private int count_;
    /**
     * creates a WordOccurrence with the given String
     * the number of occurrences is 1
     * @param w 
     */
    public WordOccurrence(String w){
        word_ = w;
        count_ = 1;
    }
    /**
     * creates a WordOccurrence with the given String and 
     * the number of occurrences is i
     * @param w
     * @param i 
     */
    public WordOccurrence(String w, int i){
        word_ = w;
        if(i > 0)
            count_ = i;
        else
            count_ = 1;
    }
    /**
     * returns the word
     * @return the word
     */
    public String getWord(){
        return word_;
    }
    /**
     * comparesWordOccurrences based on the number of occurrences (count_)
     * @param o
     * @return 1, 0, -1
     */
    public int compareTo(WordOccurrence o){
        if(count_ > o.count_)
            return 1;
        else if(count_ == o.count_)
            return 0;
        else
            return -1;
    }
    /**
     * Returns true if words are the same
     * @param o Object type
     * @return true or false
     */
    public boolean equals(Object o){
        if(o == null)
            return false;
        if(o == this)
            return true;
        if(o.getClass() != getClass())
            return false;
        WordOccurrence p = (WordOccurrence) o;
        if(getWord().equals(p.getWord()))
            return true;
        return false;
    }
    /**
     * Converts the WordOccurrence to a String
     * @return the number of occurrences and the word
     */
    public String toString()
    {
        return count_ + "\t" + word_;
    }
}
