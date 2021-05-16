package jng.world;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import jng.actions.SpawnPlanesAction;
import jng.ai.AIBehaviour;
import jng.entities.GameEntity;
import jng.entities.Item;
import jng.entities.PlayerPlaneEntity;
import jng.entities.Projectile;
import jng.entities.Structure;
import jng.entities.Item.Type;
import jng.enums.Orientation;
import jng.factories.ItemFactory;
import jng.factories.PlaneFactory;
import jng.factories.WeaponFactory;
import jng.ui.Controls;

/**
 * This class provides methods for loading worlds/levels from a text file and saving
 * them in one.
 * @author daniel
 *
 */
public class WorldFileManager {
	
	public static void saveWorld(World world, String path)
	{
		String so = "Level ";

	    Path file = Paths.get(path);//Paths.get(System.getProperty("user.home") + "/test.txt");

		try {
			if (Files.exists(file))
				Files.delete(file);
			Files.createFile(file);
			
			so += world.getName() + " ";
			so += world.getWidth() + " ";
			so += world.getBackgroundPath() + " ";
			so += world.getCameraSpeed() + " ";
			so += Controls.displayResolution.x + " ";
			so += Controls.displayResolution.y + "\n";
			
			for (Structure e : world.getStructures())
				so += e.toString() + "\n";
			for (PlayerPlaneEntity e : world.getPlayers())
				so += e.toString() + "\n";
			for (GameEntity e : world.getEnemies())
				so += e.toString() + "\n";
			for (Projectile e : world.getProjectiles())
				so += e.toString() + "\n";
			for (Item e : world.getItems())
				so += e.toString() + "\n";
			for (SpawnPlanesAction spa : world.getSpawnPlanesActions())
				so += spa.toString() + "\n";

			
			
			Files.write(file, so.getBytes(), StandardOpenOption.WRITE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static String readFile(String filename)
	{
	    String content = null;
	    File file = new File(filename); //for ex foo.txt
	    FileReader reader = null;
	    try {
	        reader = new FileReader(file);
	        char[] chars = new char[(int) file.length()];
	        reader.read(chars);
	        content = new String(chars);
	        reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if(reader !=null){
	        	try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
	    }
	    return content;
	}
	
	public static World parseWorld(String world, int stateID) throws IOException, SlickException
	{
		World w = null;
		
		//String lines = new String(Files.readAllBytes(file));
		Scanner scanner = new Scanner(world);
		
		String nextLine;
		while (scanner.hasNext())
		{
			nextLine = scanner.nextLine();
			Scanner sw = new Scanner(nextLine);
			sw.useLocale(Locale.US);
			String nextWord = sw.next();
			if (nextWord.equals("Level"))
			{
				w = new World(sw.next(), Float.valueOf(sw.next()));
				w.setBackground(sw.next());
				w.setCameraSpeed(Float.valueOf(sw.next()));
			}
			else if (nextWord.equals("Structure"))
			{
				float positionX = (float)sw.nextDouble();
				float positionY = sw.nextFloat();
				float rotation = sw.nextFloat();
				float scale = sw.nextFloat();
				String entityID = sw.next();
				String picturePath = sw.next();
				Orientation orientation = Orientation.values()[sw.nextInt()];
				boolean invincible = sw.nextBoolean();
				float maxHealth = sw.nextFloat();
				float health = sw.nextFloat();
				float maxArmor = sw.nextFloat();
				float armor = sw.nextFloat();
				int i;
				Type itemType = null;
				if ((i = sw.nextInt()) != -1)
					itemType = Type.values()[i];
				Structure structure = new Structure(entityID, picturePath, stateID, orientation);
				structure.setPosition(new Vector2f(positionX, positionY));
				structure.setRotation(rotation);
				structure.setScale(scale);
				structure.setInvincible(invincible);
				structure.setMaxHealth(maxHealth);
				structure.setHealth(health);
				structure.setMaxArmor(maxArmor);
				structure.setArmor(armor);
				if (itemType != null)
					structure.setItem(ItemFactory.createItem(itemType));
				w.addEntity(structure);
			}
			else if (nextWord.equals("PlayerPlaneEntity"))
			{
				float positionX = sw.nextFloat();
				float positionY = sw.nextFloat();
				float rotation = sw.nextFloat();
				float scale = sw.nextFloat();
				String entityID = sw.next();
				String picturePath = sw.next();
				//Orientation orientation = Orientation.values()[sw.nextInt()];
				sw.nextInt();
				boolean invincible = sw.nextBoolean();
				float maxHealth = sw.nextFloat();
				float health = sw.nextFloat();
				float maxArmor = sw.nextFloat();
				float armor = sw.nextFloat();
				float speed = sw.nextFloat();
				// weapons
				//int weaponType = sw.nextInt();
				sw.nextInt();
				// lifes
				int lifes = sw.nextInt();
				// keys
				int key_up = sw.nextInt();
				int key_down = sw.nextInt();
				int key_left = sw.nextInt();
				int key_right = sw.nextInt();
				int key_mg = sw.nextInt();
				int key_rocket = sw.nextInt();
				int key_bomb = sw.nextInt();
				
				
				PlayerPlaneEntity ppe = PlaneFactory.createStandardPlayerPlaneEntity(entityID, stateID, picturePath,
							key_up, key_down, key_left, key_right, key_mg, key_rocket, key_bomb);
				ppe.setPosition(new Vector2f(positionX, positionY));
				ppe.setRotation(rotation);
				ppe.setScale(scale);
				ppe.setInvincible(invincible);
				ppe.setMaxHealth(maxHealth);
				ppe.setHealth(health);
				ppe.setMaxArmor(maxArmor);
				ppe.setArmor(armor);
				ppe.setSpeed(speed);
				ppe.setCameraSpeed(speed);
				ppe.setLifes(lifes);
				if (w.getPlayers().size() < Controls.playerCount)
					w.addEntity(ppe);
			}
			else if (nextWord.equals("GameEntity"))
			{
				GameEntity ge = readGameEntity(sw, stateID, w.getPlayers().toArray(new GameEntity[w.getPlayers().size()]));
				w.addEntity(ge);
			}
			else if (nextWord.equals("Item"))
			{
				float positionX = sw.nextFloat();
				float positionY = sw.nextFloat();
				Type itemType = Type.values()[sw.nextInt()];
				Item i = ItemFactory.createItem(itemType);
				i.setPosition(new Vector2f(positionX, positionY));
				w.addEntity(i);
			}
			else if (nextWord.equals("SpawnPlanesAction"))
			{
				float positionX = sw.nextFloat();
				float positionY = sw.nextFloat();
				float frequency = sw.nextFloat();
				sw.next(); // [
				ArrayList<GameEntity> planes = new ArrayList<GameEntity>();
				while (!sw.next().equals("]"))
				{
					GameEntity ge = readGameEntity(sw, stateID, w.getPlayers().toArray(new GameEntity[w.getPlayers().size()]));
					planes.add(ge);
				}
				SpawnPlanesAction spa = new SpawnPlanesAction(planes, frequency);
				spa.setPosition(new Vector2f(positionX, positionY));
				w.getSpawnPlanesActions().add(spa);
			}
			
			sw.close();
		}
		scanner.close();
		
		
		return w;
	}
	
	public static World loadWorld(String path, int stateID) throws IOException, SlickException
	{
		String lines = readFile(path);
		return parseWorld(lines, stateID);
	}
	
	private static GameEntity readGameEntity(Scanner sw, int stateID, GameEntity... target) throws SlickException
	{
		float positionX = sw.nextFloat();
		float positionY = sw.nextFloat();
		float rotation = sw.nextFloat();
		float scale = sw.nextFloat();
		String entityID = sw.next();
		String picturePath = sw.next();
		Orientation orientation = Orientation.values()[sw.nextInt()];
		boolean invincible = sw.nextBoolean();
		float maxHealth = sw.nextFloat();
		float health = sw.nextFloat();
		float maxArmor = sw.nextFloat();
		float armor = sw.nextFloat();
		float speed = sw.nextFloat();
		// weapons
		int weaponType = sw.nextInt();
		boolean fireModeOn = sw.nextBoolean();
		ArrayList<Vector2f> p = new ArrayList<Vector2f>();
		sw.next(); // [
		String x;
		while (!(x = sw.next()).equals("]"))
			p.add(new Vector2f(Float.valueOf(x), sw.nextFloat()));
		boolean isCollidingWithStructures = sw.nextBoolean();
		boolean affectedByDetonation = sw.nextBoolean();
		int i;
		Type itemType = null;
		if ((i = sw.nextInt()) != -1)
			itemType = Type.values()[i];
		
		GameEntity ge = new GameEntity(entityID, picturePath, stateID, orientation, invincible);
		ge.setScale(scale);
		ge.setRotation(rotation);
		WeaponFactory.setWeaponsOfType(ge, weaponType, target);
		ge.setPosition(new Vector2f(positionX, positionY));
		ge.setMaxHealth(maxHealth);
		ge.setHealth(health);
		ge.setMaxArmor(maxArmor);
		ge.setArmor(armor);
		ge.setSpeed(speed);
		ge.setCollidingWithStructures(isCollidingWithStructures);
		ge.setAffectedByDetonation(affectedByDetonation);
		if (itemType != null)
			ge.setItem(ItemFactory.createItem(itemType));
		
		ge.setAiBehaviour(new AIBehaviour(ge, fireModeOn));
		if (!p.isEmpty())
		{
			Vector2f[] pathTemp = new Vector2f[p.size()];
			for (int j = 0; j < p.size(); ++j)
				pathTemp[j] = p.get(j);
			ge.getAiBehaviour().setMovement(pathTemp);
		}
		return ge;
	}
}
