package jng.events;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.event.Event;
import jng.ui.GameplayState;

public class CameraMovementEvent extends Event{

	private float screenSizeX;
	
	private float offset;
	
	public CameraMovementEvent(String id, float screenSizeX, float offset) {
		super(id);
		this.screenSizeX = screenSizeX;
		this.offset = offset;
	}

	@Override
	protected boolean performAction(GameContainer gc, StateBasedGame sb,
			int delta) {
		
		float cameraPositionX = GameplayState.cameraPosition.x;
		if (cameraPositionX + screenSizeX + offset + owner.getShape().getWidth() >= owner.getPosition().x &&
				owner.getPosition().x > cameraPositionX - offset - owner.getShape().getWidth())
			return true;
		return false;
	}

}
