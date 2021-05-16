package jng.actions.sound;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import jng.ui.Controls;
import jng.ui.GameplayState;
import jng.ui.Jng;

public class SoundManager {
	
	
	private static SoundManager instance = new SoundManager();
	
	private boolean soundOn;
	
	private float volumeScale;
	
	private Sound fireMGSound;
	
	private Sound fireRocketSound;
	
	private Sound fireBombSound;
	
	private Sound fireTeslaSound;
	
	private Sound detonationRocketSound;
	
	private Sound detonationBomb;
	
	private Sound detonationPlaneSound;
	
	private Sound collectItemSound;
	
	private boolean initialized;
	private Image imageMute;
	private Image imageVolume1;
	private Image imageVolume2;
	private Image imageVolume3;
	private Image imageVolume4;
	private Image currentImage;
	
	private SoundManager()
	{
		if (Controls.debug)
			return;
		soundOn = Controls.soundOn;
		volumeScale = 1.0f;
		initialized = false;
		try {
			//backgroundMusic = new Sound(Controls.getClassPath(this, "assets/sounds/backgroundNoise.ogg"));
			fireMGSound = new Sound(Controls.getClassPath(this, "assets/sounds/singleShotMG.ogg"));
			fireRocketSound = new Sound(Controls.getClassPath(this, "assets/sounds/singleShotRocket.ogg"));
			fireBombSound = new Sound(Controls.getClassPath(this, "assets/sounds/singleShotBomb.ogg"));
			fireTeslaSound = new Sound(Controls.getClassPath(this, "assets/sounds/singleShotTesla.ogg"));
			detonationRocketSound = new Sound(Controls.getClassPath(this, "assets/sounds/detonationRocket.ogg"));
			detonationBomb = new Sound(Controls.getClassPath(this, "assets/sounds/detonationBomb.ogg"));
			detonationPlaneSound = new Sound(Controls.getClassPath(this, "assets/sounds/detonationPlane.ogg"));
			collectItemSound = new Sound(Controls.getClassPath(this, "assets/sounds/collectItem.ogg"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public Entity setVolumeControl(final int stateID)
	{
		if (Controls.debug)
			return null;
		// images
		if (!initialized)
		{
			try {
				if (!Controls.debug)
				{
					imageMute = new Image(Controls.getClassPath(this, "assets/sounds/mute.png"));
					imageVolume1 = new Image(Controls.getClassPath(this, "assets/sounds/volume1.png"));
					imageVolume2 = new Image(Controls.getClassPath(this, "assets/sounds/volume2.png"));
					imageVolume3 = new Image(Controls.getClassPath(this, "assets/sounds/volume3.png"));
					imageVolume4 = new Image(Controls.getClassPath(this, "assets/sounds/volume4.png"));
					currentImage = imageVolume4;					
				}
			} catch (SlickException e) {
				e.printStackTrace();
			}
			initialized = true;
		}
		
		Entity soundEntity = new Entity("SoundEntity"){

			@Override
			public void render(GameContainer gc, StateBasedGame sb,
					Graphics g) {
				super.render(gc, sb, g);
				if (stateID == Jng.GAMEPLAY_STATE)
				{
					g.drawImage(SoundManager.getInstance().getCurrentImage(), GameplayState.cameraPosition.x + Controls.displayResolution.x - 70, 
							20);
				}
				else
				{
					g.drawImage(SoundManager.getInstance().getCurrentImage(), Controls.displayResolution.x - 70, 
							20);
				}
			}
			
		};
		// right upper corner
		Vector2f position;
		if (stateID == Jng.GAMEPLAY_STATE)
			position = getSoundSymbolPosition((int)GameplayState.cameraPosition.x);
		else
			position = getSoundSymbolPosition(0);
		position.x += 24;
		position.y += 24;
		soundEntity.setPosition(position);
		
		soundEntity.setSize(new Vector2f(48.0f, 48.0f));
		// fires when the mouse clicked on the entity
		ANDEvent mainEvents = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		mainEvents.addAction(new Action() {
			
			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta,
					Component event) {
				volumeScale = (volumeScale + 0.25f) % 1.25f;
				switch((Math.round(volumeScale*4.0f)))
				{
				case 0:
					currentImage = imageMute;
					break;
				case 1:
					currentImage = imageVolume1;
					break;
				case 2:
					currentImage = imageVolume2;
					break;
				case 3:
					currentImage = imageVolume3;
					break;
				case 4:
					currentImage = imageVolume4;
					break;
				}
			}
		});
		soundEntity.addComponent(mainEvents);
		
		StateBasedEntityManager.getInstance().addEntity(stateID, soundEntity);
		return soundEntity;
	}
	
	public Vector2f getSoundSymbolPosition(int offsetX)
	{
		return new Vector2f(offsetX + Controls.displayResolution.x - 70, 20);
	}
	
	public void toggleBackgroundMusic(boolean play)
	{
//		if (backgroundMusic.playing())
//			backgroundMusic.stop();
//		else
//			backgroundMusic.loop();
	}

	
	public void playFireMG()
	{
		if (soundOn && !Controls.debug)
			fireMGSound.play(1, volumeScale);
	}
	
	public void playFireRocket()
	{
		if (soundOn && !Controls.debug)
			fireRocketSound.play(1, volumeScale*0.3f);
	}
	
	public void playFireBomb()
	{
		if (soundOn && !Controls.debug)
			fireBombSound.play(1, volumeScale*0.4f);
	}
	
	public void playFireTesla()
	{
		if (soundOn && !Controls.debug)
			fireTeslaSound.play(1, volumeScale*0.3f);
	}
	
	public void playDetonationPlane()
	{
		if (soundOn && !Controls.debug)
			detonationPlaneSound.play(1, volumeScale*0.25f);
	}
	
	public void playDetonationRocket()
	{
		if (soundOn && !Controls.debug)
			detonationRocketSound.play(1, volumeScale*0.3f);
	}
	
	public void playDetonationBomb()
	{
		if (soundOn && !Controls.debug)
			detonationBomb.play(1, volumeScale*0.2f);
	}
	
	public void playCollectItem()
	{
		if (soundOn && !Controls.debug)
			collectItemSound.play(1, volumeScale*0.6f);
	}
	
	
	
	
	public Image getCurrentImage() {
		return currentImage;
	}

	public static SoundManager getInstance()
	{
		return instance;
	}

	public boolean isSoundOn() {
		return soundOn;
	}

	public void setSoundOn(boolean soundOn) {
		this.soundOn = soundOn;
		toggleBackgroundMusic(false);
	}
	
	
	
}
