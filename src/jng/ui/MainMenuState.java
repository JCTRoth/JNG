package jng.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateInitAction;
import eea.engine.action.basicactions.QuitAction;
import eea.engine.component.Component;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import jng.actions.sound.SoundManager;
import jng.factories.PlaneFactory;
import jng.factories.WorldFactory;
import jng.ui.buttonGameState.BasicButtonGameState;
import jng.world.World;
import jng.world.WorldFileManager;

/**
 * @author Timo Bähr
 *
 * Diese Klasse repraesentiert das Menuefenster, indem ein neues
 * Spiel gestartet werden kann und das gesamte Spiel beendet 
 * werden kann.
 */
public class MainMenuState extends BasicButtonGameState {

	private int stateID; 							// Identifier von diesem BasicGameState
	private StateBasedEntityManager entityManager; 	// zugehoeriger entityManager
	
	private final int distance = 100;
    private final int start_Position = 340;

	MainMenuState(int sid)
	{
		super(sid);
		stateID = sid;
		entityManager = StateBasedEntityManager.getInstance();
    }
    
    /**
     * Wird vor dem (erstmaligen) Starten dieses State's ausgefuehrt
     */
    @Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
    	// Hintergrund laden
    	Entity background = addBackground("/assets/backgroundMenu.png");
    	
    	// Fuege die Entity zum StateBasedEntityManager hinzu
    	entityManager.addEntity(stateID, background);
    	
    	// Sound symbol
    	SoundManager.getInstance().setVolumeControl(stateID);
    	
    	int distanceCounter = start_Position;
    	/* Neues Spiel starten-Entitaet */
    	Entity newGame1PEntity = addButton(
    			Controls.displayResolution.x/2, 
    			distanceCounter, 
    			buttonSize, 
    			"1 Spieler",
    			"/assets/button.png");
    	distanceCounter += distance;
    	
    	// Erstelle das Ausloese-Event und die zugehoerige Action
    	ANDEvent mainEvents1P = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
    	Action newGame1PAction = new ChangeStateInitAction(Jng.NEWGAME_STATE){

			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta,
					Component event) {
				Controls.playerCount = 1;
				super.update(gc, sb, delta, event);
			}
    		
    	};
    	mainEvents1P.addAction(newGame1PAction);
    	newGame1PEntity.addComponent(mainEvents1P);
    	
    	entityManager.addEntity(this.stateID, newGame1PEntity);
    	
    	// 2 player
    	Entity newGameP2Entity = addButton(
    			Controls.displayResolution.x/2, 
    			distanceCounter, 
    			buttonSize, 
    			"2 Spieler",
    			"/assets/button.png"); 
    	distanceCounter += distance;
    	
    	// Erstelle das Ausloese-Event und die zugehoerige Action
    	ANDEvent mainEvents2P = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
    	Action newGame2PAction = new ChangeStateInitAction(Jng.NEWGAME_STATE){

			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta,
					Component event) {
				Controls.playerCount = 2;
				super.update(gc, sb, delta, event);
			}
    		
    	};
    	mainEvents2P.addAction(newGame2PAction);
    	newGameP2Entity.addComponent(mainEvents2P);
    	entityManager.addEntity(this.stateID, newGameP2Entity);
    	
    	// 3 player
    	Entity newGameP3Entity = addButton(
    			Controls.displayResolution.x/2, 
    			distanceCounter, 
    			buttonSize, 
    			"3 Spieler",
    			"/assets/button.png"); 
    	distanceCounter += distance;
    	
    	// Erstelle das Ausloese-Event und die zugehoerige Action
    	ANDEvent mainEvents3P = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
    	Action newGame3PAction = new ChangeStateInitAction(Jng.NEWGAME_STATE){

			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta,
					Component event) {
				Controls.playerCount = 3;
				super.update(gc, sb, delta, event);
			}
    		
    	};
    	mainEvents3P.addAction(newGame3PAction);
    	newGameP3Entity.addComponent(mainEvents3P);
    	
    	entityManager.addEntity(this.stateID, newGameP3Entity);
    	
    	// Controls
    	Entity controlsEntity = addButton(
    			Controls.displayResolution.x/2, 
    			distanceCounter, 
    			buttonSize, 
    			"Steuerung",
    			"/assets/button.png"); 
    	distanceCounter += distance;
    	
    	// Erstelle das Ausloese-Event und die zugehoerige Action
    	ANDEvent controlsEvents = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
    	Action controlsAction = new ChangeStateInitAction(Jng.CONTROLS_STATE);
    	controlsEvents.addAction(controlsAction);
    	controlsEntity.addComponent(controlsEvents);
    	
    	entityManager.addEntity(this.stateID, controlsEntity);
    	
    	/* Beenden-Entitaet */
    	Entity quit_Entity = addButton(
    			Controls.displayResolution.x/2, 
    			distanceCounter, 
    			buttonSize, 
    			"Beenden",
    			"/assets/button.png"); 
    	distanceCounter += distance;
    	
    	// Erstelle das Ausloese-Event und die zugehoerige Action
    	ANDEvent mainEvents_q = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
    	Action quit_Action = new QuitAction();
    	mainEvents_q.addAction(quit_Action);
    	quit_Entity.addComponent(mainEvents_q);
    	
    	// Fuege die Entity zum StateBasedEntityManager hinzu
    	entityManager.addEntity(this.stateID, quit_Entity);
    	
    	
    	// Erstelle zufaellig generierte Levels
		try {
	    	if (!Controls.debug && Controls.createNewWorlds && !Controls.worldsCreated)
	    	{
	    		Controls.worldsCreated = true;
	    		
	    		// Tiny World
	    		World world = WorldFactory.createExampleWorld1(-1, 3, 0);
	        	WorldFileManager.saveWorld(world, Controls.getClassPath(this, "assets/maps/worldTiny.txt"));
	        	world.getPlayers().clear();
	        	world.getPlayers().add(PlaneFactory.createStandardPlayerPlaneEntity(world));
	        	WorldFileManager.saveWorld(world, Controls.getClassPath(this, "assets/maps/worldTiny1Player.txt"));
	        	
	    		// Small world
	    		world = WorldFactory.createExampleWorld1(-1, 3, 1);
	        	WorldFileManager.saveWorld(world, Controls.getClassPath(this, "assets/maps/worldSmall.txt"));
	        	world.getPlayers().clear();
	        	world.getPlayers().add(PlaneFactory.createStandardPlayerPlaneEntity(world));
	        	WorldFileManager.saveWorld(world, Controls.getClassPath(this, "assets/maps/worldSmall1Player.txt"));
	        	
	        	// Medium world
	        	world = WorldFactory.createExampleWorld1(-1, 3, 2);
	        	WorldFileManager.saveWorld(world, Controls.getClassPath(this, "assets/maps/worldMedium.txt"));
	        	world.getPlayers().clear();
	        	world.getPlayers().add(PlaneFactory.createStandardPlayerPlaneEntity(world));
	        	WorldFileManager.saveWorld(world, Controls.getClassPath(this, "assets/maps/worldMedium1Player.txt"));
	        	
	        	// Big world
	        	world = WorldFactory.createExampleWorld1(-1, 3, 3);
	        	WorldFileManager.saveWorld(world, Controls.getClassPath(this, "assets/maps/worldBig.txt"));
	        	world.getPlayers().clear();
	        	world.getPlayers().add(PlaneFactory.createStandardPlayerPlaneEntity(world));
	        	WorldFileManager.saveWorld(world, Controls.getClassPath(this, "assets/maps/worldBig1Player.txt"));
	        	
	        	// Enormous World
	        	world = WorldFactory.createExampleWorld1(-1, 3, 4);
	        	WorldFileManager.saveWorld(world, Controls.getClassPath(this, "assets/maps/worldEnormous.txt"));
	        	world.getPlayers().clear();
	        	world.getPlayers().add(PlaneFactory.createStandardPlayerPlaneEntity(world));
	        	WorldFileManager.saveWorld(world, Controls.getClassPath(this, "assets/maps/worldEnormous1Player.txt"));
	        	
	        	
	        	// Example world
	        	world = WorldFactory.createExampleWorld(-1);
	        	WorldFileManager.saveWorld(world, Controls.getClassPath(this, "assets/maps/worldexample.txt"));
	        	
	    	}
    	} catch (SlickException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Wird mit dem Frame ausgefuehrt
     */
	@Override
	public void render(GameContainer container, StateBasedGame game, 
												Graphics g) throws SlickException {
		super.render(container, game, g);
		
	}

	@Override
	public int getID() {
		return stateID;
	}
	
}
