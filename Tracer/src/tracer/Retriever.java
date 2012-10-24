/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tracer;
import java.util.ArrayList;


/**
 *
 * @author adamthrash
 */
public class Retriever {
    
    //private double precision, recall, f1, f2;
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
    public Retriever(Indexer requirement, Indexer file, Boolean commentsIncluded){
        //precision = 0;
        //recall = 0;
        //f1 = 0;
        //f2 = 0;
        words = file.getCodeArray();
        fileName = file.getPathName();
        
        if (commentsIncluded==true){
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
        float score = (2*inCommon)/(arrayOne.size()+arrayTwo.size());
        return score;
        
    }
    
    public String getFileName(){
        return fileName;
    }
    
    public double getScore(){
        return score;
    }
    
}
