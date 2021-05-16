package jng.tests.adapter;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.basicactions.ChangeStateInitAction;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.Event;
import jng.entities.GameEntity;
import jng.entities.PlayerPlaneEntity;
import jng.entities.Projectile;
import jng.tests.TestAppGameContainer;
import jng.tests.adapter.interfaces.IJngTestAdapterMinimal;
import jng.ui.Controls;
import jng.ui.GameplayState;
import jng.ui.Jng;
import jng.world.World;
import jng.world.WorldFileManager;

public class JngTestAdapterMinimal implements IJngTestAdapterMinimal {
	
	private Jng jng; 						// erbt von StateBasedGame
	private TestAppGameContainer app;		// spezielle Variante des AppGameContainer,
	
	protected StateBasedEntityManager entityManager;
	
	/**
	 * Verwenden Sie diesen Konstruktor zur Initialisierung von allem,
	 * was sie benoetigen
	 * 
	 * Use this constructor to set up everything you need.
	 */
	public JngTestAdapterMinimal() {
		jng = null;
		entityManager = StateBasedEntityManager.getInstance();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#initializeGame()
	 */
	@Override
	public void initializeGame() {
		
		System.out.println(System.getProperty("user.dir") + "/native/windows");
    	if (System.getProperty("os.name").toLowerCase().contains("windows")) { ;
    		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/native/windows");
    	} else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
    		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/native/macosx");
    	} else {
    		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/native/" +System.getProperty("os.name").toLowerCase());
    	}
    	
    	Controls.debug = true;
		jng = new Jng();
		
		// Initialisiere die statische Klasse Map
		try {
			app = new TestAppGameContainer(jng, Controls.displayResolution.x, Controls.displayResolution.y, false);
			app.start(0);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#startGame()
	 */
	@Override
	public void startGame() {
		Entity dummy = new Entity("dummy");
		Event dummyEvent = new Event("dummyEvent") {
			
			boolean fired = false;
			
			@Override
			protected boolean performAction(GameContainer gc, StateBasedGame sb,
					int delta) {
				if (!fired)
				{
					fired = true;
					return true;
				}
				return false;
			}
		};
		dummyEvent.addAction(new ChangeStateInitAction(Jng.GAMEPLAY_STATE));
		dummy.addComponent(dummyEvent);
		entityManager.addEntity(jng.getCurrentStateID(), dummy);
		try {
			app.updateGame(0);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#stopGame()
	 */
	@Override
	public void stopGame() {
		if (app != null) {
			app.exit();
			app.destroy();
		}
		StateBasedEntityManager.getInstance().clearAllStates();
		jng = null;
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#runGame(int)
	 */
	@Override
	public void runGame(int ms) {
		if (jng != null && app != null) {
			try {
				app.updateGame(ms);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#pauseGame()
	 */
	@Override
	public void pauseGame() {
		if (jng.getCurrentState() instanceof GameplayState) {
			GameplayState gs = (GameplayState)jng.getCurrentState();
			gs.setGamePaused(true);
		}
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#unpauseGame()
	 */
	@Override
	public void unpauseGame() {
		if (jng.getCurrentState() instanceof GameplayState) {
			GameplayState gs = (GameplayState)jng.getCurrentState();
			gs.setGamePaused(false);
		}
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getCameraPosition()
	 */
	@Override
	public float getCameraPosition() {
		return GameplayState.cameraPosition.x;
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#isGameFinished()
	 */
	@Override
	public boolean isGameFinished() {
		return jng.getCurrentStateID() == Jng.GAMEWON_STATE ||
				jng.getCurrentStateID() == Jng.GAMELOST_STATE;
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#isGameWon()
	 */
	@Override
	public boolean isGameWon() {
		if (jng.getCurrentStateID() == Jng.GAMEWON_STATE)
			return true;
		return false;
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#parseLevel(java.lang.String)
	 */
	@Override
	public void parseLevel(String level) {
		World w;
		try {
			w = WorldFileManager.parseWorld(level, Jng.GAMEPLAY_STATE);
			World.setInstance(w);
		} catch (IOException | SlickException e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getLevelName()
	 */
	@Override
	public String getLevelName()
	{
		return World.getInstance().getName();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getLevelWidth()
	 */
	@Override
	public int getLevelWidth()
	{
		return (int)World.getInstance().getWidth();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getLevelCameraSpeed()
	 */
	@Override
	public float getLevelCameraSpeed()
	{
		return World.getInstance().getCameraSpeed();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getLevelDisplaySize()
	 */
	@Override
	public Point2D.Float getLevelDisplaySize()
	{
		return new Point2D.Float(Controls.displayResolution.x,
				Controls.displayResolution.y);
	}
	
	protected PlayerPlaneEntity getPlayerPlane()
	{
		return World.getInstance().getPlayers().get(0);
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getPlayerPlanePosition()
	 */
	@Override
	public Point2D.Float getPlayerPlanePosition()
	{
		PlayerPlaneEntity ppe = World.getInstance().getPlayers().get(0);
		return new Point2D.Float(ppe.getPosition().x, ppe.getPosition().y);
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getPlayerPlaneRotation()
	 */
	@Override
	public float getPlayerPlaneRotation()
	{
		return getPlayerPlane().getRotation();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getPlayerPlaneScale()
	 */
	@Override
	public float getPlayerPlaneScale()
	{
		return getPlayerPlane().getScale();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getPlayerPlaneOrientation()
	 */
	@Override
	public int getPlayerPlaneOrientation()
	{
		return getPlayerPlane().getOrientation().ordinal();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getPlayerPlaneIsInvicible()
	 */
	@Override
	public boolean getPlayerPlaneIsInvicible()
	{
		return getPlayerPlane().isInvincible();
	}

	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getPlayerPlaneSpeed()
	 */
	@Override
	public float getPlayerPlaneSpeed()
	{
		return getPlayerPlane().getSpeed();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getPlayerPlaneHealth()
	 */
	@Override
	public float getPlayerPlaneHealth()
	{
		return getPlayerPlane().getHealth();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getPlayerPlaneMaxHealth()
	 */
	@Override
	public float getPlayerPlaneMaxHealth()
	{
		return getPlayerPlane().getMaxHealth();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getPlayerPlaneShields()
	 */
	@Override
	public float getPlayerPlaneShields()
	{
		return getPlayerPlane().getArmor();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getPlayerPlaneMaxShields()
	 */
	@Override
	public float getPlayerPlaneMaxShields()
	{
		return getPlayerPlane().getMaxArmor();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getNEnemyPlanes()
	 */
	@Override
	public int getNEnemyPlanes()
	{
		int counter = 0;
		for (Entity e : 
			entityManager.getEntitiesByState(Jng.GAMEPLAY_STATE)) {
			if (e instanceof GameEntity &&
					((GameEntity)e).getId().startsWith("plane"))
				counter++;
				
		}
		return counter;
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getEnemyPlanePosition(int)
	 */
	@Override
	public Point2D.Float getEnemyPlanePosition(int index)
	{
		GameEntity ge = getEnemyPlanes().get(index);
		return new Point2D.Float(ge.getPosition().x, ge.getPosition().y);
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getEnemyPlaneRotation(int)
	 */
	@Override
	public float getEnemyPlaneRotation(int index)
	{
		return getEnemyPlanes().get(index).getRotation();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getEnemyPlaneScale(int)
	 */
	@Override
	public float getEnemyPlaneScale(int index)
	{
		return getEnemyPlanes().get(index).getScale();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getEnemyPlaneOrientation(int)
	 */
	@Override
	public int getEnemyPlaneOrientation(int index)
	{
		return getEnemyPlanes().get(index).getOrientation().ordinal();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getEnemyPlaneIsInvicible(int)
	 */
	@Override
	public boolean getEnemyPlaneIsInvicible(int index)
	{
		return getEnemyPlanes().get(index).isInvincible();
	}

	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getEnemyPlaneSpeed(int)
	 */
	@Override
	public float getEnemyPlaneSpeed(int index)
	{
		return getEnemyPlanes().get(index).getSpeed();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getEnemyPlaneEntityID(int)
	 */
	@Override
	public String getEnemyPlaneEntityID(int index)
	{
		return getEnemyPlanes().get(index).getId();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getEnemyPlaneHealth(int)
	 */
	@Override
	public float getEnemyPlaneHealth(int index)
	{
		return getEnemyPlanes().get(index).getHealth();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getEnemyPlaneMaxHealth(int)
	 */
	@Override
	public float getEnemyPlaneMaxHealth(int index)
	{
		return getEnemyPlanes().get(index).getMaxHealth();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getEnemyPlaneShields(int)
	 */
	@Override
	public float getEnemyPlaneShields(int index)
	{
		return getEnemyPlanes().get(index).getArmor();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getEnemyPlaneMaxShields(int)
	 */
	@Override
	public float getEnemyPlaneMaxShields(int index)
	{
		return getEnemyPlanes().get(index).getMaxArmor();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getEnemyPlaneFireModeOn(int)
	 */
	@Override
	public boolean getEnemyPlaneFireModeOn(int index)
	{
		return getEnemyPlanes().get(index).getAiBehaviour().isFireModeOn();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getEnemyPlaneMovementPath(int)
	 */
	@Override
	public List<Point2D.Float> getEnemyPlaneMovementPath(int index) {
		List<Point2D.Float> list = new ArrayList<Point2D.Float>();
		for (Vector2f p : getEnemyPlanes().get(index).getAiBehaviour().getPath()) {
			list.add(new Point2D.Float(p.x, p.y));
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getEnemyPlanesShootingFrequency(int)
	 */
	@Override
	public float getEnemyPlanesShootingFrequency(int index) {
		GameEntity ge = getEnemyPlanes().get(index);
		return ge.getWeapons().get(0).getFrequency();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#setPlayerPlanePosition(java.awt.geom.Point2D.Float)
	 */
	@Override
	public void setPlayerPlanePosition(Point2D.Float position) {
		getPlayerPlane().setPosition(new Vector2f(position.x, position.y));
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#setPlayerPlaneHealth(float)
	 */
	@Override
	public void setPlayerPlaneHealth(float health) {
		getPlayerPlane().setHealth(health);
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#damagePlayerPlane(float)
	 */
	@Override
	public void damagePlayerPlane(float damage) {
		getPlayerPlane().damage(damage, Jng.GAMEPLAY_STATE);
	}
	
	protected List<GameEntity> getEnemyPlanes()
	{
		List<GameEntity> list = new ArrayList<GameEntity>();
		for (GameEntity ge : World.getInstance().getEnemies())
		{
			if (ge.getId().startsWith("plane"))
				list.add(ge);
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getProjectilesPositionsOfEnemyPlane(int)
	 */
	@Override
	public List<Point2D.Float> getProjectilesPositionsOfEnemyPlane(int index) {
		List<Point2D.Float> list = new ArrayList<Point2D.Float>();
		GameEntity ge = getEnemyPlanes().get(index);
		for (Entity e : entityManager.getEntitiesByState(Jng.GAMEPLAY_STATE)) {
			if (e instanceof Projectile) {
				Projectile p = (Projectile)e;
				if (p.getSource() == ge)
					list.add(new Point2D.Float(
							e.getPosition().x, e.getPosition().y));
			}
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getProjectileSpeedOfEnemyPlane(int)
	 */
	@Override
	public float getProjectileSpeedOfEnemyPlane(int index) {
		return getEnemyPlanes().get(index).getWeapons().get(0).getLaunchPads().get(0).getProjectile().getSpeed();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#getNPrimaryProjectiles()
	 */
	@Override
	public int getNPrimaryProjectiles() {
		int count = 0;
		for (Entity e : entityManager.getEntitiesByState(Jng.GAMEPLAY_STATE)) {
			if (e instanceof Projectile) {
				Projectile p = (Projectile) e;
				if (p.getSource() instanceof PlayerPlaneEntity) {
					if (p.getId().startsWith("ProjectileMG"))
						count++;
				}
			}
		}
		return count;
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#handleKeyFirePrimary()
	 */
	@Override
	public void handleKeyFirePrimary() {
		handleKeyDown(200, Input.KEY_Y);
	}

	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#handleKeyPressedUp()
	 */
	@Override
	public void handleKeyPressedUp() {
		handleKeyDown(100, Input.KEY_UP);
	}

	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#handleKeyPressedDown()
	 */
	@Override
	public void handleKeyPressedDown() {
		handleKeyDown(100, Input.KEY_DOWN);
	}

	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#handleKeyPressedLeft()
	 */
	@Override
	public void handleKeyPressedLeft() {
		handleKeyDown(100, Input.KEY_LEFT);
	}

	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterMinimal#handleKeyPressedRight()
	 */
	@Override
	public void handleKeyPressedRight() {
		handleKeyDown(100, Input.KEY_RIGHT);
	}
	
	/**
	 * This Method should emulate the key down event.
	 * 
	 * @param updatetime
	 * @param input
	 */
	protected void handleKeyDown(int updatetime, Integer input) {
		if (jng != null && app != null) {
			app.getTestInput().setKeyDown(input);
			try {
				app.updateGame(updatetime);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}
}
