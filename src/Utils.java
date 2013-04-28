//AudioBike Utils
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;


public class Utils 
{

	
	static Cart cart;
	int score = 0;
	int lives = 3; //start out with 3 health
	boolean livesMutable = true;

	//motor1 is a RevoluteJoint
	AudioLoader audioLoader;

	//Create a JBox2D world. 
	public static final World world = new World(new Vec2(0.0f, -10.0f), true);

	//Screen width and height
	public static final int WIDTH = 1024;
	public static final int HEIGHT = 780;

	//Ball radius in pixel
	public static final int BALL_SIZE = 8;

	//Total number of balls
	public final static int NO_OF_BALLS = 100; 

	//Ball gradient
	private final static LinearGradient BALL_GRADIENT = new LinearGradient(0.0, 0.0, 1.0, 0.0, true, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0, Color.WHITE), new Stop(1, Color.RED)});

	//This method adds a ground to the screen. 
	public static void addGround(float width, float height){
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(width,height);


		FixtureDef fd = new FixtureDef();
		fd.shape = ps;

		BodyDef bd = new BodyDef();
		bd.position= new Vec2(0.0f,-10f);
	

		world.createBody(bd).createFixture(fd);
	}

	private static void makeLevel() 
	{
		
		Rectangle r = new Rectangle();
		r.setFill(Color.RED);

		BodyDef b = new BodyDef();
		b.position.set(0.0f, 0.5f);
		r.setLayoutX(0.0f);
		r.setLayoutY(0.5f);


		PolygonShape bbox = new PolygonShape();
		bbox.setAsBox(50.0f, 0.5f);


		Body body = Utils.world.createBody(b);
		FixtureDef fixtureDef = new FixtureDef();

		//this is the ground
		bbox.setAsBox(10.0f, 1.0f, new Vec2(0.0f, 0.5f), 0.0f);
		fixtureDef.shape = bbox;
		body.createFixture(fixtureDef);
		r.setUserData(body);
		

		//this is the left wall
		bbox.setAsBox(1.0f, 3.0f, new Vec2(-10.0f, 0.5f), 0.0f);
		fixtureDef.shape = bbox;
		body.createFixture(fixtureDef);
		r.setUserData(body);
		
		
		

		//makeParticles();

		addCart();
		
		body.resetMassData();

	}
	
	public static void addCart()
	{
		cart = new Cart();
		
	}
	
	//This method creates a walls. 
	public static void addWall(float posX, float posY, float width, float height){
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(width,height);

		FixtureDef fd = new FixtureDef();
		fd.shape = ps;
		fd.density = 1.0f;
		fd.friction = 0.3f;    

		BodyDef bd = new BodyDef();
		bd.position.set(posX, posY);
		

		Utils.world.createBody(bd).createFixture(fd);
	}

	//This gives a look and feel to balls
	public static LinearGradient getBallGradient(Color color)
	{
		if(color.equals(Color.RED))
			return BALL_GRADIENT;
		else
			return new LinearGradient(0.0, 0.0, 1.0, 0.0, true, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0, Color.WHITE), new Stop(1, color)});
	}

	

	public static void constructWorld()
	{
		Utils.world.setGravity(new Vec2(0.0f, -10.0f));
		Utils.world.setAllowSleep(true);

		makeLevel();
		makeHills();

	}

	private static void makeHills() 
	{
		BodyDef sandbox = new BodyDef();
		Body hillMaker = Utils.world.createBody(sandbox);

		System.out.println("----------------");

		int[] waveform2 = compressWaveform(Global.waveform);
		System.out.println("here");
		System.out.println(waveform2.length);

		/* CIRCLES METHOD */
		float sizeX = 0.1f;
		float sizeY = 0.1f;
		//anchors: starting positions
		float anchorX = 11f;
		float anchorY = 1f;
		//how much space we add between blocks
		float offsetX = 0.2f;
		float offsetY = 0.2f;
		int dampningFactor = 0; //integer that we will divide the array by 

		FixtureDef particleDef = new FixtureDef();
		particleDef.density = 0.01f;
		particleDef.friction = 0.1f;
		particleDef.restitution = 0.5f;

		float radius = 0.2f;

		for(int i = 0; i < waveform2.length; i++)
		{
			CircleShape particleShape = new CircleShape();
			particleShape.m_radius = radius;
			particleDef.shape = particleShape;

			BodyDef particleBD = new BodyDef();
			particleBD.position.set(11f, 4f);
			if(i != (waveform2.length-1) && waveform2[i+1] > waveform2[i])
			{
				anchorY += offsetY;
			}
			else if(i != (waveform2.length-1) && waveform2[i+1] < waveform2[i])
			{
				anchorY -= offsetY;
			}
			else
			{
				anchorY = anchorY; //if the next one is == to the current one
			}
			anchorX += offsetX;

			BodyDef bd = new BodyDef();
			bd.type = BodyType.STATIC;
			bd.position.set(anchorX + offsetX, anchorY + offsetY);
			bd.allowSleep = true;
			bd.linearDamping = 0.1f;
			bd.angularDamping = 0.1f;

			Body body = Utils.world.createBody(bd);
			body.createFixture(particleDef);
			body.resetMassData();
		}

	}

	private static int[] compressWaveform(byte[] waveform) 
	{
		//sum from start to checkpoint of signal[i]^2/(2*(end-start))
		//average of the powers is the zero

		int damping = 20000; //TODO figure out good damping number
		int chunks = waveform.length/damping; //number of data pieces in each chunks     141 == CHUNKS
		int[] powers = new int[chunks]; // copying the array

		//System.out.println(powers.length);


		for(int i = 0; i < chunks; i++) // for each CHUNK
		{
			long sumOfPowers = 0;
			//System.out.println("This is chunk " + i + ".");
			for(int j = damping*i; j < damping*(i+1); j++) //for each Summation
			{
				//System.out.println("Element j: " + waveform[j]);
				//do the summation
				sumOfPowers += waveform[j]*waveform[j];

			}
			//System.out.println("Sum of chunk " + i + ": " + sumOfPowers);
			//System.out.println("----------------------------");
			powers[i] = (int) (sumOfPowers/chunks);

		}

		System.out.println(powers.length);
		//				for(int i : powers)
		//				{
		//					System.out.println(powers[i]);
		//				}
		return powers;

	}

	
	
	//Convert a JBox2D x coordinate to a JavaFX pixel x coordinate
		public static float toPixelPosX(float posX) {
			float x = WIDTH*posX / 100.0f;
			return x;
		}

		//Convert a JavaFX pixel x coordinate to a JBox2D x coordinate
		public static float toPosX(float posX) {
			float x =   (posX*100.0f*1.0f)/WIDTH;
			return x;
		}

		//Convert a JBox2D y coordinate to a JavaFX pixel y coordinate
		public static float toPixelPosY(float posY) {
			float y = HEIGHT - (1.0f*HEIGHT) * posY / 100.0f;
			return y;
		}

		//Convert a JavaFX pixel y coordinate to a JBox2D y coordinate
		public static float toPosY(float posY) {
			float y = 100.0f - ((posY * 100*1.0f) /HEIGHT) ;
			return y;
		}

		//Convert a JBox2D width to pixel width
		public static float toPixelWidth(float width) {
			return WIDTH*width / 100.0f;
		}

		//Convert a JBox2D height to pixel height
		public static float toPixelHeight(float height) {
			return HEIGHT*height/100.0f;
		}

}

