package jng.entities;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.action.basicactions.Movement;
import eea.engine.component.Component;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.Event;
import eea.engine.event.basicevents.CollisionEvent;
import jng.actions.AccelerationExplosionAction;
import jng.actions.FireAction;
import jng.actions.movements.AIMovementAction;
import jng.actions.sound.SoundManager;
import jng.ai.AIBehaviour;
import jng.enums.Orientation;
import jng.events.CameraMovementEvent;
import jng.events.ImprovedCollisionEvent;
import jng.factories.WeaponFactory;
import jng.statistics.GameStatistics;
import jng.ui.Controls;
import jng.weapons.Weapon;
import jng.world.WorldManager;

public class GameEntity extends AbstractEntity {
	
	protected int stateID;
	
	protected float speed;

	// 0 - primary, 1 - secundary_rockets, 2 - secundary_bombs, 3 - special
	protected ArrayList<Weapon> weapons;
	
	protected AIBehaviour aiBehaviour;
	
	protected boolean isCollidingWithStructures;
	
	// if set, this item is dropped when the entity is destroyed.
	protected Item item;
	
	protected int weaponType;
	
	private boolean dead;
	
	private boolean affectedByDetonation;
	
	/**
	 * A GameEntity is a movable object that is able to shoot. By setting the speed
	 * parameter to a value bigger than zero and using {@link #AIBehaviour.setMovement(Movement)},
	 * the object starts to move on the specified path (see {@link #setMovement(Vector2f...)}).
	 * @param entityID
	 * @param picturePath
	 * @param stateID
	 * @param orientation
	 * @param invincible
	 * @throws SlickException
	 */
	public GameEntity(String entityID, String picturePath, int stateID, 
			Orientation orientation, boolean invincible) throws SlickException {
		super(entityID, picturePath);
		this.stateID = stateID;
		speed = Attributes.playerPlaneSpeed;
		weapons = new ArrayList<Weapon>(4);
		setPosition(new Vector2f(0, 0));
		
		this.orientation = orientation;
		
		this.invincible = invincible;
		maxHealth = Attributes.playerPlaneHealth;
		health = Attributes.playerPlaneHealth;
		maxArmor = Attributes.playerPlaneArmor;
		armor = 0.0f;
		aiBehaviour = new AIBehaviour(this);
		isCollidingWithStructures = true;
		dead = false;
		affectedByDetonation = true;
		item = null;
		if (!invincible)
			setCollisions();
		setFireAction();
	}
	
	public GameEntity(GameEntity pe)
	{
		super(pe.entityID, pe.picturePath);
		stateID = pe.stateID;
		speed = pe.speed;
		weapons = new ArrayList<Weapon>(pe.weapons);
		setPosition(new Vector2f(pe.getPosition()));
		orientation = pe.orientation;
		invincible = pe.invincible;
		maxHealth = pe.maxHealth;
		health = pe.health;
		maxArmor = pe.maxArmor;
		armor = pe.armor;
		weaponType = pe.getWeaponType();
		AIBehaviour aiBehaviour = new AIBehaviour(pe.aiBehaviour, this);
		this.aiBehaviour = aiBehaviour;
		isCollidingWithStructures = pe.isCollidingWithStructures;
		affectedByDetonation = pe.isAffectedByDetonation();
		dead = pe.dead;
		setRotation(pe.getRotation());
		if (pe.item != null)
			item = new Item(pe.getItem());
		if (!invincible)
			setCollisions();
		setFireAction();
	}
	
	/**
	 * Calling this method initializes an event that lets the Entity fire, but
	 * only if {@link AIBehaviour#isFireModeOn()} is true.
	 */
	private void setFireAction()
	{
		Event fireEvent = new Event("fire event") {
			
			@Override
			protected boolean performAction(GameContainer gc, StateBasedGame sb,
					int delta) {
				return ((GameEntity)owner).getWeapons() != null &&
						((GameEntity)owner).getAiBehaviour().isFireModeOn();
			}
		};
		for (int i = 0; i < 4; ++i)
		{
			FireAction fireAction = new FireAction(stateID, this, i);
			Event combinedEvent = new ANDEvent(fireEvent, 
					new CameraMovementEvent("camera movement event", Controls.displayResolution.x, 
							Controls.standardOffset));
			combinedEvent.addAction(fireAction);
			addComponent(combinedEvent);
		}
	}
	
	/**
	 * Adds the collision event. If called, the entity will collide with structures or other
	 * entities of different {@link Orientation} and thereby getting damaged. This can be prevented
	 * by calling {@link #setInvincible(boolean)}.
	 */
	private void setCollisions()
	{
		if (invincible)
			return;
		CollisionEvent collisionEvent = new ImprovedCollisionEvent();
    	collisionEvent.addAction(new Action(){

			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta,
					Component event) {
				if (event instanceof CollisionEvent) {
					CollisionEvent collisionEvent = (CollisionEvent) event;
					Entity e = collisionEvent.getColidedEntity();
					if (e instanceof AbstractEntity) {
						AbstractEntity ae = (AbstractEntity) e;
						resolveCollision(ae, sb.getCurrentStateID(), delta);
					}
				}
			}
    		
    	});
    	addComponent(collisionEvent);
	}
	
	private void resolveCollision(AbstractEntity ae, int stateID, float delta)
	{
		
		resolveEntityHit(ae, stateID);
	}
	
	public void resolveEntityHit(AbstractEntity ae, int stateID)
	{
		float damage = 0.0f;
		boolean collision = false;
		
		// destroy falling plane wreck when it collides with a structure
		if (ae instanceof Structure && dead)
		{
			if (getOrientation() != Orientation.NEUTRAL)
				detonate();
			destroy(stateID);
		}
		
		if (ae instanceof GameEntity &&
				ae.getOrientation() == getOrientation())
			return;
		else if (ae instanceof Structure)
		{
			if (isCollidingWithStructures)
			{
				damage = Attributes.planeCollisionDamage;
				collision = true;
			}
		}
		else if (ae instanceof GameEntity)
		{
			damage = Attributes.planeCollisionDamage;
			collision = true;
		}
		else if (ae instanceof Item &&
				this instanceof PlayerPlaneEntity)
		{
			PlayerPlaneEntity ppe = (PlayerPlaneEntity)this;
			ae.destroy(stateID);
			SoundManager.getInstance().playCollectItem();

			// Record collected items in statistics
			GameStatistics.getInstance().incrementItemsCollected();

			//StateBasedEntityManager.getInstance().removeEntity(stateID, ae);
			switch(((Item)ae).getType())
			{
			case HEALTH:
				health = Math.min(maxHealth, health + 2);
				break;
			case SHIELDS:
				armor = Math.min(maxArmor, armor + 2);
				break;
			case WEAPON:
				try {
					weapons.set(0, WeaponFactory.createPrimaryWeaponOfRank(this, weapons.get(0).getRank()+1));
				} catch (SlickException e) {
					e.printStackTrace();
				}
				break;
			case LIFES:
				ppe.setLifes(ppe.getLifes()+1);
				break;
			default:
				break;
			}
		}
		
		if (!collision)
			return;
		
		// Damage
		damage(damage, stateID);
	}
	
	protected void detonate()
	{
		
		for (Entity ae : WorldManager.getEntityList())
		{
			if (ae != this &&
					ae instanceof GameEntity &&
					((GameEntity)ae).isAffectedByDetonation())
			{
				GameEntity ge = (GameEntity)ae;
				Vector2f direction = new Vector2f(ge.getPosition()).sub(getPosition());
				float distance = direction.length();
				if (distance <= Attributes.explosionAffectionRange)
				{
					// explosion affects ge
					direction.normalise();
					Action movement = new AccelerationExplosionAction(direction, distance);
					if (ge.getAiBehaviour() == null)
						ge.setAiBehaviour(new AIBehaviour(ge));
					ge.getAiBehaviour().setMovement(movement);
				}
			}
		}
	}
	
	
	@Override
	public void damage(float damage, int stateID) {
		float damageHealth = Math.max(0.0f, damage - getArmor());
		setArmor(Math.max(0.0f, getArmor() - damage));
		setHealth(Math.max(0.0f, getHealth() - damageHealth));
		
		// Record hit in statistics when the entity is hit by a weapon
		GameStatistics.getInstance().incrementShotsHit();

		if (getHealth() <= 0.0f && !dead)
		{
			// Record destroyed enemies in statistics
			if (this.getOrientation() != Orientation.ALLIED && !(this instanceof PlayerPlaneEntity)) {
				GameStatistics.getInstance().incrementEnemiesDestroyed();
			}

			// Record destroyed structures in statistics
			if (this instanceof Structure) {
				GameStatistics.getInstance().incrementStructuresDestroyed();
			}

			if (this instanceof PlayerPlaneEntity)
			{
				PlayerPlaneEntity ppe = (PlayerPlaneEntity)this;
				ppe.setLifes(ppe.getLifes()-1);
				
				// if the lives of a player entity are at 0, the weapons are
				// reset and one life gets taken away.
				if (ppe.getLifes() >= 0)
				{
					ppe.setWeapons();
					ppe.setHealth(Attributes.playerPlaneHealth);
					ppe.setArmor(Attributes.playerPlaneArmor);
					return;
				}
			}
			if (item != null)
			{
				// drops an item if it dies
				item.setPosition(getPosition());
				StateBasedEntityManager.getInstance().addEntity(stateID, item);
				WorldManager.entityAdded(item);
			}
			aiBehaviour.setFireModeOn(false);
			aiBehaviour.setMovementAcceleration();
			dead = true;
			setCollidingWithStructures(true);
			setCollisions();
		}
	}

	@Override
	public void destroy(int stateID) {
		setHealth(0.0f);
		super.destroy(stateID);
		SoundManager.getInstance().playDetonationPlane();
	}
	

	@Override
	public void render(GameContainer gc, StateBasedGame sb,
			Graphics g)
	{
		super.render(gc, sb, g);
		if (invincible)
			return;
		
		if (Controls.debug)
		{
			if (orientation == Orientation.ALLIED)
				g.setColor(Color.green);
			else
				g.setColor(Color.white);
			g.drawRect(getShape().getMinX(), getShape().getMinY(), getShape().getWidth(), getShape().getHeight());
			g.drawOval(getShape().getCenterX(), getShape().getCenterY(), 5, 5);	
			g.setColor(Color.white);
			
		}
		
		// health
		g.setColor(getHealthColor(maxHealth, health));
		g.fillRect(getShape().getMinX(), getShape().getMinY() - 12, (int)(getShape().getWidth() * health / maxHealth), 4);
		
		// armor
		g.setColor(Color.lightGray);
		g.fillRect(getShape().getMinX(), getShape().getMinY() - 8, (int)(getShape().getWidth() * armor / maxArmor), 4);
	}
	
	private Color getHealthColor(double maxHealt, double health)
	{
		double healthRatio = health / maxHealth;
		if (healthRatio < 0.333)
			return Color.red;
		else if (healthRatio < 0.666)
			return Color.orange;
		else
			return Color.green;
	}
	
	@Override
	public String toString() {
		String s = " ";
		s += speed + " "
				+ weaponType + " "
				+ aiBehaviour + " "
				+ isCollidingWithStructures + " "
				+ isAffectedByDetonation() + " "
				+ (item == null ? "-1" : item.getType().ordinal());
		return "GameEntity " + super.toString() + s;
	}

	public boolean isCollidingWithStructures() {
		return isCollidingWithStructures;
	}

	public int getWeaponType() {
		return weaponType;
	}

	public void setWeaponType(int weaponType) {
		this.weaponType = weaponType;
	}

	public void setCollidingWithStructures(boolean isCollidingWithStructures) {
		this.isCollidingWithStructures = isCollidingWithStructures;
	}

	public void setTriggerAndMovement(Event triggerEvent, AIMovementAction movement)
	{
		aiBehaviour.setTriggerAndMovement(triggerEvent, movement);
		addComponent(triggerEvent);
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}

	public void setWeapons(ArrayList<Weapon> weapons) {
		this.weapons = weapons;
	}

	public AIBehaviour getAiBehaviour() {
		return aiBehaviour;
	}

	public void setAiBehaviour(AIBehaviour aiBehaviour) {
		this.aiBehaviour = aiBehaviour;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public boolean isAffectedByDetonation() {
		return affectedByDetonation;
	}

	public void setAffectedByDetonation(boolean affectedByDetonation) {
		this.affectedByDetonation = affectedByDetonation;
	}

	public int getStateID() {
		return stateID;
	}

	public boolean isDead() {
		return dead;
	}
}
