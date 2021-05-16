package jng.world;

import java.util.HashSet;
import java.util.Set;

import eea.engine.entity.Entity;
import jng.entities.PlayerPlaneEntity;

/**
 * This class provides a simple collision detection.
 * To guarantee its functionality {@link #entityAdded(Entity) has to be called
 * each time an entity is added in the system (that means all entity for which
 * the collsion detection may work and {@link #entityRemoved(Entity) has to be called
 * each time an entity is removed from the system (where the collision detection
 * shouldn't work for anymore).
 * 
 * By letting the programmer decide for which object the collision detection has to
 * work and which can be ignored because they are at the moment not relevant,
 * the performance can be improved greatly.
 * @author daniel
 *
 */
public class WorldManager {

	
	private static Set<Entity> entityList = new HashSet<Entity>();
	
	public static void entityAdded(Entity e)
	{
		entityList.add(e);
	}
	
	public static void entityRemoved(Entity e)
	{
		if (entityList.contains(e))
			entityList.remove(e);
	}
	
	/**
	 *  The collision detection is as simple as checking for each entity if the given 
	 *  entity collides with them resulting in O(n) operations.
	 * @param entity
	 * @return Entity the provided entity collides with.
	 */
	public static Entity collides(Entity entity)
	{
		Entity collisionEvent = null;
		for (Entity e : entityList)
		{
			if (e.colides(entity))
			{
				collisionEvent = e;
				if (collisionEvent instanceof PlayerPlaneEntity)
					return collisionEvent;				
			}
		}
		return collisionEvent;
	}
	
	public static Set<Entity> getEntityList() {
		return entityList;
	}
	
	public static void reset()
	{
		entityList.clear();
	}
}
