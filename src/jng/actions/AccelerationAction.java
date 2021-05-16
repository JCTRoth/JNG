package jng.actions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.component.Component;
import jng.entities.Attributes;

public class AccelerationAction implements Action{

	private Vector2f speed;
	
	private Vector2f acceleration;
	
	public AccelerationAction(Vector2f speed, Vector2f acceleration) {
		this.speed = speed;
		this.acceleration = acceleration;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta,
			Component event) {		
		Vector2f position = event.getOwnerEntity().getPosition();
		float h = delta;
		float x;
		if (speed.x < 0)
			x = Math.min(0, speed.x + h * Attributes.gravity.x - speed.x * h * 0.001f);
		else
			x = Math.max(0, speed.x + h * Attributes.gravity.x - speed.x * h * 0.001f);
		
		speed = new Vector2f(x, speed.y + h * Attributes.gravity.y);
		event.getOwnerEntity().setPosition(new Vector2f(
				(float) (position.x + h * speed.x + Math.pow(h, 2) / 2.0f * acceleration.x),
				(float) (position.y + h * speed.y + Math.pow(h, 2) / 2.0f * acceleration.y)));
		
	}



}
