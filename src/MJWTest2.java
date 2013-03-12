import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
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


		body.resetMassData();

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


