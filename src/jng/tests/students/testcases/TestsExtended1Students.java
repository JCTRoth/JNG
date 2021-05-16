package jng.tests.students.testcases;

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
import jng.tests.adapter.interfaces.IJngTestAdapterExtended1;

public class TestsExtended1Students {
	
	private static IJngTestAdapterExtended1 extended;
	
	private static String exampleWorld = 
			"Level ExampleWorld 2000.0 /assets/background.png 0.05 1440 900\n" + 
			"Structure 1000.0 850.0 0.0 0.5 Box2 /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1100.0 850.0 0.0 0.5 Box2 /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1200.0 850.0 0.0 0.5 Box2 /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1300.0 850.0 0.0 0.5 Box2 /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1400.0 850.0 0.0 0.5 Box2 /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1500.0 850.0 0.0 0.5 Box2 /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1600.0 850.0 0.0 0.5 Box2 /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1700.0 850.0 0.0 0.5 Box2 /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1800.0 850.0 0.0 0.5 Box2 /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1900.0 850.0 0.0 0.5 Box2 /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1125.0 325.0 0.0 0.25 Box2Small /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1175.0 325.0 0.0 0.25 Box2Small /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1225.0 325.0 0.0 0.25 Box2Small /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1275.0 325.0 0.0 0.25 Box2Small /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1325.0 325.0 0.0 0.25 Box2Small /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1375.0 325.0 0.0 0.25 Box2Small /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1425.0 325.0 0.0 0.25 Box2Small /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1200.0 250.0 0.0 0.5 Box /assets/structures/box.png 2 false 3.0 3.0 5.0 0.0 -1\n" + 
			"Structure 1300.0 250.0 0.0 0.5 Box /assets/structures/box.png 2 false 3.0 3.0 5.0 0.0 0\n" + 
			"PlayerPlaneEntity 400.0 300.0 90.0 1.0 PlayerPlane /assets/plane1Right.png 0 false 5.0 5.0 5.0 5.0 0.05 1 1 200 208 203 205 21 45 46\n" + 
			"GameEntity 1400.0 600.0 -90.0 1.0 plane2 /assets/planes/plane2.png 1 false 1.0 1.0 0.5 0.5 0.1 2 true [ 1400.0 600.0 1000.0 600.0 ] true true -1\n" + 
			"GameEntity 1420.0 683.0 -90.0 1.0 plane2 /assets/planes/plane2.png 1 false 1.0 1.0 0.5 0.5 0.1 2 true [ 1420.0 683.0 1000.0 683.0 ] true true 2\n" + 
			"GameEntity 1420.0 517.0 -90.0 1.0 plane2 /assets/planes/plane2.png 1 false 1.0 1.0 0.5 0.5 0.1 2 true [ 1420.0 517.0 1000.0 517.0 ] true true -1\n" +
			"SpawnPlanesAction 1400.0 475.5 0.0015 [ GameEntity 1900.0 475.5 -90.0 1.0 plane2 /assets/planes/plane2.png 1 false 1.0 1.0 0.5 0.5 0.1 2 true [ 1900.0 "
			+ "475.5 1812.5571 600.0 1900.0 724.5 1987.4429 600.0 1900.0 475.5 ] true true -1 GameEntity 1900.0 475.5 -90.0 1.0 plane2 /assets/planes/plane2.png 1 "
			+ "false 1.0 1.0 0.5 0.5 0.1 2 true [ 1900.0 475.5 1812.5571 600.0 1900.0 724.5 1987.4429 600.0 1900.0 475.5 ] true true -1 GameEntity 1900.0 475.5 -90.0 "
			+ "1.0 plane2 /assets/planes/plane2.png 1 false 1.0 1.0 0.5 0.5 0.1 2 true [ 1900.0 475.5 1812.5571 600.0 1900.0 724.5 1987.4429 600.0 1900.0 475.5 ] true "
			+ "true -1  ]";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		extended = JngTestAdapter.getExtended1();
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		
	}

	@Before
	public void setUp() throws Exception {
		extended.initializeGame();
		extended.parseLevel(exampleWorld);
		extended.runGame(100);
		extended.startGame();
	}
	
	@After
	public void tearDown() throws Exception {
		extended.stopGame();
	}
	
	@Test
	public void collisionTestStructure() {
		extended.setPlayerPlanePosition(new Point2D.Float(1000.0f,
				850.0f));
		float shieldsBefore = extended.getPlayerPlaneShields();
		extended.runGame(1000);
		float shieldsCurrent = extended.getPlayerPlaneShields();
		assertThat(shieldsCurrent, lessThan(shieldsBefore));
	}
	
	@Test
	public void collisionTestEnemyPlane() {
		extended.setPlayerPlanePosition(new Point2D.Float(1400.0f,
				600.0f));
		float shieldsBefore = extended.getPlayerPlaneShields();
		float shieldsBeforeEnemyPlane =
				extended.getEnemyPlaneShields(0);
		extended.runGame(1000);
		float shieldsCurrent = extended.getPlayerPlaneShields();
		float shieldsCurrentEnemyPlane =
				extended.getEnemyPlaneShields(0);
		assertThat(shieldsCurrent, lessThan(shieldsBefore));
		assertThat(shieldsCurrentEnemyPlane, 
				lessThan(shieldsBeforeEnemyPlane));
	}
	
	@Test
	public void screenBordersTestLeftUpper() {
		// left upper
		Point2D.Float leftUpperBefore = 
				extended.getPlayerPlaneLeftUpperCorner();
		Point2D.Float positionBefore =
				extended.getPlayerPlanePosition();
		float offset = extended.getBorderOffset() + 5.0f;
		extended.setPlayerPlanePosition(new Point2D.Float(
				positionBefore.x - leftUpperBefore.x - offset,
				positionBefore.y - leftUpperBefore.y - offset));
		
		float shieldsBefore = extended.getPlayerPlaneShields();
		extended.runGame(1000);
		float shieldsCurrent = extended.getPlayerPlaneShields();
		assertThat(shieldsCurrent, lessThan(shieldsBefore));
	}
	
	@Test
	public void screenBorderTestRight() {
		// right
		Point2D.Float bottomRightBefore = 
				extended.getPlayerPlaneLeftUpperCorner();
		Point2D.Float positionBefore =
				extended.getPlayerPlanePosition();
		float offset = extended.getBorderOffset() + 5.0f;
		extended.setPlayerPlanePosition(new Point2D.Float(
				extended.getLevelDisplaySize().x + positionBefore.x - bottomRightBefore.x + offset,
				positionBefore.y));
		
		float shieldsBefore = extended.getPlayerPlaneShields();
		extended.runGame(1000);
		float shieldsCurrent = extended.getPlayerPlaneShields();
		assertThat(shieldsCurrent, lessThan(shieldsBefore));
	}
	
	@Test
	public void weaponUpgradesTest() {
		int nFiredProjectileBefore = extended.getNFiredProjectilesByWeaponOfRank(1);
		float weaponStrengthBefore = extended.getWeaponStrengthOfRank(1);
		float fireRateBefore = extended.getFireRate(1);
		
		int nFiredProjectileCurrent = extended.getNFiredProjectilesByWeaponOfRank(2);
		float weaponStrengthCurrent = extended.getWeaponStrengthOfRank(2);
		float fireRateCurrent = extended.getFireRate(2);
		
		boolean atLeastOnCriteriaIsBetter = false;
		if (nFiredProjectileCurrent > nFiredProjectileBefore)
			atLeastOnCriteriaIsBetter = true;
		if (weaponStrengthCurrent > weaponStrengthBefore)
			atLeastOnCriteriaIsBetter = true;
		if (fireRateCurrent > fireRateBefore)
			atLeastOnCriteriaIsBetter = true;
		
		assertTrue("the weapon of rank " + 2 + " is in no single criterion better "
				+ "than the weapon of rank " + 1, atLeastOnCriteriaIsBetter);
	}
	
	@Test
	public void lifesTest() {
		assertEquals("lifes", 1, extended.getPlayerPlaneLifes());
		extended.damagePlayerPlane(10.0f);
		assertEquals("lifes", 0, extended.getPlayerPlaneLifes());
	}
	
	@Test
	public void spawnPlanesActionTest() {
		assertEquals(3, extended.getNEnemyPlanes());
		int timeUntilNextPlane = 
				(int)(1/extended.getSpawnPlanesActionFrequency(0)) + 5;
		extended.runGame(timeUntilNextPlane);
		extended.runGame(0);
		assertEquals(4, extended.getNEnemyPlanes());
	}
}
