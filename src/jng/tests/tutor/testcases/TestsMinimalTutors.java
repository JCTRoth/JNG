package jng.tests.tutor.testcases;

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

public class TestsMinimalTutors {
	
	private static IJngTestAdapterMinimal minimal;
	
	private static String exampleWorld = 
			"Level ExampleWorld 2000.0 /assets/background.png 0.05 1440 900\n" + 
			"PlayerPlaneEntity 400.0 300.0 90.0 1.0 PlayerPlane /assets/plane1Right.png 0 false 5.0 5.0 5.0 5.0 0.05 1 1 200 208 203 205 21 45 46\n" + 
			"GameEntity 1400.0 600.0 -90.0 1.0 plane3 /assets/planes/plane3.png 1 false 1.0 1.0 0.5 0.5 0.1 3 true [ 1400.0 600.0 1000.0 350.0 ] true true -1\n" + 
			"GameEntity 1420.0 673.0 -90.0 1.0 plane3 /assets/planes/plane3.png 1 false 1.0 1.0 0.5 0.5 0.1 3 true [ 1420.0 673.0 1000.0 673.0 ] true true -1\n" + 
			"GameEntity 1420.0 527.0 -90.0 1.0 plane3 /assets/planes/plane3.png 1 false 1.0 1.0 0.5 0.5 0.1 3 true [ 1420.0 527.0 1000.0 527.0 ] true true -1\n" + 
			"SpawnPlanesAction 1900.0 505.5 0.0015 [ GameEntity 1900.0 505.5 -90.0 1.0 plane5 /assets/planes/plane5.png 1 false 1.0 1.0 0.5 0.5 0.1 5 true [ "
			+ "1900.0 505.5 1811.0427 600.0 1900.0 694.5 1988.9573 600.0 1900.0 505.5 ] true true -1 GameEntity 1900.0 505.5 -90.0 1.0 plane5 /assets/planes/"
			+ "plane5.png 1 false 1.0 1.0 0.5 0.5 0.1 5 true [ 1900.0 505.5 1811.0427 600.0 1900.0 694.5 1988.9573 600.0 1900.0 505.5 ] true true -1 GameEntity "
			+ "1900.0 505.5 -90.0 1.0 plane5 /assets/planes/plane5.png 1 false 1.0 1.0 0.5 0.5 0.1 5 true [ 1900.0 505.5 1811.0427 600.0 1900.0 694.5 1988.9573 "
			+ "600.0 1900.0 505.5 ] true true -1  ]";

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
	
	@Test
	public void testPlanesControls() {
		
		int direction = 0;
		Point2D.Float pBefore;
		Point2D.Float pCurrent;
		for (int i = 0; i < 20; ++i)
		{
			switch(direction) {
			case 0:
				pBefore = minimal.getPlayerPlanePosition();
				minimal.handleKeyPressedUp();
				pCurrent = minimal.getPlayerPlanePosition();
				assertTrue("Player plane did not move up.", pCurrent.y < pBefore.y);
				break;
			case 1:
				// down
				pBefore = minimal.getPlayerPlanePosition();
				minimal.handleKeyPressedDown();
				pCurrent = minimal.getPlayerPlanePosition();
				assertTrue("Player plane did not move down.", pCurrent.y > pBefore.y);
				break;
			case 2:
				// left
				pBefore = minimal.getPlayerPlanePosition();
				minimal.handleKeyPressedLeft();
				pCurrent = minimal.getPlayerPlanePosition();
				assertTrue("Player plane did not move left.", pCurrent.x < pBefore.x);
				break;
			case 3:
				// right
				pBefore = minimal.getPlayerPlanePosition();
				minimal.handleKeyPressedRight();
				pCurrent = minimal.getPlayerPlanePosition();
				assertTrue("Player plane did not move right.", pCurrent.x > pBefore.x);
				break;
				
			}
			direction++;
		}
	}
	
	@Test
	public void testPlaneFirePrimaryWeapon()
	{
		int projectileCountBefore;
		int projectileCountNow;
		// not to many repetitions because a projectile could collide and thus get removed
		for (int i = 0; i < 4; ++i) {
			projectileCountBefore = minimal.getNPrimaryProjectiles();
			minimal.handleKeyFirePrimary();
			projectileCountNow = minimal.getNPrimaryProjectiles();
			assertTrue("projectile count after fireing(" + projectileCountNow + ") "
					+ "is smaller or equals the projectile count before(" + projectileCountBefore + ")",
					projectileCountNow > projectileCountBefore);
		}
	}
	
	@Test
	public void pauseGameTest() {
		for (int i = 0; i < 10; ++i)
		{
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
	}
	
	@Test
	public void screenMovementTest() {
		for (int i = 0; i < 5; ++i)
		{
			float cameraPositionBefore = minimal.getCameraPosition();
			minimal.runGame(500);
			float cameraPositionCurrent = minimal.getCameraPosition();
			assertThat("camera did not move forward in the running game",
					cameraPositionBefore,
					lessThan(cameraPositionCurrent));			
		}
	}
	
	@Test
	public void screenMovementTest2() {
		for (int i = 0; i < 5; ++i) {
			float cameraPositionBefore = minimal.getCameraPosition();
			float playerPlanePositionBefore = minimal.getPlayerPlanePosition().x;
			minimal.runGame(500);
			float cameraPositionCurrent = minimal.getCameraPosition();
			float playerPlanePositionCurrent = minimal.getPlayerPlanePosition().x;
			assertEquals("playerPlane is not moving with the same speed forward as the screen",
					cameraPositionCurrent - cameraPositionBefore,
					playerPlanePositionCurrent - playerPlanePositionBefore, 0.1);
		}
	}
	
	@Test
	public void simpleEnemyAIShootingFrequencyTest() {
		float frequency = minimal.getEnemyPlanesShootingFrequency(0);
		// time between each shot + a small offset
		int timeBetweenEachShot = (int)(1 / frequency)+50;
		minimal.runGame(timeBetweenEachShot);
		assertThat(minimal.getProjectilesPositionsOfEnemyPlane(0).size(),
				greaterThan(0));
		minimal.runGame(timeBetweenEachShot);
		assertThat(minimal.getProjectilesPositionsOfEnemyPlane(0).size(),
				greaterThan(0));
		minimal.runGame(timeBetweenEachShot);
		assertThat(minimal.getProjectilesPositionsOfEnemyPlane(0).size(),
				greaterThan(0));
		
	}
	
	@Test
	public void healthTest() {
		// take all shields away
		minimal.damagePlayerPlane(5.0f);
		float healthCurrent;
		for (int i = 0; i < 4; ++i) {
			healthCurrent = minimal.getPlayerPlaneHealth();
			assertEquals("health", 5.0f-i*1.0f, healthCurrent, 0.01);			
			minimal.damagePlayerPlane(1.0f);
		}
		healthCurrent = minimal.getPlayerPlaneHealth();
		assertEquals("health", 1.0f, healthCurrent, 0.01);			
	}
	
	@Test
	public void shieldsTest() {
		float shieldsCurrent;
		for (int i = 0; i < 4; ++i) {
			shieldsCurrent = minimal.getPlayerPlaneShields();
			minimal.damagePlayerPlane(1.0f);
			assertEquals("shields", 5.0f-i*1.0f, shieldsCurrent, 0.01);			
		}
		shieldsCurrent = minimal.getPlayerPlaneShields();
		assertEquals("shields", 1.0f, shieldsCurrent, 0.01);	
	}
	
	@Test
	public void healthShieldsTest() {
		minimal.damagePlayerPlane(3.5f);
		assertEquals("shields", 1.5f, minimal.getPlayerPlaneShields(), 0.01);
		minimal.damagePlayerPlane(3.0f);
		assertEquals("shields", 0.0f, minimal.getPlayerPlaneShields(), 0.01);
		assertEquals("health", 3.5f, minimal.getPlayerPlaneHealth(), 0.01);
		minimal.damagePlayerPlane(3.0f);
		assertEquals("shields", 0.0f, minimal.getPlayerPlaneShields(), 0.01);
		assertEquals("health", 0.5f, minimal.getPlayerPlaneHealth(), 0.01);
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
		float frequency = minimal.getEnemyPlanesShootingFrequency(1);
		float projectileSpeed = minimal.getProjectileSpeedOfEnemyPlane(1);
		// time between each shot + a small offset
		int timeBetweenEachShot = (int)(1 / frequency)+30;
		minimal.runGame(timeBetweenEachShot);
		minimal.runGame(0);
		
		float shieldsBefore = minimal.getPlayerPlaneShields();
		float xPosition = minimal.getEnemyPlanePosition(1).x;
		minimal.setPlayerPlanePosition(new Point2D.Float(xPosition-200, 673.0f));
		minimal.runGame(0);
		
		float flyingDuration = 200.0f / projectileSpeed + 30f;
		minimal.runGame((int)flyingDuration);
		minimal.runGame(0);
		
		float shieldsCurrent = minimal.getPlayerPlaneShields();
		assertThat(shieldsCurrent, lessThan(shieldsBefore));
		
		xPosition = minimal.getEnemyPlanePosition(0).x;
		minimal.setPlayerPlanePosition(new Point2D.Float(xPosition-200, 673.0f));
		minimal.runGame(0);
		
		minimal.runGame((int)flyingDuration);
		minimal.runGame(0);
		
		shieldsCurrent = minimal.getPlayerPlaneShields();
		assertThat(shieldsCurrent, lessThan(shieldsBefore));
	}
}
