/**
 * Retriever file for part 2 of software traceability project
 * Runs retriever functions
 * CSE 4214 | Retriever
 * @author Adam Thrash
 * @version 1.0 25.10.12
 */
package tracer;
import java.util.ArrayList;

public class Retriever {
    
    private double precision, recall, f1, f2;
    private ArrayList<RetrieverRecord> recordList;
    private ArrayList<String> correctAnswers;
    private int numericThreshold;
    private float percentageThreshold;
    private ArrayList<RetrieverRecord> results;
    
    
   /**
   * @about Constructor creates RetrieverRecords in ArrayList<RetrieverRecord>
   * and sorts the list by the score of the RetrieverRecord
   * @param requirement - the requirement that is being traced
   * @param files - an ArrayList<Indexer> containing the Indexer objects of
   * source code
   * @param includeComments - a flag to indicate whether comments should be
   * retrieved
   */
    public Retriever(Indexer requirement, ArrayList<Indexer> files, boolean includeComments){
        for (int i = 0; i < files.size(); i++){
            RetrieverRecord tempRecord = new RetrieverRecord(requirement, files.get(i), includeComments);
            recordList.add(tempRecord);
        }
        quickSort(recordList, 0, recordList.size());
    }
    
    /**
    * @about Basic setter functions for setting threshold values and inputting
    * correct answers
    */
    public void setNumericThreshold(int threshold){
        numericThreshold = threshold;
    }
    
    public void setPercentageThreshold(float threshold){
        percentageThreshold = threshold;
    }
    public void setCorrectAnswers(ArrayList<String> answers){
        correctAnswers = answers;
    }
    
    /**
    * @about quicksort implemented in Java
    * source : http://www.algolist.net/Algorithms/Sorting/Quicksort
    */
    private int partition(ArrayList<RetrieverRecord> records, int left, int right)
    {
          int i = left, j = right;
          RetrieverRecord tmp;
          float pivot = records.get((left + right) / 2).getScore();

          while (i <= j) {
                while (records.get(i).getScore() < pivot){
                      i++;
                }
                while (records.get(j).getScore() > pivot){
                      j--;
                }
                if (i <= j) {
                      tmp = records.get(i);
                      records.set(i, records.get(j));
                      records.set(j, tmp);
                      i++;
                      j--;
                }
          }

          return i;
    }

    private void quickSort(ArrayList<RetrieverRecord> records, int left, int right) {
          int index = partition(records, left, right);
          if (left < index - 1){
                quickSort(records, left, index - 1);
          }
          if (index < right){
                quickSort(records, index, right);
          }
    }
    
    /**
    * @about Calculates precision of the retrieval
    */
    private void calculatePrecision(){
        int inCommon = 0;
        for (int i = 0; i < correctAnswers.size(); i++){
            for (int j = 0; j < results.size(); j++){
                if (correctAnswers.get(i).equals(results.get(j).getFileName())){
                    inCommon++;
                }
            }
        }
        precision = inCommon/results.size();
    }
    
    /**
    * @about Calculates recall of the retrieval
    */    
    private void calculateRecall(){
        int inCommon = 0;
        for (int i = 0; i < correctAnswers.size(); i++){
            for (int j = 0; j < results.size(); j++){
                if (correctAnswers.get(i).equals(results.get(j).getFileName())){
                    inCommon++;
                }
            }
        }
        recall = inCommon/correctAnswers.size();
    }

    /**
    * @about Calculates f1 value of the retrieval
    */    
    private void calculateF1(){
        f1 = ((2)*(precision*recall))/(1*(precision+recall));
    }
    
    /**
    * @about Calculates f2 value of the retrieval
    */  
    private void calculateF2(){
        f2 = ((1+4)*(precision*recall))/(4*(precision+recall));
    }
    
    /**
    * @about Store results of retrieval in results; cut off retrieval at 
    * top x results (numericThreshold)
    */  
    private void retrieveResultsByNumeric(){
        for (int i = 0; i < numericThreshold; i++){
            results.add(recordList.get(i));
        }
    }
    
    /**
    * @about Store results of retrieval in results; cut off retrieval below 
    * a certain score (percentageThreshold)
    */ 
    private void retrieveResultsByPercentage(){
        int counter = 0;
        while (recordList.get(counter).getScore() <= percentageThreshold){
            results.add(recordList.get(counter));
            counter++;
        }
    }
    
    /**
    * @about return f1 value to Tracer
    * @return f1
    */ 
    public double getF1(){
        return f1;
    }
    
    /**
    * @about return f2 value to Tracer
    * @return f2
    */ 
    public double getF2(){
        return f2;
    }
    
    /**
    * @about return results value to Tracer
    * @return results
    */
    public ArrayList<RetrieverRecord> getResults(){
        return results;
    }
    
}