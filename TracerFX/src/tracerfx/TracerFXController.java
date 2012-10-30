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
  @FXML private Button openButton;
  @FXML private TextArea correctFiles;
  @FXML private Button retrieveButton;
  @FXML private TextArea log;
  
  // Logic variables
  int fileCount = 0, dirCount = 1;
  public ArrayList<Indexer> indexArray;
  public Indexer indexedReq;
  
  /**
   * @about Constructor that creates new index and requirements arrays.
   */
  public TracerFXController ()
  {
    indexArray = new ArrayList<Indexer>();
  }
  
  /**
   * @about Function that fires when indexer button is used. Indexes source
   * code and requirements based on value in indexType. Shows results in log.
   * @param e - Any ActionEvent that triggers the function.
   */
  @FXML
  public void indexProcess(ActionEvent e)
  {
    String indexTypeString = (String)indexType.getValue();
    if (indexTypeString == null || 
        indexTypeString.equals("Choose Type to Index"))
    {
      log.appendText("Select a type to index.\n");
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
        log.appendText("Source code indexing took " + duration + " seconds.\n");
        dirCount = 1;
        fileCount = 0;
      }
      else
      {
        log.appendText("Open cancelled.\n");
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
        log.appendText("Requirement indexing took " + duration + " seconds.\n");
      }
      else
      {
        log.appendText("Open cancelled.\n");
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
        Indexer fileToIndex = new Indexer(file, true);
        indexArray.add(fileToIndex);
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
    Object[] correctFileArray = correctFiles.getParagraphs().toArray();
    Retriever retriever;
    
    // Do retriever things here. correctFileArray is what you think it is.
    for (int i = 0; i <= indexArray.size() - 1; i++)
    {
      if (indexArray.get(i).getCommentArray() == null)
      {
        //retriever = new Retriever(indexedReq,indexArray,correctFileArray,false);
      }
      else
      {
        //retriever = new Retriever(indexedReq,indexArray,correctFileArray,true);
      }
      //log.appendText(retriever.getF1() + " " + retriever.getF2() + "\n");
    }
    
    // Testing to see if you're actually pulling in the files; shows in log.
    for (int i = 0; i <= correctFileArray.length - 1; i++)
    {
      log.appendText(correctFileArray[i].toString() + "\n");
    }
  }
  
  /**
   * @about Starts controller
   * @param url
   * @param rb 
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    indexType.setValue("Choose Type to Index");
    log.setEditable(false);
  }  
}