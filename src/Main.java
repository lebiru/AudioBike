import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

public class Main {
    static boolean stop = false;
	public static void main(String[] args) throws IOException, UnsupportedAudioFileException, UnsupportedTagException, InvalidDataException
	{
		
		System.out.println("AudioBike!");
        
		FileChooser fileChooser = new FileChooser();
		String fileName = fileChooser.getFileName();
	
        AudioLoader audioLoader = new AudioLoader(fileName);
        Global.waveform = audioLoader.audioArr;
        
		AudioWorld audioWorld = new AudioWorld(audioLoader);
		audioWorld.simulationTest2();
		audioWorld.simulateWorld();
		
		AudioPlayer audioPlayer = new AudioPlayer(fileName);
		audioPlayer.playSong();	
		
	}
}
