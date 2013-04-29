import java.io.File;
import javax.swing.JFileChooser;



/**
 * Opens a file dialog window allowing the user to choose a song
 * @author abaez02.student
 *
 */
public class FileChooser {
	
	File musicFile;
	
    public FileChooser(){
    	this.musicFile = chooseFile();
    }
	/**
	 * Opens file dialog window
	 * @return
	 */
	private File chooseFile(){
		final JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION){
        	File fileToOpen = fileChooser.getSelectedFile();
        	return fileToOpen;
        }
        
	    return null;
	}
	
	/**
	 * Get the path name of the file
	 * @return
	 */
	public String getFileName() {
		String fileName = musicFile.getPath();
		System.out.println(fileName);
		return fileName;
	}


}
