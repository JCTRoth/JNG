package jng.tests.tutor.testcases;

import static org.junit.Assert.assertEquals;

import java.awt.geom.Point2D;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import jng.tests.adapter.JngTestAdapter;
import jng.tests.adapter.interfaces.IJngTestAdapterExtended3;

public class TestsExtended3Tutors {
	
	private static IJngTestAdapterExtended3 extended;
	
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
			"GameEntity 1400.0 760.0 0.0 1.0 RocketTowerWeak /assets/towerWeak.png 1 false 5.0 5.0 5.0 0.0 0.0 13 true [ ] false false -1\n" + 
			"Item 1400.0 650.0 0\n" +
			"Item 1650.0 800.0 3\n" +
			"SpawnPlanesAction 1900.0 505.5 0.0015 [ GameEntity 1900.0 505.5 -90.0 1.0 plane5 /assets/planes/plane5.png 1 false 1.0 1.0 0.5 0.5 0.1 5 true [ "
			+ "1900.0 505.5 1811.0427 600.0 1900.0 694.5 1988.9573 600.0 1900.0 505.5 ] true true -1 GameEntity 1900.0 505.5 -90.0 1.0 plane5 /assets/planes/"
			+ "plane5.png 1 false 1.0 1.0 0.5 0.5 0.1 5 true [ 1900.0 505.5 1811.0427 600.0 1900.0 694.5 1988.9573 600.0 1900.0 505.5 ] true true -1 GameEntity "
			+ "1900.0 505.5 -90.0 1.0 plane5 /assets/planes/plane5.png 1 false 1.0 1.0 0.5 0.5 0.1 5 true [ 1900.0 505.5 1811.0427 600.0 1900.0 694.5 1988.9573 "
			+ "600.0 1900.0 505.5 ] true true -1  ]";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		extended = JngTestAdapter.getExtended3();
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
	public void parseTowersTest() {
		double accuracy = 0.01;
		assertEquals(1, extended.getNTowers());
		assertEquals("TowerPositionX", 1400.0f, extended.getTowerPosition(0).x, accuracy);
		assertEquals("TowerPositionY", 760.0f, extended.getTowerPosition(0).y, accuracy);
		assertEquals("TowerID", "RocketTowerWeak", extended.getTowerEntityID(0));
		assertEquals("TowerWeaponType", 13, extended.getTowerWeaponType(0));

		assertEquals("TowerRotation", 0.0f, extended.getTowerRotation(0), accuracy);
		assertEquals("TowerScale", 1.0f, extended.getTowerScale(0), accuracy);
		assertEquals("TowerOrientation", 1, extended.getTowerOrientation(0));
		assertEquals("TowerIsInvicible", false, extended.getTowerIsInvicible(0));
		assertEquals("TowerSpeed", 0.0f, extended.getTowerSpeed(0), accuracy);
		assertEquals("TowerHealth", 5.0f, extended.getTowerHealth(0), accuracy);
		assertEquals("TowerMaxHealth", 5.0f, extended.getTowerMaxHealth(0), accuracy);
		assertEquals("TowerShields", 0.0f, extended.getTowerShields(0), accuracy);
		assertEquals("TowerMaxShields", 5.0f, extended.getTowerMaxShields(0), accuracy);
		assertEquals("TowerFireMode", true,extended.getTowerFireModeOn(0));
	}
	
	@Test
	public void towersProjectilesTest() {
		//1st try
		int shootingTime = (int)(1/extended.getTowerWeaponFrequency(0)) + 60;
		extended.runGame(shootingTime);
		
		Point2D.Float position = extended.getProjectilePositionsOfTower(0).get(0);
		float rotation = extended.getProjectileRotationOfTower(0).get(0);
		
		Point2D.Float ppp = extended.getPlayerPlanePosition();
		Point2D.Float expectedRotationVector = new Point2D.Float(
				ppp.x - position.x,
				ppp.y - position.y);
		
		float length = (float)Math.sqrt(expectedRotationVector.x*expectedRotationVector.x+
				expectedRotationVector.y*expectedRotationVector.y);
		
		float angle = 180+(float)(180.0 / Math.PI * Math.acos(expectedRotationVector.y/length));
		if (expectedRotationVector.x > 0)
			angle *= -1;
		
		assertEquals("projectile isn't rotated to the player plane", angle, rotation, 5.0);
		
		//2nd try
		shootingTime = (int)(1/extended.getTowerWeaponFrequency(0)) + 60;
		extended.runGame(shootingTime);
		
		position = extended.getProjectilePositionsOfTower(0).get(1);
		rotation = extended.getProjectileRotationOfTower(0).get(1);
		
		ppp = extended.getPlayerPlanePosition();
		expectedRotationVector = new Point2D.Float(
				ppp.x - position.x,
				ppp.y - position.y);
		
		length = (float)Math.sqrt(expectedRotationVector.x*expectedRotationVector.x+
				expectedRotationVector.y*expectedRotationVector.y);
		
		angle = 180+(float)(180.0 / Math.PI * Math.acos(expectedRotationVector.y/length));
		if (expectedRotationVector.x > 0)
			angle *= -1;
		assertEquals("projectile isn't rotated to the player plane", angle, rotation, 5.0);
	}
	
	@Test
	public void bombsTest() {
		int timeBeforeBombCanBeFired =
				(int)(1.0f / extended.getBombFireFrequency()) + 30;
		
		for (int i = 0; i < 2; ++i) {
			extended.runGame(timeBeforeBombCanBeFired);
			int bombCountBefore = extended.getNBombs();
			extended.handleKeyPressedC();
			extended.runGame(0);
			int bombCountCurrent = extended.getNBombs();
			assertEquals("bombCount", 
					bombCountBefore+1, bombCountCurrent);
			
		}
	}
}
