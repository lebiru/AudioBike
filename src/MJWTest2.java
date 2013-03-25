import org.jbox2d.collision.shapes.PolygonShape;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.joints.PrismaticJoint;
import org.jbox2d.dynamics.joints.PrismaticJointDef;
import org.jbox2d.testbed.framework.TestbedTest;

public class MJWTest2 extends TestbedTest {


	@Override
	public void initTest(boolean argDeserialized) {
		setTitle("Level 1");

		getWorld().setGravity(new Vec2(0.0f, -10.0f));
		boolean doSleep = true;
		getWorld().setAllowSleep(doSleep);

		makeVehicle();
		makeLevel();

	}

	private void makeVehicle() 
	{


	}

	private void makeLevel() {

		BodyDef b = new BodyDef();
		b.position.set(0.0f, 0.5f);

		PolygonShape bbox = new PolygonShape();
		bbox.setAsBox(50.0f, 0.5f);



		Body body = getWorld().createBody(b);
		FixtureDef fixtureDef = new FixtureDef();

		bbox.setAsBox(1.0f, 2.0f, new Vec2(-50.0f, 0.5f), 0.0f);
		fixtureDef.shape = bbox;
		body.createFixture(fixtureDef);

		bbox.setAsBox(1.0f, 2.0f, new Vec2(50.0f, 0.5f), 0.0f);
		fixtureDef.shape = bbox;
		body.createFixture(fixtureDef);

		bbox.setAsBox(50.0f, 1.0f, new Vec2(0.0f, 0.5f), 0.0f);
		fixtureDef.shape = bbox;
		body.createFixture(fixtureDef);

		bbox.setAsBox(3.0f, 0.5f, new Vec2(5.0f, 1.5f), (float)Math.PI/4);
		fixtureDef.shape = bbox;
		body.createFixture(fixtureDef);

		bbox.setAsBox(3.0f, 0.5f, new Vec2(3.5f, 1.0f), (float)Math.PI/8);
		fixtureDef.shape = bbox;
		body.createFixture(fixtureDef);

		bbox.setAsBox(3.0f, 0.5f, new Vec2(9.0f, 1.5f), (float)-Math.PI/4);
		fixtureDef.shape = bbox;
		body.createFixture(fixtureDef);

		bbox.setAsBox(3.0f, 0.5f, new Vec2(10.5f, 1.0f), (float)-Math.PI/8);
		fixtureDef.shape = bbox;
		body.createFixture(fixtureDef);

		makeParticles();

		makeBike();


		body.resetMassData();

	}

	private void makeBike() 
	{


		//make cart
		BodyDef bd = new BodyDef();
		bd.position.set(0.0f, 3.5f);

		Body cart = getWorld().createBody(bd);

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
		
		PrismaticJoint spring1 = (PrismaticJoint) getWorld().createJoint(prismaticJointDef);
		
		Body axle2 = getWorld().createBody(bd);
		
		bbox.setAsBox(0.4f, 0.1f, new Vec2((float)(1.0f + 0.6f*Math.cos(-Math.PI/3)), (float)(-0.3f + 0.6f*Math.sin(-Math.PI/3))), (float) (-Math.PI/3));
		boxDef.shape = bbox;
		axle2.createFixture(boxDef);
		axle2.resetMassData();
		
		prismaticJointDef.initialize(cart, axle2, axle2.getWorldCenter(), new Vec2((float)-Math.cos(Math.PI/3), (float)Math.sin(Math.PI/3)));
		
		PrismaticJoint spring2 = (PrismaticJoint) getWorld().createJoint(prismaticJointDef);
		
		//add wheels
		




	}

	private void makeParticles() 
	{


		for (int i = 0; i < 30; i++) 
		{

			PolygonShape dynamicBox = new PolygonShape();
			dynamicBox.setAsBox(0.05f, 0.05f);

			FixtureDef fd = new FixtureDef();
			fd.density = 0.01f;
			fd.friction = 0.1f;
			fd.restitution = 0.5f;
			dynamicBox.m_radius = (float) ((float) Math.random()/20+0.02);
			fd.shape = dynamicBox;

			BodyDef bd = new BodyDef();
			bd.type = BodyType.DYNAMIC;
			bd.position.set((float)Math.random()*35+15, 3.0f);
			bd.allowSleep = true;
			bd.linearDamping = 0.1f;
			bd.angularDamping = 0.1f;


			Body body = getWorld().createBody(bd);
			body.createFixture(fd);
			body.resetMassData();
		}

	}

	@Override
	public String getTestName() {
		return "Level 1";
	}

}


