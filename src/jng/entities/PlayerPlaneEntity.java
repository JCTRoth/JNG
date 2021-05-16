package jng.entities;


import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.action.basicactions.MoveForwardAction;
import eea.engine.component.Component;
import eea.engine.event.OREvent;
import eea.engine.event.basicevents.KeyDownEvent;
import eea.engine.event.basicevents.LoopEvent;
import jng.actions.FireKeyAction;
import jng.actions.movements.UIMovement;
import jng.enums.Orientation;
import jng.events.OwnerOutOfScreenEvent;
import jng.factories.WeaponFactory;
import jng.ui.Controls;
import jng.ui.GameplayState;

public class PlayerPlaneEntity extends GameEntity {

	
	private int key_up;
	
	private int key_down;
	
	private int key_left;
	
	private int key_right;
	
	private int key_mg;
	
	private int key_rocket;
	
	private int key_bomb;
	
	private int lifes;
	
	private TrueTypeFont font;
	
	private TrueTypeFont fontBig;
	
	private Image hearthImage;
	
	private boolean outOfScreen;
	
	/**
	 * This class specifies a special plane that can be controlled with a keyboard. The plane
	 * moves up, down, left and right with the specified keys. Furthermore, it fires primary,
	 * secondaries and the special weapon with specified keys.
	 * @param entityID
	 * @param picturePath
	 * @param stateID
	 * @param orientation
	 * @throws SlickException
	 */
	public PlayerPlaneEntity(String entityID, String picturePath, int stateID, Orientation orientation,
			int key_up, int key_down, int key_left, int key_right, 
			int key_mg, int key_rocket, int key_bomb) throws SlickException {
		super(entityID, picturePath, stateID, orientation, false);
		this.key_up = key_up;
		this.key_down = key_down; 
		this.key_left = key_left;
		this.key_right = key_right;
		this.key_mg = key_mg;
		this.key_rocket = key_rocket;
		this.key_bomb = key_bomb;
		this.lifes = Attributes.playerPlaneLife;
		outOfScreen = false;
		
		if (!Controls.debug)
		{
			Font awtFont = new Font("Arial", Font.BOLD, 24);
			font = new TrueTypeFont(awtFont, false);
			fontBig = new TrueTypeFont(new Font("Arial", Font.BOLD, 40), false);
			//font = new UnicodeFont(fontPath, 15, true, false);
//	    font.addAsciiGlyphs();
//	    font.addGlyphs(400, 600);
//	    font.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
//	    font.loadGlyphs();
			
			hearthImage = Controls.loadImage(this, "/assets/hearth.png");
		}
	    
		// initiate button listener for user interaction
		OREvent e = new OREvent(
				new KeyDownEvent(key_up), 
				new KeyDownEvent(key_down), 
				new KeyDownEvent(key_left), 
				new KeyDownEvent(key_right)); 
		e.addAction(new UIMovement(Attributes.playerPlaneSpeed, key_up, key_down, key_left, key_right));
		addComponent(e);
		orientation = Orientation.ALLIED;
		
		// fire weapon buttons
		LoopEvent keyEvent = new LoopEvent();
    	keyEvent.addAction(new FireKeyAction(stateID, key_mg, this, 0));
    	keyEvent.addAction(new FireKeyAction(stateID, key_rocket, this, 1));
    	keyEvent.addAction(new FireKeyAction(stateID, key_bomb, this, 2));
    	keyEvent.addAction(new FireKeyAction(stateID, Input.KEY_SPACE, this, 3));
    	addComponent(keyEvent);
    	
    	OwnerOutOfScreenEvent outOfScreenEvent = new OwnerOutOfScreenEvent("outOfScreenEvent", 20.0f, false);
    	outOfScreenEvent.addAction(new Action() {
			
			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta,
					Component event) {
				((PlayerPlaneEntity)event.getOwnerEntity()).damage(Attributes.structureCollisionDamage, sb.getCurrentStateID());
				((PlayerPlaneEntity)event.getOwnerEntity()).setOutOfScreen(true);
			}
		});
    	addComponent(outOfScreenEvent);
    	OwnerOutOfScreenEvent notOutOfScreenEvent = new OwnerOutOfScreenEvent("outOfScreenEvent", 20.0f, true);
    	notOutOfScreenEvent.addAction(new Action() {
			
			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta,
					Component event) {
				((PlayerPlaneEntity)event.getOwnerEntity()).setOutOfScreen(false);
			}
		});
    	addComponent(notOutOfScreenEvent);
	}
	
	public void setWeapons()
	{
		try {
			getWeapons().clear();
			getWeapons().add(0, WeaponFactory.createMGRight(this));
			getWeapons().add(1, WeaponFactory.createSecundaryRocketWeapon(this));
			getWeapons().add(2, WeaponFactory.createSecundaryBombWeapon(this));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void setCameraSpeed(float cameraSpeed)
	{
		LoopEvent e = new LoopEvent();
		e.addAction(new MoveForwardAction(cameraSpeed));
		addComponent(e);
	}

	@Override
	public String toString() {
//		String ws = "[ ";
//		for (Weapon w : weapons)
//			ws += w + " ";
//		ws += "]";
		return "PlayerPlaneEntity "
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
				+ speed + " "
				+ weapons.get(0).getRank() + " "
				+ lifes + " "
				+ key_up + " "
				+ key_down + " "
				+ key_left + " "
				+ key_right + " "
				+ key_mg + " "
				+ key_rocket + " "
				+ key_bomb;
	}
	
	

	@Override
	public void render(GameContainer gc, StateBasedGame sb, Graphics g) {
		super.render(gc, sb, g);
		String lifes = "Leben: ";
		int offset = 40;
		int offsetHearts = 110;
		if (Controls.playerCount == 1)
		{
			for (int i = 0; i < getLifes(); ++i)
				g.drawImage(hearthImage, GameplayState.cameraPosition.x+offsetHearts+25*i, offset);
			font.drawString(GameplayState.cameraPosition.x+10, offset, lifes, Color.white);
			if (outOfScreen)
				fontBig.drawString(GameplayState.cameraPosition.x+Controls.displayResolution.x/2-250, 
						Controls.displayResolution.y/2-100, "Spieler 1 verlässt das Schlachtfeld!", Color.white);
		}
		else
		{
			if (getId().equals("PlayerPlaneP1"))
			{
				for (int i = 0; i < getLifes(); ++i)
					g.drawImage(hearthImage, GameplayState.cameraPosition.x+offsetHearts+25*i, offset);
				font.drawString(GameplayState.cameraPosition.x+10, offset, lifes, Color.red);
				if (outOfScreen)
					fontBig.drawString(GameplayState.cameraPosition.x+Controls.displayResolution.x/2-250, 
							Controls.displayResolution.y/2+offset-100, "Spieler 1 verlässt das Schlachtfeld!", Color.red);
				
			}
			else if (getId().equals("PlayerPlaneP2"))
			{
				for (int i = 0; i < getLifes(); ++i)
					g.drawImage(hearthImage, GameplayState.cameraPosition.x+offsetHearts+25*i, offset+45);
				font.drawString(GameplayState.cameraPosition.x+10, offset+45, lifes, Color.blue);
				if (outOfScreen)
					fontBig.drawString(GameplayState.cameraPosition.x+Controls.displayResolution.x/2-250, 
							Controls.displayResolution.y/2+offset+45-100, "Spieler 2 verlässt das Schlachtfeld!", Color.blue);
				
			}
			else if (getId().equals("PlayerPlaneP3"))
			{
				for (int i = 0; i < getLifes(); ++i)
					g.drawImage(hearthImage, GameplayState.cameraPosition.x+offsetHearts+25*i, offset+90);
				font.drawString(GameplayState.cameraPosition.x+10, offset+90, lifes, Color.yellow);
				if (outOfScreen)
					fontBig.drawString(GameplayState.cameraPosition.x+Controls.displayResolution.x/2-250, 
							Controls.displayResolution.y/2+offset+90-100, "Spieler 3 verlässt das Schlachtfeld!", Color.yellow);
				
			}
			
		}
	}

	public int getLifes() {
		return lifes;
	}

	public void setLifes(int lifes) {
		this.lifes = lifes;
	}

	public boolean isOutOfScreen() {
		return outOfScreen;
	}

	public void setOutOfScreen(boolean outOfScreen) {
		this.outOfScreen = outOfScreen;
	}
}
