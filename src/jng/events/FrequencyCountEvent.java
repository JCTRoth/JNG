package jng.events;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.event.Event;

public class FrequencyCountEvent extends Event {

	private float frequency;
	
	private int nFireOperations;
	
	private float passedTime;
	
	/**
	 * An event that fires nFireOperations times with a given frequency.
	 * @param id
	 * @param frequency
	 * @param nFireOperations
	 */
	public FrequencyCountEvent(String id, float frequency, int nFireOperations) {
		super(id);
		this.frequency = frequency;
		this.nFireOperations = nFireOperations;
		this.passedTime = 0.0f;
	}

	@Override
	protected boolean performAction(GameContainer gc, StateBasedGame sb,
			int delta) {
		passedTime += delta;
		if (passedTime > 1/frequency &&
				nFireOperations > 0)
		{
			passedTime = 0.0f;
			nFireOperations--;
			return true;
		}
		return false;
	}

}
