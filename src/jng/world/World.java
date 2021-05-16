package jng.world;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.Event;
import eea.engine.event.OREvent;
import jng.actions.AddRemoveEntityAction;
import jng.actions.SpawnPlanesAction;
import jng.entities.AbstractEntity;
import jng.entities.Attributes;
import jng.entities.GameEntity;
import jng.entities.Item;
import jng.entities.PlayerPlaneEntity;
import jng.entities.Projectile;
import jng.entities.Structure;
import jng.events.CameraMovementEvent;
import jng.events.OwnerOutOfScreenEvent;
import jng.ui.Controls;

public class World {
	
	private static World world = new World("NewWorld", 0);
	
	private String name;

	private Entity background;
	
	private String backgroundPath;
	
	// speed with which the camera moves
	private float cameraSpeed;
	
	private List<PlayerPlaneEntity> players;
	
	private List<GameEntity> enemies;
	
	private List<Projectile> projectiles;
	
	private List<Structure> structures;
	
	private List<Item> items;
	
	private List<Entity> others;
	
	private List<SpawnPlanesAction> spawnPlanesActions;
	
	// when the camera comes to the level end, the game is won
	private float width;

	public World(String name, float width) {
		this.width = width;
		this.name = name;
		players = new ArrayList<PlayerPlaneEntity>();
		enemies = new ArrayList<GameEntity>();
		projectiles = new ArrayList<Projectile>();
		structures = new ArrayList<Structure>();
		items = new ArrayList<Item>();
		others = new ArrayList<Entity>();
		spawnPlanesActions = new ArrayList<SpawnPlanesAction>();
		cameraSpeed = Attributes.standardCameraSpeed;
	}
	
	public void resetWorld() {
		players.clear();
		enemies.clear();
		projectiles.clear();
		structures.clear();
		items.clear();
		others.clear();
		spawnPlanesActions.clear();
	}
	
	public static World getInstance()
	{
		return world;
	}
	
	public static void setInstance(World world)
	{
		World.world = world;
	}
	
	public void addEntity(Entity e) {
		if (e instanceof PlayerPlaneEntity)
			players.add((PlayerPlaneEntity) e);
		else if (e instanceof Structure)
			structures.add((Structure) e);
		else if (e instanceof GameEntity)
			enemies.add((GameEntity) e);
		else if (e instanceof Projectile)
			projectiles.add((Projectile) e);
		else if (e instanceof Item)
			items.add((Item)e);
		else
			others.add(e);
	}
	
	public void convertSpawnPlanesActionsInEntities()
	{
		for (SpawnPlanesAction spa : spawnPlanesActions)
		{
			Event cameraEvent = 
					new CameraMovementEvent("camera movement event", Controls.displayResolution.x, 100.0f);
			cameraEvent.addAction(spa);
			Entity e = new Entity("dummy");
			e.setPosition(spa.getPosition());
			e.addComponent(cameraEvent);
			addEntity(e);
		}
	}
	
	/**
	 * merges this world with the given one. In this process this world gets
	 * all entities from world.
	 * @param w
	 */
	public void merge(World world)
	{
		for (AbstractEntity e : world.getEnemies()) {
			e.setPosition(new Vector2f(e.getPosition().x+width, e.getPosition().y));
			GameEntity ge = (GameEntity) e;
			Vector2f[] pArray = ge.getAiBehaviour().getPath();
			if (ge.getAiBehaviour().getPath() != null)
			{
				for (int i = 0; i < ge.getAiBehaviour().getPath().length; ++i)
				{
					pArray[i] = new Vector2f(pArray[i].x + width, pArray[i].y);
				}				
			}
			addEntity(e);
		}
		for (AbstractEntity e : world.getPlayers()){
			e.setPosition(new Vector2f(e.getPosition().x+width, e.getPosition().y));
			addEntity(e);
		}
		for (AbstractEntity e : world.getProjectiles()){
			e.setPosition(new Vector2f(e.getPosition().x+width, e.getPosition().y));
			addEntity(e);
		}
		for (AbstractEntity e : world.getStructures()){
			e.setPosition(new Vector2f(e.getPosition().x+width, e.getPosition().y));
			addEntity(e);
		}
		for (AbstractEntity e : world.getItems()){
			e.setPosition(new Vector2f(e.getPosition().x+width, e.getPosition().y));
			addEntity(e);
		}
		for (SpawnPlanesAction e : world.getSpawnPlanesActions()){
			e.setPosition(new Vector2f(e.getPosition().x+width, e.getPosition().y));
			for (GameEntity pe : e.getPlanes())
			{
				pe.setPosition(new Vector2f(pe.getPosition().x+width, pe.getPosition().y));
				Vector2f[] pArray = pe.getAiBehaviour().getPath();
				for (int i = 0; i < pe.getAiBehaviour().getPath().length; ++i)
				{
					pArray[i] = new Vector2f(pArray[i].x + width, pArray[i].y);
				}
				
			}
			spawnPlanesActions.add(e);
		}
		width += world.getWidth();
	}
	
	public void loadNewWorld(int stateID) {
		StateBasedEntityManager entityManager = StateBasedEntityManager.getInstance();
		entityManager.clearEntitiesFromState(stateID);
		//entityManager.addEntity(stateID, world.getBackground());
		convertSpawnPlanesActionsInEntities();
		
		for (Entity e : getEnemies())
			addEntityAsInScreenEvent(stateID, e);
		for (Entity e : getPlayers())
			addEntityAsInScreenEvent(stateID, e);
		for (Entity e : getProjectiles())
			addEntityAsInScreenEvent(stateID, e);
		for (Entity e : getStructures())
			addEntityAsInScreenEvent(stateID, e);
		for (Entity e : getItems())
			addEntityAsInScreenEvent(stateID, e);
		for (Entity e : getOthers())
			addEntityAsInScreenEvent(stateID, e);
	}
	
	/**
	 * Before inserting an entity, it has to be made sure that the entity only
	 * spawns when it is in the visible screen location. Therefore, a "dummy" event
	 * is used that adds the given entity when the screen reaches its location and
	 * removes itself afterwards so that the entity is spawned only once.
	 * @param stateID
	 * @param e
	 */
	private void addEntityAsInScreenEvent(int stateID, Entity e)
	{
		StateBasedEntityManager entityManager = StateBasedEntityManager.getInstance();
		Event screenEvent = new OwnerOutOfScreenEvent("outofscreenevent", 300.0f, true);
		Entity dummy = new Entity("dummy");
		screenEvent.addAction(new AddRemoveEntityAction(e, dummy, stateID));
		dummy.addComponent(screenEvent);
		dummy.setPosition(e.getPosition());
		
		entityManager.addEntity(stateID, dummy);
	}
	
	public List<PlayerPlaneEntity> getPlayers() {
		return players;
	}

	public List<GameEntity> getEnemies() {
		return enemies;
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	public List<Structure> getStructures() {
		return structures;
	}
	
	public List<Item> getItems() {
		return items;
	}

	public List<Entity> getOthers() {
		return others;
	}

	public Entity getBackground() {
		return background;
	}

	public void setBackground(Entity background) {
		this.background = background;
	}
	
	public void setBackground(String backgroundPath) throws SlickException {
		this.backgroundPath = backgroundPath;
		background = new Entity("background");
		background.setScale(2.0f);
    	background.setPosition(new Vector2f(Controls.displayResolution.x / 2, 
    			Controls.displayResolution.y / 2));
    	
    	Controls.loadImage(background, backgroundPath);
	}

	public float getCameraSpeed() {
		return cameraSpeed;
	}

	public void setCameraSpeed(float cameraSpeed) {
		this.cameraSpeed = cameraSpeed;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SpawnPlanesAction> getSpawnPlanesActions() {
		return spawnPlanesActions;
	}

	public String getBackgroundPath() {
		return backgroundPath;
	}
}
