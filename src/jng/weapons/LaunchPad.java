package jng.weapons;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import eea.engine.entity.StateBasedEntityManager;
import jng.entities.GameEntity;
import jng.entities.Projectile;

public class LaunchPad {
	
	private Vector2f relativePosition;
	
	private float angle;
	
	private List<GameEntity> targets;
	
	private Projectile projectile;

	public LaunchPad(Vector2f relativePosition, float angle,
			Projectile projectile) {
		this.relativePosition = relativePosition;
		this.angle = angle;
		this.projectile = projectile;
		targets = new ArrayList<GameEntity>();
	}

	public LaunchPad(Vector2f relativePosition, float angle,
			Projectile projectile, List<GameEntity> target)
	{
		this(relativePosition, angle, projectile);
		this.targets = target;
	}
	
	public void fireLaunchPad(GameEntity pe, int stateID) throws SlickException
	{
		Vector2f globalPosition = new Vector2f(relativePosition.x + pe.getPosition().x,
				relativePosition.y + pe.getPosition().y);
		
		float d = angle;
		if (!targets.isEmpty())
		{
			GameEntity target = targets.get((int)(Math.floor(Math.random()*targets.size())));
			// filter out dead targets
			while (!targets.isEmpty() && target.isDead()) 
			{
				targets.remove(target);
				if (!targets.isEmpty())
					target = targets.get((int)(Math.floor(Math.random()*targets.size())));
			}
			Vector2f targetTemp = new Vector2f(target.getPosition().x - globalPosition.x,
					target.getPosition().y - globalPosition.y);//.sub();
			d = 180+(float)(180.0 / Math.PI * Math.acos(targetTemp.y/targetTemp.length()));
			if (targetTemp.x > 0)
				d *= -1;
		}
		Projectile p = new Projectile(stateID, pe, getProjectile());
		p.setPosition(globalPosition);
		p.setRotation(d);
		StateBasedEntityManager.getInstance().addEntity(stateID, p);
		//WorldManager.entityAdded(p);
		
	}

	@Override
	public String toString() {
		return relativePosition.x + " " +
				relativePosition.y + " " + 
				angle + " " + 
				targets + " " +
				projectile;
	}

	public List<GameEntity> getTargets() {
		return targets;
	}

	public void setTarget(List<GameEntity> target) {
		this.targets = target;
	}

	public Vector2f getRelativePosition() {
		return relativePosition;
	}

	public void setRelativePosition(Vector2f relativePosition) {
		this.relativePosition = relativePosition;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public Projectile getProjectile() {
		return projectile;
	}

	public void setProjectile(Projectile projectile) {
		this.projectile = projectile;
	}
	
	
	
	
}
