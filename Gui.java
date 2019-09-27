import java.io.*;
public class Gui {
//Imports WriteSettings as a constructor
	static WriteSettings printToJson = new WriteSettings();
//Beginning of script
	public static void main(String[] args) throws IOException {
//Settings array declarations
   boolean[] settings1 = {false, false, false};
	boolean[] settings2 = {false, false, false};
	boolean[] settings3 = {true, true, false};
	boolean[][] arrays = new boolean[][] { settings1, settings2, settings3 };
	//Names of the arrays
	String[] settingNames = {"settings1", "settings2", "settings3" };
	//Runs the method to write setting values to Json files
		printToJson.writeSettings(arrays, settingNames);
	}
}