package jng.ui;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import eea.engine.entity.StateBasedEntityManager;
import jng.actions.sound.SoundManager;

/**
 * @author Timo Bähr
 *
 * Diese Klasse startet das Spiel "Drop of Water". Es enthaelt
 * zwei State's für das Menue und das eigentliche Spiel.
 */
public class Jng extends StateBasedGame {
	
	
	
	// Jeder State wird durch einen Integer-Wert gekennzeichnet
    public static final int MAINMENU_STATE = 0;
    public static final int GAMEPLAY_STATE = 1;
    public static final int NEWGAME_STATE = 2;
    public static final int GAMEWON_STATE = 3;
    public static final int GAMELOST_STATE = 4;
    public static final int CONTROLS_STATE = 5;
    
    private static AppGameContainer app;
    
    public Jng()
    {
        super("Jets'n'Guns");
    }
 
    public static void main(String[] args) throws SlickException
    {	
    	// Setze den library Pfad abhaengig vom Betriebssystem
    	System.out.println(System.getProperty("user.dir") + "/native/linux");
    	if (System.getProperty("os.name").toLowerCase().contains("windows")) { ;
    		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/native/windows");
    	} else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
    		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/native/macosx");
    	} else {
    		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/native/" +System.getProperty("os.name").toLowerCase());
    	}
    	
    	// Setze dieses StateBasedGame in einen App Container (oder Fenster)
        app = new AppGameContainer(new Jng());
        app.setVSync(true);
        if (!Controls.debug)
        	app.setShowFPS(false);
 
        // Lege die Einstellungen des Fensters fest und starte das Fenster
        // (nicht aber im Vollbildmodus)
        
        app.setDisplayMode(Controls.displayResolution.x, Controls.displayResolution.y, false);
        try {
        	for (DisplayMode dm : Display.getAvailableDisplayModes())
        		System.out.println(dm.toString());
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
        SoundManager.getInstance().setSoundOn(Controls.soundOn);
        app.start();
        
    }

	@Override
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);
		if (key == Input.KEY_F1) {
	         if (app != null) {
	            try {
	            	app.setFullscreen(!app.isFullscreen());
	            } catch (SlickException e) {
	               Log.error(e);
	            }
	         }
	      }
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		
		// Fuege dem StateBasedGame die States hinzu 
		// (der zuerst hinzugefuegte State wird als erster State gestartet)
		addState(new MainMenuState(MAINMENU_STATE));
        addState(new GameplayState(GAMEPLAY_STATE));
        addState(new NewGameState(NEWGAME_STATE));
        addState(new GameEndState(GAMEWON_STATE, true));
        addState(new GameEndState(GAMELOST_STATE, false));
        addState(new ControlsState(CONTROLS_STATE));
        
        // Fuege dem StateBasedEntityManager die States hinzu
        StateBasedEntityManager.getInstance().addState(MAINMENU_STATE);
        StateBasedEntityManager.getInstance().addState(GAMEPLAY_STATE);
        StateBasedEntityManager.getInstance().addState(NEWGAME_STATE);
        StateBasedEntityManager.getInstance().addState(GAMEWON_STATE);
        StateBasedEntityManager.getInstance().addState(GAMELOST_STATE);
        StateBasedEntityManager.getInstance().addState(CONTROLS_STATE);
		
	}
}