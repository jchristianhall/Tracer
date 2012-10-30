/**
 * COPYRIGHT (C) 2012 Christian Hall. All Rights Reserved.
 * Main file for software traceability project
 * Initialises GUI and runs functions
 * CSE 4214 | Tracer
 * @author Christian Hall
 * @version 1.0 2.11.12
 */
package tracer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Tracer extends JPanel
                    implements ActionListener 
{
  JComboBox indexerOptions;
  String selectedOption;
  JButton openButton;
  JButton retrieverButton;
  JTextArea log;
  JTextArea correctFiles;
  JFileChooser fileChooser;
  int fileCount = 0, dirCount = 1;
  public ArrayList<Indexer> indexList;
  public ArrayList<String> reqArray;

  /**
   * @about Constructor initialises GUI elements
   */
  public Tracer() 
  {
    super(new BorderLayout());
    indexList = new ArrayList<Indexer>();
    reqArray = new ArrayList<String>();
    
    // Indexer selection
    String[] options = {"Choose Type to Index", "Source Code", "Requirements"};
    indexerOptions = new JComboBox(options);
    indexerOptions.addActionListener(this);

    // Open Button
    openButton = new JButton("Choose Directory to Index");
    openButton.addActionListener(this);
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(indexerOptions);
    buttonPanel.add(openButton);
    
    // Correct files
    JLabel cfLabel = new JLabel("Input Correct File Names");
    JPanel titlePanel = new JPanel();
    titlePanel.add(cfLabel);
    correctFiles = new JTextArea(5,10);
    correctFiles.setMargin(new Insets(5,5,5,5));
    correctFiles.setEditable(true);
    JScrollPane cfScrollPane = new JScrollPane(correctFiles);
    
    // Log
    log = new JTextArea(5,20);
    log.setMargin(new Insets(5,5,5,5));
    log.setEditable(false);
    JScrollPane logScrollPane = new JScrollPane(log);

    // Add to current panel
    add(buttonPanel, BorderLayout.PAGE_START);
    add(titlePanel, BorderLayout.LINE_START);
    add(cfScrollPane, BorderLayout.LINE_END);
    add(logScrollPane, BorderLayout.PAGE_END);
  }

  /**
   * @about Function to create a new frame and add GUI to frame
   */
  private static void buildGUI()
  {
    JFrame frame = new JFrame("Tracer");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Add content to window
    frame.add(new Tracer());

    // Show and position window
    frame.pack();
    Dimension win = frame.getSize();
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    int winWidth = win.width;
    int winHeight = win.height;
    int x = (screen.width / 2) - (winWidth / 2);
    int y = (screen.height / 2) - (winHeight / 2);
    frame.setBounds(x,y,winWidth,winHeight);
    frame.setVisible(true);
  }

  /**
   * @about Override method for reacting to button press, also detects combo box
   * selection and updates attribute variable accordingly.
   * @param e - the action event doing the firing
   */
  @Override
  public void actionPerformed(ActionEvent e) 
  {
    if (e.getSource() == indexerOptions)
    {
      JComboBox box = (JComboBox)e.getSource();
      selectedOption = (String)box.getSelectedItem();
    }
    if (e.getSource() == openButton) 
    {
      // Process for source code directory
      if (selectedOption == "Source Code")
      {
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = fileChooser.showOpenDialog(Tracer.this);
        if (returnValue == JFileChooser.APPROVE_OPTION) 
        {
          // Get directory from fileChooser and make an array of paths
          File file = fileChooser.getSelectedFile();
          File[] fileList = file.listFiles();

          // Go over files and calculate directories, files, and time
          long start = System.nanoTime();
          countFiles(fileList);
          long end = System.nanoTime();
          double duration = (end - start)/1000000000.0;

          // Add results to log and reset counts.
          log.append("Directories found: " + dirCount + "\n");
          log.append("Files indexed: " + fileCount + "\n");
          log.append("Source code indexing took " + duration + " seconds.\n");
          dirCount = 1;
          fileCount = 0;
        }
        else
        {
          log.append("Open cancelled.\n");
        }
      }
      
      // Process for individual requirement file
      else
      {
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnValue = fileChooser.showOpenDialog(Tracer.this);
        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
          File file = fileChooser.getSelectedFile();
          
          // Indexing process
          long start = System.nanoTime();
          Indexer fileToIndex = new Indexer(file, false);
          reqArray.clear();
          reqArray = fileToIndex.getReqArray();
          long end = System.nanoTime();
          double duration = (end - start)/1000000000.0;

          // Add results to log
          log.append("Requirement indexing took " + duration + " seconds.\n");
        }
        else
        {
          log.append("Open cancelled.\n");
        }
      }
      log.setCaretPosition(log.getDocument().getLength());
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
        indexList.add(fileToIndex);
      }
    }
  }

  /**
   * Output file to test array results
   */
  public void writeIndices()
  {
    try
    {
      PrintWriter writer = new PrintWriter("indexedFiles.txt");
      for (Indexer index : indexList)
      {
        writer.println("Path: " + index.getPathName());
        writer.println("Comments: " + index.getCommentArray());
        writer.println("Code: " + index.getCodeArray());
        writer.println();
      }
      writer.close();
    }
    catch (FileNotFoundException e)
    {
      System.out.println(e.getStackTrace());
    }
  }
  
  /**
   * @about Run the GUI
   * @param args - the command line arguments
   */
  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(new Runnable()
    {
        @Override
        public void run()
        {
            UIManager.put("swing.boldMetal", Boolean.FALSE);
            buildGUI();
        }
    });
  }
}