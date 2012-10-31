/**
 * Retriever file for part 2 of software traceability project
 * Runs retriever functions
 * CSE 4214 | Retriever
 * @author Adam Thrash
 * @version 1.0 25.10.12
 */
package tracerfx;
import java.util.ArrayList;
import java.util.Collections;

public class Retriever {

    private double precision, recall;
    private ArrayList<RetrieverRecord> recordList, results;
    private ArrayList<String> correctAnswers;

   /**
   * @about Constructor creates RetrieverRecords in ArrayList<RetrieverRecord>
   * and sorts the list by the score of the RetrieverRecord
   * @param requirement - the requirement that is being traced
   * @param indexedCode - an ArrayList<Indexer> containing the Indexer objects of
   * source code
   * @param goldStandard - an ArrayList<String> of the correct answers
   * @param includeComments - a flag to indicate whether comments should be
   * retrieved
   */
    public Retriever(Indexer requirement, ArrayList<Indexer> indexedCode,
            ArrayList<String> goldStandard, boolean includeComments){
        recordList = new ArrayList<RetrieverRecord>();
        correctAnswers = goldStandard;
        results = new ArrayList<RetrieverRecord>();

        for (int i = 0; i < indexedCode.size(); i++){
            recordList.add(new RetrieverRecord(requirement, indexedCode.get(i), includeComments));
        }
        basicQuickSort(0, recordList.size());
        Collections.reverse(recordList);
        retrieveResultsByNumeric(8);//Tweak this value
//        retrieveResultsByPercentage(0.25);

        calculatePR();
    }
    
    //http://www.javacodegeeks.com/2012/06/all-you-need-to-know-about-quicksort.html
    public void basicQuickSort(int beginIdx, int len) {
    if ( len <= 1 )
         return;
    
    final int endIdx = beginIdx+len-1;

    // Pivot selection
    final int pivotPos = beginIdx+len/2;
    double pivot = recordList.get(pivotPos).getScore();
    Collections.swap(recordList, pivotPos, endIdx);

    // partitioning
    int p = beginIdx;
    for(int i = beginIdx; i != endIdx; ++i) {
         if ( recordList.get(i).getScore() <= pivot ) {
             Collections.swap(recordList, i, p++);
         }
     }
     Collections.swap(recordList, p, endIdx);

     // recursive call
     basicQuickSort(beginIdx, p-beginIdx);
     basicQuickSort(p+1,  endIdx-p);
}
    
    /**
    * @about Calculates precision and recall of the retrieval
    */
    private void calculatePR(){
        double inCommon = 0;
        precision = recall = 0;
        for (int i = 0; i < correctAnswers.size(); i++)
            for (int j = 0; j < results.size(); j++)
                if (correctAnswers.get(i).equals(results.get(j).getFileName()))
                    inCommon++;
        if (results.size() > 0 )
            precision = inCommon/results.size();
        if (correctAnswers.size() > 0)
            recall = inCommon/correctAnswers.size();
        System.out.println(precision);
        System.out.println(recall);
    }

    /**
    * @about Calculates f1 value of the retrieval
    */    
    public double getF1(){
        if (precision+recall != 0 )
            return (2*precision*recall)/(precision+recall);
        return 0;
    }
    
    /**
    * @about Calculates f2 value of the retrieval
    */  
    public double getF2(){
        if (precision+recall != 0)
            return (5*precision*recall)/(4*(precision+recall));
        return 0;
    }
    
    /**
    * @about Store results of retrieval in results; cut off retrieval at 
    * top x results (numericThreshold)
    */  
    private void retrieveResultsByNumeric(int numericThreshold){
        for (int i = 0; i < numericThreshold; i++){
            results.add(recordList.get(i));
        }
    }
    
    /**
    * @about Store results of retrieval in results; cut off retrieval below 
    * a certain score (percentageThreshold)
    */ 
    private void retrieveResultsByPercentage(double percentageThreshold){
        for (int i = 0; recordList.get(i).getScore() > percentageThreshold; i++)
            results.add(recordList.get(i));
    }
    
    /**
    * @about return results value to Tracer
    * @return results
    */
    public ArrayList<RetrieverRecord> getResults(){
        return results;
    }
}
