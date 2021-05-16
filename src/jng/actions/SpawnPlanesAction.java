package jng.actions;

import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.entity.StateBasedEntityManager;
import jng.entities.GameEntity;
import jng.world.WorldManager;

public class SpawnPlanesAction implements Action {

	private float frequency;
	
	private float passedTime;
	
	private int nFireOperations;
	
	private List<GameEntity> planes;
	
	private Vector2f position;
	
	public SpawnPlanesAction(List<GameEntity> planes, float frequency)
	{
		this.planes = planes;
		this.frequency = frequency;
		nFireOperations = 0;
		passedTime = 0.0f;
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta,
			Component event) {
		passedTime += delta;
		if (passedTime > 1/frequency &&
				nFireOperations < planes.size())
		{
			passedTime = 0.0f;
			GameEntity ge = planes.get(nFireOperations);
			ge.setPosition(getPosition());
			StateBasedEntityManager.getInstance().addEntity(sb.getCurrentStateID(), ge);
			WorldManager.entityAdded(ge);
			nFireOperations++;
		}
	}
	
	public List<GameEntity> getPlanes() {
		return planes;
	}

	@Override
	public String toString() {
		String s = "[ ";
		for (GameEntity ge : planes)
		{
			s += ge + " ";
		}
		s += " ]";
		return "SpawnPlanesAction "
				+ position.x + " "
				+ position.y + " "
				+ frequency + " "
				+ s;
				
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public float getFrequency() {
		return frequency;
	}
}
