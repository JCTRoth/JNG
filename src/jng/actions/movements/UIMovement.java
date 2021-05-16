package jng.actions.movements;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.component.Component;

public class UIMovement implements Action{

	private float speed;
	
	private int up_key;
	
	private int left_key;
	
	private int right_key;
	
	private int down_key;
	
	
	public UIMovement(float speed, int up_key, int down_key, int left_key, int right_key) {
		this.speed = speed;
		this.up_key = up_key;
		this.left_key = left_key;
		this.right_key = right_key;
		this.down_key = down_key;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta,
			Component event) {
		// determine the target position, based on the current position, movement
	    // speed, orientation of the object, and time passed
	    Vector2f position = getNextPosition(event.getOwnerEntity().getPosition(),
	        speed, event.getOwnerEntity().getRotation(), delta, gc);

	    // let the object assume the new position
	    event.getOwnerEntity().setPosition(position);
	}


	public Vector2f getNextPosition(Vector2f position, float speed,
			float rotation, int delta, GameContainer gc) {
		Vector2f direction = new Vector2f(0, 0);
		if (gc.getInput().isKeyDown(up_key))
			direction.y -= 1;
		if (gc.getInput().isKeyDown(left_key))
			direction.x -= 1;
		if (gc.getInput().isKeyDown(right_key))
			direction.x += 1;
		if (gc.getInput().isKeyDown(down_key))
			direction.y += 1;
		direction.normalise();
		return new Vector2f(position.x + delta * speed * direction.x,
				position.y + delta * speed * direction.y);
	}

}
