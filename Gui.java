import java.io.*;
//Imports JavaFX, the ibrary used for the gui.
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeView;
import javafx.geometry.Insets;

public class Gui extends Application {
// sets a stage
   Stage window;
//For user input on whether to harden
   boolean harden = false;
//Imports WriteSettings as a constructor
   static WriteSettings printToJson = new WriteSettings();
   boolean[] Networking = {false, false, false};
   boolean[] LocalSecPol = {false, false, false};
   boolean[] Lusrmgr = {false, false, false};
   boolean[] Services = {false, false, false};
   boolean[] CyPat = {false, false, false};
   boolean[][] arrays = new boolean[][] {Networking, LocalSecPol, Lusrmgr, Services, CyPat};
   //Names of the Files that will be printed. Written In same order as the arrays array.
   String[] settingNames = {"Networking", "LocalSecPol", "Lusrmgr", "Services", "CyPat"};
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
      Scene homePage, setting, progressBar;
   // Beginning of GUI building code:
      primaryStage.setTitle("Vector Shield");
      window = primaryStage;
      window.setOnCloseRequest(e -> {
      e.consume();
      exitProgram();
      });
      //For homepage
      Label welcome = new Label("Welcome to vector shield! The system hardening application for windows 10.");  
      Button hardenSyst = new Button("Harden my system!(creates .json files for now)");
      Button goToSettings = new Button("Advanced settings");
      Button low = new Button("Low setting");
      Button medium = new Button("Medium setting");
      Button high = new Button("High setting");  
      Button exitProgram = new Button("Exit");       
      //For settings
      Label settingsDesc = new Label("Here are the advanced settings. Modify only if you know what your doing.");
      StackPane.setAlignment(settingsDesc, Pos.TOP_CENTER);
      Button goToHome = new Button("Go back to home");
      StackPane.setAlignment(goToHome, Pos.TOP_LEFT);
      
      CheckBox example = new CheckBox("Networking, position 1.");//Check boxes can be set on action the same way a button is.
      
      ListView<CheckBox> NetworkingOptions = new ListView<>();
         NetworkingOptions.getItems().addAll(example);
      ListView<CheckBox> LocalSecPolOptions = new ListView<>();
         LocalSecPolOptions.getItems().addAll();
      ListView<CheckBox> LusrmgrOptions = new ListView<>();
         LusrmgrOptions.getItems().addAll();
      ListView<CheckBox> ServicesOptions = new ListView<>();
         ServicesOptions.getItems().addAll();
      //For Progress bar
      Label description = new Label("(Insert Progress Bar here)");
    
      // Sets layouts
      //Home layouts
      VBox homeLayoutTop = new VBox(10);
      homeLayoutTop.setAlignment(Pos.CENTER);
      HBox homeLayoutMid = new HBox(50);
      homeLayoutMid.setAlignment(Pos.CENTER);
      VBox homeLayoutBottom = new VBox(390);
      homeLayoutBottom.setAlignment(Pos.CENTER);
      //Settings layouts
      StackPane settingLayout = new StackPane();
      HBox settingBoxesLayout = new HBox();
      settingBoxesLayout.setAlignment(Pos.CENTER);
      settingBoxesLayout.setPadding(new Insets(100, 40, 40, 40));
      //Progress bar layouts
      StackPane progressLayout = new StackPane();
      
      // Adds to layouts
      //Home
      homeLayoutTop.getChildren().addAll(welcome, goToSettings);
      homeLayoutMid.getChildren().addAll(low, medium, high);
      homeLayoutBottom.getChildren().addAll(hardenSyst, exitProgram);
            BorderPane homeLayout = new BorderPane();
      homeLayout.setTop(homeLayoutTop);
      homeLayout.setCenter(homeLayoutMid);
      homeLayout.setBottom(homeLayoutBottom);
      //Settings
      settingBoxesLayout.getChildren().addAll(NetworkingOptions, LocalSecPolOptions, LusrmgrOptions, ServicesOptions);
      settingLayout.getChildren().addAll(settingsDesc, settingBoxesLayout, goToHome);
      //ProgressBar
      progressLayout.getChildren().addAll(description);
      
      // Sets the scenes
      setting = new Scene(settingLayout, 1200, 800);
      homePage = new Scene(homeLayout, 1200, 800);
      progressBar = new Scene(progressLayout, 1200, 800);
       // Sets the actions when button is pressed
      hardenSyst.setOnAction(
         e -> {
            harden = AlertBox.display("Warning!", "VectorShield makes changes that have to be manually reversed! Make sure you've set the proper settings before proceeding", "Harden my system", "Exit");
            if (harden == true) {
//IMPORTANT: Handles checkboxes, modifying the array based on their state.
    CheckBox[] NetworkBoxes = {example};
    CheckBox[] LocalSecPolBoxes = {};
    CheckBox[] LusrmgrBoxes = {};
    CheckBox[] ServiceBoxes = {};
    CheckBox[] CyPatBoxes = {};
    handleOptions(NetworkBoxes, LocalSecPolBoxes, LusrmgrBoxes, ServiceBoxes, CyPatBoxes);
               
               window.setScene(progressBar);
            }
         }
         );
      goToSettings.setOnAction(e -> window.setScene(setting));
      goToHome.setOnAction(e -> window.setScene(homePage));
      exitProgram.setOnAction(e -> exitProgram());
      
      //Sets primary stage to home page
      window.setScene(homePage);
      primaryStage.show();
   }
               //Generic action statement
   public void handle(ActionEvent event) {
      printToJson.writeSettings(arrays, settingNames);
   }
   //Actions taken wwhen exiting program
   private void exitProgram() {
      //Add anything neccesary here:
      boolean exit = AlertBox.display("Confirm", "Are you sure you wish to exit?", "Yes", "No");
      if (exit == true) {
      window.close();
      }
   }
   //Handling the user options when hardening.
      public void handleOptions(CheckBox[] networking, CheckBox[] localSecPol, CheckBox[] lusrmgr, CheckBox[] services, CheckBox[] cyPat) {
   //Networking checking
      for (int x = 0; x<networking.length; x++) {
   if (networking[x].isSelected()) {
   Networking[x]=true;
    }
   }
  //LocalSecPol checking
        for (int x = 0; x<localSecPol.length; x++) {
   if (localSecPol[x].isSelected()) {
   LocalSecPol[x]=true;
    }
   }
  //Lusrmgr checking
        for (int x = 0; x<lusrmgr.length; x++) {
   if (lusrmgr[x].isSelected()) {
   Lusrmgr[x]=true;
    }
   }
  //Services checking
        for (int x = 0; x<services.length; x++) {
   if (services[x].isSelected()) {
   Services[x]=true;
    }
   }
  //CyberPatriots checking
        for (int x = 0; x<cyPat.length; x++) {
   if (cyPat[x].isSelected()) {
   CyPat[x]=true;
    }
   }

   //writing .json files
   printToJson.writeSettings(arrays, settingNames);
   }
}