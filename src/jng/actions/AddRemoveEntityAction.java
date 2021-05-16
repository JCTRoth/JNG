package jng.actions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.Event;
import jng.events.OwnerOutOfScreenEvent;
import jng.world.WorldManager;

public class AddRemoveEntityAction implements Action {
	
	private Entity addEntity;
	
	private Entity removeEntity;
	
	private int stateID;

	public AddRemoveEntityAction(Entity addEntity, Entity removeEntity, int stateID) {
		super();
		this.addEntity = addEntity;
		this.removeEntity = removeEntity;
		this.stateID = stateID;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta,
			Component event) {
		Event screenEvent = new OwnerOutOfScreenEvent("outofscreenevent", 600.0f, false);
		screenEvent.addAction(new RemoveOwnerAction(stateID));
		addEntity.addComponent(screenEvent);
		StateBasedEntityManager entityManager = StateBasedEntityManager.getInstance();
		entityManager.addEntity(stateID, addEntity);
		WorldManager.entityAdded(addEntity);
		entityManager.removeEntity(stateID, removeEntity);
		WorldManager.entityRemoved(removeEntity);
	}

}
