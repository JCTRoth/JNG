package jng.actions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.component.Component;
import jng.entities.GameEntity;
import jng.statistics.GameStatistics;
import jng.weapons.Weapon;

public class FireKeyAction implements Action{
	
	public static int NO_KEY = -1;
	
	private int stateID;
	
	private int key;
	
	private float timeCounter;
	
	private float frequencyInv;
	
	private GameEntity planeEntity;
	
	private int weaponIndex;
	
	/**
	 * This action allows to fire a weapon of a given planeEntity when
	 * a specified key is pressed. 
	 * @param stateID
	 * @param key
	 * @param planeEntity
	 * @param weaponIndex 0 - primary, 1 - secundary_rockets, 2 - secundary_bombs, 3 - special
	 */
	public FireKeyAction(int stateID, int key, GameEntity planeEntity, int weaponIndex)
	{
		this.stateID = stateID;
		this.key = key;
		this.planeEntity = planeEntity;
		this.timeCounter = frequencyInv;
		this.weaponIndex = weaponIndex;
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta,
			Component event) {
		timeCounter += delta;
		
		try {
			if (gc.getInput().isKeyDown(key))
			{
				if (planeEntity.getWeapons().size() < weaponIndex+1 ||
						planeEntity.getWeapons().get(weaponIndex) == null)
					return;
				Weapon w = planeEntity.getWeapons().get(weaponIndex);
				if (w == null)
					return;
				if (timeCounter >= 1 / w.getFrequency())
				{
					timeCounter = 0;

					// Increment Statics by Weapon Type
					if (weaponIndex == 0) {
						GameStatistics.getInstance().incrementMgShotsFired();
					} else if (weaponIndex == 1) {
						GameStatistics.getInstance().incrementRocketsFired();
					} else if (weaponIndex == 2) {
						GameStatistics.getInstance().incrementBombsDropped();
					}

					// Allgemeine Schussstatistik
					GameStatistics.getInstance().incrementShotsFired();

					w.fireWeapon(planeEntity, stateID);
				}
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}
}
