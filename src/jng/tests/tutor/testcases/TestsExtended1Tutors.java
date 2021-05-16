package jng.tests.tutor.testcases;

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

public class TestsExtended1Tutors {
	
	private static IJngTestAdapterExtended1 extended;
	
	private static String exampleWorld = 
			"Level ExampleWorld 2000.0 /assets/background.png 0.05 1440 900\n" + 
			"Structure 1050.0 850.0 0.0 0.5 Box2 /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1150.0 850.0 0.0 0.5 Box2 /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1250.0 850.0 0.0 0.5 Box2 /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1350.0 850.0 0.0 0.5 Box2 /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1450.0 850.0 0.0 0.5 Box2 /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1550.0 850.0 0.0 0.5 Box2 /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1650.0 850.0 0.0 0.5 Box2 /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1750.0 850.0 0.0 0.5 Box2 /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1850.0 850.0 0.0 0.5 Box2 /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1950.0 850.0 0.0 0.5 Box2 /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1135.0 325.0 0.0 0.25 Box2Small /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1185.0 325.0 0.0 0.25 Box2Small /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1235.0 325.0 0.0 0.25 Box2Small /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1285.0 325.0 0.0 0.25 Box2Small /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1335.0 325.0 0.0 0.25 Box2Small /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1385.0 325.0 0.0 0.25 Box2Small /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1435.0 325.0 0.0 0.25 Box2Small /assets/structures/box2.png 2 true 5.0 5.0 5.0 0.0 -1\n" + 
			"Structure 1250.0 250.0 0.0 0.5 Box /assets/structures/box.png 2 false 3.0 3.0 5.0 0.0 -1\n" + 
			"Structure 1350.0 250.0 0.0 0.5 Box /assets/structures/box.png 2 false 3.0 3.0 5.0 0.0 0\n" + 
			"PlayerPlaneEntity 400.0 300.0 90.0 1.0 PlayerPlane /assets/plane1Right.png 0 false 5.0 5.0 5.0 5.0 0.05 1 1 200 208 203 205 21 45 46\n" + 
			"GameEntity 1400.0 600.0 -90.0 1.0 plane3 /assets/planes/plane3.png 1 false 1.0 1.0 0.5 0.5 0.1 3 true [ 1400.0 600.0 1000.0 350.0 ] true true -1\n" + 
			"GameEntity 1420.0 673.0 -90.0 1.0 plane3 /assets/planes/plane3.png 1 false 1.0 1.0 0.5 0.5 0.1 3 true [ 1420.0 673.0 1000.0 673.0 ] true true -1\n" + 
			"GameEntity 1420.0 527.0 -90.0 1.0 plane3 /assets/planes/plane3.png 1 false 1.0 1.0 0.5 0.5 0.1 3 true [ 1420.0 527.0 1000.0 527.0 ] true true 3\n" + 
			"SpawnPlanesAction 1400.0 505.5 0.0015 [ GameEntity 1900.0 505.5 -90.0 1.0 plane5 /assets/planes/plane5.png 1 false 1.0 1.0 0.5 0.5 0.1 5 true [ "
			+ "1900.0 505.5 1811.0427 600.0 1900.0 694.5 1988.9573 600.0 1900.0 505.5 ] true true -1 GameEntity 1900.0 505.5 -90.0 1.0 plane5 /assets/planes/"
			+ "plane5.png 1 false 1.0 1.0 0.5 0.5 0.1 5 true [ 1900.0 505.5 1811.0427 600.0 1900.0 694.5 1988.9573 600.0 1900.0 505.5 ] true true -1 GameEntity "
			+ "1900.0 505.5 -90.0 1.0 plane5 /assets/planes/plane5.png 1 false 1.0 1.0 0.5 0.5 0.1 5 true [ 1900.0 505.5 1811.0427 600.0 1900.0 694.5 1988.9573 "
			+ "600.0 1900.0 505.5 ] true true -1  ]";

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
		extended.setPlayerPlanePosition(new Point2D.Float(11135.0f,
				325.0f));
		float shieldsBefore = extended.getPlayerPlaneShields();
		extended.runGame(1000);
		float shieldsCurrent = extended.getPlayerPlaneShields();
		assertThat(shieldsCurrent, lessThan(shieldsBefore));
	}
	
	@Test
	public void collisionTestEnemyPlane() {
		extended.setPlayerPlanePosition(new Point2D.Float(1420.0f,
				527.0f));
		float shieldsBefore = extended.getPlayerPlaneShields();
		float shieldsBeforeEnemyPlane =
				extended.getEnemyPlaneShields(2);
		extended.runGame(1000);
		float shieldsCurrent = extended.getPlayerPlaneShields();
		float shieldsCurrentEnemyPlane =
				extended.getEnemyPlaneShields(2);
		assertThat(shieldsCurrent, lessThan(shieldsBefore));
		assertThat(shieldsCurrentEnemyPlane, 
				lessThan(shieldsBeforeEnemyPlane));
	}
	
	@Test
	public void screenBordersTestLeft() {
		// left upper
		Point2D.Float leftUpperBefore = 
				extended.getPlayerPlaneLeftUpperCorner();
		Point2D.Float positionBefore =
				extended.getPlayerPlanePosition();
		float offset = extended.getBorderOffset() + 5.0f;
		extended.setPlayerPlanePosition(new Point2D.Float(
				positionBefore.x - leftUpperBefore.x - offset,
				positionBefore.y));
		
		float shieldsBefore = extended.getPlayerPlaneShields();
		extended.runGame(1000);
		float shieldsCurrent = extended.getPlayerPlaneShields();
		assertThat(shieldsCurrent, lessThan(shieldsBefore));
	}
	
	@Test
	public void screenBorderTestRightUpper() {
		// right
		Point2D.Float bottomRightBefore = 
				extended.getPlayerPlaneLeftUpperCorner();
		Point2D.Float leftUpperBefore = 
				extended.getPlayerPlaneLeftUpperCorner();
		Point2D.Float positionBefore =
				extended.getPlayerPlanePosition();
		float offset = extended.getBorderOffset() + 5.0f;
		extended.setPlayerPlanePosition(new Point2D.Float(
				extended.getLevelDisplaySize().x + bottomRightBefore.x + offset,
				positionBefore.y - leftUpperBefore.y - offset));
		
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
		
		
		for (int i = 2; i < 5; ++i) {
			int nFiredProjectileCurrent = extended.getNFiredProjectilesByWeaponOfRank(i);
			float weaponStrengthCurrent = extended.getWeaponStrengthOfRank(i);
			float fireRateCurrent = extended.getFireRate(i);
			
			boolean atLeastOnCriteriaIsBetter = false;
			if (nFiredProjectileCurrent > nFiredProjectileBefore)
				atLeastOnCriteriaIsBetter = true;
			if (weaponStrengthCurrent > weaponStrengthBefore)
				atLeastOnCriteriaIsBetter = true;
			if (fireRateCurrent > fireRateBefore)
				atLeastOnCriteriaIsBetter = true;
			
			assertTrue("the weapon of rank " + i + " is in no single criterion better "
					+ "than the weapon of rank " + (i-1), atLeastOnCriteriaIsBetter);
			
			nFiredProjectileBefore = nFiredProjectileCurrent;
			weaponStrengthBefore = weaponStrengthCurrent;
			fireRateBefore = fireRateCurrent;
		}
	}
	
	@Test
	public void lifesTest() {
		assertEquals("lifes", 1, extended.getPlayerPlaneLifes());
		extended.damagePlayerPlane(10.0f);
		assertEquals("lifes", 0, extended.getPlayerPlaneLifes());
		assertEquals("shields", 5.0f, extended.getPlayerPlaneShields(), 0.01);
		assertEquals("health", 5.0f, extended.getPlayerPlaneHealth(), 0.01);
	}
	
	@Test
	public void spawnPlanesActionTest() {
		assertEquals(3, extended.getNEnemyPlanes());
		int timeUntilNextPlane = 
				(int)(1/extended.getSpawnPlanesActionFrequency(0)) + 5;
		extended.runGame(timeUntilNextPlane);
		assertEquals(4, extended.getNEnemyPlanes());
		extended.runGame(timeUntilNextPlane);
		assertEquals(5, extended.getNEnemyPlanes());
		extended.runGame(timeUntilNextPlane);
		assertEquals(6, extended.getNEnemyPlanes());
	}
}
