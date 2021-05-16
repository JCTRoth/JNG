package jng.factories;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import jng.ai.AIBehaviour;
import jng.entities.Attributes;
import jng.entities.GameEntity;
import jng.entities.PlayerPlaneEntity;
import jng.enums.Orientation;
import jng.world.World;

public class PlaneFactory {
	
	public static GameEntity createStandardPlaneEntity(
			String entityID, final int stateID) throws SlickException
	{
		GameEntity ppe = new GameEntity(entityID, "/assets/plane1Left.png", stateID, Orientation.ENEMY, false);
		ppe.setRotation(-90.0f);
		
		return ppe;
	}
	
	public static GameEntity createStandardPlaneEntityWithWeapon(
			String entityID, final int stateID) throws SlickException
	{
		GameEntity ppe = createStandardPlaneEntity(entityID, stateID);
		ppe.getWeapons().add(0, WeaponFactory.createMGSingleFireLeft(ppe));
		return ppe;
	}
	
	public static PlayerPlaneEntity createStandardPlayerPlaneEntity(World w) throws SlickException
	{
		PlayerPlaneEntity ppe = PlaneFactory.createStandardPlayerPlaneEntity("PlayerPlane", -1,
				Input.KEY_UP, Input.KEY_DOWN, Input.KEY_LEFT, Input.KEY_RIGHT, Input.KEY_Y, Input.KEY_X, Input.KEY_C);
		ppe.setSpeed(w.getCameraSpeed());
		ppe.setCameraSpeed(w.getCameraSpeed());
		ppe.setPosition(new Vector2f(400, 300));
		return ppe;
	}
	
	public static PlayerPlaneEntity createStandardPlayerPlaneEntity(
			String entityID, 
			final int stateID, 
			int key_up,
			int key_down,
			int key_left,
			int key_right,
			int key_mg,
			int key_rocket,
			int key_bomb) throws SlickException
	{
		return createStandardPlayerPlaneEntity(entityID, stateID, "/assets/plane1Right.png", key_up, key_down, key_left, key_right, key_mg, key_rocket, key_bomb);
	}
	
	public static PlayerPlaneEntity createStandardPlayerPlaneEntity(
			String entityID, 
			final int stateID,
			String picturePath,
			int key_up,
			int key_down,
			int key_left,
			int key_right,
			int key_mg,
			int key_rocket,
			int key_bomb) throws SlickException
	{
		PlayerPlaneEntity ppe = new PlayerPlaneEntity(entityID, picturePath,
				stateID, Orientation.ALLIED, key_up, key_down, key_left, key_right, key_mg, key_rocket, key_bomb);
		ppe.setRotation(90.0f);
		ppe.setWeapons();
    	ppe.setArmor(Attributes.playerPlaneArmor);
		
		return ppe;
	}
	
	public static PlayerPlaneEntity createMultiplayerPlayer1PlaneEntity(
			final int stateID, 
			int key_up,
			int key_down,
			int key_left,
			int key_right,
			int key_mg,
			int key_rocket,
			int key_bomb) throws SlickException
	{
		PlayerPlaneEntity ppe = new PlayerPlaneEntity("PlayerPlaneP1", "/assets/plane1RightP1.png",
				stateID, Orientation.ALLIED, key_up, key_down, key_left, key_right, key_mg, key_rocket, key_bomb);
		ppe.setRotation(90.0f);
    	ppe.setWeapons();
    	ppe.setArmor(Attributes.playerPlaneArmor);
		
		return ppe;
	}
	
	public static PlayerPlaneEntity createMultiplayerPlayer2PlaneEntity(
			final int stateID, 
			int key_up,
			int key_down,
			int key_left,
			int key_right,
			int key_mg,
			int key_rocket,
			int key_bomb) throws SlickException
	{
		PlayerPlaneEntity ppe = new PlayerPlaneEntity("PlayerPlaneP2", "/assets/plane1RightP2.png",
				stateID, Orientation.ALLIED, key_up, key_down, key_left, key_right, key_mg, key_rocket, key_bomb);
		ppe.setRotation(90.0f);
		ppe.setWeapons();
    	ppe.setArmor(Attributes.playerPlaneArmor);
		
		return ppe;
	}
	
	public static PlayerPlaneEntity createMultiplayerPlayer3PlaneEntity(
			final int stateID, 
			int key_up,
			int key_down,
			int key_left,
			int key_right,
			int key_mg,
			int key_rocket,
			int key_bomb) throws SlickException
	{
		PlayerPlaneEntity ppe = new PlayerPlaneEntity("PlayerPlaneP3", "/assets/plane1RightP3.png",
				stateID, Orientation.ALLIED, key_up, key_down, key_left, key_right, key_mg, key_rocket, key_bomb);
		ppe.setRotation(90.0f);
		ppe.setWeapons();
    	ppe.setArmor(Attributes.playerPlaneArmor);
		
		return ppe;
	}
	
	public static GameEntity createStandardTower(
			float x, float y,
			final int stateID, GameEntity... target) throws SlickException
	{
		GameEntity pe = new GameEntity("towerMG", "/assets/towerWeak.png", stateID, Orientation.ENEMY, false);
		pe.setCollidingWithStructures(false);
		pe.setAffectedByDetonation(false);
		pe.setPosition(new Vector2f(x, y));
		pe.setSpeed(0.0f);
		WeaponFactory.setWeaponsOfType(pe, 13, target);
		pe.setAiBehaviour(new AIBehaviour(pe, true));
		return pe;
	}
	
	public static GameEntity createRocketTower(
			float x, float y,
			final int stateID, GameEntity... target) throws SlickException
	{
		GameEntity pe = new GameEntity("towerRocket", "/assets/rocketTowerWeak.png", stateID, Orientation.ENEMY, false);
		pe.setCollidingWithStructures(false);
		pe.setAffectedByDetonation(false);
		pe.setPosition(new Vector2f(x, y));
		pe.setSpeed(0.0f);
		WeaponFactory.setWeaponsOfType(pe, 14, target);
		pe.setAiBehaviour(new AIBehaviour(pe, true));
		return pe;
	}
	
	public static GameEntity createRandomTower(float x, float y, 
			int stateID, GameEntity target) throws SlickException
	{
		return (Math.random()<0.5?createStandardTower(x, y, stateID, target) :
			createRocketTower(x, y, stateID, target));
	}
	
	public static GameEntity createRandomPlane(float x, float y, int stateID, int difficulty, PlayerPlaneEntity... targets) throws SlickException
	{
		if (difficulty < 5)
			return createRandomWeakPlane(x, y, stateID, targets);
		return createRandomStrongPlane(x, y, stateID, targets);
	}
	
	public static GameEntity createRandomWeakPlane(float x, float y, int stateID, PlayerPlaneEntity... targets) throws SlickException
	{
		GameEntity ge = createPlaneOfType(stateID, (int)Math.floor(Math.random()*6)+1, targets);
		ge.setPosition(new Vector2f(x, y));
		if (Math.random() < Attributes.itemDropProbability)
			ge.setItem(ItemFactory.createRandomItem());
		return ge;
	}
	
	public static GameEntity createRandomStrongPlane(float x, float y, int stateID, PlayerPlaneEntity... targets) throws SlickException
	{
		GameEntity ge = createPlaneOfType(stateID, (int)Math.floor(Math.random()*6)+7, targets);
		ge.setPosition(new Vector2f(x, y));
		if (Math.random() < Attributes.itemDropProbability)
			ge.setItem(ItemFactory.createRandomItem());
		return ge;
	}
	
	public static GameEntity createRandomTower(float x, float y, 
			int stateID, int difficulty, GameEntity... targets) throws SlickException
	{
		GameEntity pe;
		// 1 - mgTower; 2 - rocketTower; 3 - rocketTeslaStation
		int type = 1;
		double rnd = Math.random();
		if (rnd < 0.15)
			type = 3;
		else if (rnd < 0.45)
			type = 2;
		if (difficulty < 4)
		{
			if (type == 2)
				pe = new GameEntity("towerWeak", "/assets/rocketTowerWeak.png", stateID, Orientation.ENEMY, false);
			else if (type == 1)
				pe = new GameEntity("towerRocketWeak", "/assets/towerWeak.png", stateID, Orientation.ENEMY, false);
			else
				pe = new GameEntity("towerRocketTeslaStationWeak", "/assets/rocketTeslaStationWeak.png", stateID, Orientation.ENEMY, false);
			pe.setMaxHealth(Attributes.towerHealthWeak);
			pe.setHealth(Attributes.towerHealthWeak);
		}
		else if (difficulty < 7)
		{
			if (type == 2)
				pe = new GameEntity("towerRocketStrong", "/assets/rocketTowerStrong.png", stateID, Orientation.ENEMY, false);
			else if (type == 1)
				pe = new GameEntity("towerMGStrong", "/assets/towerStrong.png", stateID, Orientation.ENEMY, false);
			else
				pe = new GameEntity("towerRocketTeslaStationStrong", "/assets/rocketTeslaStationStrong.png", stateID, Orientation.ENEMY, false);
			pe.setMaxHealth(Attributes.towerHealthStrong);
			pe.setHealth(Attributes.towerHealthStrong);
		}
		else
		{
			if (type == 2)
				pe = new GameEntity("towerRocketStrongest", "/assets/rocketTowerStrongest.png", stateID, Orientation.ENEMY, false);
			else if (type == 1)
				pe = new GameEntity("towerMGStrongest", "/assets/towerStrongest.png", stateID, Orientation.ENEMY, false);
			else
				pe = new GameEntity("towerRocketTeslaStationStrongest", "/assets/rocketTeslaStationStrongest.png", stateID, Orientation.ENEMY, false);
			pe.setMaxHealth(Attributes.towerHealthStrongest);
			pe.setHealth(Attributes.towerHealthStrongest);
		}
		pe.setPosition(new Vector2f(x, y));
		pe.setCollidingWithStructures(false);
		pe.setAffectedByDetonation(false);
		pe.setSpeed(0.0f);
		if (type == 1) // mg
			WeaponFactory.setWeaponsOfType(pe, 13, targets);
		else if (type == 2) // rocket
			WeaponFactory.setWeaponsOfType(pe, 14, targets);
		else
			WeaponFactory.setWeaponsOfType(pe, 15, targets);
		pe.setAiBehaviour(new AIBehaviour(pe, true));
		return pe;
	}
	
	/**
	 * create planes of type weak (1-6) and stronger planes (7-12). Weak planes do only have a single shot weapon 
	 * with a either higher fire rate and low damage or a low fire rate and medium damage. Also, their health
	 * is low. Strong planes can have multi shot weapons and shields.
	 * @param stateID
	 * @param number - there are 12 different planes (1-12)
	 * @return
	 * @throws SlickException
	 */
	public static GameEntity createPlaneOfType(int stateID, int number, GameEntity... target) throws SlickException
	{
		GameEntity pe = new GameEntity("plane"+number, "/assets/planes/plane" + number + ".png", stateID, Orientation.ENEMY, false);
		pe.setRotation(-90.0f);
		
		float health = Attributes.aiPlaneHealthWeak;
		float armor = Attributes.aiPlaneArmorWeak;
		float speed = Attributes.aiPlaneSpeedMedium;
		switch(number)
		{
		case 1:
			WeaponFactory.setWeaponsOfType(pe, 1, target);
			break;
		case 2:
			WeaponFactory.setWeaponsOfType(pe, 2, target);
			break;
		case 3:
			WeaponFactory.setWeaponsOfType(pe, 3, target);
			break;
		case 4:
			WeaponFactory.setWeaponsOfType(pe, 4, target);
			break;
		case 5:
			WeaponFactory.setWeaponsOfType(pe, 5, target);
			break;
		case 6:
			WeaponFactory.setWeaponsOfType(pe, 6, target);
			break;
		case 7:
			WeaponFactory.setWeaponsOfType(pe, 7, target);
			break;
		case 8:
			WeaponFactory.setWeaponsOfType(pe, 8, target);
			health = Attributes.aiPlaneHealtStrong;
			armor = Attributes.aiPlaneArmorStrong;
			speed = Attributes.aiPlaneSpeedWeak;
			break;
		case 9:
			WeaponFactory.setWeaponsOfType(pe, 9, target);
			health = Attributes.aiPlaneHealthMedium;
			armor = Attributes.aiPlaneArmorMedium;
			speed = Attributes.aiPlaneSpeedMedium;
			break;
		case 10:
			WeaponFactory.setWeaponsOfType(pe, 10, target);
			health = Attributes.aiPlaneHealtStrong;
			armor = Attributes.aiPlaneArmorStrong;
			speed = 0.0025f;
			break;
		case 11:
			WeaponFactory.setWeaponsOfType(pe, 11, target);
			health = Attributes.aiPlaneHealthMedium;
			armor = Attributes.aiPlaneArmorMedium;
			speed = Attributes.aiPlaneSpeedMedium;
			break;
		case 12:
			WeaponFactory.setWeaponsOfType(pe, 12, target);
			health = Attributes.aiPlaneHealtStrong;
			armor = Attributes.aiPlaneArmorWeak;
			speed = Attributes.aiPlaneSpeedStrong;
			break;
		}
		pe.setHealth(health);
		pe.setMaxHealth(health);
		pe.setArmor(armor);
		pe.setMaxArmor(armor);
		pe.setSpeed(speed);
		return pe;
	}
}
