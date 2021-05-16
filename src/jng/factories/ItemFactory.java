package jng.factories;

import org.newdawn.slick.geom.Vector2f;

import jng.entities.Item;
import jng.entities.Item.Type;

public class ItemFactory {

	public static Item createHealthItem(String entityID)
	{
		return new Item(entityID, "/assets/itemHealth.png", Type.HEALTH);
	}
	
	public static Item createShieldItem(String entityID)
	{
		return new Item(entityID, "/assets/itemShields.png", Type.SHIELDS);
	}
	
	public static Item createWeaponItem(String entityID)
	{
		return new Item(entityID, "/assets/itemWeapons.png", Type.WEAPON);
	}
	
	public static Item createLifeItem()
	{
		return new Item("LifeItem", "/assets/itemLifes.png", Type.LIFES);
	}
	
	/**
	 * creates a random item. The probability of a life item
	 * is 6%. The remaining probabilities are equally
	 * distributed over the other items.
	 * @return random Item
	 */
	public static Item createRandomItem()
	{
		if (Math.random() < 0.06)
			return createItem(Type.LIFES);
		Type[] types = Type.values();
		return createItem(types[(int)(Math.floor(Math.random()*(types.length-1)))]);
	}
	
	public static Item createRandomItem(float x, float y)
	{
		Item i = createRandomItem();
		i.setPosition(new Vector2f(x, y));
		return i;
	}
	
	public static Item createItem(Type type)
	{
		switch (type)
		{
		case HEALTH:
			return new Item("item", "/assets/itemHealth.png", Type.HEALTH);
		case SHIELDS:
			return new Item("item", "/assets/itemShields.png", Type.SHIELDS);
		case WEAPON:
			return new Item("item", "/assets/itemWeapons.png", Type.WEAPON);
		case LIFES:
			return new Item("itemLifes", "/assets/itemLifes.png", Type.LIFES);
		default:
			return null;
		}
	}
}
