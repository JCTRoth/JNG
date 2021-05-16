package jng.tests.students.testcases;

import static org.junit.Assert.assertEquals;

import java.awt.geom.Point2D;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import jng.tests.adapter.JngTestAdapter;
import jng.tests.adapter.interfaces.IJngTestAdapterExtended3;

public class TestsExtended3Students {
	
	private static IJngTestAdapterExtended3 extended;
	
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
			"GameEntity 1400.0 760.0 0.0 1.0 RocketTeslaStationWeak /assets/rocketTeslaStationWeak.png 1 false 5.0 5.0 5.0 0.0 0.0 15 true [ ] false false -1\n" + 
			"GameEntity 1600.0 760.0 0.0 1.0 RocketTowerWeak /assets/towerWeak.png 1 false 5.0 5.0 5.0 0.0 0.0 13 true [ ] false false -1\n" +
			"Item 1300.0 750.0 1\n" +
			"Item 1500.0 750.0 2\n" +
			"SpawnPlanesAction 1900.0 475.5 0.0015 [ GameEntity 1900.0 475.5 -90.0 1.0 plane2 /assets/planes/plane2.png 1 false 1.0 1.0 0.5 0.5 0.1 2 true [ 1900.0 "
			+ "475.5 1812.5571 600.0 1900.0 724.5 1987.4429 600.0 1900.0 475.5 ] true true -1 GameEntity 1900.0 475.5 -90.0 1.0 plane2 /assets/planes/plane2.png 1 "
			+ "false 1.0 1.0 0.5 0.5 0.1 2 true [ 1900.0 475.5 1812.5571 600.0 1900.0 724.5 1987.4429 600.0 1900.0 475.5 ] true true -1 GameEntity 1900.0 475.5 -90.0 "
			+ "1.0 plane2 /assets/planes/plane2.png 1 false 1.0 1.0 0.5 0.5 0.1 2 true [ 1900.0 475.5 1812.5571 600.0 1900.0 724.5 1987.4429 600.0 1900.0 475.5 ] true "
			+ "true -1  ]";

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
		assertEquals(2, extended.getNTowers());
		assertEquals("TowerPositionX", 1400.0f, extended.getTowerPosition(0).x, accuracy);
		assertEquals("TowerPositionY", 760.0f, extended.getTowerPosition(0).y, accuracy);
		assertEquals("TowerPositionX", 1600.0f, extended.getTowerPosition(1).x, accuracy);
		assertEquals("TowerPositionY", 760.0f, extended.getTowerPosition(1).y, accuracy);
		assertEquals("TowerID", "RocketTeslaStationWeak", extended.getTowerEntityID(0));
		assertEquals("TowerID", "RocketTowerWeak",extended.getTowerEntityID(1));
		assertEquals("TowerWeaponType", 15, extended.getTowerWeaponType(0));
		assertEquals("TowerWeaponType", 13, extended.getTowerWeaponType(1));
		
		for (int i = 0; i < 2; ++i)
		{
			assertEquals("TowerRotation", 0.0f, extended.getTowerRotation(i), accuracy);
			assertEquals("TowerScale", 1.0f, extended.getTowerScale(i), accuracy);
			assertEquals("TowerOrientation", 1, extended.getTowerOrientation(i));
			assertEquals("TowerIsInvicible", false, extended.getTowerIsInvicible(i));
			assertEquals("TowerSpeed", 0.0f, extended.getTowerSpeed(i), accuracy);
			assertEquals("TowerHealth", 5.0f, extended.getTowerHealth(i), accuracy);
			assertEquals("TowerMaxHealth", 5.0f, extended.getTowerMaxHealth(i), accuracy);
			assertEquals("TowerShields", 0.0f, extended.getTowerShields(i), accuracy);
			assertEquals("TowerMaxShields", 5.0f, extended.getTowerMaxShields(i), accuracy);
			assertEquals("TowerFireMode", true,extended.getTowerFireModeOn(i));
		}
	}
	
	@Test
	public void towersProjectilesTest() {
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
	}
	
	@Test
	public void bombsTest() {
		int timeBeforeBombCanBeFired =
				(int)(1.0f / extended.getBombFireFrequency()) + 30;
		extended.runGame(timeBeforeBombCanBeFired);
		int bombCountBefore = extended.getNBombs();
		extended.handleKeyPressedC();
		extended.runGame(0);
		int bombCountCurrent = extended.getNBombs();
		assertEquals("bombCount", 
				bombCountBefore+1, bombCountCurrent);
	}

}
