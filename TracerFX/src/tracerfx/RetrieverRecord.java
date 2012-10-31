/**
 * RetrieverRecord object for part 2 of software traceability project
 * Helps retriever functions
 * CSE 4214 | RetrieverRecord
 * @author Adam Thrash
 * @version 1.0 25.10.12
 */
package tracerfx;
import java.util.ArrayList;

public class RetrieverRecord {
    private String fileName;
    private ArrayList<String> wordsInCode, wordsInReq;
    private double score;
    
   /**
   * @about Constructor creates RetrieverRecord with fileName of source file
   * and score
   * @param requirement - the requirement that is being traced
   * @param code - an Indexer object of a source code file
   * @param includeComments - a flag to indicate whether comments should be
   * retrieved
   */
    public RetrieverRecord(Indexer requirement, Indexer code, boolean includeComments){
        wordsInReq = requirement.getReqArray();
        wordsInCode = code.getCodeArray();
        fileName = code.getFileName();
        
        if (includeComments){
            ArrayList<String> commentArray = code.getCommentArray();
            for(String s: commentArray)
                if(!commentArray.contains(s))
                    wordsInCode.add(s);
        }
        setOverlap();
    }
    
       /**
   * @about score the retrieval using set overlap
   * @param arrayOne - the array of words for requirements
   * @param wordsInCode - the array of words for the source code file
   * @return score
   */
    private void setOverlap(){
        int inCommon = 0;
        for (int i = 0; i < wordsInReq.size(); i++)
            for (int j = 0; j < wordsInCode.size(); j++)
                if(wordsInReq.get(i).equals(wordsInCode.get(j)))
                    inCommon++;
        score = (double)2*((double)inCommon/(wordsInReq.size()+wordsInCode.size()));
    }
    
   /**
   * @about getters for filename and score
   * @return fileName or score
   */
    public String getFileName(){
        return fileName;
    }
    
    public double getScore(){
        return score;
    }
}
//    public static double compareStrings(String str1, String str2) {
//        ArrayList pairs1 = wordLetterPairs(str1.toUpperCase());
//        ArrayList pairs2 = wordLetterPairs(str2.toUpperCase());
//        int intersection = 0;
//        int union = pairs1.size() + pairs2.size();//not correct
//        for (int i = 0; i < pairs1.size(); i++) {
//            Object pair1 = pairs1.get(i);
//            for (int j = 0; j < pairs2.size(); j++) {
//                Object pair2 = pairs2.get(j);
//                if (pair1.equals(pair2)) {
//                    intersection++;
//                    pairs2.remove(j);
//                    break;
//                }
//            }
//        }
//        return (2.0 * intersection) / union;
//    }

    /**
     * @return an ArrayList of 2-character Strings.
     */
//    private static ArrayList wordLetterPairs(String str) {
//        ArrayList allPairs = new ArrayList();
//        // Tokenize the string and put the tokens/words into an array
//        String[] words = str.split("s");
//        // For each word
//        for (int w = 0; w < words.length; w++) {
//            // Find the pairs of characters
//            String[] pairsInWord = letterPairs(words[w]);
//            for (int p = 0; p < pairsInWord.length; p++) {
//                allPairs.add(pairsInWord[p]);
//            }
//        }
//        return allPairs;
//    }

    /**
     * @return an array of adjacent letter pairs contained in the input string
     */
//    private static String[] letterPairs(String str) {
//        int numPairs = str.length() - 1;
//        String[] pairs = new String[numPairs];
//        for (int i = 0; i < numPairs; i++) {
//            pairs[i] = str.substring(i, i + 2);
//        }
//        return pairs;
//    }
