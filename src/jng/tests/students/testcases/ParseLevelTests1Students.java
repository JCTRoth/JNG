package jng.tests.students.testcases;

import static org.junit.Assert.assertEquals;

import java.awt.geom.Point2D;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import jng.tests.adapter.JngTestAdapter;
import jng.tests.adapter.interfaces.IJngTestAdapterMinimal;

public class ParseLevelTests1Students {
	
	private static IJngTestAdapterMinimal minimal;
	
	private static String exampleLevel = 
			"Level ExampleWorld 2000.0 /assets/background.png 0.05 1440 900\n" + 
			"PlayerPlaneEntity 400.0 300.0 90.0 1.0 PlayerPlane /assets/plane1Right.png 0 false 5.0 5.0 5.0 5.0 0.05 1 1 200 208 203 205 21 45 46\n" + 
			"GameEntity 1400.0 600.0 -90.0 1.0 plane2 /assets/planes/plane2.png 1 false 1.0 1.0 0.5 0.5 0.1 2 true [ 1400.0 600.0 1000.0 600.0 ] true true -1\n" + 
			"GameEntity 1420.0 683.0 -90.0 1.0 plane2 /assets/planes/plane2.png 1 false 1.0 1.0 0.5 0.5 0.1 2 true [ 1420.0 683.0 1000.0 683.0 ] true true 2\n" + 
			"GameEntity 1420.0 517.0 -90.0 1.0 plane2 /assets/planes/plane2.png 1 false 1.0 1.0 0.5 0.5 0.1 2 true [ 1420.0 517.0 1000.0 517.0 ] true true -1\n";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		minimal = JngTestAdapter.getMinimal();
		minimal.initializeGame();
		minimal.parseLevel(exampleLevel);
		minimal.runGame(100);
		minimal.startGame();
	}

	@Before
	public void setUp() throws Exception {
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		minimal.stopGame();
	}

	@Test
	public void parseLevelDescriptionTest() {
		//Level
		assertEquals("ExampleWorld", minimal.getLevelName());
		assertEquals(2000.0f, minimal.getLevelWidth(), 0.01);
		assertEquals(0.05f, minimal.getLevelCameraSpeed(), 0.01);
		assertEquals(new Point2D.Float(1440, 900), minimal.getLevelDisplaySize());
	}
	
	@Test
	public void parsePlanesTest() {
		//GameEntity
		double accuracy = 0.01;
		assertEquals(3, minimal.getNEnemyPlanes());
		assertEquals(1400.0f, minimal.getEnemyPlanePosition(0).x, accuracy);
		assertEquals(600.0f, minimal.getEnemyPlanePosition(0).y, accuracy);
		assertEquals(1420.0f, minimal.getEnemyPlanePosition(1).x, accuracy);
		assertEquals(683.0f, minimal.getEnemyPlanePosition(1).y, accuracy);
		assertEquals(1420.0f, minimal.getEnemyPlanePosition(2).x, accuracy);
		assertEquals(517.0f, minimal.getEnemyPlanePosition(2).y, accuracy);
		
		List<Point2D.Float> path = minimal.getEnemyPlaneMovementPath(0);
		assertEquals("pathX", 1400.0f, path.get(0).x, accuracy);
		assertEquals("pathY", 600.0f, path.get(0).y, accuracy);
		assertEquals("pathX", 1000.0f, path.get(1).x, accuracy);
		assertEquals("pathY", 600.0f, path.get(1).y, accuracy);
		
		path = minimal.getEnemyPlaneMovementPath(1);
		assertEquals("pathX", 1420.0f, path.get(0).x, accuracy);
		assertEquals("pathY", 683.0f, path.get(0).y, accuracy);
		assertEquals("pathX", 1000.0f, path.get(1).x, accuracy);
		assertEquals("pathY", 683.0f, path.get(1).y, accuracy);
		
		path = minimal.getEnemyPlaneMovementPath(2);
		assertEquals("pathX", 1420.0f, path.get(0).x, accuracy);
		assertEquals("pathY", 517.0f, path.get(0).y, accuracy);
		assertEquals("pathX", 1000.0f, path.get(1).x, accuracy);
		assertEquals("pathY", 517.0f, path.get(1).y, accuracy);
		
		for (int i = 0; i < 3; ++i)
		{
			assertEquals("plane2", minimal.getEnemyPlaneEntityID(i));
			assertEquals(-90.0f, minimal.getEnemyPlaneRotation(i), accuracy);
			assertEquals(1.0f, minimal.getEnemyPlaneScale(i), accuracy);
			assertEquals(1, minimal.getEnemyPlaneOrientation(i));
			assertEquals(false, minimal.getEnemyPlaneIsInvicible(i));
			assertEquals(0.1f, minimal.getEnemyPlaneSpeed(i), accuracy);
			assertEquals(1.0f, minimal.getEnemyPlaneHealth(i), accuracy);
			assertEquals(1.0f, minimal.getEnemyPlaneMaxHealth(i), accuracy);
			assertEquals(0.5f, minimal.getEnemyPlaneShields(i), accuracy);
			assertEquals(0.5f, minimal.getEnemyPlaneMaxShields(i), accuracy);
			assertEquals(true, minimal.getEnemyPlaneFireModeOn(i));
		}
	}
	
	@Test 
	public void parsePlayerPlaneTest() {
		//PlayerPlaneEntity
		double accuracy = 0.01;
		assertEquals(400.0f, minimal.getPlayerPlanePosition().x, accuracy);
		assertEquals(300.0f, minimal.getPlayerPlanePosition().y, accuracy);
		assertEquals(90.0f, minimal.getPlayerPlaneRotation(), accuracy);
		assertEquals(1.0f, minimal.getPlayerPlaneScale(), accuracy);
		assertEquals(0, minimal.getPlayerPlaneOrientation());
		assertEquals(false, minimal.getPlayerPlaneIsInvicible());
		assertEquals(0.05f, minimal.getPlayerPlaneSpeed(), accuracy);
		assertEquals(5.0f, minimal.getPlayerPlaneHealth(), accuracy);
		assertEquals(5.0f, minimal.getPlayerPlaneMaxHealth(), accuracy);
		assertEquals(5.0f, minimal.getPlayerPlaneShields(), accuracy);
		assertEquals(5.0f, minimal.getPlayerPlaneMaxShields(), accuracy);
	}
	
	@Test
	public void parseLevelTest() {
		parseLevelDescriptionTest();
		parsePlanesTest();
		parsePlayerPlaneTest();
	}
}
