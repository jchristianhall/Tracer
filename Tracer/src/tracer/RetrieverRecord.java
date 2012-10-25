/**
 * RetrieverRecord object for part 2 of software traceability project
 * Helps retriever functions
 * CSE 4214 | RetrieverRecord
 * @author Adam Thrash
 * @version 1.0 25.10.12
 */
package tracer;

import java.util.ArrayList;

public class RetrieverRecord {
    private String fileName;
    private float score;
    private ArrayList<String> words;
    
   /**
   * @about Constructor creates RetrieverRecord with fileName of source file
   * and score
   * @param requirement - the requirement that is being traced
   * @param file - an Indexer object of a source code file
   * @param includeComments - a flag to indicate whether comments should be
   * retrieved
   */
    public RetrieverRecord(Indexer requirement, Indexer file, boolean includeComments){
        words = file.getCodeArray();
        fileName = file.getPathName();
        
        if (includeComments==true){
            words.addAll(file.getCommentArray());
        }
        score = setOverlap(requirement.getReqArray(), words);
        
    }
    
       /**
   * @about score the retrieval using set overlap
   * @param arrayOne - the array of words for requirements
   * @param arrayTwo - the array of words for the source code file
   * @return score
   */
    private float setOverlap(ArrayList<String> arrayOne, ArrayList<String> arrayTwo){
        int inCommon = 0;
        
        for (int i = 0; i < arrayOne.size(); i++){
            for (int j = 0; j < arrayTwo.size(); j++){
                if (arrayOne.get(i).equals(arrayTwo.get(j))){
                    inCommon++;
                }
            }
        }
        float setOverlapScore;
        setOverlapScore = (2*inCommon)/(arrayOne.size()+arrayTwo.size());
        return setOverlapScore;
        
    }
    
   /**
   * @about getters for filename and score
   * @return fileName or score
   */
    public String getFileName(){
        return fileName;
    }
    
    public float getScore(){
        return score;
    }
    
    
}
