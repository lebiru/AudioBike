import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.echonest.api.v4.EchoNestException;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

public class Main {

	public static void main(String[] args) throws IOException, UnsupportedAudioFileException, UnsupportedTagException, InvalidDataException, EchoNestException
	{
		
		System.out.println("AudioBike!");
        
		FileChooser fileChooser = new FileChooser();
		String fileName = fileChooser.getFileName();
	
        AudioLoader audioLoader = new AudioLoader(fileName);
        audioLoader.beginSongAnalysis();
        Global.waveform = audioLoader.audioArr;
        
		AudioWorld audioWorld = new AudioWorld(audioLoader);
		audioWorld.simulationTest2();
		audioWorld.simulateWorld();
		
		AudioPlayer audioPlayer = new AudioPlayer(fileName);
		audioPlayer.playSong();	
		


	}
}
