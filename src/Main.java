import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;


import com.echonest.api.v4.EchoNestException;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

public class Main extends Application{

	public static void main(String[] args) throws IOException, UnsupportedAudioFileException, UnsupportedTagException, InvalidDataException, EchoNestException
	{

		Application.launch(args);

		


	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
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
