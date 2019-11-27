import java.io.*;
//Imports JavaFX, the ibrary used for the gui.
import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
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
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Toggle;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Duration;
import javafx.animation.RotateTransition; 
import javafx.animation.Interpolator;
import java.util.concurrent.TimeUnit;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;

public class Gui extends Application {
// sets a stage
   Stage window;
//For user input on whether to harden
   boolean harden = false;
//Imports WriteSettings as a constructor
   static WriteSettings printToJson = new WriteSettings();
   boolean[] Networking = {false, false, false, false, false, false, false, false, false, false, false, false, false};
   boolean[] LocalSecPol = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
   boolean[] Lusrmgr = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
   boolean[] Services = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
   boolean[] CyPat = {false, false, false, false, false};
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
      Label welcome = new Label("Welcome to VectorShield! The system hardening application for Windows 10.");
      Label description = new Label("Choose a setting preset to get started, then click harden. Or, you can set your own settings in advanced settings for customization.");
      Label warning = new Label("NOTE: Choosing a preset will overwrite custom settings.");
      Label boost = new Label("Ver 1.0. VectorShield is a free non-profit software made for public use.");
      welcome.getStyleClass().add("labels");
      description.getStyleClass().add("labels");
      warning.getStyleClass().add("labels");
      Button hardenSyst = new Button("Secure my system");
      hardenSyst.setGraphic(new ImageView(new Image(new File("Images/buttonIcon.png").toURI().toString(), 37, 42, true, true)));
      
      String Description1 = "Low option: Strongly recommened for personal users, and is the safest option out of the three. Will set settings to include the following actions: (INSERT ACTIONS). Possible effects include: (INSERT SIDE EFFECTS).";
      String Description2 = "Medium option: Recommended for enterprise business environment. Acts as a compromise between security and usability. Will set settings to include the following actions: (INSERT ACTIONS). Possible effects include: (INSERT SIDE EFFECTS).";
      String Description3 = "High option: Reserved for high security environments, impacts computer usability severly. Will set settings to include the following actions: (INSERT ACTIONS). Possible effects include: (INSERT SIDE EFFECTS).";

      Label lowDesc = new Label (Description1);
      lowDesc.setMaxWidth(360);
      lowDesc.setMinHeight(200);
      lowDesc.setWrapText(true);
      lowDesc.getStyleClass().add("labels");
      Label midDesc = new Label (Description2);
      midDesc.setMaxWidth(360);
      midDesc.setMinHeight(200);
      midDesc.setWrapText(true);
      midDesc.getStyleClass().add("labels");
      Label highDesc = new Label (Description3);
      highDesc.setMaxWidth(360);
      highDesc.setMinHeight(200);
      highDesc.setWrapText(true);
      highDesc.getStyleClass().add("labels");
      
                     //Images Homepage
      FileInputStream logo = new FileInputStream("Images/VectorShield.png"); 
      Image imagelow = new Image(new File("Images/low.png").toURI().toString(), 150, 150, true, true);
      Image imageMid = new Image(new File("Images/mid.png").toURI().toString(), 150, 150, true, true);
      Image imageHigh = new Image(new File("Images/high.png").toURI().toString(), 150, 150, true, true);
      Image logoImage = new Image(logo);
      ImageView vectorShieldLogo = new ImageView(logoImage);
       vectorShieldLogo.setX(0); 
       vectorShieldLogo.setY(0); 
       vectorShieldLogo.setFitHeight(200); 
       vectorShieldLogo.setFitWidth(250); 
       vectorShieldLogo.setPreserveRatio(true);
      
      final ToggleGroup presetButtons = new ToggleGroup();
      ToggleButton low = new ToggleButton ();
      low.setToggleGroup(presetButtons);
      low.setGraphic(new ImageView(imagelow));
      low.getStyleClass().add("buttonSpecial");
      low.setUserData("low");
      ToggleButton medium = new ToggleButton ();
      medium.setGraphic(new ImageView(imageMid));
      medium.getStyleClass().add("buttonSpecial");
      medium.setUserData("mid");
      medium.setToggleGroup(presetButtons);
      ToggleButton high = new ToggleButton ();
      high.setGraphic(new ImageView(imageHigh));
      high.getStyleClass().add("buttonSpecial");
      high.setUserData("high");
      high.setToggleGroup(presetButtons);

      MenuBar homePageMenu = new MenuBar();
      Menu viewMenu = new Menu("_View");
      Menu optionMenu = new Menu("_Options");

      MenuItem goToHome = new MenuItem("Go To Home");
      goToHome.setDisable(true);
      MenuItem goToSettings = new MenuItem("Advanced settings");
      MenuItem exitProgram = new MenuItem("Exit");
      CheckMenuItem cyberPatriotView = new CheckMenuItem("Enable Secret Settings...");

      viewMenu.getItems().add(goToHome);
      viewMenu.getItems().add(goToSettings);
      viewMenu.getItems().add(new SeparatorMenuItem());
      viewMenu.getItems().add(exitProgram);
      optionMenu.getItems().add(cyberPatriotView);

      homePageMenu.getMenus().addAll(viewMenu, optionMenu);
      //For settings
      Label settingsDesc = new Label("Here are the advanced settings. Modify to customize which actions you wish VectorShield to take when securing.");
      settingsDesc.setMaxWidth(650);
      settingsDesc.setMinHeight(110);
      settingsDesc.setWrapText(true);
      settingsDesc.getStyleClass().add("labels");
      
                           //Setting Menu Buttons
      final ToggleGroup SettingsButtons = new ToggleGroup();
      
      ToggleButton SecpolSettings = new ToggleButton("Local Security Policy");
      SecpolSettings.getStyleClass().add("buttonMenu");
      SecpolSettings.setUserData("secpol");
      SecpolSettings.setToggleGroup(SettingsButtons);
         ToggleButton NetworkingSettings = new ToggleButton("Networking");
         NetworkingSettings.getStyleClass().add("buttonMenu");
         NetworkingSettings.setUserData("networking");
         NetworkingSettings.setSelected(true);
         NetworkingSettings.setToggleGroup(SettingsButtons);
      ToggleButton LusrmgrSettings = new ToggleButton("Users & Groups");
      LusrmgrSettings.getStyleClass().add("buttonMenu");
      LusrmgrSettings.setUserData("lusrmgr");
      LusrmgrSettings.setToggleGroup(SettingsButtons);
         ToggleButton ServicesSettings = new ToggleButton("Services");
         ServicesSettings.getStyleClass().add("buttonMenu");
         ServicesSettings.setUserData("services");
         ServicesSettings.setToggleGroup(SettingsButtons);
      ToggleButton SecretSettings = new ToggleButton("Secret Settings");
      SecretSettings.getStyleClass().add("buttonMenu");
      SecretSettings.setUserData("cypat");
      SecretSettings.setToggleGroup(SettingsButtons);
                           //Images for settings  
      ImageView settingsLogo = new ImageView(new Image(getClass().getResourceAsStream("Images/gear.png")));
       settingsLogo.setX(0); 
       settingsLogo.setY(0); 
       settingsLogo.setFitHeight(130); 
       settingsLogo.setFitWidth(130); 
       settingsLogo.setPreserveRatio(true); 
         
               //Networking Checkboxes
        CheckBox Netroot = new CheckBox("Networking");//Check boxes can be set on action the same way a button is.
          CheckBox NetSecurity = new CheckBox("Network Security");
            CheckBox N0 = new CheckBox("Enable 'Allow Local System to use computer identity for NTLM'");
            CheckBox N1 = new CheckBox("Disable 'Allow LocalSystem NULL session fallback'");
            CheckBox N2 = new CheckBox("Disable 'Allow PKU2U authentication requests to this computer to use online identities'");
            CheckBox N3 = new CheckBox("Set 'Configure encryption types allowed for Kerberos' to 'AES128_HMAC_SHA1, AES256_HMAC_SHA1, Future encryption types'");
            CheckBox N4 = new CheckBox("Enable 'Do not store LAN Manager hash value on next password change'");
            CheckBox N5 = new CheckBox("Enable 'Force logoff when logon hours expire'");
            CheckBox N6 = new CheckBox("'LAN Manager authentication level' is set to 'Send NTLMv2 response only. Refuse LM & NTLM'");
            CheckBox N7 = new CheckBox("'LDAP client signing requirements' is set to 'Negotiate signing'");
            CheckBox N8 = new CheckBox("Require NTLMv2 session security & Require 128-bit encryption for NTLM SSP based servers & clients.");
            CheckBox[] NetSecurityBoxes = {N0, N1, N2, N3, N4, N5, N6, N7, N8};
          CheckBox NetProtocol = new CheckBox("Network Protocols");
            CheckBox N9 = new CheckBox("Disable Telnet protocol");
            CheckBox N10 = new CheckBox("Disable Trivial File Transfer Protocol");
            CheckBox N11 = new CheckBox("Disable Fax & Scan services & protocols");
            CheckBox N12 = new CheckBox("Disable IPv6 networking protocol");
            CheckBox[] NetProtocolBoxes = {N9, N10, N11, N12};
         CheckBox[] NetworkBoxes1 = {NetSecurity, N0, N1, N2, N3, N4, N5, N6, N7, N8, NetProtocol , N9, N10, N11, N12};
      
      TreeItem<CheckBox> NetworkingRoot = new TreeItem<>(Netroot);
      NetworkingRoot.setExpanded(true);
         TreeItem<CheckBox> NetworkingSecurity = new TreeItem<>(NetSecurity);
         NetworkingRoot.getChildren().add(NetworkingSecurity);
         NetworkingSecurity.setExpanded(true);
          for (int x = 0; x<NetSecurityBoxes.length; x++) {
            TreeItem<CheckBox> item = new TreeItem<>(NetSecurityBoxes[x]);
            NetworkingSecurity.getChildren().add(item);
              }
         TreeItem<CheckBox> NetworkingProtocol = new TreeItem<>(NetProtocol);
         NetworkingRoot.getChildren().add(NetworkingProtocol);
         NetworkingProtocol.setExpanded(true);
          for (int x = 0; x<NetProtocolBoxes.length; x++) {
            TreeItem<CheckBox> item = new TreeItem<>(NetProtocolBoxes[x]);
            NetworkingProtocol.getChildren().add(item);
              }
              
       Netroot.setOnAction(e -> {
       if (Netroot.isSelected()) {
                 for (int x = 0; x<NetworkBoxes1.length; x++) {
            NetworkBoxes1[x].setSelected(true); 
              }
            } else if (!Netroot.isSelected()) {
            for (int x = 0; x<NetworkBoxes1.length; x++) {
            NetworkBoxes1[x].setSelected(false); 
              }
            }
       });
       NetSecurity.setOnAction(e -> {
       if (NetSecurity.isSelected()) {
                 for (int x = 0; x<NetSecurityBoxes.length; x++) {
            NetSecurityBoxes[x].setSelected(true); 
              }
            } else if (!NetSecurity.isSelected()) {
            for (int x = 0; x<NetSecurityBoxes.length; x++) {
            NetSecurityBoxes[x].setSelected(false); 
              }
            }
       });
       NetProtocol.setOnAction(e -> {
       if (NetProtocol.isSelected()) {
                 for (int x = 0; x<NetProtocolBoxes.length; x++) {
            NetProtocolBoxes[x].setSelected(true); 
              }
            } else if (!NetProtocol.isSelected()) {
            for (int x = 0; x<NetProtocolBoxes.length; x++) {
            NetProtocolBoxes[x].setSelected(false); 
              }
            }
       });
         
         
                        //Local Security Policy Checkboxes
        CheckBox SecpolRoot = new CheckBox("Local Security Policy");
          CheckBox SecpolSecurity = new CheckBox("General Security");
            CheckBox S0 = new CheckBox("Set Windows Firewall As Enabled");
            CheckBox S1 = new CheckBox("Enable full Auditing for success and failures");
            CheckBox S2 = new CheckBox("Block Microsoft accounts from signing in");
            CheckBox S3 = new CheckBox("Additional restrictions for anonymous connections 'Do not allow enumeration of SAM accounts and shares'");
            CheckBox S4 = new CheckBox("Enable 'Digitally sign server communication (when possible)'");
            CheckBox S5 = new CheckBox("Enable 'Do not display last username in logon screen'");
            CheckBox S7 = new CheckBox("'Number of previous logons to cache' set to '0 logons'");
            CheckBox[] SecpolSecurityBoxes = {S0, S1, S2, S3, S4, S5, S7};
          CheckBox SecpolMedia = new CheckBox("Media Restrictions");
            CheckBox S8 = new CheckBox("Enable 'Restrict CD-ROM/floppy access to locally logged on user'");
            CheckBox S9 = new CheckBox("set 'Smart card removal behavior' as 'Lock Workstation'");
            CheckBox S10 = new CheckBox("set 'Unsigned driver/non-driver installation behavior' as 'Warn'");
            CheckBox[] SecpolMediaBoxes = {S8, S9, S10};
          CheckBox SecpolClient = new CheckBox("Client & User Security");
            CheckBox S6 = new CheckBox("'LAN Manager authentication level' Send LM & NTLM - use NTLMv2 if negotiated");
            CheckBox S11 = new CheckBox("Enable 'Clear virtual memory pagefile'");
            CheckBox S12 = new CheckBox("Enable 'RDP network level authentication Enabled'");
            CheckBox S13 = new CheckBox("Enable 'Limit local use of blank passwords to local console only'");
            CheckBox S14 = new CheckBox("Enable 'Updates for other microsoft products'");
            CheckBox S15 = new CheckBox("Disable Remote Services");
            CheckBox S20 = new CheckBox("Network security: Enable 'Do not store LAN Manager hash value on next password change'");
            CheckBox S21 = new CheckBox("Microsoft network client: Disable 'Send unencrypted password to third-party SMB servers'");
            CheckBox[] SecpolClientBoxes = {S6, S11, S12, S13, S14, S15, S20, S21};
          CheckBox SecpolUser = new CheckBox("User restrictions");
            CheckBox S16 = new CheckBox("Admin Services: Enforce Control-Alt-Delete");
            CheckBox S17 = new CheckBox("Enable 'Restrict anonymous access to Named Pipes and Shares'");
            CheckBox S18 = new CheckBox("User Account Control: Enforce secure desktop");
            CheckBox S19 = new CheckBox("Recovery console: Disable 'Allow automatic administrative logon'");
            CheckBox S22 = new CheckBox("Devices: Enable 'Prevent users from installing printer drivers'");
            CheckBox[] SecpolUserBoxes = {S16, S17, S18, S19, S22};
               CheckBox[] SecpolBoxes1 = {SecpolSecurity, S0, S1, S2, S3, S4, S5, S6, S7, S8, SecpolMedia, S9, S10, S11, S12, SecpolClient, S13, S14, S15, S16, S17, SecpolUser, S18, S19, S20, S21, S22};
      TreeItem<CheckBox> LocalSecPolRoot = new TreeItem<>(SecpolRoot);
      LocalSecPolRoot.setExpanded(true);
         TreeItem<CheckBox> LocalSecPolSecurity = new TreeItem<>(SecpolSecurity);
         LocalSecPolRoot.getChildren().add(LocalSecPolSecurity);
         LocalSecPolSecurity.setExpanded(true);
              for (int x = 0; x<SecpolSecurityBoxes.length; x++) {
               TreeItem<CheckBox> item = new TreeItem<>(SecpolSecurityBoxes[x]);
                  LocalSecPolSecurity.getChildren().add(item);
                   }
         TreeItem<CheckBox> LocalSecPolMedia = new TreeItem<>(SecpolMedia);
         LocalSecPolRoot.getChildren().add(LocalSecPolMedia);
         LocalSecPolMedia.setExpanded(true);
              for (int x = 0; x<SecpolMediaBoxes.length; x++) {
               TreeItem<CheckBox> item = new TreeItem<>(SecpolMediaBoxes[x]);
                  LocalSecPolMedia.getChildren().add(item);
                   } 
         TreeItem<CheckBox> LocalSecPolClient = new TreeItem<>(SecpolClient);
         LocalSecPolRoot.getChildren().add(LocalSecPolClient);
         LocalSecPolClient.setExpanded(true);
              for (int x = 0; x<SecpolClientBoxes.length; x++) {
               TreeItem<CheckBox> item = new TreeItem<>(SecpolClientBoxes[x]);
                  LocalSecPolClient.getChildren().add(item);
                   }       
         TreeItem<CheckBox> LocalSecPolUser = new TreeItem<>(SecpolUser);
         LocalSecPolRoot.getChildren().add(LocalSecPolUser);
         LocalSecPolUser.setExpanded(true);
              for (int x = 0; x<SecpolUserBoxes.length; x++) {
               TreeItem<CheckBox> item = new TreeItem<>(SecpolUserBoxes[x]);
                  LocalSecPolUser.getChildren().add(item);
                   }
                
                SecpolRoot.setOnAction(e -> {
       if (SecpolRoot.isSelected()) {
                 for (int x = 0; x<SecpolBoxes1.length; x++) {
            SecpolBoxes1[x].setSelected(true); 
              }
            } else if (!SecpolRoot.isSelected()) {
            for (int x = 0; x<SecpolBoxes1.length; x++) {
            SecpolBoxes1[x].setSelected(false); 
              }
            }
       });
       SecpolSecurity.setOnAction(e -> {
       if (SecpolSecurity.isSelected()) {
                 for (int x = 0; x<SecpolSecurityBoxes.length; x++) {
            SecpolSecurityBoxes[x].setSelected(true); 
              }
            } else if (!SecpolSecurity.isSelected()) {
            for (int x = 0; x<SecpolSecurityBoxes.length; x++) {
            SecpolSecurityBoxes[x].setSelected(false); 
              }
            }
       });
       SecpolMedia.setOnAction(e -> {
       if (SecpolMedia.isSelected()) {
                 for (int x = 0; x<SecpolMediaBoxes.length; x++) {
            SecpolMediaBoxes[x].setSelected(true); 
              }
            } else if (!SecpolMedia.isSelected()) {
            for (int x = 0; x<SecpolMediaBoxes.length; x++) {
            SecpolMediaBoxes[x].setSelected(false); 
              }
            }
       });
       SecpolClient.setOnAction(e -> {
       if (SecpolClient.isSelected()) {
                 for (int x = 0; x<SecpolClientBoxes.length; x++) {
            SecpolClientBoxes[x].setSelected(true); 
              }
            } else if (!SecpolClient.isSelected()) {
            for (int x = 0; x<SecpolClientBoxes.length; x++) {
            SecpolClientBoxes[x].setSelected(false); 
              }
            }
       });
       SecpolUser.setOnAction(e -> {
       if (SecpolUser.isSelected()) {
                 for (int x = 0; x<SecpolUserBoxes.length; x++) {
            SecpolUserBoxes[x].setSelected(true); 
              }
            } else if (!SecpolUser.isSelected()) {
            for (int x = 0; x<SecpolUserBoxes.length; x++) {
            SecpolUserBoxes[x].setSelected(false); 
              }
            }
       });
       
      
      //Local User Manager Checkboxes
        CheckBox LusrmgrRoot = new CheckBox("Local Users & Groups");
          CheckBox LusrmgrAccount = new CheckBox("Account Disabling");
            CheckBox L0 = new CheckBox("Disable Defualt Admin Account");
            CheckBox L1 = new CheckBox("Disable Guest Account");
            CheckBox[] LusrmgrAccountBoxes = {L0, L1};
          CheckBox LusrmgrAccess = new CheckBox("Computer Access");
            CheckBox L2 = new CheckBox("For 'Access Credential Manager as a trusted caller' allow 'No One'");
            CheckBox L3 = new CheckBox("For 'Access this computer from the network' allow 'Administrators, Remote Desktop Users'");
            CheckBox L4 = new CheckBox("For 'Act as part of the operating system' allow 'No One'");
            CheckBox[] LusrmgrAccessBoxes = {L2, L3, L4};
          CheckBox LusrmgrAction = new CheckBox("General Action Restrictions");
            CheckBox L5 = new CheckBox("For 'Adjust memory quotas for a process' allow 'Administrators, LOCAL SERVICE, NETWORK SERVICE'");
            CheckBox L8 = new CheckBox("Restrict Object Creation(Pagfiles, Global, Token, Shared)");
            CheckBox L9 = new CheckBox("Configure 'Create symbolic links'");
            CheckBox L10 = new CheckBox("For 'Debug programs' allow 'Administrators'");
            CheckBox L14 = new CheckBox("For 'Enable computer and user accounts to be trusted for delegation' allow 'No One");
            CheckBox L15 = new CheckBox("For 'Force shutdown from a remote system' allow 'Administrators'");
            CheckBox L16 = new CheckBox("For 'Generate security audits' allow 'LOCAL SERVICE, NETWORK SERVICE'");
            CheckBox L17 = new CheckBox("For 'Impersonate a client after authentication' allow 'Administrators, LOCAL SERVICE, NETWORK SERVICE, SERVICE'"); 
            CheckBox[] LusrmgrActionBoxes = {L5, L8, L9, L10, L14, L15, L16, L17};
          CheckBox LusrmgrLogon = new CheckBox("Logon Privileges");
            CheckBox L11 = new CheckBox("Deny Logon to guests completely");
            CheckBox L12 = new CheckBox("For 'Log on as a batch job' allow 'Administrators");
            CheckBox L13 = new CheckBox("Configure 'Log on as a service'");
            CheckBox[] LusrmgrLogonBoxes = {L11, L12, L13};
          CheckBox LusrmgrMaintenance = new CheckBox("Maintenance Privileges");
            CheckBox L18 = new CheckBox("For 'Increase scheduling priority' allow 'Administrators, Window Manager|Window Manager Group'");
            CheckBox L19 = new CheckBox("For 'Load and unload device drivers' allow 'Administrators'");
            CheckBox L20 = new CheckBox("For 'Lock pages in memory' allow 'No One'");
            CheckBox L21 = new CheckBox("For 'Modify an object label' allow 'No One'");
            CheckBox L22 = new CheckBox("For 'Modify firmware environment values' allow 'Administrators'");
            CheckBox L23 = new CheckBox("For 'Perform volume maintenance tasks' allow 'Administrators'");
            CheckBox L24 = new CheckBox("Restrict Profile single process & system performance");
            CheckBox L25 = new CheckBox("For 'Replace a process level token' allow 'LOCAL SERVICE, NETWORK SERVICE'");
            CheckBox[] LusrmgrMaintenanceBoxes = {L18, L19, L20, L21, L22, L23, L24, L25};
          CheckBox LusrmgrBasic = new CheckBox("Basic Privileges");   
            CheckBox L6 = new CheckBox("For 'Back up files and directories' allow 'Administrators'");
            CheckBox L7 = new CheckBox("For 'Change the time zone' allow 'Administrators, LOCAL SERVICE, Users'"); 
            CheckBox L26 = new CheckBox("For 'Restore files and directories' allow 'Administrators'");
            CheckBox L27 = new CheckBox("For 'Shut down the system' allow 'Administrators, Users'");
            CheckBox L28 = new CheckBox("For 'Take ownership of files or other objects' allow 'Administrators'");
            CheckBox[] LusrmgrBasicBoxes = {L6, L7, L26, L27, L28};
               CheckBox[] LusrmgrBoxes1 = {LusrmgrAccount, LusrmgrAccess, LusrmgrAction, LusrmgrLogon, LusrmgrMaintenance, LusrmgrBasic, L0, L1, L2, L3, L4, L5, L6, L7, L8, L9, L10, L11, L12, L13, L14, L15, L16, L17, L18, L19, L20, L21, L22, L23, L24, L25, L26, L27, L28};
      TreeItem<CheckBox> LocalusrmgrRoot = new TreeItem<>(LusrmgrRoot);
      LocalusrmgrRoot.setExpanded(true);
         TreeItem<CheckBox> LocalusrmgrAccount = new TreeItem<>(LusrmgrAccount);
         LocalusrmgrRoot.getChildren().add(LocalusrmgrAccount);
         LocalusrmgrAccount.setExpanded(true);
          for (int x = 0; x<LusrmgrAccountBoxes.length; x++) {
              TreeItem<CheckBox> item = new TreeItem<>(LusrmgrAccountBoxes[x]);
                LocalusrmgrAccount.getChildren().add(item);
              }
         TreeItem<CheckBox> LocalusrmgrAccess = new TreeItem<>(LusrmgrAccess);
         LocalusrmgrRoot.getChildren().add(LocalusrmgrAccess);
         LocalusrmgrAccess.setExpanded(true);
          for (int x = 0; x<LusrmgrAccessBoxes.length; x++) {
              TreeItem<CheckBox> item = new TreeItem<>(LusrmgrAccessBoxes[x]);
                LocalusrmgrAccess.getChildren().add(item);
              }
         TreeItem<CheckBox> LocalusrmgrAction = new TreeItem<>(LusrmgrAction);
         LocalusrmgrRoot.getChildren().add(LocalusrmgrAction);
         LocalusrmgrAction.setExpanded(true);
          for (int x = 0; x<LusrmgrActionBoxes.length; x++) {
              TreeItem<CheckBox> item = new TreeItem<>(LusrmgrActionBoxes[x]);
                LocalusrmgrAction.getChildren().add(item);
              }
         TreeItem<CheckBox> LocalusrmgrLogon = new TreeItem<>(LusrmgrLogon);
         LocalusrmgrRoot.getChildren().add(LocalusrmgrLogon);
         LocalusrmgrLogon.setExpanded(true);
          for (int x = 0; x<LusrmgrLogonBoxes.length; x++) {
              TreeItem<CheckBox> item = new TreeItem<>(LusrmgrLogonBoxes[x]);
                LocalusrmgrLogon.getChildren().add(item);
              }
         TreeItem<CheckBox> LocalusrmgrMaintenance = new TreeItem<>(LusrmgrMaintenance);
         LocalusrmgrRoot.getChildren().add(LocalusrmgrMaintenance);
         LocalusrmgrMaintenance.setExpanded(true);
          for (int x = 0; x<LusrmgrMaintenanceBoxes.length; x++) {
              TreeItem<CheckBox> item = new TreeItem<>(LusrmgrMaintenanceBoxes[x]);
                LocalusrmgrMaintenance.getChildren().add(item);
              }
         TreeItem<CheckBox> LocalusrmgrBasic = new TreeItem<>(LusrmgrBasic);
         LocalusrmgrRoot.getChildren().add(LocalusrmgrBasic);
         LocalusrmgrBasic.setExpanded(true);
          for (int x = 0; x<LusrmgrBasicBoxes.length; x++) {
              TreeItem<CheckBox> item = new TreeItem<>(LusrmgrBasicBoxes[x]);
                LocalusrmgrBasic.getChildren().add(item);
              }
       LusrmgrRoot.setOnAction(e -> {
       if (LusrmgrRoot.isSelected()) {
                 for (int x = 0; x<LusrmgrBoxes1.length; x++) {
            LusrmgrBoxes1[x].setSelected(true); 
              }
            } else if (!LusrmgrRoot.isSelected()) {
            for (int x = 0; x<LusrmgrBoxes1.length; x++) {
            LusrmgrBoxes1[x].setSelected(false); 
              }
            }
       });
       LusrmgrAccount.setOnAction(e -> {
       if (LusrmgrAccount.isSelected()) {
                 for (int x = 0; x<LusrmgrAccountBoxes.length; x++) {
            LusrmgrAccountBoxes[x].setSelected(true); 
              }
            } else if (!LusrmgrAccount.isSelected()) {
            for (int x = 0; x<LusrmgrAccountBoxes.length; x++) {
            LusrmgrAccountBoxes[x].setSelected(false); 
              }
            }
       });
       LusrmgrAccess.setOnAction(e -> {
       if (LusrmgrAccess.isSelected()) {
                 for (int x = 0; x<LusrmgrAccessBoxes.length; x++) {
            LusrmgrAccessBoxes[x].setSelected(true); 
              }
            } else if (!LusrmgrAccess.isSelected()) {
            for (int x = 0; x<LusrmgrAccessBoxes.length; x++) {
            LusrmgrAccessBoxes[x].setSelected(false); 
              }
            }
       });
       LusrmgrAction.setOnAction(e -> {
       if (LusrmgrAction.isSelected()) {
                 for (int x = 0; x<LusrmgrActionBoxes.length; x++) {
            LusrmgrActionBoxes[x].setSelected(true); 
              }
            } else if (!LusrmgrAction.isSelected()) {
            for (int x = 0; x<LusrmgrActionBoxes.length; x++) {
            LusrmgrActionBoxes[x].setSelected(false); 
              }
            }
       });     
       LusrmgrLogon.setOnAction(e -> {
       if (LusrmgrLogon.isSelected()) {
                 for (int x = 0; x<LusrmgrLogonBoxes.length; x++) {
            LusrmgrLogonBoxes[x].setSelected(true); 
              }
            } else if (!LusrmgrLogon.isSelected()) {
            for (int x = 0; x<LusrmgrLogonBoxes.length; x++) {
            LusrmgrLogonBoxes[x].setSelected(false); 
              }
            }
       });
       LusrmgrMaintenance.setOnAction(e -> {
       if (LusrmgrMaintenance.isSelected()) {
                 for (int x = 0; x<LusrmgrMaintenanceBoxes.length; x++) {
            LusrmgrMaintenanceBoxes[x].setSelected(true); 
              }
            } else if (!LusrmgrMaintenance.isSelected()) {
            for (int x = 0; x<LusrmgrMaintenanceBoxes.length; x++) {
            LusrmgrMaintenanceBoxes[x].setSelected(false); 
              }
            }
       });
       LusrmgrBasic.setOnAction(e -> {
       if (LusrmgrBasic.isSelected()) {
                 for (int x = 0; x<LusrmgrBasicBoxes.length; x++) {
            LusrmgrBasicBoxes[x].setSelected(true); 
              }
            } else if (!LusrmgrBasic.isSelected()) {
            for (int x = 0; x<LusrmgrBasicBoxes.length; x++) {
            LusrmgrBasicBoxes[x].setSelected(false); 
              }
            }
       });
       
                        //Service Checkboxes
        CheckBox ServiceRoot = new CheckBox("Services");
          CheckBox ServiceNetwork = new CheckBox("Networking services");
             CheckBox ServiceShare = new CheckBox("Network discovery services");
               CheckBox R0 = new CheckBox("Disable Bluetooth services");
               CheckBox R1 = new CheckBox("Disable 'Downloaded Maps Manager (MapsBroker)'");
               CheckBox R2 = new CheckBox("Disable 'Geolocation Service (lfsvc)'");
               CheckBox R3 = new CheckBox("Disable 'IIS Admin Service (IISADMIN)'");
               CheckBox R4 = new CheckBox("Disable 'Infrared monitor service (irmon)'");
               CheckBox R5 = new CheckBox("Disable 'Internet Connection Sharing (ICS) (SharedAccess)'");
               CheckBox R6 = new CheckBox("Disable 'Link-Layer Topology Discovery Mapper (lltdsvc)'");
               CheckBox R21 = new CheckBox("Disable 'SNMP Service (SNMP)'");
               CheckBox R22 = new CheckBox("Disable 'SSDP Discovery (SSDPSRV)'");
               CheckBox[] ServiceShareBoxes = {R0, R1, R2, R3, R4, R5, R6, R21, R22};
             CheckBox ServiceAccess = new CheckBox("Remote Access services");
               CheckBox R14 = new CheckBox("Disable 'Remote Access Auto Connection Manager (RasAuto)'");
               CheckBox R15 = new CheckBox("Disable all Remote Desktop services");
               CheckBox R16 = new CheckBox("Disable 'Remote Procedure Call (RPC) Locator (RpcLocator)'");
               CheckBox R17 = new CheckBox("Disable 'Remote Registry (RemoteRegistry)'");
               CheckBox R18 = new CheckBox("Disable 'Routing and Remote Access (RemoteAccess)'");
               CheckBox[] ServiceAccessBoxes = {R14, R15, R16, R17, R18};
             CheckBox ServiceNetpro = new CheckBox("Networking protocols & server sevices");
               CheckBox R8 = new CheckBox("Disable 'Microsoft FTP Service (FTPSVC)'");
               CheckBox R9 = new CheckBox("Disable 'Microsoft iSCSI Initiator Service (MSiSCSI)'");
               CheckBox R11 = new CheckBox("Disable 'OpenSSH SSH Server (sshd)'");
               CheckBox R12 = new CheckBox("Disable peer networking services");
               CheckBox R19 = new CheckBox("Disable 'Server (LanmanServer)'");
               CheckBox R20 = new CheckBox("Disable 'Simple TCP/IP Services (simptcp)'");
               CheckBox R24 = new CheckBox("Disable 'Web Management Service (WMSvc)'");
               CheckBox[] ServiceNetproBoxes = {R8, R9, R11, R12, R19, R20, R24};
            CheckBox[] ServiceNetworkBoxes = {ServiceShare, ServiceAccess, ServiceNetpro, R0, R1, R2, R3, R4, R5, R6, R21, R22, R14, R15, R16, R17, R18, R8, R9, R11, R12, R19, R20, R24};
          CheckBox ServiceWindows = new CheckBox("Windows services");
            CheckBox R25 = new CheckBox("Disable 'Windows Error Reporting Service (WerSvc)'");
            CheckBox R26 = new CheckBox("Disable 'Windows Event Collector (Wecsvc)'");
            CheckBox R27 = new CheckBox("Disable 'Windows Media Player Network Sharing Service (WMPNetworkSvc)'");
            CheckBox R28 = new CheckBox("Disable 'Windows Mobile Hotspot Service (icssvc)'");
            CheckBox R29 = new CheckBox("Disable 'Windows Push Notifications System Service (WpnService)'");
            CheckBox R30 = new CheckBox("Disable 'Windows PushToInstall Service (PushToInstall)'");
            CheckBox R31 = new CheckBox("Disable 'Windows Remote Management (WS-Management) (WinRM)'");
            CheckBox R33 = new CheckBox("Ensure Windows Updates are enabled as automatic on startup");
            CheckBox[] ServiceWindowsBoxes = {R25, R26, R27, R28, R29, R30, R31, R33};
          CheckBox ServiceGeneric = new CheckBox("Miscellaneous services");
            CheckBox R7 = new CheckBox("Disable 'LxssManager (LxssManager)'");
            CheckBox R10 = new CheckBox("Disable 'Microsoft Store Install Service (InstallService)'");
            CheckBox R13 = new CheckBox("Disable 'Problem Reports and Solutions Control Panel Support (wercplsupport)'");
            CheckBox R23 = new CheckBox("Disable 'UPnP Device Host (upnphost)'");
            CheckBox R32 = new CheckBox("Disable Xbox services");
            CheckBox R34 = new CheckBox("Disable 'Printer Spooler'");
            CheckBox[] ServiceGenericBoxes = {R7, R10, R13, R23, R32, R34};
               CheckBox[] ServiceBoxes1 = {ServiceNetwork, ServiceShare, ServiceAccess, ServiceNetpro, ServiceWindows, ServiceGeneric, R0, R1, R2, R3, R4, R5, R6, R21, R22, R14, R15, R16, R17, R18, R8, R9, R11, R12, R19, R20, R24, R25, R26, R27, R28, R29, R30, R31, R33, R7, R10, R13, R23, R32, R34};

      TreeItem<CheckBox> ServicesRoot = new TreeItem<>(ServiceRoot);
      ServicesRoot.setExpanded(true);
         TreeItem<CheckBox> ServicesNetwork = new TreeItem<>(ServiceNetwork);
         ServicesRoot.getChildren().add(ServicesNetwork);
         ServicesNetwork.setExpanded(true);
            TreeItem<CheckBox> ServicesShare = new TreeItem<>(ServiceShare);
            ServicesNetwork.getChildren().add(ServicesShare);
            ServicesShare.setExpanded(true);
          for (int x = 0; x<ServiceShareBoxes.length; x++) {
              TreeItem<CheckBox> item = new TreeItem<>(ServiceShareBoxes[x]);
                ServicesShare.getChildren().add(item);
              }
            TreeItem<CheckBox> ServicesAccess = new TreeItem<>(ServiceAccess);
            ServicesNetwork.getChildren().add(ServicesAccess);
            ServicesAccess.setExpanded(true);
          for (int x = 0; x<ServiceAccessBoxes.length; x++) {
              TreeItem<CheckBox> item = new TreeItem<>(ServiceAccessBoxes[x]);
                ServicesAccess.getChildren().add(item);
              }  
            TreeItem<CheckBox> ServicesNetpro = new TreeItem<>(ServiceNetpro);
            ServicesNetwork.getChildren().add(ServicesNetpro);
            ServicesNetpro.setExpanded(true);
         for (int x = 0; x<ServiceNetproBoxes.length; x++) {
              TreeItem<CheckBox> item = new TreeItem<>(ServiceNetproBoxes[x]);
                ServicesNetpro.getChildren().add(item);
              }  
        TreeItem<CheckBox> ServicesWindows = new TreeItem<>(ServiceWindows);
        ServicesRoot.getChildren().add(ServicesWindows);
        ServicesWindows.setExpanded(true);
          for (int x = 0; x<ServiceWindowsBoxes.length; x++) {
              TreeItem<CheckBox> item = new TreeItem<>(ServiceWindowsBoxes[x]);
                ServicesWindows.getChildren().add(item);
              }
        TreeItem<CheckBox> ServicesGeneric = new TreeItem<>(ServiceGeneric);
        ServicesRoot.getChildren().add(ServicesGeneric);
        ServicesGeneric.setExpanded(true);
          for (int x = 0; x<ServiceGenericBoxes.length; x++) {
              TreeItem<CheckBox> item = new TreeItem<>(ServiceGenericBoxes[x]);
                ServicesGeneric.getChildren().add(item);
              }
       ServiceRoot.setOnAction(e -> {
       if (ServiceRoot.isSelected()) {
                 for (int x = 0; x<ServiceBoxes1.length; x++) {
            ServiceBoxes1[x].setSelected(true); 
              }
            } else if (!ServiceRoot.isSelected()) {
            for (int x = 0; x<ServiceBoxes1.length; x++) {
            ServiceBoxes1[x].setSelected(false); 
              }
            }
       });
              ServiceNetwork.setOnAction(e -> {
       if (ServiceNetwork.isSelected()) {
                 for (int x = 0; x<ServiceNetworkBoxes.length; x++) {
            ServiceNetworkBoxes[x].setSelected(true); 
              }
            } else if (!ServiceNetwork.isSelected()) {
            for (int x = 0; x<ServiceNetworkBoxes.length; x++) {
            ServiceNetworkBoxes[x].setSelected(false); 
              }
            }
       });
                    ServiceShare.setOnAction(e -> {
       if (ServiceShare.isSelected()) {
                 for (int x = 0; x<ServiceShareBoxes.length; x++) {
            ServiceShareBoxes[x].setSelected(true); 
              }
            } else if (!ServiceShare.isSelected()) {
            for (int x = 0; x<ServiceShareBoxes.length; x++) {
            ServiceShareBoxes[x].setSelected(false); 
              }
            }
       });
                     ServiceAccess.setOnAction(e -> {
       if (ServiceAccess.isSelected()) {
                 for (int x = 0; x<ServiceAccessBoxes.length; x++) {
            ServiceAccessBoxes[x].setSelected(true); 
              }
            } else if (!ServiceAccess.isSelected()) {
            for (int x = 0; x<ServiceAccessBoxes.length; x++) {
            ServiceAccessBoxes[x].setSelected(false); 
              }
            }
       });
                     ServiceNetpro.setOnAction(e -> {
       if (ServiceNetpro.isSelected()) {
                 for (int x = 0; x<ServiceNetproBoxes.length; x++) {
            ServiceNetproBoxes[x].setSelected(true); 
              }
            } else if (!ServiceNetpro.isSelected()) {
            for (int x = 0; x<ServiceNetproBoxes.length; x++) {
            ServiceNetproBoxes[x].setSelected(false); 
              }
            }
       });
                     ServiceWindows.setOnAction(e -> {
       if (ServiceWindows.isSelected()) {
                 for (int x = 0; x<ServiceWindowsBoxes.length; x++) {
            ServiceWindowsBoxes[x].setSelected(true); 
              }
            } else if (!ServiceWindows.isSelected()) {
            for (int x = 0; x<ServiceWindowsBoxes.length; x++) {
            ServiceWindowsBoxes[x].setSelected(false); 
              }
            }
       });
                    ServiceGeneric.setOnAction(e -> {
       if (ServiceGeneric.isSelected()) {
                 for (int x = 0; x<ServiceGenericBoxes.length; x++) {
            ServiceGenericBoxes[x].setSelected(true); 
              }
            } else if (!ServiceGeneric.isSelected()) {
            for (int x = 0; x<ServiceGenericBoxes.length; x++) {
            ServiceGenericBoxes[x].setSelected(false); 
              }
            }
       });
         //Cypat Checkboxes
         
      
        CheckBox cyPat0 = new CheckBox("Change user passwords to Password.json");
        CheckBox cyPat1 = new CheckBox("Delete all .mp3 files");
        CheckBox cyPat2 = new CheckBox("Delete hacking related files");
        CheckBox cyPat3 = new CheckBox("Rename Admin account to name 'VS1'");
        CheckBox cyPat4 = new CheckBox("Rename guest account to name 'VS2'");

      ListView<CheckBox> secretOptions = new ListView<>();
      secretOptions.getItems().addAll(cyPat0, cyPat1, cyPat2, cyPat3, cyPat4);
      
      //For Progress bar
      Label ProgressDescription = new Label("Batch Script has been called.");
            ProgressDescription.getStyleClass().add("labels");
      Label ProgressBarDescription = new Label("Securing System. Please wait...");
         ImageView vectorShieldLogo2 = new ImageView(new Image(getClass().getResourceAsStream("Images/VectorShield.png")));
       vectorShieldLogo2.setFitHeight(400); 
       vectorShieldLogo2.setFitWidth(500); 
       vectorShieldLogo2.setPreserveRatio(true);    
         ImageView loadingCircle = new ImageView(new Image(getClass().getResourceAsStream("Images/loading.png")));
       loadingCircle.setFitHeight(200); 
       loadingCircle.setFitWidth(200); 
       loadingCircle.setPreserveRatio(true);
      // Sets layouts
      BorderPane MainLayout = new BorderPane();
      VBox LayoutTop = new VBox();
      //Home layouts
      BorderPane homeLayout = new BorderPane();
      Group images = new Group(vectorShieldLogo);
      VBox homeLayoutTop = new VBox();
      homeLayoutTop.setAlignment(Pos.CENTER);
      homeLayoutTop.getStyleClass().add("backgroundBlue");
      HBox homeLayoutMid = new HBox(50);
      homeLayoutMid.setAlignment(Pos.CENTER);
      homeLayoutMid.getStyleClass().add("HomepageBackground");
      VBox homeLayoutCenter = new VBox(10);
      homeLayoutCenter.setAlignment(Pos.CENTER);
      HBox descriptions = new HBox(25);
      descriptions.setAlignment(Pos.CENTER);
      VBox homeLayoutBottom = new VBox(60);
      homeLayoutBottom.setAlignment(Pos.CENTER);
      //Settings layouts
      BorderPane settingLayout = new BorderPane();
            HBox LeftPane = new HBox();
      LeftPane.setPadding(new Insets(70));
            HBox RightPane = new HBox();
      RightPane.setPadding(new Insets(70));
            HBox BottomPane = new HBox();
      BottomPane.setPadding(new Insets(70));
      HBox settingTopLayout = new HBox();
      settingTopLayout.setAlignment(Pos.CENTER);
      settingTopLayout.setSpacing(40);
      HBox settingMenuLayout = new HBox(0);
      settingMenuLayout.setAlignment(Pos.CENTER);
      VBox settingTopLayoutMain = new VBox();
      settingTopLayoutMain.setAlignment(Pos.CENTER);
      Group settingImages = new Group(settingsLogo);

      TreeView<CheckBox> NetworkingOptions = new TreeView<>(NetworkingRoot);
      TreeView<CheckBox> LocalSecPolOptions = new TreeView<>(LocalSecPolRoot);
      TreeView<CheckBox> LusrmgrOptions = new TreeView<>(LocalusrmgrRoot);
      TreeView<CheckBox> ServicesOptions = new TreeView<>(ServicesRoot);
      //Progress bar layouts
      Group images2 = new Group(vectorShieldLogo2);
      Group loadingImage = new Group(loadingCircle);
      
      BorderPane progressLayout = new BorderPane();
      VBox progressLayoutTop = new VBox();
            progressLayoutTop.setAlignment(Pos.CENTER);
      VBox progressLayoutCenter = new VBox(60);
           progressLayoutCenter.setAlignment(Pos.CENTER);
      // Adds to layouts
      LayoutTop.getChildren().addAll(homePageMenu);
      MainLayout.setTop(LayoutTop);
      MainLayout.setCenter(homeLayout);
      //Home
      homeLayoutTop.getChildren().addAll(images, welcome, description);
      homeLayoutMid.getChildren().addAll(low, medium, high);
      homeLayoutBottom.getChildren().addAll(hardenSyst, boost);
      descriptions.getChildren().addAll(lowDesc, midDesc, highDesc);
      homeLayoutCenter.getChildren().addAll(homeLayoutMid, descriptions, warning);

      homeLayout.setTop(homeLayoutTop);
      homeLayout.setCenter(homeLayoutCenter);
      homeLayout.setBottom(homeLayoutBottom);
      //Settings
            //settingTopLayout.getChildren().addAll(NetworkingOptions, LocalSecPolOptions, LusrmgrOptions, ServicesOptions);
      settingTopLayout.getChildren().addAll(settingImages, settingsDesc);
      settingTopLayout.getStyleClass().add("backgroundBlue");
      settingMenuLayout.getChildren().addAll(NetworkingSettings, SecpolSettings , LusrmgrSettings, ServicesSettings);
      settingTopLayoutMain.getChildren().addAll(settingTopLayout, settingMenuLayout);
      settingLayout.setTop(settingTopLayoutMain);
      settingLayout.setLeft(LeftPane);
      settingLayout.setRight(RightPane);
      settingLayout.setBottom(BottomPane);
            settingLayout.setCenter(NetworkingOptions);
      //ProgressBar
      progressLayoutTop.getChildren().addAll(images2, ProgressDescription);
            progressLayoutTop.getStyleClass().add("backgroundBlue");
      progressLayoutCenter.getChildren().addAll(ProgressBarDescription, loadingImage);
            progressLayout.setTop(progressLayoutTop);
            progressLayout.setCenter(progressLayoutCenter);
      
      // Sets the scenes
      homePage = new Scene(MainLayout, 1200, 800);
      homePage.getStylesheets().add("homeTheme.css");
      progressBar = new Scene(progressLayout, 1200, 800);
      progressBar.getStylesheets().add("progressBar.css");
       // Sets the actions when button is pressed
      hardenSyst.setOnAction(
         e -> {
            harden = AlertBox.display("Warning!", "Changes have to be manually reversed! Check settings before proceeding.", "Secure my system", "Cancel");
            if (harden == true) {//IMPORTANT: Handles checkboxes, modifying the array based on their state.
             CheckBox[] NetworkBoxes = {N0, N1, N2, N3, N4, N5, N6, N7, N8, N9, N10, N11, N12};
             CheckBox[] LocalSecPolBoxes = {S0, S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12, S13, S14, S15, S16, S17, S18, S19, S20, S21, S22};
             CheckBox[] LusrmgrBoxes = {L0, L1, L2, L3, L4, L5, L6, L7, L8, L9, L10, L11, L12, L13, L14, L15, L16, L17, L18, L19, L20, L21, L22, L23, L24, L25, L26, L27, L28};
             CheckBox[] ServiceBoxes = {R0, R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14, R15, R16, R17, R18, R19, R20, R21, R22, R23, R24, R25, R26, R27, R28, R29, R30, R31, R32, R33, R34};
             CheckBox[] CyPatBoxes = {cyPat0, cyPat1, cyPat2, cyPat3, cyPat4};
                handleOptions(NetworkBoxes, LocalSecPolBoxes, LusrmgrBoxes, ServiceBoxes, CyPatBoxes);
                window.setScene(progressBar);
                    //Rotating loading circle
      RotateTransition rotateTransition = new RotateTransition(); 
      rotateTransition.setDuration(Duration.millis(1500));
      rotateTransition.setNode(loadingImage);       
      rotateTransition.setToAngle(360); 
      rotateTransition.setCycleCount(rotateTransition.INDEFINITE); 
      rotateTransition.setAutoReverse(false);
      rotateTransition.setInterpolator(Interpolator.LINEAR);
      rotateTransition.play();
                           //Running batch script
                          Runtime runtime = Runtime.getRuntime();
                          try {
    Process p1 = runtime.exec("cmd /c core.bat");
    Thread t=new Thread(()->{
        try{
            p1.waitFor();
            Service<Void> service = new Service<Void>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {           
                @Override
                protected Void call() throws Exception {
                    //Background work                       
                    final CountDownLatch latch = new CountDownLatch(1);
                    Platform.runLater(new Runnable() {                          
                        @Override
                        public void run() {
                            try{
                                //FX Stuff done here for multi threading
                                        Label ProgressBarDescription1 = new Label("Process complete. Exit or return to home.");
                                        ImageView loadingCheck = new ImageView(new Image(getClass().getResourceAsStream("Images/loading1.png")));
                                        loadingCheck.setFitHeight(200); 
                                        loadingCheck.setFitWidth(200); 
                                        loadingCheck.setPreserveRatio(true);
                                      HBox returnButtons = new HBox(120);
                                      returnButtons.setPadding(new Insets(20));
                                      HBox Nothing = new HBox();
                                      returnButtons.setAlignment(Pos.CENTER);
                                      Button exit = new Button("Exit");
                                      Button home = new Button("Home");
                                             home.setOnAction(e -> {
                                                MainLayout.setCenter(homeLayout);
                                                goToHome.setDisable(true);
                                                goToSettings.setDisable(false);
                                                progressLayoutCenter.getChildren().removeAll(ProgressBarDescription1, loadingCheck);
                                                progressLayoutCenter.getChildren().addAll(ProgressBarDescription, loadingImage);
                                                progressLayout.setBottom(Nothing);
                                                window.setScene(homePage);
                                                });
                                             exit.setOnAction(e -> {
                                             exitProgram();
                                                });
                                      returnButtons.getChildren().addAll(exit, home);
                                      progressLayout.setBottom(returnButtons);

                                progressLayoutCenter.getChildren().removeAll(ProgressBarDescription, loadingImage);
                                progressLayoutCenter.getChildren().addAll(ProgressBarDescription1, loadingCheck);

                                
                            }finally{
                                latch.countDown();
                            }
                        }
                    });
                    latch.await();                      
                    //Keep with the background work
                    return null;
                }
            };
        }
    };
    service.start();            
        }catch(InterruptedException ex){
        }
    });
    t.setDaemon(true);
    t.start();
} catch(IOException ioException) {}                  
        }
        });
                  //Go to settings
       goToSettings.setOnAction(e -> {
      MainLayout.setCenter(settingLayout);
      goToHome.setDisable(false);
      goToSettings.setDisable(true);
       });
                  //Go to home
        goToHome.setOnAction(e -> {
      MainLayout.setCenter(homeLayout);
      goToHome.setDisable(true);
      goToSettings.setDisable(false);
       });
                //secret settings menu option
       cyberPatriotView.setOnAction(e -> {
         if(cyberPatriotView.isSelected()) {
         boolean choice = AlertBox.display("WARNING!", "Secret settings can cause SERIOUS IRREVERSIBLE damage, do not use unless told to.", "I know what im doing", "Cancel");
         if (choice) {
         settingMenuLayout.getChildren().add(SecretSettings);
         } else {cyberPatriotView.setSelected(false);}
         } else { settingMenuLayout.getChildren().remove(SecretSettings);}
      });
      exitProgram.setOnAction(e -> exitProgram());
               //Low medium and high buttons
presetButtons.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
    public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
          if (presetButtons.getSelectedToggle() != null) {
               String choice = presetButtons.getSelectedToggle().getUserData().toString();
                           switch (choice) {
            //Low actions
            case "low":
            
                  break;
            //Mid actions
            case "mid":
            
                  break;
            //High actions
            case "high":
            
                  break;
            default: System.out.println("ERROR: Invalid preset");
            System.exit(0);
                  break;
               }
         }

     } 
});
                     //Settings menu choices
SettingsButtons.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
    public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
          if (SettingsButtons.getSelectedToggle() != null) {
               String choice = SettingsButtons.getSelectedToggle().getUserData().toString();
                           switch (choice) {
            case "networking":
            settingLayout.setCenter(NetworkingOptions);
                  break;
            case "secpol":
            settingLayout.setCenter(LocalSecPolOptions);
                  break;
            case "lusrmgr":
            settingLayout.setCenter(LusrmgrOptions);
                  break;
            case "services":
            settingLayout.setCenter(ServicesOptions);
                  break;
            case "cypat":
            settingLayout.setCenter(secretOptions);
                  break;
            default: System.out.println("ERROR: Invalid setting choice");
            System.exit(0);
                  break;
               }
         }

     } 
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
