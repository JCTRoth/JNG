package jng.entities;

import org.newdawn.slick.geom.Vector2f;

/**
 * This class provides parameters influencing the behaviour of entities in the game.
 * 
 * @author daniel
 */
public class Attributes {

	public static float standardCameraSpeed = 0.05f;
	
	public static Vector2f gravity = new Vector2f(0, 0.0004f);
	
	public static float itemDropProbability = 0.16f;
	
	public static float explosionFactor = 2.0f;
	
	public static float dampingFactor = 0.005f;
	public static float explosionAffectionRange = 300.0f;
	public static float minimumExplosionSpeed = 0.01f;
	
	// SpawnPlanes
	public static float spawnPlanesFrequency = 0.0015f;
	
	// Player Plane
	public static float playerPlaneHealth = 5.0f;
	public static float playerPlaneArmor = 5.0f;
	public static float playerPlaneSpeed = 0.4f;
	public static int playerPlaneLife = 1;
	
	// AI Plane
	public static float aiPlaneHealthWeak = 1.0f;
	public static float aiPlaneArmorWeak = 0.5f;
	public static float aiPlaneSpeedWeak = 0.025f;
	
	public static float aiPlaneHealthMedium = 3.0f;
	public static float aiPlaneArmorMedium = 1.0f;
	public static float aiPlaneSpeedMedium = 0.1f;
	
	public static float aiPlaneHealtStrong = 7.0f;
	public static float aiPlaneArmorStrong = 4.0f;
	public static float aiPlaneSpeedStrong = 0.2f;
	
	// Structures
	public static float boxHealth = 3.0f;
	public static float towerHealthWeak = 5.0f;
	public static float towerHealthStrong = 10.0f;
	public static float towerHealthStrongest = 15.0f;
	
	// Primary Weapon
	public static float playerPlanePrimaryWeaponFrequency = 0.006f;
	public static float aiPlanePrimaryWeaponFrequency = 0.004f;
	public static float aiWeaponFrequencySlow = 0.0005f;
	public static float aiWeaponFrequencyMedium = 0.0015f;
	public static float aiWeaponFrequencyFast = 0.003f;
	public static float standardProjectileDamageYellow = 0.2f;
	public static float standardProjectileDamageBlue = 0.3f;
	public static float standardProjectileDamageViolet = 0.4f;
	
	// Rockets
	public static float standardRocketDamage = 2.0f;
	public static float playerPlaneRocketFrequency = 0.0005f;
	
	// Bombs
	public static float standardBombDamage = 5.0f;
	public static float playerPlaneBombFrequency = 0.0005f;
	
	// Collision damage
	public static float planeCollisionDamage = 0.05f;
	public static float structureCollisionDamage = 0.05f;	
}