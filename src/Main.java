import java.io.IOException;

import com.mpatric.mp3agic.*;

public class Main {

	public static void main(String[] args) throws UnsupportedTagException, InvalidDataException, IOException
	{
		
		System.out.println("AudioBike!");
		
		AudioLoader audioLoader = new AudioLoader();
		audioLoader.beginSongAnalysis();
		
		AudioWorld audioWorld = new AudioWorld();
		audioWorld.simulationTest2();
		audioWorld.simulateWorld();
		
		
		
	}
}
