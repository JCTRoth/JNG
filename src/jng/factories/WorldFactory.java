package jng.factories;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import eea.engine.event.Event;
import jng.actions.SpawnPlanesAction;
import jng.ai.AIBehaviour;
import jng.entities.Attributes;
import jng.entities.Building;
import jng.entities.GameEntity;
import jng.entities.Item;
import jng.entities.PlayerPlaneEntity;
import jng.entities.Item.Type;
import jng.enums.Orientation;
import jng.events.CameraMovementEvent;
import jng.ui.Controls;
import jng.world.World;

/**
 * This class provides methods to create different worlds/levels. The most common way to
 * create a world is using {@link #createExampleWorld1(int, int, int)}.
 * @author daniel
 *
 */
public class WorldFactory {

	public static World getTestWorld(int stateID, boolean multiplayer) throws SlickException {
		World w = new World("City", 4500);
		w.setBackground("/assets/background.png");
		
		// player plane entity
		PlayerPlaneEntity ppe = null;
		if (!multiplayer)
		{
			ppe = PlaneFactory.createStandardPlayerPlaneEntity("PlayerPlane", stateID,
					Input.KEY_UP, Input.KEY_DOWN, Input.KEY_LEFT, Input.KEY_RIGHT, Input.KEY_Y, Input.KEY_X, Input.KEY_C);
			ppe.setSpeed(w.getCameraSpeed());
			ppe.setCameraSpeed(w.getCameraSpeed());
			ppe.setPosition(new Vector2f(400, 300));
			w.getPlayers().add(ppe);
		}
		else
		{
			ppe = PlaneFactory.createMultiplayerPlayer1PlaneEntity(stateID,
					Input.KEY_UP, Input.KEY_DOWN, Input.KEY_LEFT, Input.KEY_RIGHT, Input.KEY_RCONTROL, Input.KEY_RSHIFT, Input.KEY_ENTER);
			ppe.setSpeed(w.getCameraSpeed());
			ppe.setCameraSpeed(w.getCameraSpeed());
			ppe.setPosition(new Vector2f(400, 200));
			w.getPlayers().add(ppe);
			PlayerPlaneEntity ppe2 = PlaneFactory.createMultiplayerPlayer2PlaneEntity(stateID,
					Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D, Input.KEY_F, Input.KEY_G, Input.KEY_H);
			ppe2.setSpeed(w.getCameraSpeed());
			ppe2.setCameraSpeed(w.getCameraSpeed());
			ppe2.setPosition(new Vector2f(400, 400));
			w.getPlayers().add(ppe2);
		}
				
		
		for (int i = 1; i < 10; ++i)
		{
			
			GameEntity pe = PlaneFactory.createPlaneOfType(stateID, (int)Math.floor(Math.random()*12)+1, ppe);
			pe.setPosition(new Vector2f(700+i*600+(pe.getShape().getWidth()+20), 100));
			pe.setAiBehaviour(new AIBehaviour(pe, true));
			pe.getAiBehaviour().setMovement(AIBehaviour.getPath(new Vector2f(pe.getPosition()),
					new Vector2f(-200, 100),
					new Vector2f(-100, 200),
					new Vector2f(200, 0)));
			
			Event cameraEvent = 
					new CameraMovementEvent("camera movement event", Controls.displayResolution.x, 100.0f);
			
			// Planes that are fired
			List<GameEntity> planes = new ArrayList<GameEntity>();
			int rnd = (int)(Math.round(Math.random() * 2.0));
			for (int j = 0; j < 3; ++j)
			{
				GameEntity peTemp = new GameEntity(pe);
				if (j == rnd)
					peTemp.setItem(ItemFactory.createRandomItem());
				planes.add(peTemp);
			}
			
			SpawnPlanesAction spa = new SpawnPlanesAction(planes, 0.0002f);
			cameraEvent.addAction(spa);
			spa.setPosition(new Vector2f(600+i*600+(pe.getShape().getWidth()+20), 100));
			w.getSpawnPlanesActions().add(spa);
		}
		
		// buildings
		Color[] cs = {Color.gray, Color.red, Color.yellow, Color.green};
		int widthSum = 0;
		int towerCounter = -600;
		while (widthSum < 4500)
		{
			Building s = new Building("building", (int)(2 + Math.floor(Math.random()*4)), 
					cs[(int)(Math.floor(Math.random()*cs.length))], stateID, Orientation.NEUTRAL);
			widthSum += s.getWidth();
			towerCounter += s.getWidth();
			s.setPosition(new Vector2f(widthSum-s.getShape().getWidth()/2, 
					Controls.displayResolution.y-(float)(Math.random()*200-50)));
			w.getStructures().add(s);
			
			// towers
			if (towerCounter >= 600)
			{
				GameEntity tower = Math.random() < 0.5 ? 
						PlaneFactory.createStandardTower(widthSum - s.getShape().getWidth()/2, s.getShape().getMinY() - 40, stateID, ppe) :
					PlaneFactory.createRocketTower(widthSum - s.getShape().getWidth()/2, s.getShape().getMinY() - 40, stateID, ppe);
				towerCounter = 0;
				w.addEntity(tower);
			}
		}
		
		// items
		for (int i = 0; i < 30; ++i)
		{
			Item item;
			double rnd = Math.random();
			if (rnd < 0.33)
				item = ItemFactory.createHealthItem("item");
			else if (rnd < 0.66)
				item = ItemFactory.createShieldItem("item");
			else
				item = ItemFactory.createWeaponItem("item");
			item.setPosition(new Vector2f(400+i*400, Math.max(100, (float)Math.random()*300)));
			w.getItems().add(item);
		}
		
		return w;
	}
	
	/**
	 * Creates a world that is a combination of different sub worlds. Each sub-world is to a certain degree
	 * randomized. Random factors are for example the plane types that are used or the number of planes.
	 * Furthermore, tree different sizes (1-3) can be chosen. A world of size 1 combines only the 6 available
	 * level parts one time. The difficulty increases each part by one resulting in the highest difficulty of 5
	 * (the last part has no difficulty value). A world of size 2 combines 11 level parts. In this case the highest
	 * difficulty level of 9 will be reached. This makes the game significantly harder. A world of size 3 combines
	 * 20 level parts. The difficulty increases every two parts this time. This results in at least 6 parts on the
	 * highest difficulty making this size the most difficult.
	 * @param stateID
	 * @param playerCount number of players (1-3)
	 * @param size of the level (0-4)
	 * @return
	 * @throws SlickException
	 */
	public static World createExampleWorld1(int stateID, int playerCount, int size) throws SlickException {
		World w = new World("ExampleWorld", 1000);
		w.setBackground("/assets/background.png");
		
		List<PlayerPlaneEntity> targetsList = new ArrayList<PlayerPlaneEntity>();
		
		// player plane entity
		PlayerPlaneEntity ppe = null;
		if (playerCount == 1)
		{
			ppe = PlaneFactory.createStandardPlayerPlaneEntity("PlayerPlane", stateID,
					Input.KEY_UP, Input.KEY_DOWN, Input.KEY_LEFT, Input.KEY_RIGHT, Input.KEY_Y, Input.KEY_X, Input.KEY_C);
			ppe.setSpeed(w.getCameraSpeed());
			ppe.setCameraSpeed(w.getCameraSpeed());
			ppe.setPosition(new Vector2f(400, 300));
			w.getPlayers().add(ppe);
			targetsList.add(ppe);
		}
		else if (playerCount > 1)
		{
			ppe = PlaneFactory.createMultiplayerPlayer1PlaneEntity(stateID,
					Input.KEY_UP, Input.KEY_DOWN, Input.KEY_LEFT, Input.KEY_RIGHT, Input.KEY_RCONTROL, Input.KEY_RSHIFT, Input.KEY_MINUS);
			ppe.setSpeed(w.getCameraSpeed());
			ppe.setCameraSpeed(w.getCameraSpeed());
			ppe.setPosition(new Vector2f(400, 200));
			w.getPlayers().add(ppe);
			targetsList.add(ppe);
			PlayerPlaneEntity ppe2 = PlaneFactory.createMultiplayerPlayer2PlaneEntity(stateID,
					Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D, Input.KEY_F, Input.KEY_G, Input.KEY_N);
			ppe2.setSpeed(w.getCameraSpeed());
			ppe2.setCameraSpeed(w.getCameraSpeed());
			ppe2.setPosition(new Vector2f(400, 400));
			w.getPlayers().add(ppe2);
			targetsList.add(ppe2);
			if (playerCount == 3)
			{
				PlayerPlaneEntity ppe3 = PlaneFactory.createMultiplayerPlayer3PlaneEntity(stateID,
						Input.KEY_U, Input.KEY_J, Input.KEY_H, Input.KEY_K, Input.KEY_I, Input.KEY_O, Input.KEY_P);
				ppe3.setSpeed(w.getCameraSpeed());
				ppe3.setCameraSpeed(w.getCameraSpeed());
				ppe3.setPosition(new Vector2f(400, 300));
				w.getPlayers().add(ppe3);
				targetsList.add(ppe3);
			}
		}
		
		PlayerPlaneEntity[] targets = targetsList.toArray(new PlayerPlaneEntity[targetsList.size()]);
		
		// starting difficulty
		int difficulty = 1;
		
		// maximum difficulty (stops here) (value between 2 and 10)
		int maxDifficulty = 5;
		if (size > 0)
			maxDifficulty = 10;
		
		// difficulty gets increased all maxDifficultyCounter steps
		int maxDifficultyCounter = size+1;
			
		int difficultyCounter = 0;
		int worldState = 0;
		while (difficulty < maxDifficulty)
		{
			if (difficultyCounter == maxDifficultyCounter)
			{
				difficultyCounter = 0;
				difficulty++;
			}
			switch(worldState)
			{
			case 0:
				w.merge(createBasePlatform(stateID, difficulty, targets));
				break;
			case 1:
				w.merge(createBaseGates(stateID, difficulty, targets));
				break;
			case 2:
				w.merge(createBaseTowerDefense(stateID, difficulty, targets));
				break;
			case 3:
				w.merge(createCity(stateID, difficulty, targets));
				break;
			case 4:
				w.merge(createDepot(stateID, difficulty, targets));
				break;
			}
			worldState = (worldState+1)%5;
			difficultyCounter++;
		}
		
		w.merge(createFinish(stateID, ppe));

		return w;
	}
	
	public static World createExampleWorld(int stateID) throws SlickException
	{
		World w = new World("ExampleWorld", 1000);
		w.setBackground("/assets/background.png");
		
		List<PlayerPlaneEntity> targetsList = new ArrayList<PlayerPlaneEntity>();
		
		// player plane entity
		PlayerPlaneEntity ppe = null;
		ppe = PlaneFactory.createStandardPlayerPlaneEntity("PlayerPlane", stateID,
				Input.KEY_UP, Input.KEY_DOWN, Input.KEY_LEFT, Input.KEY_RIGHT, Input.KEY_Y, Input.KEY_X, Input.KEY_C);
		ppe.setSpeed(w.getCameraSpeed());
		ppe.setCameraSpeed(w.getCameraSpeed());
		ppe.setPosition(new Vector2f(400, 300));
		w.getPlayers().add(ppe);
		targetsList.add(ppe);
		
		PlayerPlaneEntity[] targets = targetsList.toArray(new PlayerPlaneEntity[targetsList.size()]);
		
		w.merge(createBasePlatform(stateID, 1, targets));
		
		return w;
	}
	
	public static World createWorld(int stateID, int difficulty, PlayerPlaneEntity... targets) throws SlickException
	{
		World w = new World("Dummy", 0);
		for (int worldState=0; worldState<5; worldState++)
		{
			switch(worldState)
			{
			case 0:
				w.merge(createBasePlatform(stateID, difficulty, targets));
				break;
			case 1:
				w.merge(createBaseGates(stateID, difficulty, targets));
				break;
			case 2:
				w.merge(createBaseTowerDefense(stateID, difficulty, targets));
				break;
			case 3:
				w.merge(createCity(stateID, difficulty, targets));
				break;
			case 4:
				w.merge(createDepot(stateID, difficulty, targets));
				break;
			}
		}
		return w;
	}
	
	private static World createBasePlatform(int stateID, int dif, PlayerPlaneEntity... targets) throws SlickException
	{
		World w = new World("Dummy", 1000);
		float height = Controls.displayResolution.y;
		for (int i = 0; i < w.getWidth()/100; ++i)
		{
			w.addEntity(StructureFactory.createBox2(i*100, height-50));
		}
		for (int i = 0; i < 7; ++i)
			w.addEntity(StructureFactory.createBox2small(125 + i*50, invert(575)));
		w.addEntity(StructureFactory.createBox(200, invert(650), stateID, null));
		w.addEntity(StructureFactory.createBox(300, invert(650), stateID, ItemFactory.createRandomItem().getType()));
		createFormation(w, PlaneFactory.createRandomPlane(400, invert(300), stateID, dif, targets), dif);
		createFormation(w, PlaneFactory.createRandomPlane(900, invert(300), stateID, dif, targets), dif);
		w.addEntity(PlaneFactory.createRandomTower(600, invert(100+40), stateID, dif, targets));
		return w;
	}
	
	private static World createBaseGates(int stateID, int dif, PlayerPlaneEntity... targets) throws SlickException
	{
		int rndGateCount = (int)(Math.floor(Math.random()*2+4));
		World w = new World("Dummy", 400+rndGateCount*300+400);
		float height = Controls.displayResolution.y;
		for (int i = 0; i < w.getWidth()/100; ++i)
		{
			w.addEntity(StructureFactory.createBox2(i*100, height-50));
		}
		int sign = 1;
		for (int i = 0; i < rndGateCount; ++i)
		{
			createGate(425+i*300, invert((float)(450+sign*(200+Math.random()*125))), w);
			sign = -sign;
			w.addEntity(ItemFactory.createRandomItem(550+i*300, (Math.random()<0.5?invert(150):invert(650))));
		}
		w.addEntity(PlaneFactory.createRandomTower(350, invert(140), stateID, dif, targets));
		w.addEntity(PlaneFactory.createRandomTower(500+rndGateCount*300, invert(140), stateID, dif, targets));
		return w;
	}
	
	private static World createBaseTowerDefense(int stateID, int dif, PlayerPlaneEntity... targets) throws SlickException
	{
		int rndTowerCount = (int)(Math.floor(Math.random()*3+3));
		int distance = 400;
		World w = new World("Dummy", 400+rndTowerCount*distance+600);
		float height = Controls.displayResolution.y;
		for (int i = 0; i < w.getWidth()/100; ++i)
		{
			w.addEntity(StructureFactory.createBox2(i*100, height-50));
		}
		for (int i = 0; i < rndTowerCount; ++i)
		{
			w.addEntity(StructureFactory.createBox2small(425+i*distance, invert(125)));
			w.addEntity(PlaneFactory.createRandomTower(480+i*distance, invert(140), stateID, dif, targets));
			w.addEntity(StructureFactory.createBox2(450+i*distance, invert(450)));
			w.addEntity(StructureFactory.createBox2(550+i*distance, invert(450)));
			w.addEntity(StructureFactory.createBox2(650+i*distance, invert(450)));
			w.addEntity(StructureFactory.createBox2(750+i*distance, invert(450)));
			if (i > 0)
				w.addEntity(ItemFactory.createRandomItem(550+i*distance, invert(250)));
		}
		for (int i = 2; i < rndTowerCount+2; ++i)
		{
			GameEntity ge = PlaneFactory.createRandomPlane(400+i*distance, invert(350), stateID, dif, targets);
			ge.setAiBehaviour(new AIBehaviour(ge, true));
			ge.getAiBehaviour().setMovementLinear();
			w.addEntity(ge);
			ge = PlaneFactory.createRandomPlane(425+i*distance, invert(250), stateID, dif, targets);
			ge.setAiBehaviour(new AIBehaviour(ge, true));
			ge.getAiBehaviour().setMovementLinear();
			w.addEntity(ge);
		}
		for (int i = 0; i < 6; ++i)
		{
			w.addEntity(StructureFactory.createBox2(350, invert(450+i*100)));
		}
		return w;
	}
	
	private static World createCity(int stateID, int dif, PlayerPlaneEntity... targets) throws SlickException
	{
		int offset = 400;
		int rndWidth = offset*2+(int)(Math.floor(2000+Math.random()*3000));
		World w = new World("Dummy", rndWidth);
		
		for (int i = 0; i < 4; ++i)
		{
			w.addEntity(StructureFactory.createBox2(i*100, invert(50)));
			//w.addEntity(StructureFactory.createBox2(rndWidth-offset+i*100, invert(50)));
		}
		w.addEntity(PlaneFactory.createRandomTower(300, invert(140), stateID, dif, targets));
		
		// buildings
		Color[] cs = {Color.gray, Color.red, Color.yellow, Color.green};
		int widthSum = offset-50;
		int towerCounter = 0;
		int itemCounter = 0;
		int planeCounter = 0;
		while (widthSum < rndWidth-offset)
		{
			Building s = new Building("building", (int)(2 + Math.floor(Math.random()*5)), 
					cs[(int)(Math.floor(Math.random()*cs.length))], stateID, Orientation.NEUTRAL);
			widthSum += s.getWidth();
			towerCounter += s.getWidth();
			itemCounter += s.getWidth();
			planeCounter += s.getWidth();
			s.setPosition(new Vector2f(widthSum-s.getShape().getWidth()/2, 
					Controls.displayResolution.y-(float)(Math.random()*200-50)));
			w.getStructures().add(s);
			
			// towers
			if (towerCounter >= 600)
			{
				w.addEntity(PlaneFactory.createRandomTower(widthSum - s.getShape().getWidth()/2, 
						s.getShape().getMinY() - 40, stateID, dif, targets));
				towerCounter = 0;
			}
			// items
			if (itemCounter >= 700)
			{
				w.addEntity(ItemFactory.createRandomItem(widthSum, invert(400+(float)(250*Math.random()))));
				itemCounter = 0;
			}
			// planes
			if (planeCounter >= 400)
			{
				createFormation(w, 
						PlaneFactory.createRandomPlane(
								widthSum, invert(450+(float)(200*Math.random())), stateID, dif, targets), dif);
				planeCounter = 0;
			}
		}
		widthSum += 50;
		w.addEntity(PlaneFactory.createRandomTower(widthSum, invert(140), stateID, dif, targets));
		//widthSum = widthSum+(int)(150-(widthSum%150.0f));
		while (widthSum < rndWidth)
		{
			w.addEntity(StructureFactory.createBox2(widthSum, invert(50)));
			widthSum += 100;
		}
		
		w.setWidth(widthSum);
		
		return w;
	}
	
	private static World createDepot(int stateID, int dif, PlayerPlaneEntity... targets) throws SlickException
	{
		int offset = 400;
		int nDepots = 3+(int)Math.floor(Math.random()*4);
		World w = new World("Dummy", offset+nDepots*1000+200);
		float height = Controls.displayResolution.y;
		for (int i = 0; i < w.getWidth()/100; ++i)
		{
			w.addEntity(StructureFactory.createBox2(i*100, height-50));
		}
		
		for (int i = 0; i < nDepots; ++i)
		{
			w.addEntity(StructureFactory.createBox2(offset +i*1000, invert(150)));
			w.addEntity(StructureFactory.createBox2(offset +i*1000, invert(250)));
			w.addEntity(PlaneFactory.createRandomTower(offset +i*1000, invert(340), stateID, dif, targets));
			
			createFormation(w, PlaneFactory.createRandomPlane(offset+i*1000, invert(550), stateID, dif, targets), dif);
			
			// Boxes
			List<Integer> list = new ArrayList<Integer>();
			for (int j = 0; j < 4; ++j)
				list.add(j);
			float positionIndex = 0;
			int rndItemIndex = (int)Math.floor(Math.random()*list.size());
			while(!list.isEmpty())
			{
				int rndIndex = (int)Math.floor(Math.random()*list.size());
				int value = list.get(rndIndex);
				Type itemType = rndItemIndex==value?ItemFactory.createRandomItem().getType():null;
				list.remove(rndIndex);
				float x0 = positionIndex*100+offset+100+i*1000;
				switch(value)
				{
				case 0:
					w.addEntity(StructureFactory.createBox(x0, invert(150), stateID, itemType));
					w.addEntity(StructureFactory.createBoxSmall(x0-25, invert(225), stateID, null));
					break;
				case 1:
					w.addEntity(StructureFactory.createBoxBigWidth(x0+25, invert(150), stateID, itemType));
					w.addEntity(StructureFactory.createBoxSmall(x0+75, invert(225), stateID, null));
					positionIndex += 0.5f;
					break;
				case 2:
					w.addEntity(StructureFactory.createBoxBigHeight(x0, invert(175), stateID, itemType));
					break;
				case 3:
					w.addEntity(StructureFactory.createBox(x0, invert(150), stateID, itemType));
					break;
				}
				positionIndex++;
			}
			
			w.addEntity(PlaneFactory.createRandomTower(offset + 550+i*1000, invert(340), stateID, dif, targets));
			w.addEntity(StructureFactory.createBox2(offset + 550+i*1000, invert(150)));
			w.addEntity(StructureFactory.createBox2(offset + 550+i*1000, invert(250)));
		}
		
		
		return w;
	}
	
	private static World createFinish(int stateID, PlayerPlaneEntity target) throws SlickException
	{
		int offset = 400;
		World w = new World("Dummy", offset+4500);
		float height = Controls.displayResolution.y;
		for (int i = 0; i < offset/100; ++i)
		{
			w.addEntity(StructureFactory.createBox2(i*100, height-50));
		}
		for (int i = 0; i < 2; i++)
		{
			int y = (int)invert(400+i*300);
			for (int j = 0; j < 5; ++j)
			{
				int x = offset-100+j*100;
				if (j == 3)
					x = offset+1600;
				else if (j == 4)
					x = offset+3100;
				Item it = ItemFactory.createHealthItem("HI");
				it.setPosition(new Vector2f(x, y));
				w.addEntity(it);
				
				it = ItemFactory.createShieldItem("SI");
				it.setPosition(new Vector2f(x, y+100));
				w.addEntity(it);
				
				it = ItemFactory.createWeaponItem("WI");
				it.setPosition(new Vector2f(x, y+200));
				w.addEntity(it);
				
			}
			
		}
		GameEntity ge = PlaneFactory.createPlaneOfType(stateID, 10, target);
		ge.setPosition(new Vector2f(offset+1000, invert(425.0f)));
		createFormation(w, ge, 35.0f, 11);
		ge = PlaneFactory.createPlaneOfType(stateID, 10, target);
		ge.setPosition(new Vector2f(offset+2500, invert(425.0f)));
		createFormation(w, ge, 35.0f, 11);
		ge = PlaneFactory.createPlaneOfType(stateID, 10, target);
		ge.setPosition(new Vector2f(offset+4000, invert(425.0f)));
		createFormation(w, ge, 35.0f, 11);
		return w;
	}
	
	private static void createGate(float x, float opening, World w) throws SlickException
	{
		float openingIndex = (float)Math.floor(invert(opening) / 100.0f);
		w.addEntity(StructureFactory.createGate(x, invert(openingIndex*50+100+62.5f)));
		for (int i = 0; i < 14; ++i)
		{
			if (i >= openingIndex)
				w.addEntity(StructureFactory.createBox2small(x, invert(i*50+250)));
			else
				w.addEntity(StructureFactory.createBox2small(x, invert(i*50+125)));
		}
	}
	
	private static float invert(float value)
	{
		return Controls.displayResolution.y - value;
	}
	
	private static void createFormation(World w, GameEntity ge, int difficulty)
	{
		int number = 3;
		switch(difficulty)
		{
		case 1:
			number = 3;
			break;
		case 2:
			number = 3+(int)(Math.floor(Math.random()*2));
			break;
		case 3:
			number = 4+(int)(Math.floor(Math.random()*2));
			break;
		case 4:
			number = 5;
			break;
		case 5:
			number = 3;
			break;
		case 6:
			number = 3;
			break;
		case 7:
			number = 3;
			break;
		case 8:
			number = 3+(int)(Math.floor(Math.random()*2));
			break;
		case 9:
			number = 4+(int)(Math.floor(Math.random()*2));
			break;
		}
		if (Math.random() < 0.5)
			createFormation(w, ge, 30.0f, number);
		else
			createSpawnPlanesFormation(w, ge, 30.0f, number);
	}
	
	private static void createSpawnPlanesFormation(World w, GameEntity ge, float distance, int number)
	{
		ge.setItem(null);
		float height = number * (ge.getShape().getHeight()+distance);
		float width = (float)(Math.random()*100.0f);
		ge.setPosition(new Vector2f(ge.getPosition().x, ge.getPosition().y-height/2));
		if (ge.getId().equals("plane10")) // zeppelin
			ge.setSpeed(Attributes.aiPlaneSpeedWeak);
		ge.getAiBehaviour().setFireModeOn(true);
		ge.getAiBehaviour().setMovement(AIBehaviour.getPath(new Vector2f(ge.getPosition()),
				new Vector2f(-width, height/2),
				new Vector2f(width, height/2),
				new Vector2f(width, -height/2),
				new Vector2f(-width, -height/2)));
		
		Event cameraEvent = 
				new CameraMovementEvent("camera movement event", Controls.displayResolution.x, 100.0f);
		
		// Planes that are fired
		List<GameEntity> planes = new ArrayList<GameEntity>();
		
		for (int j = 0; j < number; ++j)
		{
			GameEntity peTemp = new GameEntity(ge);
			if (Math.random() < Attributes.itemDropProbability)
				peTemp.setItem(ItemFactory.createRandomItem());
			planes.add(peTemp);
		}
		
		SpawnPlanesAction spa = new SpawnPlanesAction(planes, Attributes.spawnPlanesFrequency);
		cameraEvent.addAction(spa);
		spa.setPosition(new Vector2f(ge.getPosition()));
		w.getSpawnPlanesActions().add(spa);
	}
	
	private static void createFormation(World w, GameEntity ge, float distance, int number)
	{
		ge.setItem(null);
		GameEntity geTemp = new GameEntity(ge);
		geTemp.setAiBehaviour(new AIBehaviour(geTemp, true));
		geTemp.getAiBehaviour().setMovementLinear();
		if (Math.random() < Attributes.itemDropProbability)
			geTemp.setItem(ItemFactory.createRandomItem());
		if (number % 2 == 1)
			w.addEntity(geTemp);
		
		for (int i = 1; i < Math.floor(number/2)+1; ++i)
		{
			geTemp = new GameEntity(ge);
			if (number % 2 == 1)
				geTemp.setPosition(
						new Vector2f(ge.getPosition().x+20*i, ge.getPosition().y + i*(ge.getShape().getHeight()+distance)));
			else
				geTemp.setPosition(
						new Vector2f(ge.getPosition().x+20*i, ge.getPosition().y + i*(ge.getShape().getHeight()+distance)
								-ge.getShape().getHeight()/2));
			geTemp.setAiBehaviour(new AIBehaviour(geTemp, true));
			geTemp.getAiBehaviour().setMovementLinear();
			if (Math.random() < Attributes.itemDropProbability)
				geTemp.setItem(ItemFactory.createRandomItem());
			w.addEntity(geTemp);
			geTemp = new GameEntity(ge);
			if (number % 2 == 1)
				geTemp.setPosition(
						new Vector2f(ge.getPosition().x+20*i, ge.getPosition().y - i*(ge.getShape().getHeight()+distance)));
			else
				geTemp.setPosition(
						new Vector2f(ge.getPosition().x+20*i, ge.getPosition().y - i*(ge.getShape().getHeight()+distance)
								-ge.getShape().getHeight()/2));
			geTemp.setAiBehaviour(new AIBehaviour(geTemp, true));
			geTemp.getAiBehaviour().setMovementLinear();
			if (Math.random() < Attributes.itemDropProbability)
				geTemp.setItem(ItemFactory.createRandomItem());
			w.addEntity(geTemp);
		}
	}
}
