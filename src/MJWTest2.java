import org.jbox2d.collision.shapes.PolygonShape;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.testbed.framework.TestbedTest;

public class MJWTest2 extends TestbedTest {


	@Override
	public void initTest(boolean argDeserialized) {
		setTitle("Level 1");

		getWorld().setGravity(new Vec2(0.0f, -10.0f));
		boolean doSleep = true;
		getWorld().setAllowSleep(doSleep);
		
		BodyDef groundBodyDef = new BodyDef(); // body definition
		groundBodyDef.position.set(0.0f, -10.0f); // set bodydef position
		Body groundBody = getWorld().createBody(groundBodyDef); // create body based on definition
		PolygonShape groundBox = new PolygonShape(); // make a shape representing ground
		groundBox.setAsBox(50.0f, 10.0f); // shape is a rect: 100 wide, 20 high
		groundBody.createFixture(groundBox, 0.0f); // bind shape to ground body
		
		BodyDef leftWall = new BodyDef();
		leftWall.position.set(-20.0f, 0.0f);
		Body leftWallBody = getWorld().createBody(leftWall);
		PolygonShape leftWallBox = new PolygonShape();
		leftWallBox.setAsBox(5.0f, 100.0f);
		leftWallBody.createFixture(leftWallBox, 0.0f);

		
		

		for (int i = 0; i < 30; i++) {
			PolygonShape polygonShape = new PolygonShape();
			polygonShape.setAsBox(1, 1);

			BodyDef bodyDef = new BodyDef();
			bodyDef.type = BodyType.DYNAMIC;
			bodyDef.position.set(5 * i, 0);
			bodyDef.angle = (float) (Math.PI / 4 * i);
			bodyDef.allowSleep = false;
			Body body = getWorld().createBody(bodyDef);
			body.createFixture(polygonShape, 5.0f);

			body.applyForce(new Vec2(-10000 * (i - 1), 0), new Vec2());
		}
		
	}

	@Override
	public String getTestName() {
		return "Level 1";
	}

}


