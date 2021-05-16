package jng.factories;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import jng.entities.Attributes;
import jng.entities.GameEntity;
import jng.entities.Structure;
import jng.entities.Item.Type;
import jng.enums.Orientation;

public class StructureFactory {
	
	public static GameEntity createBoxSmall(float x, float y, int stateID, Type item) throws SlickException
	{
		GameEntity pe = createBoxBig("Small", x, y, stateID, item);
		pe.setScale(0.25f);
		return pe;
	}
	
	public static GameEntity createBox(float x, float y, int stateID, Type item) throws SlickException
	{
		GameEntity pe = createBoxBig("Box", x, y, stateID, item);
		pe.setScale(0.5f);
		return pe;
	}

	public static GameEntity createBoxBig(float x, float y, int stateID, Type item) throws SlickException
	{
		return createBoxBig("BoxBig", x, y, stateID, item);
	}
	
	private static GameEntity createBoxBig(String entityID, float x, float y, int stateID, Type item) throws SlickException
	{
		GameEntity pe = new Structure(entityID, "/assets/structures/box.png", stateID, Orientation.NEUTRAL);
		pe.setHealth(Attributes.boxHealth);
		pe.setMaxHealth(Attributes.boxHealth);
		pe.setPosition(new Vector2f(x, y));
		pe.setInvincible(false);
		pe.setCollidingWithStructures(false);
		if (item != null)
			pe.setItem(ItemFactory.createItem(item));
		return pe;
	}
	
	public static GameEntity createBoxBigWidth(float x, float y, int stateID, Type item) throws SlickException
	{
		GameEntity pe = new Structure("BoxBigWidth", "/assets/structures/boxBigWidth.png", stateID, Orientation.NEUTRAL);
		pe.setHealth(Attributes.boxHealth);
		pe.setMaxHealth(Attributes.boxHealth);
		pe.setPosition(new Vector2f(x, y));
		pe.setScale(0.5f);
		pe.setInvincible(false);
		if (item != null)
			pe.setItem(ItemFactory.createItem(item));
		return pe;
	}
	
	public static GameEntity createBoxBigHeight(float x, float y, int stateID, Type item) throws SlickException
	{
		GameEntity pe = new Structure("BoxBigHeight", "/assets/structures/boxBigHeight.png", stateID, Orientation.NEUTRAL);
		pe.setHealth(Attributes.boxHealth);
		pe.setMaxHealth(Attributes.boxHealth);
		pe.setPosition(new Vector2f(x, y));
		pe.setScale(0.5f);
		pe.setInvincible(false);
		if (item != null)
			pe.setItem(ItemFactory.createItem(item));
		return pe;
	}
	
	/**
	 * creates a structure of size 50x50
	 * @param x
	 * @param y
	 * @return
	 * @throws SlickException
	 */
	public static GameEntity createBox2small(float x, float y) throws SlickException
	{
		GameEntity pe = createBox2Big("Box2Small", x, y);
		pe.setScale(0.25f);
		return pe;
	}
	
	/**
	 * creates a structure of size 100x100
	 * @param x
	 * @param y
	 * @return
	 * @throws SlickException
	 */
	public static GameEntity createBox2(float x, float y) throws SlickException
	{
		GameEntity pe = createBox2Big("Box2", x, y);
		pe.setScale(0.5f);
		return pe;
	}
	
	/**
	 * creates a structure of size 200x200
	 * @param x
	 * @param y
	 * @return
	 * @throws SlickException
	 */
	public static GameEntity createBox2Big(float x, float y) throws SlickException
	{
		return createBox2Big("Box2Big", x, y);
	}
	
	/**
	 * creates a structure of size 200x200
	 * @param x
	 * @param y
	 * @return
	 * @throws SlickException
	 */
	private static GameEntity createBox2Big(String entityID, float x, float y) throws SlickException
	{
		GameEntity pe = new Structure(entityID, "/assets/structures/box2.png", -1, Orientation.NEUTRAL);
		pe.setPosition(new Vector2f(x, y));
		pe.setCollidingWithStructures(false);
		return pe;
	}
	
	public static GameEntity createGate(float x, float y) throws SlickException
	{
		GameEntity pe = new Structure("BoxWidth", "/assets/structures/gate.png", -1, Orientation.NEUTRAL);
		pe.setPosition(new Vector2f(x, y));
		pe.setInvincible(false);
		return pe;
	}
}
