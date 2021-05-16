package jng.tests.students.testcases;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.awt.geom.Point2D;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import jng.tests.adapter.JngTestAdapter;
import jng.tests.adapter.interfaces.IJngTestAdapterMinimal;

public class TestsMinimalStudents {
	
	private static IJngTestAdapterMinimal minimal;
	
	private static String exampleWorld = 
			"Level ExampleWorld 2000.0 /assets/background.png 0.05 1440 900\n" + 
			"PlayerPlaneEntity 400.0 300.0 90.0 1.0 PlayerPlane /assets/plane1Right.png 0 false 5.0 5.0 5.0 5.0 0.05 1 1 200 208 203 205 21 45 46\n" + 
			"GameEntity 1400.0 600.0 -90.0 1.0 plane2 /assets/planes/plane2.png 1 false 1.0 1.0 0.5 0.5 0.1 2 true [ 1400.0 600.0 1000.0 600.0 ] true true -1\n" + 
			"GameEntity 1420.0 683.0 -90.0 1.0 plane2 /assets/planes/plane2.png 1 false 1.0 1.0 0.5 0.5 0.1 2 true [ 1420.0 683.0 1000.0 683.0 ] true true 2\n" + 
			"GameEntity 1420.0 517.0 -90.0 1.0 plane2 /assets/planes/plane2.png 1 false 1.0 1.0 0.5 0.5 0.1 2 true [ 1420.0 517.0 1000.0 517.0 ] true true -1\n";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		minimal = JngTestAdapter.getMinimal();
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		
	}

	@Before
	public void setUp() throws Exception {
		minimal.initializeGame();
		minimal.parseLevel(exampleWorld);
		minimal.runGame(100);
		minimal.startGame();
	}
	
	@After
	public void tearDown() throws Exception {
		minimal.stopGame();
	}
	
	// ******************************
	// ******************************
	// Key input tests
	// ******************************
	// ******************************
	
	
	// Flugzeugsteuerung
	
	@Test
	public void testPlanesControls() {
		
		// up
		Point2D.Float pBefore = minimal.getPlayerPlanePosition();
		minimal.handleKeyPressedUp();
		Point2D.Float pCurrent = minimal.getPlayerPlanePosition();
		assertTrue("Player plane did not move up.", pCurrent.y < pBefore.y);
		// down
		pBefore = minimal.getPlayerPlanePosition();
		minimal.handleKeyPressedDown();
		pCurrent = minimal.getPlayerPlanePosition();
		assertTrue("Player plane did not move down.", pCurrent.y > pBefore.y);
		// left
		pBefore = minimal.getPlayerPlanePosition();
		minimal.handleKeyPressedLeft();
		pCurrent = minimal.getPlayerPlanePosition();
		assertTrue("Player plane did not move left.", pCurrent.x < pBefore.x);
		// right
		pBefore = minimal.getPlayerPlanePosition();
		minimal.handleKeyPressedRight();
		pCurrent = minimal.getPlayerPlanePosition();
		assertTrue("Player plane did not move right.", pCurrent.x > pBefore.x);
	}
	
	@Test
	public void testPlaneFirePrimaryWeapon() {
		int projectileCountBefore = minimal.getNPrimaryProjectiles();
		minimal.handleKeyFirePrimary();
		int projectileCountNow = minimal.getNPrimaryProjectiles();
		assertTrue("projectile count after fireing(" + projectileCountNow + ") "
				+ "is smaller or equals the projectile count before(" + projectileCountBefore + ")",
				projectileCountNow > projectileCountBefore);
	}
	
	@Test
	public void pauseGameTest() {
		
		minimal.pauseGame();
		
		float cameraPositionBefore = minimal.getCameraPosition();
		minimal.runGame(500);
		float cameraPositionCurrent = minimal.getCameraPosition();
		assertEquals("camera moved while game was paused",
				cameraPositionBefore,
				cameraPositionCurrent, 0.01);
		
		minimal.unpauseGame();
		
		cameraPositionBefore = minimal.getCameraPosition();
		minimal.runGame(500);
		cameraPositionCurrent = minimal.getCameraPosition();
		assertThat("camera did not move while game was unpaused",
				cameraPositionBefore,
				lessThan(cameraPositionCurrent));
	}
	
	@Test
	public void screenMovementTest() {
		float cameraPositionBefore = minimal.getCameraPosition();
		minimal.runGame(500);
		float cameraPositionCurrent = minimal.getCameraPosition();
		assertThat("camera did not move forward in the running game",
				cameraPositionBefore,
				lessThan(cameraPositionCurrent));
	}
	
	@Test
	public void screenMovementTest2() {
		float cameraPositionBefore = minimal.getCameraPosition();
		float playerPlanePositionBefore = minimal.getPlayerPlanePosition().x;
		minimal.runGame(500);
		float cameraPositionCurrent = minimal.getCameraPosition();
		float playerPlanePositionCurrent = minimal.getPlayerPlanePosition().x;
		assertEquals("the player plane has not moved with the same speed as the screen",
				cameraPositionCurrent - cameraPositionBefore,
				playerPlanePositionCurrent - playerPlanePositionBefore, 0.1);
	}
	
	@Test
	public void healthTest() {
		// take all shields away
		minimal.damagePlayerPlane(5.0f);
		
		minimal.damagePlayerPlane(4.0f);
		float healthCurrent = minimal.getPlayerPlaneHealth();
		assertEquals("health", 1.0f, healthCurrent, 0.01);
	}
	
	@Test
	public void shieldsTest() {
		minimal.damagePlayerPlane(4.0f);
		float shieldsCurrent = minimal.getPlayerPlaneShields();
		assertEquals("shields", 1.0f, shieldsCurrent, 0.01);
	}
	
	@Test
	public void healthShieldsTest() {
		minimal.damagePlayerPlane(3.0f);
		assertEquals("shields", 2.0f, minimal.getPlayerPlaneShields(), 0.01);
		minimal.damagePlayerPlane(3.0f);
		assertEquals("shields", 0.0f, minimal.getPlayerPlaneShields(), 0.01);
		assertEquals("health", 4.0f, minimal.getPlayerPlaneHealth(), 0.01);
		minimal.damagePlayerPlane(3.0f);
		assertEquals("shields", 0.0f, minimal.getPlayerPlaneShields(), 0.01);
		assertEquals("health", 1.0f, minimal.getPlayerPlaneHealth(), 0.01);
	}
	
	@Test
	public void gameWonCondition() {
		minimal.runGame(1000);
		minimal.runGame((int)(2000.0f/0.05f)+100);
		assertEquals("the game should be finished", true, minimal.isGameFinished());
		assertEquals("the game should be won", true, minimal.isGameWon());
	}
	
	@Test
	public void gameLostCondition() {
		minimal.runGame(1000);
		int counter = 0;
		
		while(minimal.getPlayerPlaneHealth() > 0.0f) {
			counter++;
			assertTrue("it took too many projectiles(" + counter + ") to kill the player plane",
					counter < 20);
			minimal.damagePlayerPlane(minimal.getPlayerPlaneHealth() +
					minimal.getPlayerPlaneShields());
			minimal.runGame(1000);			
		}
		assertEquals("the game should be finished", true, minimal.isGameFinished());
		assertEquals("the game should be lost", false, minimal.isGameWon());
	}
	
	@Test
	public void projectileShootingTest() {
		float frequency = minimal.getEnemyPlanesShootingFrequency(0);
		float projectileSpeed = minimal.getProjectileSpeedOfEnemyPlane(0);
		// time between each shot + a small offset
		int timeBetweenEachShot = (int)(1 / frequency)+30;
		minimal.runGame(timeBetweenEachShot);
		minimal.runGame(0);
		
		float shieldsBefore = minimal.getPlayerPlaneShields();
		float xPosition = minimal.getEnemyPlanePosition(0).x;
		minimal.setPlayerPlanePosition(new Point2D.Float(xPosition-200, 600.0f));
		minimal.runGame(0);
		
		float flyingDuration = 200.0f / projectileSpeed + 30f;
		minimal.runGame((int)flyingDuration);
		minimal.runGame(0);
		
		float shieldsCurrent = minimal.getPlayerPlaneShields();
		assertThat(shieldsCurrent, lessThan(shieldsBefore));
	}
}
