import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
/**
 *
 * @author Seth Holland
 */
public class splitterTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        System.out.print("Enter a file name: ");
        String input;
        ArrayList<String> words = new ArrayList();
        //reading from file and placing each word into an ArrayList
        Scanner inputSc = new Scanner(System.in);
        boolean fileOK = false;
        while(!fileOK)
        {
            input = inputSc.nextLine();
            try
            {
                fileOK = true;
                Scanner fileSc = new Scanner(new File(input));
                while(fileSc.hasNext())
                    words.add(fileSc.next());
            }catch(FileNotFoundException e)
            {
                fileOK = false;
                System.out.print("File not found, Try again: ");
            }
        }
        
        Indexer x = new Indexer(words);  //creating new Indexer object
        x.split();                       //splitting camelCase words
        System.out.println(x.getWords());//displaying new list of words
    }
}
