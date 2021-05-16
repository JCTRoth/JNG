package jng.ai;

import org.newdawn.slick.geom.Vector2f;

import eea.engine.action.Action;
import eea.engine.event.Event;
import jng.actions.AccelerationAction;
import jng.actions.movements.AIMovementAction;
import jng.actions.movements.AIMovementLinear;
import jng.entities.Attributes;
import jng.entities.GameEntity;
import jng.events.CameraMovementEvent;
import jng.ui.Controls;

public class AIBehaviour {
	
	private boolean fireModeOn;
	
	private GameEntity pe;
	
	private Vector2f[] path = null;
	
	private Action movement = null;
	
	public AIBehaviour(GameEntity pe)
	{
		this.pe = pe;
		fireModeOn = false;
	}

	public AIBehaviour(GameEntity pe, boolean fireModeOn) {
		this.fireModeOn = fireModeOn;
		this.pe = pe;
		
	}
	
	public AIBehaviour(AIBehaviour ai, GameEntity pe) {
		this.fireModeOn = ai.fireModeOn;
		this.pe = pe;
		if (ai.path != null)
			setMovement(ai.path);
	}
	
	/**
	 * Set a linear movement. The Plane moves on the specified path.
	 * The first movement goes from the first to the second point in the path.
	 * Therefore, the starting position of the plane should be equal to path[0].
	 * @param path
	 */
	public void setMovement(Vector2f... path) {
		this.path = new Vector2f[path.length];
		for (int i = 0; i < path.length; ++i)
			this.path[i] = new Vector2f(path[i]);
		
		Event triggerEvent = 
				new CameraMovementEvent("camera movement event linear", Controls.displayResolution.x, 20.0f);
				
		movement = new AIMovementLinear(pe.getSpeed(), 0, 1,
				this);
		
		triggerEvent.addAction(movement);
		pe.addComponent(triggerEvent);
	}
	
	/**
	 * The plane moves from the current position straight to the left.
	 * @param ge
	 */
	public void setMovementLinear()
	{
		setMovement(getPath(pe.getPosition(), new Vector2f(-pe.getPosition().x, 0)));
	}
	
	public void setMovement(Action movement)
	{
		Event triggerEvent = 
				new CameraMovementEvent("camera movement event acceleration", Controls.displayResolution.x, 20.0f);
		
		triggerEvent.addAction(movement);
		pe.addComponent(triggerEvent);
	}
	
	public void setMovementAcceleration()
	{
		if (this.movement != null)
			pe.removeComponent("camera movement event linear");
		
		Vector2f speed = new Vector2f();
		if (this.movement != null && this.movement instanceof AIMovementLinear)
			speed = ((AIMovementLinear)this.movement).getSpeedInDirection();
		
		Action movement = new AccelerationAction(speed, Attributes.gravity);
		setMovement(movement);
	}
	
	public static Vector2f[] getPath(Vector2f... path){
		Vector2f[] vr = new Vector2f[path.length];
		vr[0] = path[0];
		for (int i = 1; i < path.length; ++i)
		{
			vr[i] = new Vector2f(
					vr[i-1].x + path[i].x,
					vr[i-1].y + path[i].y);
		}
		return vr;
	}
	
	/**
	 * do not call this method but instead the one in PlaneEntity.
	 * @param triggerEvent
	 * @param movement
	 */
	public void setTriggerAndMovement(Event triggerEvent, AIMovementAction movement)
	{
		triggerEvent.addAction(movement);
	}

	public boolean isFireModeOn() {
		return fireModeOn;
	}

	public void setFireModeOn(boolean fireModeOn) {
		this.fireModeOn = fireModeOn;
	}

	public Vector2f[] getPath() {
		return path;
	}

	@Override
	public String toString() {
		String s = "[ ";
		if (path != null)
			for (Vector2f p : path)
				s += p.x + " " + p.y + " ";
		s += "]";
		return fireModeOn + " " + s;
				
	}
	
	
}
