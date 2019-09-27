import java.io.*;

public class WriteSettings {
//Writes to txt file Function
	public void writeSettings(boolean[][] array, String[] names) {
	for (int y = 0;y < array.length; y++) {
	boolean[] curArray = array[y];
	File file = new File(names[y] + ".json");
	    BufferedWriter bw = null;
	    try {
	        bw = new BufferedWriter(new FileWriter(file));
	            for (int x = 0; x < curArray.length; x++) {
	                bw.write(Boolean.toString(curArray[x]));
	                bw.newLine();
	            }
	        } catch (IOException ex) {
	    } finally {
	        if (bw != null) {
	            try { bw.close(); }
	            catch (IOException ignored) { }
	        }
	      }
	    }
	}
	}