package jng.events;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.entity.Entity;
import eea.engine.event.basicevents.CollisionEvent;
import jng.world.WorldManager;

public class ImprovedCollisionEvent extends CollisionEvent {

	private Entity collidedEntity;

	@Override
	protected boolean performAction(GameContainer gc, StateBasedGame sb,
			int delta) {

		Entity entity = WorldManager.collides(owner);

		if (entity != null) {
			collidedEntity = entity;
			return true;
		}
		return false;
	}
	
	@Override
	public Entity getColidedEntity() {
		return collidedEntity;
	}
}
