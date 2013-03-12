import java.io.IOException;
import audioBike_AudioLoader.InvalidDataException;
import audioBike_AudioLoader.Mp3File;
import audioBike_AudioLoader.UnsupportedTagException;
public class AudioLoader {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InvalidDataException 
	 * @throws UnsupportedTagException 
	 * 
	 * Decodes a mp3 file
	 */
	public AudioLoader()
	{
		
	}

	public void beginSongAnalysis() throws UnsupportedTagException, InvalidDataException, IOException
	{
		Mp3File mp3File = new Mp3File("C:\\Users\\biru\\git\\AudioBike\\src\\testSongs\\Loveline.mp3");
		System.out.println("lenght of mp3 is: " + mp3File.getLengthInSeconds() + " second");
		System.out.println(mp3File.getFilename());
	}

}
