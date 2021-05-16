package jng.tests.students.testcases;

import static org.junit.Assert.assertEquals;

import java.awt.geom.Point2D;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import jng.tests.adapter.JngTestAdapter;
import jng.tests.adapter.interfaces.IJngTestAdapterExtended1;

public class ParseLevelTests2Students {
	
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
			"SpawnPlanesAction 1900.0 475.5 0.0015 [ GameEntity 1900.0 475.5 -90.0 1.0 plane2 /assets/planes/plane2.png 1 false 1.0 1.0 0.5 0.5 0.1 2 true [ 1900.0 "
			+ "475.5 1812.5571 600.0 1900.0 724.5 1987.4429 600.0 1900.0 475.5 ] true true -1 GameEntity 1900.0 475.5 -90.0 1.0 plane2 /assets/planes/plane2.png 1 "
			+ "false 1.0 1.0 0.5 0.5 0.1 2 true [ 1900.0 475.5 1812.5571 600.0 1900.0 724.5 1987.4429 600.0 1900.0 475.5 ] true true -1 GameEntity 1900.0 475.5 -90.0 "
			+ "1.0 plane2 /assets/planes/plane2.png 1 false 1.0 1.0 0.5 0.5 0.1 2 true [ 1900.0 475.5 1812.5571 600.0 1900.0 724.5 1987.4429 600.0 1900.0 475.5 ] true "
			+ "true -1  ]";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		extended = JngTestAdapter.getExtended1();
		extended.initializeGame();
		extended.parseLevel(exampleWorld);
		extended.runGame(100);
		extended.startGame();
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		extended.stopGame();
	}

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void parseLifesTest() {
		// lifes
		assertEquals(1, extended.getPlayerPlaneLifes());
	}
	
	@Test
	public void parseStructuresTest() {
		// structures
		int index = 0;
		for (int i = 0; i < 10; ++i)
		{
			assertEquals(new Point2D.Float(1000.0f+i*100.0f, 850.0f),
					extended.getStructurePosition(index));
			assertEquals(0.5f, extended.getStructureScale(index), 0.01);
			assertEquals("Box2", extended.getStructureID(index));
			assertEquals(true, extended.getStructureIsInvincible(index));
			index++;
		}
		for (int i = 0; i < 7; ++i)
		{
			assertEquals(new Point2D.Float(1125.0f+i*50.0f, 325.0f),
					extended.getStructurePosition(index));
			assertEquals(0.25f, extended.getStructureScale(index), 0.01);
			assertEquals("Box2Small", extended.getStructureID(index));
			assertEquals(true, extended.getStructureIsInvincible(index));
			index++;
		}
		assertEquals(new Point2D.Float(1200.0f, 250.0f),
				extended.getStructurePosition(index));
		assertEquals(0.5f, extended.getStructureScale(index), 0.01);
		assertEquals("Box", extended.getStructureID(index));
		assertEquals(false, extended.getStructureIsInvincible(index));
		assertEquals(3.0f, extended.getStructureHealth(index), 0.01);
		assertEquals(3.0f, extended.getStructureMaxHealth(index), 0.01);
		assertEquals(0.0f, extended.getStructureShields(index), 0.01);
		assertEquals(5.0f, extended.getStructureMaxShields(index), 0.01);
		index++;
		assertEquals(new Point2D.Float(1300.0f, 250.0f),
				extended.getStructurePosition(index));
		assertEquals(0.5f, extended.getStructureScale(index), 0.01);
		assertEquals("Box", extended.getStructureID(index));
		assertEquals(false, extended.getStructureIsInvincible(index));
		assertEquals(3.0f, extended.getStructureHealth(index), 0.01);
		assertEquals(3.0f, extended.getStructureMaxHealth(index), 0.01);
		assertEquals(0.0f, extended.getStructureShields(index), 0.01);
		assertEquals(5.0f, extended.getStructureMaxShields(index), 0.01);
		
		for (int i = 0; i < 19; ++i)
		{
			assertEquals(0.0f, extended.getStructureRotation(i), 0.01);
			assertEquals(2, extended.getStructureOrientation(i));
		}
	}
	
	@Test
	public void parseSpawnPlanesActionsTest() {
		// SpawnPlanesActions
		assertEquals(new Point2D.Float(1900.0f, 475.5f),
				extended.getSpawnPlanesActionPosition(0));
		assertEquals(0.0015f, 
				extended.getSpawnPlanesActionFrequency(0), 0.0001);
		assertEquals(3, extended.getSpawnPlanesActionNPlanes(0));
	}
	
	@Test
	public void parsePlaneWeaponTypesTest() {
		for (int i = 0; i < 3; ++i)
			assertEquals("weaponType", 2, extended.getEnemyPlaneWeaponType(i));
	}

	@Test
	public void parseNewMapElementsTest()
	{
		parseLifesTest();
		parseStructuresTest();
		parseSpawnPlanesActionsTest();
		parsePlaneWeaponTypesTest();
	}
}
