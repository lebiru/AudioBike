import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;



public class Main {

	public static void main(String[] args) throws IOException, UnsupportedAudioFileException
	{
		
		System.out.println("AudioBike!");
		
		AudioLoader audioLoader = new AudioLoader();
		audioLoader.beginSongAnalysis();
		
		AudioWorld audioWorld = new AudioWorld();
		audioWorld.simulationTest2();
		audioWorld.simulateWorld();
		
		
		
	}
}
