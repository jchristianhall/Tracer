/**
 * COPYRIGHT (C) 2012 Adam Thrash. All Rights Reserved.
 * Retriever file for part 1 of software traceability project
 * Main retriever functions
 * CSE 4214 | Retriever
 * @author Adam Thrash
 * @version 1.0 9.10.12
 */
package tracerfx;
import java.util.ArrayList;

public class Retriever {
    
    private double precision, recall, f1, f2;
    private ArrayList<RetrieverRecord> recordList;
    private ArrayList<String> correctAnswers;
    private int numericThreshold;
    private float percentageThreshold;
    
    public Retriever(Indexer requirement, ArrayList<Indexer> files, boolean includeComments){
        for (int i = 0; i < files.size(); i++){
            RetrieverRecord tempRecord = new RetrieverRecord(requirement, files.get(i), includeComments);
            recordList.add(tempRecord);
        }
        quickSort(recordList, 0, recordList.size());
    }
    
    public void setNumericThreshold(int threshold){
        numericThreshold = threshold;
    }
    
    public void setPercentageThreshold(float threshold){
        percentageThreshold = threshold;
    }
    
    // source http://www.algolist.net/Algorithms/Sorting/Quicksort
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
    // source http://www.algolist.net/Algorithms/Sorting/Quicksort
    private void quickSort(ArrayList<RetrieverRecord> records, int left, int right) {
          int index = partition(records, left, right);
          if (left < index - 1){
                quickSort(records, left, index - 1);
          }
          if (index < right){
                quickSort(records, index, right);
          }
    }
    
    public ArrayList<RetrieverRecord> retrieveResultsByNumeric(){
        ArrayList<RetrieverRecord> results = null;
        for (int i = 0; i < numericThreshold; i++){
            results.add(recordList.get(i));
        }
        return results;
        
    }
    
    private ArrayList<RetrieverRecord> retrieveResultsByPercentage(){
        ArrayList<RetrieverRecord> results = null;
        int counter = 0;
        while (recordList.get(counter).getScore() <= percentageThreshold){
            results.add(recordList.get(counter));
            counter++;
        }
        return results;

    }
    
}

//    private void sortByScore(){
//        ArrayList<RetrieverRecord> unsorted;
//        unsorted = recordList;
//        float maxPercent = 1;
//        RetrieverRecord firstRecord;
//        
//        for (int i = 0; i < recordList.size(); i++){
//            RetrieverRecord tempRecord = recordList.get(i);
//            if (Math.min(maxPercent, tempRecord.getScore()) == tempRecord.getScore()){
//                maxPercent = tempRecord.getScore();
//                firstRecord = tempRecord;
//            }
//        }
//        unsorted.set(0, firstRecord);
//        // recursion?
//        
//        
//    }