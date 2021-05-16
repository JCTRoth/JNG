package jng.tests.students.testcases;

import static org.junit.Assert.assertEquals;

import java.awt.geom.Point2D;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import jng.tests.adapter.JngTestAdapter;
import jng.tests.adapter.interfaces.IJngTestAdapterExtended2;

public class ParseLevelTests3Students {
	
	private static IJngTestAdapterExtended2 extended;
	
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
			"Item 1300.0 750.0 1\n" +
			"Item 1500.0 750.0 2\n" +
			"SpawnPlanesAction 1900.0 475.5 0.0015 [ GameEntity 1900.0 475.5 -90.0 1.0 plane2 /assets/planes/plane2.png 1 false 1.0 1.0 0.5 0.5 0.1 2 true [ 1900.0 "
			+ "475.5 1812.5571 600.0 1900.0 724.5 1987.4429 600.0 1900.0 475.5 ] true true -1 GameEntity 1900.0 475.5 -90.0 1.0 plane2 /assets/planes/plane2.png 1 "
			+ "false 1.0 1.0 0.5 0.5 0.1 2 true [ 1900.0 475.5 1812.5571 600.0 1900.0 724.5 1987.4429 600.0 1900.0 475.5 ] true true -1 GameEntity 1900.0 475.5 -90.0 "
			+ "1.0 plane2 /assets/planes/plane2.png 1 false 1.0 1.0 0.5 0.5 0.1 2 true [ 1900.0 475.5 1812.5571 600.0 1900.0 724.5 1987.4429 600.0 1900.0 475.5 ] true "
			+ "true -1  ]";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		extended = JngTestAdapter.getExtended2();
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
	public void parseItemsInPlanesTest() {
		assertEquals("EnemyPlaneItem", -1, extended.getEnemyPlaneItem(0));
		assertEquals("EnemyPlaneItem", 2, extended.getEnemyPlaneItem(1));
		assertEquals("EnemyPlaneItem", -1, extended.getEnemyPlaneItem(2));
	}
	
	@Test
	public void parseItemsTest() {
		assertEquals("item count", 2, extended.getNItems());
		
		assertEquals("item position", new Point2D.Float(1300.0f, 750.0f),
				extended.getItemPosition(0));
		assertEquals("item position", new Point2D.Float(1500.0f, 750.0f),
				extended.getItemPosition(1));
		
		assertEquals("item type", 1, extended.getItemType(0));
		assertEquals("item type", 2, extended.getItemType(1));
	}
	
	@Test
	public void parseNewMapElementsTest()
	{
		parseItemsInPlanesTest();
		parseItemsTest();
	}
}
