package jng.entities;

public class Item extends AbstractEntity{
	
	public enum Type
	{
		HEALTH, SHIELDS, WEAPON, LIFES
	}
	
	private Type type;

	/**
	 * Do not use this constructor but instead use the
	 * ItemFactory.
	 * @param entityID
	 * @param picturePath
	 */
	public Item(String entityID, String picturePath, Type type) {
		super(entityID, picturePath);
		this.type = type;
	}
	
	public Item(Item i)
	{
		super(i.entityID, i.picturePath);
		type = i.getType();
	}
	
	public Type getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Item "
				+ getPosition().x + " "
				+ getPosition().y + " "
				+ type.ordinal();
	}
}
