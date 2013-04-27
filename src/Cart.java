import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;

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


public class Cart {

	public Node node;
	
	Body cart;
	RevoluteJoint motor1;
	RevoluteJoint motor2;
	
	Body axle1;
	Body axle2;
	
	Body wheel1;
	Body wheel2;

	PrismaticJoint spring1;
	PrismaticJoint spring2;
	
	private LinearGradient gradient;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public Cart()
	{

		//make cart
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DYNAMIC;
		bd.position.set(0.0f, 3.5f);

		cart = Utils.world.createBody(bd);

		////////////wheels////////
		wheel1 = Utils.world.createBody(bd);
		wheel2 = Utils.world.createBody(bd);

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
		axle1 = Utils.world.createBody(bd);

		bbox.setAsBox(0.4f, 0.1f, new Vec2((float)(-1.0f - 0.6f*Math.cos(Math.PI/3)), (float)(-0.3f - 0.6f*Math.sin(Math.PI/3))), (float) (Math.PI/3));
		axle1.createFixture(boxDef);
		axle1.resetMassData();

		PrismaticJointDef prismaticJointDef = new PrismaticJointDef();
		prismaticJointDef.initialize(cart, axle1, axle1.getWorldCenter(), new Vec2((float)Math.cos(Math.PI/3), (float)Math.sin(Math.PI/3)));
		prismaticJointDef.lowerTranslation = -0.3f;
		prismaticJointDef.upperTranslation = 0.5f;
		prismaticJointDef.enableLimit = true;
		prismaticJointDef.enableMotor = true;

		spring1 = (PrismaticJoint) Utils.world.createJoint(prismaticJointDef);

		axle2 = Utils.world.createBody(bd);

		bbox.setAsBox(0.4f, 0.1f, new Vec2((float)(1.0f + 0.6f*Math.cos(-Math.PI/3)), (float)(-0.3f + 0.6f*Math.sin(-Math.PI/3))), (float) (-Math.PI/3));
		boxDef.shape = bbox;
		axle2.createFixture(boxDef);
		axle2.resetMassData();

		prismaticJointDef.initialize(cart, axle2, axle2.getWorldCenter(), new Vec2((float)-Math.cos(Math.PI/3), (float)Math.sin(Math.PI/3)));

		spring2 = (PrismaticJoint) Utils.world.createJoint(prismaticJointDef);

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
				wheel1 = Utils.world.createBody(circleBodyDef);
			}

			else
			{
				wheel2 = Utils.world.createBody(circleBodyDef);
			}

			(i == 0 ? wheel1 : wheel2).createFixture(boxDef);
			(i == 0 ? wheel1 : wheel2).resetMassData();

		}


		//add joints

		RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
		revoluteJointDef.enableMotor = true;


		motor1 = new RevoluteJoint(Utils.world.getPool(), revoluteJointDef);
		motor2 = new RevoluteJoint(Utils.world.getPool(), revoluteJointDef);

		revoluteJointDef.initialize(axle1, wheel1, wheel1.getWorldCenter());
		motor1 = (RevoluteJoint) Utils.world.createJoint(revoluteJointDef);

		revoluteJointDef.initialize(axle2, wheel2, wheel2.getWorldCenter());
		motor2 = (RevoluteJoint) Utils.world.createJoint(revoluteJointDef);
		
		node = makeNode();

	}

	private Node makeNode() 
	{
		//Create an UI for ball - JavaFX code
        Circle ball = new Circle();
        ball.setRadius(5f);
        ball.setFill(new LinearGradient(0.0, 0.0, 1.0, 0.0, true, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0, Color.WHITE), new Stop(1, Color.RED)})); //set look and feel 
        
        /*
         * Set ball position on JavaFX scene. We need to convert JBox2D coordinates 
         * to JavaFX coordinates which are in pixels.
         */
        ball.setLayoutX(Utils.toPixelPosX(100)); 
        ball.setLayoutY(Utils.toPixelPosY(100));
       
        ball.setCache(true); //Cache this object for better performance
        
        //Create an JBox2D body definition for ball.
        BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.position.set(100, 100);
        
        CircleShape cs = new CircleShape();
        cs.m_radius = 5f * 0.1f;  //We need to convert radius to JBox2D equivalent
        
        // Create a fixture for ball
        FixtureDef fd = new FixtureDef();
        fd.shape = cs;
        fd.density = 0.9f;
        fd.friction = 0.3f;        
        fd.restitution = 0.6f;

        /*
        * Virtual invisible JBox2D body of ball. Bodies have velocity and position. 
        * Forces, torques, and impulses can be applied to these bodies.
        */
        Body body = Utils.world.createBody(bd);
        body.createFixture(fd);
        ball.setUserData(body);
        
        return ball;
	}

}


