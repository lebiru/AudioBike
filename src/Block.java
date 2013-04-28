import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Block {

	public Node node;
	
	private float posX;
	private float posY;
	private float width;
	private float height;	
	
	private BodyType bodyType;
	
	private Color color ;
	
	public Block(float posX, float posY, float width, float height, BodyType bodyType, Color color)
	{
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.bodyType = bodyType;
		this.color = color;
		node = create();
	}
	
	private Node create()
	{
		Rectangle rect = new Rectangle();
		rect.setWidth(width);
		rect.setHeight(height);
		rect.setFill(this.color);

		rect.setLayoutX(Utils.toPixelPosX(width));
		rect.setLayoutY(Utils.toPixelPosY(height));
		
		
		BodyDef b = new BodyDef();
		b.type = bodyType;
		b.position.set(width, height);

		
		PolygonShape bbox = new PolygonShape();
		bbox.setAsBox(width * 0.1f, height *0.1f, new Vec2(0.0f, 0.5f), 0.0f);

		FixtureDef fixtureDef = new FixtureDef();

		fixtureDef.shape = bbox;
		Body body = Utils.world.createBody(b);
		body.createFixture(fixtureDef);
		rect.setUserData(body);
		return rect;
		
	}

}
