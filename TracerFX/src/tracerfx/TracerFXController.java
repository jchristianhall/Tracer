/**
 * COPYRIGHT (C) 2012 Christian Hall. All Rights Reserved.
 * Main file for software traceability project
 * Runs functions for TracerFX GUI
 * CSE 4214 | TracerFX
 * @author Christian Hall
 * @version 1.0 2.11.12
 */
package tracerfx;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class TracerFXController implements Initializable
{
  // Interface variables
  @FXML private ChoiceBox indexType;
  @FXML private ChoiceBox indexGrouping;
  @FXML private Button openButton;
  @FXML private TextArea correctFiles;
  @FXML private Button retrieveButton;
  @FXML private Button f2Button;
  @FXML private Button clearButton;
  @FXML private TextArea log;
  
  // Logic variables
  int fileCount = 0, dirCount = 1;
  public ArrayList<Indexer> indexArray;
  public Indexer indexedReq;
  public ArrayList<Double> f2Scores;
  
  /**
   * @about Constructor that creates new index and requirements arrays.
   */
  public TracerFXController ()
  {
    indexArray = new ArrayList<Indexer>();
    f2Scores = new ArrayList<Double>();
  }
  
  /**
   * @about Function that fires when indexer button is used. Indexes source
   * code and requirements based on value in indexType. Shows results in log.
   * @param e - Any ActionEvent that triggers the function.
   */
  @FXML
  public void indexProcess(ActionEvent e)
  {
    // Choose type to index and method of grouping
    String indexTypeString = (String)indexType.getValue();
    String indexGroupingString = (String)indexGrouping.getValue();
    if (indexTypeString == null || 
        indexTypeString.equals("Choose Type to Index"))
    {
      log.appendText("Select a type to index.\n");
    }
    else if (indexGroupingString == null ||
        indexGroupingString.equals("Choose Index Grouping"))
    {
      log.appendText("Select a method of grouping.\n");
    }
    
    // Process for source code directory
    else if (indexTypeString.equals("Source Code"))
    {
      // Get directory from DirectoryChooser and make an array of paths
      File[] fileList = TracerFX.makeDirectoryChooser();
      if (fileList != null)
      {
        // Go over files and calcuate directories, files, and time
        long start = System.nanoTime();
        countFiles(fileList);
        long end = System.nanoTime();
        double duration = (end - start)/1000000000.0;

        // Add results to log and reset counts
        log.appendText("Directories found: " + dirCount + "\n");
        log.appendText("Files indexed: " + fileCount + "\n");
        log.appendText("Source code indexing took " + duration + " seconds.\n\n");
        dirCount = 1;
        fileCount = 0;
      }
      else
      {
        log.appendText("Open cancelled.\n\n");
      }
    }
    
    // Process for individual requirement file
    else if (indexTypeString.equals("Requirement"))
    {
      // Get file from FileChooser and make an array of paths
      File file = TracerFX.makeFileChooser();
      if (file != null)
      {
        // Go over files and calcuate directories, files, and time
        long start = System.nanoTime();
        indexedReq = new Indexer(file, false);
        long end = System.nanoTime();
        double duration = (end - start)/1000000000.0;

        // Add results to log and reset counts
        log.appendText("Requirement indexing took " + duration + " seconds.\n\n");
      }
      else
      {
        log.appendText("Open cancelled.\n\n");
      }
    }
    else
    {
      log.appendText(indexTypeString);
    }
  }
  
  /**
   * @about Count number of files in directory and subdirectories
   * If directory, recursively go through files in that directory
   * If file, increment file count and run indexer
   * Run index file for each file
   * @param fileList - List of file absolute paths to be iterated over
   */
  public void countFiles(File[] fileList)
  {
    for (File file : fileList)
    {
      if (file.isDirectory())
      {
        dirCount++;
        File[] sublist = file.listFiles();
        countFiles(sublist);
      } 
      else 
      {
        fileCount++;
        indexArray.add(new Indexer(file, !indexGrouping.getValue().equals("Source Code")));
      }
    }
  }
  
  /**
   * @about Function that fires when retriever button is used. Compares correct
   * array of file names to file names found from retrieval process.
   * @param e - Any ActionEvent that triggers the function.
   */
  @FXML
  public void retrieverProcess(ActionEvent e)
  {
    ObservableList<CharSequence> correctFileArray = correctFiles.getParagraphs();
    ArrayList<String> correctAnswers = new ArrayList<String>();
    for (int i = 0; i < correctFileArray.size() - 1; i++)
    {
      correctAnswers.add(correctFileArray.get(i).toString());
    }
    Retriever retriever = new Retriever(indexedReq,indexArray,
            correctAnswers,!indexGrouping.getValue().equals("Source Code"));
    ArrayList<RetrieverRecord> results = new ArrayList<RetrieverRecord>(retriever.getResults());
    log.appendText("F1 Score: " + Double.toString(retriever.getF1()) + "\n");
    log.appendText("F2 Score: " + Double.toString(retriever.getF2()) + "\n\n");
    f2Scores.add(retriever.getF2());
    for (int i = 0; i < results.size(); i++)
    {
      log.appendText(results.get(i).getFileName() + "\n");
      log.appendText("Similarity Score: " + 
                     Double.toString(results.get(i).getScore()) + "\n\n");
    }
  }
  
  /**
   * @about Function to calculate average F2 out of all of the current F2 scores
   * @param e 
   */
  public void calculateAverageF2(ActionEvent e)
  {
    double total = 0;
    for (int i = 0; i <= f2Scores.size() - 1; i++)
    {
      total += f2Scores.get(i);
    }
    double average = total / f2Scores.size();
    log.appendText("Current Average F2: " + average + "\n\n");
  }
  
  /**
   * @about Function to clear attributes of controller and GUI.
   */
  public void clearAttributes(ActionEvent e)
  {
    // Attributes
    fileCount = 0;
    dirCount = 1;
    indexArray.clear();
    f2Scores.clear();
    
    // GUI
    indexType.setValue("Choose Type to Index");
    indexGrouping.setValue("Choose Index Grouping");
    correctFiles.clear();
    log.clear();
  }
  
  /**
   * @about Starts controller
   * @param url
   * @param rb 
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    indexType.setValue("Choose Type to Index");
    indexGrouping.setValue("Choose Index Grouping");
    log.setEditable(false);
  }  
}
