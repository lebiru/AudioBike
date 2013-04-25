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


		primaryStage.setTitle("AudioBike");
		primaryStage.setFullScreen(true);
		primaryStage.setResizable(false);

		final Group root = new Group(); //Create a group for holding all objects on the screen
		final Scene scene = new Scene(root, Utils.WIDTH, Utils.HEIGHT,Color.BLACK);

		FileChooser fileChooser = new FileChooser();
		String fileName = fileChooser.getFileName();

		AudioLoader audioLoader = new AudioLoader(fileName);
		Global.waveform = audioLoader.audioArr;


		//		AudioWorld audioWorld = new AudioWorld(audioLoader);
		//		audioWorld.simulationTest2();
		//		audioWorld.simulateWorld();

		//		AudioPlayer audioPlayer = new AudioPlayer(fileName);
		//		audioPlayer.playSong();	


		//Ball array for hold the  balls
		final Ball[] ball = new Ball[Utils.NO_OF_BALLS];

		Random r = new Random(System.currentTimeMillis());

		/**
		 * Generate balls and position them on random locations.  
		 * Random locations between 5 to 95 on x axis and between 100 to 500 on y axis 
		 */
		for(int i=0;i<Utils.NO_OF_BALLS;i++) {
			ball[i]=new Ball(r.nextInt(90)+5,r.nextInt(400)+100);
		}

		//Add ground to the application, this is where balls will land
		Utils.addGround(100, 10);

		//Add left and right walls so balls will not move outside the viewing area.
		Utils.addWall(0,100,1,100); //Left wall
		Utils.addWall(99,100,1,100); //Right wall



		final Timeline timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);

		Duration duration = Duration.seconds(1.0/60.0); // Set duration for frame.

		//Create an ActionEvent, on trigger it executes a world time step and moves the balls to new position 
		EventHandler<ActionEvent> ae = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				//Create time step. Set Iteration count 8 for velocity and 3 for positions
				Utils.world.step(1.0f/60.f, 8, 3); 

				//Move balls to the new position computed by JBox2D
				for(int i=0;i<Utils.NO_OF_BALLS;i++) {
					Body body = (Body)ball[i].node.getUserData();
					float xpos = Utils.toPixelPosX(body.getPosition().x);
					float ypos = Utils.toPixelPosY(body.getPosition().y);
					ball[i].node.setLayoutX(xpos);
					ball[i].node.setLayoutY(ypos);
				}
			}
		};


		/**
		 * Set ActionEvent and duration to the KeyFrame. 
		 * The ActionEvent is trigged when KeyFrame execution is over. 
		 */
		KeyFrame frame = new KeyFrame(duration, ae, null,null);

		timeline.getKeyFrames().add(frame);

		//Create button to start simulation.
		final Button btn = new Button();
		btn.setLayoutX((Utils.WIDTH/2));
		btn.setLayoutY((Utils.HEIGHT-30));
		btn.setText("Start");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				timeline.playFromStart(); 
				btn.setVisible(false);
			}
		});

		//Add button to the root group
		root.getChildren().add(btn);

		//Add all balls to the root group
		for(int i=0;i<Utils.NO_OF_BALLS;i++) {
			root.getChildren().add(ball[i].node);
		}

		//Draw hurdles on mouse event.
		EventHandler<MouseEvent> addHurdle = new EventHandler<MouseEvent>(){
			public void handle(MouseEvent me) {
				//Get mouse's x and y coordinates on the scene
				float dragX = (float)me.getSceneX();
				float dragY = (float)me.getSceneY();

				//Draw ball on this location. Set balls body type to static.
				Ball hurdle = new Ball(Utils.toPosX(dragX), Utils.toPosY(dragY),2,BodyType.STATIC,Color.BLUE);
				//Add ball to the root group
				root.getChildren().add(hurdle.node);
			}
		};

		scene.setOnMouseDragged(addHurdle);

		primaryStage.setScene(scene);
		primaryStage.show();
	}


}
