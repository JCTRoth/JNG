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

public class ControlsState extends BasicButtonGameState{

	public ControlsState(int sid) {
		super(sid);
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		Point res = Controls.displayResolution;
		
		Entity background = addBackground("/assets/backgroundMenu.png");
		entityManager.addEntity(stateID, background);
		
    	// text
    	Entity textEntity = new Entity("textEntity");
    	Controls.loadImage(textEntity, "/assets/controls.png");
    	textEntity.setPosition(new Vector2f(res.x/2, res.y/2));
    	textEntity.setScale(Math.min(Controls.displayResolution.y/textEntity.getShape().getHeight(), 
    			Controls.displayResolution.x/textEntity.getShape().getWidth()));
    	entityManager.addEntity(stateID, textEntity);
    	
    	// back to menu button
		Entity back_to_menu_Entity = addButton(250, 
				res.y-100, 
				BasicButtonGameState.buttonSize, 
				"Back to main menu",
				"/assets/button.png");
    	
    	ANDEvent mainEvents = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
    	Action back_to_menu_Action = new ChangeStateInitAction(Jng.MAINMENU_STATE);
    	mainEvents.addAction(back_to_menu_Action);
    	back_to_menu_Entity.addComponent(mainEvents);
    	entityManager.addEntity(stateID, back_to_menu_Entity);
    	
    	// volume control
    	SoundManager.getInstance().setVolumeControl(stateID);
	}

}
