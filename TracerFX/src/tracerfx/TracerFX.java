/**
 * COPYRIGHT (C) 2012 Christian Hall. All Rights Reserved.
 * Main file for software traceability project GUI
 * Initialises GUI
 * CSE 4214 | TracerFX
 * @author Christian Hall
 * @version 1.0 2.11.12
 */
package tracerfx;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TracerFX extends Application
{ 
  private static Stage stage;
  private static TracerFX instance;
  private static DirectoryChooser dChooser;
  private static FileChooser fChooser;
  
  /**
   * @about Basic constructor and getter for overall GUI
   */
  public TracerFX()
  {
    instance = this;
  }
  public static TracerFX getInstance()
  {
    return instance;
  }
  
  /**
   * @about Creates a new DirectoryChooser to go through source code
   * @return fileList - A list of files and directories from the selected directory
   */
  public static File[] makeDirectoryChooser()
  {
    dChooser = new DirectoryChooser();
    File file = dChooser.showDialog(stage);
    File[] fileList = file.listFiles();
    return fileList;
  }
  
  /**
   * @about Creates a new DirectoryChooser to go through source code
   * @return file - requirement file to be indexed
   */
  public static File makeFileChooser()
  {
    fChooser = new FileChooser();
    File file = fChooser.showOpenDialog(stage);
    return file;
  }
  
  /**
   * @about Function to initialise GUI
   * @param stage - Stage to populate with items
   * @throws Exception - Occurs for any exceptions that get thrown
   */
  @Override
  public void start(Stage stage) throws Exception
  {
    try 
    {
      Parent root = FXMLLoader.load(getClass().getResource("TracerFX.fxml"));
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();       
    } 
    catch (Exception ex)
    {
      Logger.getLogger(TracerFX.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  /**
   * The main() method is ignored in correctly deployed JavaFX application.
   * main() serves only as fallback in case the application can not be launched
   * through deployment artifacts, e.g., in IDEs with limited FX support.
   * NetBeans ignores main().
   *
   * @param args the command line arguments
   */
  public static void main(String[] args)
  {
    launch(args);
  }
}