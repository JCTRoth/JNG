package jng.actions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.component.Component;
import jng.entities.GameEntity;
import jng.weapons.Weapon;

public class FireAction implements Action{
	
	public static int NO_KEY = -1;
	
	private int stateID;
	
	private float timeCounter;
	
	private GameEntity planeEntity;
	
	private int weaponIndex;
	
	public FireAction(int stateID, GameEntity planeEntity, int weaponIndex)
	{
		this.stateID = stateID;
		this.timeCounter = 0.0f;
		this.planeEntity = planeEntity;
		this.weaponIndex = weaponIndex;
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta,
			Component event) {
		timeCounter += delta;
		if (planeEntity.getWeapons().size() < weaponIndex+1 ||
				planeEntity.getWeapons().get(weaponIndex) == null)
			return;
		Weapon w = planeEntity.getWeapons().get(weaponIndex);
		if (w == null)
			return;
		float frequencyInv = 1 / w.getFrequency();
		try {
			if (timeCounter >= frequencyInv)
			{
				timeCounter = 0;
				w.fireWeapon(planeEntity, stateID);
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}
}
