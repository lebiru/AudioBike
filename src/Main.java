import java.io.IOException;

import audioBike_AudioLoader.InvalidDataException;
import audioBike_AudioLoader.UnsupportedTagException;

public class Main {

	public static void main(String[] args) throws UnsupportedTagException, InvalidDataException, IOException
	{
		
		System.out.println("AudioBike!");
		
		AudioLoader al = new AudioLoader();
		al.beginSongAnalysis();
		
		AudioWorld aw = new AudioWorld();
		aw.simulationTest2();
		aw.simulateWorld();
		
		
		
	}
}
