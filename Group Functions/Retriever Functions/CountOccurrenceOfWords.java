//add package
import java.util.*;

public class CountOccurrenceOfWords {
  public static void main(String[] args) {
    // Set text in a string
    String text = "Good morning. Have a good class. " +
      "Have a good visit. Have fun!";

    // Create a TreeMap to hold words as key and count as value
    TreeMap<String, Integer> map = new TreeMap<String, Integer>();

    String[] words = text.split("[ \n\t\r.,;:!?(){}]");
    for (int i = 0; i < words.length; i++) {
      String key = words[i].toLowerCase();

      if (words[i].length() > 1) {
        if (map.get(key) == null) {
          map.put(key, 1);
        }
        else {
          int value = map.get(key).intValue();
          value++;
          map.put(key, value);
        }
      }
    }

    // Get all entries into a set
    Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
    
    Iterator it = entrySet.iterator();
    ArrayList<WordOccurrence> array = new ArrayList<WordOccurrence>();
    for(int i = 0; it.hasNext(); i++)
    {
        Map.Entry<String, Integer> e = (Map.Entry<String, Integer>) it.next();
        array.add(new WordOccurrence(e.getKey(), e.getValue()));
    }
    Collections.sort(array);
    // Get key and value from each entry
    for (int i = 0; i < array.size(); i++)
      System.out.println(array.get(i));
  }
}
