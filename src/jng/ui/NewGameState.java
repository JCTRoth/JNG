package jng.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateInitAction;
import eea.engine.component.Component;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;
import jng.actions.sound.SoundManager;
import jng.ui.buttonGameState.BasicButtonGameState;

public class NewGameState extends BasicButtonGameState {

	public NewGameState(int sid) {
		super(sid);
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
		// Hintergrund laden
    	Entity background = addBackground("/assets/backgroundMenu.png");
    	
    	// Fuege die Entity zum StateBasedEntityManager hinzu
    	entityManager.addEntity(stateID, background);
    	
    	// Sound symbol
    	SoundManager.getInstance().setVolumeControl(stateID);
		
    	
    	// initiate buttons
		int nRows = 1;
		int nColumns = 5;
		int missionCounter = 0;
		
		int distanceX = 220;
		int distanceY = 120;
		int offsetX = Controls.displayResolution.x / 2 - (nColumns-1) * distanceX / 2;
		int offsetY = Controls.displayResolution.y / 2 - nRows * distanceY / 2;
    			
    	// back button
    	Entity back_button = addButton(offsetX, 
				nRows * distanceY + offsetY, 
				BasicButtonGameState.buttonSize, 
				"Back",
				"/assets/button.png");
		// Erstelle das Ausloese-Event und die zugehoerige Action
    	ANDEvent mainEvents = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
    	Action new_Game_Action = new ChangeStateInitAction(Jng.MAINMENU_STATE);
    	mainEvents.addAction(new_Game_Action);
    	back_button.addComponent(mainEvents);
    	entityManager.addEntity(this.stateID, back_button);
    	
		// level buttons
		
		for (int r = 0; r < nRows; r++)
		{
			for (int c = 0; c < nColumns; ++c)
			{
				String name;
				switch(missionCounter)
				{
				case 0:
					name = "Tiny";
					break;
				case 1:
					name = "Small";
					break;
				case 2:
					name = "Normal";
					break;
				case 3:
					name = "Big";
					break;
				case 4:
					name = "Huge";
					break;
				default:
					name = "Normal";
				}
				Entity new_Game_Entity = addButton(c * distanceX + offsetX, 
						r * distanceY + offsetY, 
						BasicButtonGameState.buttonSize, name,
						"/assets/button.png");
				// Erstelle das Ausloese-Event und die zugehoerige Action
				final int level = c;
		    	mainEvents = new ANDEvent(new MouseEnteredEvent(), new MouseClickedEvent());
		    	new_Game_Action = new ChangeStateInitAction(Jng.GAMEPLAY_STATE){

		    		
					@Override
					public void update(GameContainer gc, StateBasedGame sb,
							int delta, Component event) {
						Controls.worldSize = level;
						super.update(gc, sb, delta, event);
					}
		    		
		    	};
		    	mainEvents.addAction(new_Game_Action);
		    	new_Game_Entity.addComponent(mainEvents);
		    	
		    	entityManager.addEntity(this.stateID, new_Game_Entity);
		    	missionCounter++;
			}
		}
	}
}
