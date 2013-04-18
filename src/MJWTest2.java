import java.util.ArrayList;
import java.util.Random;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.joints.PrismaticJoint;
import org.jbox2d.dynamics.joints.PrismaticJointDef;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;
import org.jbox2d.testbed.framework.TestbedModel;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class MJWTest2 extends TestbedTest {

	Body cart;
	RevoluteJoint motor1;
	RevoluteJoint motor2;

	PrismaticJoint spring1;
	PrismaticJoint spring2;

	BodyDef sandbox = new BodyDef();
	PolygonShape sandboxbox = new PolygonShape();
	FixtureDef hill = new FixtureDef();
	
	//motor1 is a RevoluteJoint

	@Override
	public void initTest(boolean argDeserialized) 
	{
		setTitle("Level 1");

		getWorld().setGravity(new Vec2(0.0f, -10.0f));
		boolean doSleep = true;
		getWorld().setAllowSleep(doSleep);

		makeLevel();
		makeHills();

	}

	@Override
	public void step(TestbedSettings settings)
	{
		super.step(settings);

		//40 is down arrow, so "s"
		//38 is up arrow, so "w"
		//37 is left arrow, so "a"
		//39 is right arrow, so "d"

		TestbedModel model = super.getModel();
		if (model.getKeys()['s']) 
		{ // model also contains the coded key values
			motor1.setMotorSpeed((float) (15*Math.PI) );
			motor1.setMaxMotorTorque(17f);
			motor2.setMotorSpeed((float) (15*Math.PI)*1 );
			motor2.setMaxMotorTorque(12f);
		}

		if (model.getKeys()['w']) 
		{ // model also contains the coded key values
			motor1.setMotorSpeed((float) (15*Math.PI)*-1 );
			motor1.setMaxMotorTorque(17f);
			motor2.setMotorSpeed((float) (15*Math.PI)*-1 );
			motor2.setMaxMotorTorque(12f);
		}

		if(!model.getKeys()['w'] && !model.getKeys()['s'])
		{
			motor1.setMaxMotorTorque(0.5f);
			motor2.setMaxMotorTorque(0.5f);
		}


		if(model.getKeys()['a'])
		{
			cart.applyTorque(30);
		}
		else if(model.getKeys()['d'])
		{
			cart.applyTorque(-30);
		}

		spring1.setMaxMotorForce((float) (30f+Math.abs(800*Math.pow(spring1.getJointTranslation(), 2))));
		spring1.setMotorSpeed((float) ((spring1.getMotorSpeed() - 10*spring1.getJointTranslation())*0.4));

		spring2.setMaxMotorForce((float) (30f+Math.abs(800*Math.pow(spring2.getJointTranslation(), 2))));
		spring2.setMotorSpeed((float) (-4 * Math.pow(spring2.getJointTranslation(), 1)));

		this.setCamera(cart.getPosition(), 25f);

	}


	private void makeLevel() 
	{

		BodyDef b = new BodyDef();
		b.position.set(0.0f, 0.5f);

		PolygonShape bbox = new PolygonShape();
		bbox.setAsBox(50.0f, 0.5f);


		Body body = getWorld().createBody(b);
		FixtureDef fixtureDef = new FixtureDef();

		//this is the ground
		bbox.setAsBox(10.0f, 1.0f, new Vec2(0.0f, 0.5f), 0.0f);
		fixtureDef.shape = bbox;
		body.createFixture(fixtureDef);

		//this is the left wall
		bbox.setAsBox(1.0f, 3.0f, new Vec2(-10.0f, 0.5f), 0.0f);
		fixtureDef.shape = bbox;
		body.createFixture(fixtureDef);

		//makeParticles();

		makeBike();
		body.resetMassData();

	}

	private void makeBike() 
	{

		//make cart
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DYNAMIC;
		bd.position.set(0.0f, 3.5f);

		cart = getWorld().createBody(bd);
		Body wheel1 = getWorld().createBody(bd);
		Body wheel2 = getWorld().createBody(bd);

		PolygonShape bbox = new PolygonShape();
		bbox.setAsBox(1.5f, 0.3f);

		FixtureDef boxDef = new FixtureDef();
		boxDef.density = 2;
		boxDef.friction = 0.5f;
		boxDef.restitution = 0.2f;
		boxDef.filter.groupIndex = -1;

		bbox.setAsBox(1.5f, 0.3f);
		boxDef.shape = bbox;
		cart.createFixture(boxDef);

		bbox.setAsBox(0.4f, 0.15f, new Vec2(-1.0f, -0.3f), (float)Math.PI/3);
		boxDef.shape = bbox;
		cart.createFixture(boxDef);

		bbox.setAsBox(0.4f, 0.15f, new Vec2(1.0f, -0.3f), (float)-Math.PI/3);
		boxDef.shape = bbox;
		cart.createFixture(boxDef);

		cart.resetMassData();
		boxDef.density = 1;

		//add the axles
		Body axle1 = getWorld().createBody(bd);

		bbox.setAsBox(0.4f, 0.1f, new Vec2((float)(-1.0f - 0.6f*Math.cos(Math.PI/3)), (float)(-0.3f - 0.6f*Math.sin(Math.PI/3))), (float) (Math.PI/3));
		axle1.createFixture(boxDef);
		axle1.resetMassData();

		PrismaticJointDef prismaticJointDef = new PrismaticJointDef();
		prismaticJointDef.initialize(cart, axle1, axle1.getWorldCenter(), new Vec2((float)Math.cos(Math.PI/3), (float)Math.sin(Math.PI/3)));
		prismaticJointDef.lowerTranslation = -0.3f;
		prismaticJointDef.upperTranslation = 0.5f;
		prismaticJointDef.enableLimit = true;
		prismaticJointDef.enableMotor = true;

		spring1 = (PrismaticJoint) getWorld().createJoint(prismaticJointDef);

		Body axle2 = getWorld().createBody(bd);

		bbox.setAsBox(0.4f, 0.1f, new Vec2((float)(1.0f + 0.6f*Math.cos(-Math.PI/3)), (float)(-0.3f + 0.6f*Math.sin(-Math.PI/3))), (float) (-Math.PI/3));
		boxDef.shape = bbox;
		axle2.createFixture(boxDef);
		axle2.resetMassData();

		prismaticJointDef.initialize(cart, axle2, axle2.getWorldCenter(), new Vec2((float)-Math.cos(Math.PI/3), (float)Math.sin(Math.PI/3)));

		spring2 = (PrismaticJoint) getWorld().createJoint(prismaticJointDef);

		//add wheels
		CircleShape circleDef = new CircleShape();
		circleDef.m_radius = 0.7f;
		boxDef.density = 0.1f;
		boxDef.friction = 5.0f;
		boxDef.restitution = 0.2f;
		boxDef.filter.groupIndex = -1;
		boxDef.shape = circleDef;

		for (int i = 0; i < 2; i++)
		{
			BodyDef circleBodyDef = new BodyDef();
			circleBodyDef.type = BodyType.DYNAMIC;
			if (i == 0)
			{
				circleBodyDef.position.set((float)(axle1.getWorldCenter().x - (0.3f*Math.cos(Math.PI/3))), (float)(axle1.getWorldCenter().y - (0.3f * Math.sin(Math.PI/3))));
			}
			else
			{
				circleBodyDef.position.set((float)(axle2.getWorldCenter().x + 0.3f * Math.cos(-Math.PI/3)), (float)(axle2.getWorldCenter().y + 0.3f*Math.sin(-Math.PI/3)));
			}
			circleBodyDef.allowSleep = false; 

			if(i == 0)
			{
				wheel1 = getWorld().createBody(circleBodyDef);
			}

			else
			{
				wheel2 = getWorld().createBody(circleBodyDef);
			}

			(i == 0 ? wheel1 : wheel2).createFixture(boxDef);
			(i == 0 ? wheel1 : wheel2).resetMassData();

		}


		//add joints

		RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
		revoluteJointDef.enableMotor = true;


		motor1 = new RevoluteJoint(getWorld().getPool(), revoluteJointDef);
		motor2 = new RevoluteJoint(getWorld().getPool(), revoluteJointDef);

		revoluteJointDef.initialize(axle1, wheel1, wheel1.getWorldCenter());
		motor1 = (RevoluteJoint) getWorld().createJoint(revoluteJointDef);

		revoluteJointDef.initialize(axle2, wheel2, wheel2.getWorldCenter());
		motor2 = (RevoluteJoint) getWorld().createJoint(revoluteJointDef);


	}

	private void makeParticles() 
	{

		FixtureDef particleDef = new FixtureDef();
		particleDef.density = 0.01f;
		particleDef.friction = 0.1f;
		particleDef.restitution = 0.5f;

		for (int i = 0; i < 30; i++) 
		{

			CircleShape particleShape = new CircleShape();
			particleShape.m_radius = (float) ((float)Math.random()/20+0.02);
			particleDef.shape = particleShape;

			BodyDef particleBD = new BodyDef();
			particleBD.position.set((float)((float)Math.random()*35+15), 1f);


			BodyDef bd = new BodyDef();
			bd.type = BodyType.DYNAMIC;
			bd.position.set((float)Math.random()*35+15, 3.0f);
			bd.allowSleep = true;
			bd.linearDamping = 0.1f;
			bd.angularDamping = 0.1f;


			Body body = getWorld().createBody(bd);
			body.createFixture(particleDef);
			body.resetMassData();
		}

	}

	/**
	 * This function creates the levels in the game
	 * @return
	 */
	private ArrayList makeHills()
	{

		Body hillMaker = getWorld().createBody(sandbox);

		ArrayList<Integer> audioSample = new ArrayList<Integer>();
		
		audioSample = generateHills(audioSample, 500);
		int[] waveform2 = compressWaveform(Global.waveform);
		
		
		
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


			Body body = getWorld().createBody(bd);
			body.createFixture(particleDef);
			body.resetMassData();

		}

		return null;

	}

	
	private int[] compressWaveform(byte[] waveform) {
		
		//sum from start to checkpoint of signal[i]^2/(2*(end-start))
		//average of the powers is the zero
		
		int damping = 20000;
		int chunks = waveform.length/damping;
		int[] waveformSquared = new int[chunks];
		
		for(int i = 0; i < waveform.length; i+= damping)
		{
			System.out.println("elemnt = " + i);
			System.out.println("value = " + waveform[i]);
			//waveform2[counter] = waveform[i];	
		}
		
		return waveformSquared;
		
	}

	private ArrayList<Integer> generateHills(ArrayList<Integer> audioSample, int size) 
	{
		Random r = new Random();

		for(int i = 0; i < size; i++)
		{
			audioSample.add(r.nextInt(200));
		}


		return audioSample;

	}

	@Override
	public String getTestName() 
	{
		return "Level 1";
	}

}


