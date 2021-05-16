package jng.entities;

import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import jng.enums.Orientation;
import jng.ui.Controls;
import jng.world.WorldManager;

public abstract class AbstractEntity extends Entity{
	
	protected Orientation orientation;
	
	protected boolean invincible;
	
	protected float maxHealth;
	
	protected float health;
	
	protected float maxArmor;
	
	protected float armor;
	
	protected String picturePath;
	
	protected String entityID;
	
	protected int id;
	
	protected static int idCounter = 0;
	
	
	public AbstractEntity(String entityID, String picturePath) {
		super(entityID);
		id = idCounter;
		idCounter++;
//			System.out.println(path);
//			System.out.println(new Image(getClass().getResource(path).getPath()));
		Controls.loadImage(this, picturePath);
//			System.out.println("picture " + picturePath + " has a wrong format -> use a 32 bit color png format");
//			addComponent(new ImageRenderComponent(new Image(picturePath)));

		this.picturePath = picturePath;
		this.entityID = entityID;
		maxHealth = Float.MAX_VALUE;
		health = Float.MAX_VALUE;
		maxArmor = Float.MAX_VALUE;
		armor = Float.MAX_VALUE;
		setInvincible(true);
	}
	
	/**
	 * Call this method if the Entity has to be destroyed instead of removing it directly
	 * with the StateBasedEntityManager. It ensures that i.e. items are dropped.
	 * @param stateID
	 */
	public void destroy(int stateID)
	{
		if (StateBasedEntityManager.getInstance().getEntitiesByState(stateID).contains(this))
		{
			StateBasedEntityManager.getInstance().removeEntity(stateID, this);
			WorldManager.entityRemoved(this);
		}
	}
	
	public void damage(float damage, int stateID)
	{
		float damageHealth = Math.max(0.0f, damage - getArmor());
		setArmor(Math.max(0.0f, getArmor() - damage));
		setHealth(Math.max(0.0f, getHealth() - damageHealth));
		
		if (getHealth() <= 0)
		{
			destroy(stateID);	
		}
	}
	
	@Override
	public String toString() {
		return getPosition().x + " "
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
				+ armor;
				
	}

	public Orientation getOrientation() {
		return orientation;
	}


	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}


	public boolean isInvincible() {
		return invincible;
	}


	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}


	public float getMaxHealth() {
		return maxHealth;
	}


	public void setMaxHealth(float maxHealth) {
		this.maxHealth = maxHealth;
	}


	public float getHealth() {
		return health;
	}


	public void setHealth(float health) {
		this.health = health;
	}
	

	public float getMaxArmor() {
		return maxArmor;
	}


	public void setMaxArmor(float maxArmor) {
		this.maxArmor = maxArmor;
	}
	

	public float getArmor() {
		return armor;
	}


	public void setArmor(float armor) {
		this.armor = armor;
	}
	
}
