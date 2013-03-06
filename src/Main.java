import javax.swing.JFrame;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.testbed.framework.TestList;
import org.jbox2d.testbed.framework.TestbedFrame;
import org.jbox2d.testbed.framework.TestbedModel;
import org.jbox2d.testbed.framework.TestbedPanel;
import org.jbox2d.testbed.framework.TestbedSetting;
import org.jbox2d.testbed.framework.TestbedSetting.SettingType;
import org.jbox2d.testbed.framework.j2d.TestPanelJ2D;


public class Main {

	public static void main(String[] args) {
		
		System.out.println("AudioBike!");
		
		/*
		 * Simulation Code! Uncomment to get a feel of JBox2D
		 */
//		TestbedModel model = new TestbedModel();         // create our model
//
//		// add tests
//		TestList.populateModel(model);                   // populate the provided testbed tests
//		model.addCategory("My Super Tests");             // add a category
//		model.addTest(new MJWTest2());                // add our test
//		
//
//		// add our custom setting "My Range Setting", with a default value of 10, between 0 and 20
//		model.getSettings().addSetting(new TestbedSetting("My Range Setting", SettingType.ENGINE, 10, 0, 20));
//
//		TestbedPanel panel = new TestPanelJ2D(model);    // create our testbed panel
//
//		JFrame testbed = new TestbedFrame(model, panel); // put both into our testbed frame
//		// etc
//		testbed.setVisible(true);
//		testbed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Vec2 gravity = new Vec2(0.0f, -10.0f);
		boolean doSleep = true;
		World audioWorld = new World(gravity, doSleep);
		
		BodyDef groundBodyDef = new BodyDef();
		groundBodyDef.position.set(0.0f, -10.0f);
		
		Body groundBody = audioWorld.createBody(groundBodyDef);
		
		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(50.0f, 10.0f);
		
		groundBody.createFixture(groundBox, 0);
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.position.set(0.0f, 4.0f);
		Body body = audioWorld.createBody(bodyDef);
		
		PolygonShape dynamicBox = new PolygonShape();
		dynamicBox.setAsBox(1.0f, 1.0f);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = dynamicBox;
		fixtureDef.density = 1;
		fixtureDef.friction = 0.3f;
		
		body.createFixture(fixtureDef);
		
		//That's it for init. Time for simulation
		
		float timestep = (1.0f)/(60.0f);
		
		int velocityIterations = 6;
		int positionIterations = 2;
		
		for(int i = 0; i < 60; i++)
		{
			audioWorld.step(timestep, velocityIterations, positionIterations);
			Vec2 position = body.getPosition();
			float angle = body.getAngle();
			
			System.out.println(position.x + " " + position.y + " " + angle);
			
		}
		
		//http://johnsogg.blogspot.com/2011/10/box2d-in-java-hello-world.html
		
		
		
		
		
		
		

	}

}
