import java.io.*;
//Imports JavaFX, the ibrary used for the gui.
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
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
import javafx.scene.control.TreeItem;
import javafx.geometry.Insets;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
      Label description = new Label("Choose a settings preset to get started, then click harden. Or, you can use advanced settings to choose specifics.");
      Label boost = new Label("Ver 1.0. VectorShield is a free non-profit software made for public use.");
      welcome.getStyleClass().add("labels");
      description.getStyleClass().add("labels");
      Button hardenSyst = new Button("Harden my system");
      Image imagelow = new Image(new File("Images/low.png").toURI().toString(), 150, 150, true, true);
      Image imageMid = new Image(new File("Images/mid.png").toURI().toString(), 150, 150, true, true);
      Image imageHigh = new Image(new File("Images/high.png").toURI().toString(), 150, 150, true, true);
      Button low = new Button ();
      low.setGraphic(new ImageView(imagelow));
      low.getStyleClass().add("buttonSpecial");
      Button medium = new Button ();
      medium.setGraphic(new ImageView(imageMid));
      medium.getStyleClass().add("buttonSpecial");
      Button high = new Button ();
      high.setGraphic(new ImageView(imageHigh));
      high.getStyleClass().add("buttonSpecial");

      MenuBar homePageMenu = new MenuBar();
      Menu viewMenu = new Menu("_View");
      Menu optionMenu = new Menu("_Options");

      MenuItem goToHome = new MenuItem("Go To Home");
      goToHome.setDisable(true);
      MenuItem goToSettings = new MenuItem("Advanced settings");
      MenuItem exitProgram = new MenuItem("Exit");
      CheckMenuItem saveSettings = new CheckMenuItem("Save setting choices");
      CheckMenuItem cyberPatriotView = new CheckMenuItem("Enable Secret Settings...");

      viewMenu.getItems().add(goToHome);
      viewMenu.getItems().add(goToSettings);
      viewMenu.getItems().add(new SeparatorMenuItem());
      viewMenu.getItems().add(exitProgram);
      optionMenu.getItems().add(saveSettings);
      optionMenu.getItems().add(cyberPatriotView);

      homePageMenu.getMenus().addAll(viewMenu, optionMenu);
      //For settings
      Label settingsDesc = new Label("Here are the advanced settings. Modify only if you know what your doing.");
      StackPane.setAlignment(settingsDesc, Pos.TOP_CENTER);

        CheckBox Netroot = new CheckBox("Networking");//Check boxes can be set on action the same way a button is.
          CheckBox Netgeneric = new CheckBox("Generic");
            CheckBox example = new CheckBox("Networking, position 1.");
            CheckBox[] NetgenericBoxes = {example};

        CheckBox cyPat1 = new CheckBox("Change user passwords to Password.json");
        CheckBox cyPat2 = new CheckBox("Delete all .mp3 files");
        CheckBox cyPat3 = new CheckBox("Delete hacking related files");

      ListView<CheckBox> secretOptions = new ListView<>();
      secretOptions.getItems().addAll(cyPat1, cyPat2, cyPat3);

      TreeItem<CheckBox> NetworkingRoot = new TreeItem<>(Netroot);
      NetworkingRoot.setExpanded(true);
         TreeItem<CheckBox> NetworkingGeneric = new TreeItem<>(Netgeneric);
         NetworkingRoot.getChildren().add(NetworkingGeneric);
         NetworkingGeneric.setExpanded(true);
          for (int x = 0; x<NetgenericBoxes.length; x++) {
            TreeItem<CheckBox> item = new TreeItem<>(NetgenericBoxes[x]);
            NetworkingGeneric.getChildren().add(item);
              }

      TreeItem<CheckBox> LocalSecPolRoot = new TreeItem<>();
      LocalSecPolRoot.setExpanded(true);
         TreeItem<CheckBox> LocalSecPolGeneric = new TreeItem<>();
         LocalSecPolRoot.getChildren().add(LocalSecPolGeneric);
         LocalSecPolGeneric.setExpanded(true);

      TreeItem<CheckBox> LusrmgrRoot = new TreeItem<>();
      LusrmgrRoot.setExpanded(true);
         TreeItem<CheckBox> LusrmgrGeneric = new TreeItem<>();
         LusrmgrRoot.getChildren().add(LusrmgrGeneric);
         LusrmgrGeneric.setExpanded(true);

      TreeItem<CheckBox> ServicesRoot = new TreeItem<>();
      ServicesRoot.setExpanded(true);
         TreeItem<CheckBox> ServicesGeneric = new TreeItem<>();
         ServicesRoot.getChildren().add(ServicesGeneric);
         ServicesGeneric.setExpanded(true);
      //For Progress bar
      Label ProgressDescription = new Label("(Insert Progress Bar here)");

      // Sets layouts
      BorderPane MainLayout = new BorderPane();
      VBox LayoutTop = new VBox();
      //Home layouts
      BorderPane homeLayout = new BorderPane();

      VBox homeLayoutTop = new VBox();
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

      TreeView<CheckBox> NetworkingOptions = new TreeView<>(NetworkingRoot);
      TreeView<CheckBox> LocalSecPolOptions = new TreeView<>(LocalSecPolRoot);
      TreeView<CheckBox> LusrmgrOptions = new TreeView<>(LusrmgrRoot);
      TreeView<CheckBox> ServicesOptions = new TreeView<>(ServicesRoot);
      //Progress bar layouts
      StackPane progressLayout = new StackPane();

      // Adds to layouts
      LayoutTop.getChildren().addAll(homePageMenu);
      MainLayout.setTop(LayoutTop);
      MainLayout.setCenter(homeLayout);
      //Home
      homeLayoutTop.getChildren().addAll(welcome, description);
      homeLayoutMid.getChildren().addAll(low, medium, high);
      homeLayoutBottom.getChildren().addAll(hardenSyst, boost);

      homeLayout.setTop(homeLayoutTop);
      homeLayout.setCenter(homeLayoutMid);
      homeLayout.setBottom(homeLayoutBottom);
      //Settings
      settingBoxesLayout.getChildren().addAll(NetworkingOptions, LocalSecPolOptions, LusrmgrOptions, ServicesOptions);
      settingLayout.getChildren().addAll(settingsDesc, settingBoxesLayout);
      //ProgressBar
      progressLayout.getChildren().addAll(ProgressDescription);

      // Sets the scenes
      homePage = new Scene(MainLayout, 1200, 800);
      homePage.getStylesheets().add("homeTheme.css");
      progressBar = new Scene(progressLayout, 1200, 800);
       // Sets the actions when button is pressed
      hardenSyst.setOnAction(
         e -> {
            harden = AlertBox.display("Warning!", "Changes have to be manually reversed! Check settings before proceeding", "Harden my system", "Cancel");
            if (harden == true) {//IMPORTANT: Handles checkboxes, modifying the array based on their state.
             CheckBox[] NetworkBoxes = {example};
             CheckBox[] LocalSecPolBoxes = {};
             CheckBox[] LusrmgrBoxes = {};
             CheckBox[] ServiceBoxes = {};
             CheckBox[] CyPatBoxes = {cyPat1, cyPat2, cyPat3};
                handleOptions(NetworkBoxes, LocalSecPolBoxes, LusrmgrBoxes, ServiceBoxes, CyPatBoxes);
                window.setScene(progressBar);
            }
           }
          );
       goToSettings.setOnAction(e -> {
      MainLayout.setCenter(settingLayout);
      goToHome.setDisable(false);
      goToSettings.setDisable(true);
       });
        goToHome.setOnAction(e -> {
      MainLayout.setCenter(homeLayout);
      goToHome.setDisable(true);
      goToSettings.setDisable(false);
       });
       cyberPatriotView.setOnAction(e -> {
         if(cyberPatriotView.isSelected()) {
         boolean choice = AlertBox.display("WARNING!", "Secret settings can cuase SERIOUS IRREVERSIBLE damage, this option is for specifc cases only.", "I know what im doing", "Cancel");
         if (choice) {
         settingBoxesLayout.getChildren().add(secretOptions);
         } else {cyberPatriotView.setSelected(false);}
         } else { settingBoxesLayout.getChildren().remove(secretOptions);}
      });
      exitProgram.setOnAction(e -> exitProgram());
            low.setOnAction(e -> {

      });
            medium.setOnAction(e -> {

      });
            high.setOnAction(e -> {

      });
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

   //writing .json files---
   printToJson.writeSettings(arrays, settingNames);
   }
}
