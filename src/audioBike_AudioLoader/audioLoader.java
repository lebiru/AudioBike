package audioBike_AudioLoader;
import java.io.IOException;

import javax.sound.*;
public class audioLoader {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InvalidDataException 
	 * @throws UnsupportedTagException 
	 * 
	 * Decodes a mp3 file
	 */
	public static void main(String[] args) throws UnsupportedTagException, InvalidDataException, IOException {
	    Mp3File mp3File = new Mp3File("/home/student/abaez02/workspace/AudioBike_AudioLoader/src/Robot Death Kites - Fight Or Flight.mp3");
	    System.out.println("lenght of mp3 is: " + mp3File.getLengthInSeconds() + " second");

	}

}
