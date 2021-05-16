package jng.actions.movements;

import org.newdawn.slick.geom.Vector2f;

import eea.engine.action.Action;
import eea.engine.action.basicactions.Movement;

public abstract class AIMovementAction extends Movement implements Action{
	
	protected Vector2f position;
	
	public AIMovementAction(float speed, Vector2f position)
	{
		super(speed);
		this.position = position;
	}

}
