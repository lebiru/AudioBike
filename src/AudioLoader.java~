import java.io.IOException;


import com.mpatric.mp3agic.*;

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
		Mp3File mp3File = new Mp3File("/home/student/abaez02/workspace/AudioBike/src/testSongs/Loveline.mp3");
		System.out.println("lenght of mp3 is: " + mp3File.getLengthInSeconds() + " second");
		System.out.println(mp3File.getFilename());
	}

}
