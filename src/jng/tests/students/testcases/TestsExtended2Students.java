package jng.tests.students.testcases;

import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import jng.tests.adapter.JngTestAdapter;
import jng.tests.adapter.interfaces.IJngTestAdapterExtended2;

public class TestsExtended2Students {
	
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
	public void differentEnemyStrengthsTest() {
		float healthBefore = extended.getHealthOfEnemyPlaneOfRank(1);
		float shieldsBefore = extended.getShieldsOfEnemyPlaneOfRank(1);
		float speedBefore = extended.getSpeedOfEnemyPlaneOfRank(1);

		float healthCurrent = extended.getHealthOfEnemyPlaneOfRank(2);
		float shieldsCurrent = extended.getShieldsOfEnemyPlaneOfRank(2);
		float speedCurrent = extended.getSpeedOfEnemyPlaneOfRank(2);
		
		boolean atLeastOnCriteriaIsBetter = false;
		if (healthCurrent > healthBefore)
			atLeastOnCriteriaIsBetter = true;
		if (shieldsCurrent > shieldsBefore)
			atLeastOnCriteriaIsBetter = true;
		if (speedCurrent > speedBefore)
			atLeastOnCriteriaIsBetter = true;
		
		assertTrue("the plane of rank " + 2 + " is in no single criterion better "
				+ "than the plane of rank " + 1, atLeastOnCriteriaIsBetter);
	}
	
	@Test
	public void itemsTest()
	{
		// health
		extended.damagePlayerPlane(6.0f);
		float healthBefore = extended.getPlayerPlaneHealth();
		extended.playerPlaneGathersItemOfType(0);
		float healthCurrent = extended.getPlayerPlaneHealth();
		assertThat(healthBefore, lessThanOrEqualTo(healthCurrent));
		
		// shields
		float shieldsBefore = extended.getPlayerPlaneShields();
		extended.playerPlaneGathersItemOfType(1);
		float shieldsCurrent = extended.getPlayerPlaneShields();
		assertThat(shieldsBefore, lessThanOrEqualTo(shieldsCurrent));
	}
	
	@Test
	public void rocketsTest()
	{
		int timeBeforeRocketCanBeFired =
				(int)(1.0f / extended.getRocketFireFrequency()) + 20;
		extended.runGame(timeBeforeRocketCanBeFired);
		int rocketCountBefore = extended.getNRockets();
		extended.handleKeyPressedX();
		extended.runGame(0);
		int rocketCountCurrent = extended.getNRockets();
		assertEquals("rocketCount", 
				rocketCountBefore+1, rocketCountCurrent);
	}
}
