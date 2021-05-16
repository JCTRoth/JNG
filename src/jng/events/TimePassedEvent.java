package jng.events;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.event.Event;

public class TimePassedEvent extends Event{
	
	float passedTime;
	
	float time;
	
	int keyType;
	
	public TimePassedEvent(int keyType)
	{
		super("TimePassedEvent");
		time = 0;
		passedTime = 0;
		this.keyType = keyType;
	}
	
	public TimePassedEvent(String id, int keyType) {
		super(id);
		time = 0;
		passedTime = 0;
		this.keyType = keyType;
	}
	
	public TimePassedEvent(String id, int keyType, float time) {
		super(id);
		this.time = time;
		passedTime = 0;
		this.keyType = keyType;
	}
	
	
	@Override
	protected boolean performAction(GameContainer gc, StateBasedGame sb, int delta)
	{   
		passedTime += delta;
		if (passedTime >= time)
		{
			passedTime = 0;
			return true;
		}
		return false;
	}

}
