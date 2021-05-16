package jng.ui;

import java.awt.Point;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateInitAction;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import jng.actions.sound.SoundManager;
import jng.ui.buttonGameState.BasicButtonGameState;



public class GameEndState extends BasicButtonGameState {

	private boolean won;
	
	public GameEndState(int sid, boolean won) {
		super(sid);
		this.won = won;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		Point res = Controls.displayResolution;
		
		// Hintergrund laden
    	Entity background = addBackground("/assets/backgroundMenu.png");
    	
    	// Fuege die Entity zum StateBasedEntityManager hinzu
    	entityManager.addEntity(stateID, background);
    	
    	// Sound symbol
    	SoundManager.getInstance().setVolumeControl(stateID);
    	
    	// Label
    	labels.add(new Label(won ? "You have won!" : "You have lost." , new Vector2f(res.x/2, res.y/2-BasicButtonGameState.buttonSize.y*2)));
    	
    	// initiate buttons
    	Entity back_to_menu_Entity = addButton((int)(res.x/2+BasicButtonGameState.buttonSize.x/2+25), 
				res.y/2, 
				BasicButtonGameState.buttonSize, 
				"Back to main menu",
				"/assets/button.png");
    	
		// Erstelle das Ausloese-Event und die zugehoerige Action
    	ANDEvent mainEvents = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
    	Action back_to_menu_Action = new ChangeStateInitAction(Jng.MAINMENU_STATE);
    	mainEvents.addAction(back_to_menu_Action);
    	back_to_menu_Entity.addComponent(mainEvents);
    	entityManager.addEntity(stateID, back_to_menu_Entity);
    	
    	
    	Entity restart_Entity = addButton((int)(res.x/2-BasicButtonGameState.buttonSize.x/2-25), 
				res.y/2, 
				BasicButtonGameState.buttonSize, 
				"Restart",
				"/assets/button.png");
    	mainEvents = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
    	Action restart_Action = new ChangeStateInitAction(Jng.GAMEPLAY_STATE);
    	mainEvents.addAction(restart_Action);
    	restart_Entity.addComponent(mainEvents);
    	entityManager.addEntity(this.stateID, restart_Entity);
	}

}
