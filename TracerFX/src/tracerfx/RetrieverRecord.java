/**
 * COPYRIGHT (C) 2012 Adam Thrash. All Rights Reserved.
 * Second retriever file for part 1 of software traceability project
 * Helper functions for retriever, contains set overlap
 * CSE 4214 | Retriever
 * @author Adam Thrash
 * @version 1.0 9.10.12
 */
package tracerfx;

import java.util.ArrayList;

public class RetrieverRecord {
    private String fileName;
    private float score;
    private ArrayList<String> words;
    
    /**
     *
     * @param requirement: an Indexer object of the indexed requirement to 
     *                     search
     * @param file: an Indexer object of the source code file
     * @param commentsIncluded: a Boolean value specifying whether comments 
     *                          should be included in the retrieval
     */
    public RetrieverRecord(Indexer requirement, Indexer file, boolean includeComments){
        words = file.getCodeArray();
        fileName = file.getPathName();
        
        if (includeComments==true){
            words.addAll(file.getCommentArray());
        }
        score = setOverlap(requirement.getReqArray(), words);
        
    }
    
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
    
    public String getFileName(){
        return fileName;
    }
    
    public float getScore(){
        return score;
    }
    
    
}
