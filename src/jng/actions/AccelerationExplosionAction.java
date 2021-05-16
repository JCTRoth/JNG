package jng.actions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.component.Component;
import jng.entities.Attributes;

public class AccelerationExplosionAction implements Action{

	private Vector2f speed;
	
	// shows away from the explosion
	private Vector2f direction;
	
	public AccelerationExplosionAction(Vector2f direction, float distance) {
		this.direction = direction;
		float temp = Attributes.explosionFactor*(float)Math.pow(distance/100.0+1, -2.0);
		// v_expl
		speed = new Vector2f(direction.x*temp, direction.y*temp);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta,
			Component event) {	
		if (speed.length() < Attributes.minimumExplosionSpeed)
			return;
		
		Vector2f position = event.getOwnerEntity().getPosition();
		
		// f_damp
		float speedFactor = speed.length();
		Vector2f fd = new Vector2f(
				-direction.x*speedFactor*Attributes.dampingFactor,
				-direction.y*speedFactor*Attributes.dampingFactor);
		
		
		float h = delta;
		speed = new Vector2f(
				speed.x + h * fd.x,
				speed.y + h * fd.y);
		
		float temp = Math.min(1.0f, Math.max(-1.0f, new Vector2f(speed).dot(direction)/(speed.length()*direction.length())));
		float angle = (float)Math.acos(temp);
		
		if (angle > Math.PI/2.0)
		{
			speed.x = 0.0f;
			speed.y = 0.0f;
		}
//		Vector2f speedDirection = new Vector2f(speed).normalise();
//		if (Math.abs(speedDirection.x - direction.x) < 0.01)
//			speed.x = 0.0f;
//		if (Math.abs(speedDirection.y - direction.y) < 0.01)
//			speed.y = 0.0f;
		
		event.getOwnerEntity().setPosition(new Vector2f(
				position.x + h * speed.x,
				position.y + h * speed.y));
		
		
//		event.getOwnerEntity().removeComponent(event);
		
	}
}
