package jng.events;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.event.Event;
import jng.ui.Controls;
import jng.ui.GameplayState;

public class OwnerOutOfScreenEvent extends Event{
	
	private float offset;
	private boolean isInverted;

	/**
	 * This Event fires when the position of the owner of it is outside the screen.
	 * @param id
	 * @param offset is the amount of space the owner can leave the screen
	 * 		before this event fires.
	 */
	public OwnerOutOfScreenEvent(String id, float offset, boolean isInverted) {
		super(id);
		this.offset = offset;
		this.isInverted = isInverted;
	}

	@Override
	protected boolean performAction(GameContainer gc, StateBasedGame sb,
			int delta) {
		float cameraPositionX = GameplayState.cameraPosition.x;
		int screenSizeX = Controls.displayResolution.x;
		int screenSizeY = Controls.displayResolution.y;
		if (owner.getShape().getMaxX() >= cameraPositionX + screenSizeX + offset ||
				owner.getShape().getMinX() <= cameraPositionX - offset ||
				owner.getShape().getMaxY() >= screenSizeY + offset ||
				owner.getShape().getMinY() <= -offset)
			return !isInverted;
		return isInverted;
	}

}
