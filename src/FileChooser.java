import java.io.File;
import javax.swing.JFileChooser;




public class FileChooser {
	
	File musicFile;
	
    public FileChooser(){
    	this.musicFile = chooseFile();
    }
	
	private File chooseFile(){
		final JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION){
        	File fileToOpen = fileChooser.getSelectedFile();
        	return fileToOpen;
        }
        
	    return null;
	}
	
	public String getFileName() {
		String fileName = musicFile.getPath();
		System.out.println(fileName);
		return fileName;
	}


}
