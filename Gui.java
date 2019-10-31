import java.io.*;
//Imports JavaFX, the ibrary used for the gui.
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class Gui extends Application {
   Stage window;
//Imports WriteSettings as a constructor
   static WriteSettings printToJson = new WriteSettings();
   boolean[] settings1 = {false, false, false};
   boolean[] settings2 = {false, false, false};
   boolean[] settings3 = {true, true, false};
   boolean[][] arrays = new boolean[][] { settings1, settings2, settings3 };
   //Names of the Files that will be printed. Written In same order as the arrays array.
   String[] settingNames = {"settings1", "settings2", "settings3" };
   //Runs the method to write setting values to Json files
   //printToJson.writeSettings(arrays, settingNames);
//Beginning of script
   public static void main(String[] args) throws IOException { 
   // GUI Start
      launch(args);
   //Any code to be run after GUI is closed:
   
   }
   @Override
   //Gui Code:
    public void start(Stage primaryStage) throws Exception {
   
   // Beginning of GUI building code:
      primaryStage.setTitle("Vector Shield");
      window = primaryStage;
      //Creates a button called 'Create Settings Json files'
      Button btn = new Button();
      btn.setText("Create Settings Json files");
      
      // Sets the actions when button is pressed
      btn.setOnAction(
         e -> {printToJson.writeSettings(arrays, settingNames);//Can be called 'this::handle' for the set generic action
         });
     
      // Sets layout for Home page
      StackPane homeLayout = new StackPane();
       // Adds btn to layout
      homeLayout.getChildren().addAll(btn);
      // Sets the scene size
      Scene homePage = new Scene(homeLayout, 1200, 800);
      //Sets primary stage to home page
      window.setScene(homePage);
      primaryStage.show();
   }
               //Generic action statement
   public void handle(ActionEvent event) {
      printToJson.writeSettings(arrays, settingNames);
   }
   
}