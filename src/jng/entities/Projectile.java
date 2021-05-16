package jng.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.action.basicactions.MoveForwardAction;
import eea.engine.component.Component;
import eea.engine.entity.Entity;
import eea.engine.event.basicevents.CollisionEvent;
import eea.engine.event.basicevents.LoopEvent;
import jng.actions.AccelerationAction;
import jng.actions.sound.SoundManager;
import jng.events.ImprovedCollisionEvent;
import jng.events.OwnerOutOfScreenEvent;
import jng.ui.Controls;

public class Projectile extends AbstractEntity {
	
	private int stateID = -1;
	
	private AbstractEntity source;
	
	private float speed;
	
	private boolean bomb;
	
	private float strength;
	
	private String imagePath;

	public Projectile(
			final int stateID, 
			final AbstractEntity source, 
			float speed, 
			float strength,
			boolean bomb,
			String imagePath, 
			String entityID) throws SlickException {
		super(entityID, imagePath);
		this.stateID = stateID;
		this.source = source;
		this.speed = speed;
		this.strength = strength;
		this.bomb = bomb;
		this.imagePath = imagePath;
		
		LoopEvent loop = new LoopEvent();
		if (!bomb)
			loop.addAction(new MoveForwardAction(speed));
		else
			loop.addAction(new AccelerationAction(new Vector2f(0, 0.15f), Attributes.gravity));
    	addComponent(loop);
    	
    	OwnerOutOfScreenEvent removeOutOfBounds = new OwnerOutOfScreenEvent("cme", Controls.standardOffset, false);
    	removeOutOfBounds.addAction(new Action() {
			
			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta,
					Component event) {
				
				Projectile.this.destroy(stateID);
			}
		});
    	addComponent(removeOutOfBounds);
    	
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
						resolveCollision(ae);
					}
				}
			}
    		
    	});
    	addComponent(collisionEvent);
	}
	
	/**
	 * Copy constructor
	 * @param stateID
	 * @param source
	 * @param p
	 * @throws SlickException
	 */
	public Projectile(int stateID, AbstractEntity source, Projectile p) throws SlickException
	{
		this(stateID, source, p.getSpeed(), p.getStrength(), p.isBomb(), p.getImagePath(), p.getId());
		this.source = source;
	}
	
	/**
	 * Before the Projectile hits an object, it has to be decided if the
	 * hit is legit or if the collision should be ignored. A legit hit
	 * occurss when colliding with an opponent plane or a structure.
	 * @param ae
	 */
	private void resolveCollision(AbstractEntity ae)
	{
		if (ae.orientation != source.orientation &&
				(ae instanceof GameEntity ||
				ae instanceof Structure))
			resolveEntityHit((GameEntity)ae, stateID);
	}
	
	/**
	 * When the Projectile hits an AbstractEntity, it gets removed and the AbstractEntity
	 * gets damaged if it's not invincible.
	 * @param ae
	 * @param stateID
	 */
	private void resolveEntityHit(GameEntity ae, int stateID)
	{
		if (getId().startsWith("ProjectileRocket"))
			SoundManager.getInstance().playDetonationRocket();
		else if (getId().startsWith("ProjectileBomb"))
			SoundManager.getInstance().playDetonationBomb();
		if (ae.isInvincible())
		{
			destroy(stateID);
			return;
		}
		
		// Damage
		ae.damage(strength, stateID);
		
		destroy(stateID);
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sb,
			Graphics g)
	{
		super.render(gc, sb, g);
		if (Controls.debug)
		{
			g.setColor(Color.red);
			g.drawRect(getShape().getMinX(), getShape().getMinY(), getShape().getWidth(), getShape().getHeight());
			g.drawOval(getShape().getCenterX(), getShape().getCenterY(), 5, 5);
			g.setColor(Color.white);
		}
	}
	
	

	@Override
	public String toString() {
		return imagePath + " " + speed + " " + strength + " " + bomb;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public float getStrength() {
		return strength;
	}

	public void setStrength(float strength) {
		this.strength = strength;
	}

	public boolean isBomb() {
		return bomb;
	}

	public AbstractEntity getSource() {
		return source;
	}
	
	
}
