package jng.ui;

import java.awt.Point;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import jng.actions.sound.SoundManager;
import jng.entities.GameEntity;
import jng.tests.TestRenderComponent;

/**
 * This class provides different parameters of the game.
 * @author daniel
 *
 */
public class Controls {

	public static Point displayResolution = 
			new Point(1440, 900);
	
	public static float standardOffset = 20.0f;
	
	public static boolean debug = false;
	
	// play the worldexample.txt independent of any other option
	public static boolean playExampleWorld = false;
	
	// create a small, medium and big world and save them in smallWorld.txt, mediumWorld.txt, bigWorld.txt
	public static boolean createNewWorlds = false;
	// don't change this variable
	public static boolean worldsCreated = false;
	
	// instead of loading files from the .txt file, they are randomly generated each time
	public static boolean randomWorlds = true;
	
	// this parameter is set automatically in the game depending on the user interaction.
	public static int playerCount = 1;
	
	// this parameter is set automatically in the game depending on the user interaction.
	public static int worldSize = 0;
	
	// if the game is exported to a file, this parameter may be set true, although, it is
	// recommended to first try it with setting it false
	public static boolean loadClassPaths = false;
	
	public static boolean soundOn = true;
	
	public static void loadImage(Entity e, String imagePath)
	{
//		if (Controls.debug)
//			return;
		if (loadClassPaths)
		{
			String path = imagePath.substring(8, imagePath.length());
			boolean imageLoaded = false;
			while (!imageLoaded)
			{
				try {
					//e.addComponent(new ImageRenderComponent(new Image(e.getClass().getResource(path).getPath())));
					if (debug)
						e.addComponent(new TestRenderComponent(imagePath.substring(1)));
					else
						e.addComponent(new ImageRenderComponent(new Image(path)));
					imageLoaded = true;
				} catch (Exception e1) {
					imageLoaded = false;
					System.out.println("failed to load image: " + path);
					//e1.printStackTrace();
				}
			}			
		}
		else
		{
			//String path = imagePath.substring(8, imagePath.length());
			boolean imageLoaded = false;
			while (!imageLoaded)
			{
				try {
					if (debug)
						e.addComponent(new TestRenderComponent(imagePath.substring(1)));
					else
						e.addComponent(new ImageRenderComponent(new Image(imagePath)));
					imageLoaded = true;
				} catch (Exception e1) {
					imageLoaded = false;
					System.out.println("failed to load image: " + imagePath);
					e1.printStackTrace();
				}
				
			}
		}
	}
	
	public static Image loadImage(GameEntity ae, String imagePath)
	{
		if (debug)
			return null;
		if (loadClassPaths)
		{
			String path = imagePath.substring(8, imagePath.length());
			try {
				return new Image(path);
				//return new Image(ae.getClass().getResource(path).getPath());
			} catch (SlickException e1) {
				e1.printStackTrace();
			}			
		}
		else
		{
			//String path = imagePath.substring(8, imagePath.length());
			try {
				return new Image(imagePath);
			} catch (SlickException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
	
	public static String getClassPath(SoundManager s, String path)
	{
		String pathTemp = path.substring(7, path.length());
		if (loadClassPaths)
			return pathTemp;//s.getClass().getResource(pathTemp).getPath();
		else
			return path;
	}
	
	public static String getClassPath(BasicGameState s, String path)
	{
		if (loadClassPaths)
		{
			String pathTemp = path.substring(6, path.length());
			return pathTemp;//s.getClass().getResource(pathTemp).getPath();			
		}
		else
		{
			//String pathTemp = path.substring(7, path.length());
			return path;
		}

	}
}
