package jng.actions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.entity.StateBasedEntityManager;
import jng.world.WorldManager;

public class RemoveOwnerAction implements Action{

	private int stateID;
	
	
	public RemoveOwnerAction(int stateID) {
		super();
		this.stateID = stateID;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta,
			Component event) {
		StateBasedEntityManager.getInstance().removeEntity(stateID, event.getOwnerEntity());
		WorldManager.entityRemoved(event.getOwnerEntity());
	}

}
