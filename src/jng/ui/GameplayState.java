package jng.ui;

import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateInitAction;
import eea.engine.action.basicactions.MoveForwardAction;
import eea.engine.component.Component;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.Event;
import eea.engine.event.basicevents.KeyPressedEvent;
import eea.engine.event.basicevents.LoopEvent;
import jng.actions.sound.SoundManager;
import jng.entities.PlayerPlaneEntity;
import jng.events.CameraMovementEvent;
import jng.factories.WorldFactory;
import jng.statistics.GameStatistics;
import jng.world.World;
import jng.world.WorldFileManager;
import jng.world.WorldManager;

/**
 * @author Timo Bähr
 *
 * Diese Klasse repraesentiert das Spielfenster, indem ein Wassertropfen
 * erscheint und nach unten faellt.
 */
public class GameplayState extends BasicGameState {

	private int stateID; 							// Identifier dieses BasicGameState
	
	private StateBasedEntityManager entityManager; 	// zugehoeriger entityManager
	
	private World world;
	
	private boolean gamePaused;
	private Entity escEntity = null;
	
	public static Vector2f cameraPosition;
    
    private int timeCounter; // Zählt Millisekunden für die Spielzeiterfassung

    GameplayState(int sid) {
       stateID = sid;
       gamePaused = false;
       entityManager = StateBasedEntityManager.getInstance();
       timeCounter = 0;
    }
    
    /**
     * Wird vor dem (erstmaligen) Starten dieses States ausgefuehrt
     */
    @Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
    	
    	//world = WorldFactory.getTestWorld(stateID, true);

    	// Load a new map according to the user input
    	if (!Controls.debug)
    	{
    		if (!Controls.randomWorlds)
    		{
    			try {
    				switch (Controls.worldSize)
    				{
    				case 0:
    					if (Controls.playerCount == 1)
    						world = WorldFileManager.loadWorld(Controls.getClassPath(this, "assets/maps/worldTiny1Player.txt"), stateID);
    					else
    						world = WorldFileManager.loadWorld(Controls.getClassPath(this, "assets/maps/worldTiny.txt"), stateID);
    					break;
    				case 1:
    					if (Controls.playerCount == 1)
    						world = WorldFileManager.loadWorld(Controls.getClassPath(this, "assets/maps/worldSmall1Player.txt"), stateID);
    					else
    						world = WorldFileManager.loadWorld(Controls.getClassPath(this, "assets/maps/worldSmall.txt"), stateID);
    					break;
    				case 2:
    					if (Controls.playerCount == 1)
    						world = WorldFileManager.loadWorld(Controls.getClassPath(this, "assets/maps/worldMedium1Player.txt"), stateID);
    					else
    						world = WorldFileManager.loadWorld(Controls.getClassPath(this, "assets/maps/worldMedium.txt"), stateID);
    					break;
    				case 3:
    					if (Controls.playerCount == 1)
    						world = WorldFileManager.loadWorld(Controls.getClassPath(this, "assets/maps/worldBig1Player.txt"), stateID);
    					else
    						world = WorldFileManager.loadWorld(Controls.getClassPath(this, "assets/maps/worldBig.txt"), stateID);
    					break;
    				case 4:
    					if (Controls.playerCount == 1)
    						world = WorldFileManager.loadWorld(Controls.getClassPath(this, "assets/maps/worldEnormous1Player.txt"), stateID);
    					else
    						world = WorldFileManager.loadWorld(Controls.getClassPath(this, "assets/maps/worldEnormous.txt"), stateID);
    					break;
    				}
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    		else
    		{
    			world = WorldFactory.createExampleWorld1(stateID, Controls.playerCount, Controls.worldSize);
    		}
    		
    		if (Controls.playExampleWorld)
    		{
    			try {
    				world = WorldFileManager.loadWorld(Controls.getClassPath(this, "assets/maps/worldexample.txt"), stateID);
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    		
    		World.setInstance(world);
    	}
    	WorldManager.reset();
    	
    	world = World.getInstance();
    	world.loadNewWorld(stateID);
    	
    	Entity moveCameraEntity = new Entity("MoveCameraEntity");
    	LoopEvent loop = new LoopEvent();
    	cameraPosition = new Vector2f(0.0f, 0.0f);
    	loop.addAction(new Action() {
			
			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta,
					Component event) {
				cameraPosition.x = new MoveForwardAction(
						World.getInstance().getCameraSpeed()).getNextPosition(cameraPosition,
								World.getInstance().getCameraSpeed(), 90, delta).x;
			}
		});
    	moveCameraEntity.addComponent(loop);
    	entityManager.addEntity(stateID, moveCameraEntity);
    	
    	
    	// game won condition
    	Entity gameWonEntity = new Entity("wonEntity");
    	gameWonEntity.setPosition(new Vector2f(World.getInstance().getWidth()+Controls.displayResolution.x, 0));
    	Event gameWonEvent = new CameraMovementEvent("gameWonEvent", Controls.displayResolution.x, 0.0f);

    	// Danach erst zum GameEndState wechseln
    	gameWonEvent.addAction(new ChangeStateInitAction(Jng.GAMEWON_STATE));
    	gameWonEntity.addComponent(gameWonEvent);
    	entityManager.addEntity(stateID, gameWonEntity);
    	
    	// game lost condition
    	Entity gameLostEntity = new Entity("lostEntity");
    	Event gameLostEvent = new Event("gameLostEvent") {
			
			@Override
			protected boolean performAction(GameContainer gc, StateBasedGame sb,
					int delta) {
				for (PlayerPlaneEntity ppe : World.getInstance().getPlayers())
					if (ppe.getHealth() > 0 || ppe.getLifes() > 0)
							//StateBasedEntityManager.getInstance().hasEntity(sb.getCurrentStateID(), ppe.getID()))
						return false;
				return true;
			}
		};
		// Danach erst zum GameEndState wechseln
		gameLostEvent.addAction(new ChangeStateInitAction(Jng.GAMELOST_STATE));
		gameLostEntity.addComponent(gameLostEvent);
		entityManager.addEntity(stateID, gameLostEntity);
		
		escEntity = new Entity("dummy");
		Event escKeyEvent = new KeyPressedEvent(Input.KEY_ESCAPE);
		escKeyEvent.addAction(new Action() {
			
			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta,
					Component event) {
				GameplayState gs = (GameplayState)sb.getCurrentState();
				gs.setGamePaused(!gs.isGamePaused());
				
			}
		});
		
		escEntity.addComponent(escKeyEvent);
		entityManager.addEntity(stateID, escEntity);
		
		// Sound volume control
		SoundManager.getInstance().setVolumeControl(stateID);
		
    }

    /**
     * Wird vor dem Frame ausgefuehrt
     */
    @Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// StatedBasedEntityManager soll alle Entities aktualisieren
    	if (!isGamePaused()) {
    		entityManager.updateEntities(container, game, delta);

    		// Spielzeit aktualisieren
    		timeCounter += delta;
    		if (timeCounter >= 1000) { // Jede Sekunde
    			timeCounter -= 1000;
    			GameStatistics.getInstance().incrementPlayTime();
    		}
    	}
    	else if (escEntity != null)
    		escEntity.update(container, game, delta);
    		
	}
    
    /**
     * Wird mit dem Frame ausgefuehrt
     */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// StatedBasedEntityManager soll alle Entities rendern
		world.getBackground().render(container, game, g);
		g.translate(-cameraPosition.x, cameraPosition.y);
		entityManager.renderEntities(container, game, g);
		g.resetTransform();
		g.setColor(Color.white);
		if (isGamePaused())
			g.drawString("Game paused", 
					Controls.displayResolution.x/2-new String("Game paused").length()/2, 
					Controls.displayResolution.y/2-50);
	}

	public World getWorld() {
		return world;
	}

	@Override
	public int getID() {
		return stateID;
	}

	public boolean isGamePaused() {
		return gamePaused;
	}

	public void setGamePaused(boolean gamePaused) {
		this.gamePaused = gamePaused;
	}
}
