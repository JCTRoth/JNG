package jng.entities;

import org.newdawn.slick.SlickException;

import jng.enums.Orientation;

public class Structure extends GameEntity{

	public Structure(String entityID, String picturePath, int stateID,
			Orientation orientation) throws SlickException {
		super(entityID, picturePath, stateID, orientation, true);
		setAffectedByDetonation(false);
		//setPassable(true);
		setCollidingWithStructures(false);
	}
	
	public Structure(String entityID, String picturePath, int stateID,
			Orientation orientation, boolean invincible) throws SlickException {
		super(entityID, picturePath, stateID, orientation, invincible);
		setAffectedByDetonation(false);
		//setPassable(true);
		setCollidingWithStructures(false);
	}

	@Override
	public String toString() {
		return "Structure "
				+ getPosition().x + " "
				+ getPosition().y + " "
				+ getRotation() + " "
				+ getScale() + " "
				+ entityID + " "
				+ picturePath + " "
				+ orientation.ordinal() + " "
				+ invincible + " "
				+ maxHealth + " "
				+ health + " "
				+ maxArmor + " "
				+ armor + " "
				+ (item == null ? "-1" : item.getType().ordinal());
	}
	
	


}
