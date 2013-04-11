import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.echonest.api.v4.EchoNestException;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;



public class Main {

	public static void main(String[] args) throws IOException, UnsupportedAudioFileException, UnsupportedTagException, InvalidDataException, EchoNestException
	{
		
		System.out.println("AudioBike!");
       
		String fileName = "/home/student/abaez02/Desktop/AudioBike/AudioBike/src/testSongs/01 - For What It's Worth (LP Version).mp3";
		
//        AudioLoader audioLoader = new AudioLoader(fileName);
//        audioLoader.beginSongAnalysis();
//        byte[] waveform = audioLoader.audioArr;
//        audioLoader.printArray(waveform);
//        double bpm = audioLoader.findTempo();
//        System.out.println(bpm);
		AudioWorld audioWorld = new AudioWorld();
		audioWorld.simulationTest2();
		audioWorld.simulateWorld();
		
		AudioPlayer audioPlayer = new AudioPlayer(fileName);
		audioPlayer.playSong();	


	}
}
