package jng.tests.tutor.testcases;

import static org.junit.Assert.assertEquals;

import java.awt.geom.Point2D;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import jng.tests.adapter.JngTestAdapter;
import jng.tests.adapter.interfaces.IJngTestAdapterExtended2;

public class ParseLevelTests3Tutors {
	
	private static IJngTestAdapterExtended2 extended;
	
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
			"Item 1400.0 650.0 0\n" +
			"Item 1650.0 800.0 3\n" +
			"Item 1250.0 700.0 2\n" +
			"SpawnPlanesAction 1900.0 505.5 0.0015 [ GameEntity 1900.0 505.5 -90.0 1.0 plane5 /assets/planes/plane5.png 1 false 1.0 1.0 0.5 0.5 0.1 5 true [ "
			+ "1900.0 505.5 1811.0427 600.0 1900.0 694.5 1988.9573 600.0 1900.0 505.5 ] true true -1 GameEntity 1900.0 505.5 -90.0 1.0 plane5 /assets/planes/"
			+ "plane5.png 1 false 1.0 1.0 0.5 0.5 0.1 5 true [ 1900.0 505.5 1811.0427 600.0 1900.0 694.5 1988.9573 600.0 1900.0 505.5 ] true true -1 GameEntity "
			+ "1900.0 505.5 -90.0 1.0 plane5 /assets/planes/plane5.png 1 false 1.0 1.0 0.5 0.5 0.1 5 true [ 1900.0 505.5 1811.0427 600.0 1900.0 694.5 1988.9573 "
			+ "600.0 1900.0 505.5 ] true true -1  ]";

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
		assertEquals("EnemyPlaneItem", -1, extended.getEnemyPlaneItem(1));
		assertEquals("EnemyPlaneItem", 3, extended.getEnemyPlaneItem(2));
	}
	
	@Test
	public void parseItemsTest() {
		assertEquals("item count", 3, extended.getNItems());
		
		assertEquals("item position", new Point2D.Float(1400.0f, 650.0f),
				extended.getItemPosition(0));
		assertEquals("item position", new Point2D.Float(1650.0f, 800.0f),
				extended.getItemPosition(1));
		assertEquals("item position", new Point2D.Float(1250.0f, 700.0f),
				extended.getItemPosition(2));
		
		assertEquals("item type", 0, extended.getItemType(0));
		assertEquals("item type", 3, extended.getItemType(1));
		assertEquals("item type", 2, extended.getItemType(2));
	}
	
	@Test
	public void parseNewMapElementsTest()
	{
		parseItemsInPlanesTest();
		parseItemsTest();
	}
}
