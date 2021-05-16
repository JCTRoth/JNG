package jng.tests.adapter.interfaces;

import java.awt.geom.Point2D;

public interface IJngTestAdapterExtended1 extends IJngTestAdapterMinimal{

	/**
	 * Task: Leben
	 * @return the lives of the player plane
	 */
	int getPlayerPlaneLifes();

	/**
	 * It's up to you how you specify behind what weapon type
	 * what weapon is. This method is only used in parsing tests.
	 * @param index
	 * @return the weapon type of the enemy plane at index
	 */
	int getEnemyPlaneWeaponType(int index);

	/**
	 * task: Bildschirmbegrenzung
	 * returns the offset before the player plane gets damaged
	 * when flying out of the screen area.
	 * @return the offset
	 */
	float getBorderOffset();

	/**
	 * task: Bildschirmbegrenzung
	 * returns the left upper corner of the player plane. That is
	 * the usual position minus half of its windexth and height.
	 * @return left upper corner of the player plane
	 */
	Point2D.Float getPlayerPlaneLeftUpperCorner();

	/**
	 * task: Bildschirmbegrenzung
	 * returns the bottom right corner of the player plane. That is
	 * the usual position plus half of its windexth and height.
	 * @return bottom right corner of the player plane
	 */
	Point2D.Float getPlayerPlaneBottomRightCorner();

	/**
	 * task: Waffenupgrades
	 * Each weapon the player plane can shoot has a rank.
	 * A weapon of a higher rank is better in at least(!) one of 
	 * the following
	 * criteria:
	 *  - number of fired projectiles
	 *  - strength
	 *  - fire rate
	 * 
	 * @param rank value between 1 and 4
	 * @return number of fires projectiles
	 */
	int getNFiredProjectilesByWeaponOfRank(int rank);

	/**
	 * task: Waffenupgrades
	 * see above {@link #getNFiredProjectilesByWeaponOfRank(int)}
	 * @param rank value between 1 and 4
	 * @return
	 */
	float getWeaponStrengthOfRank(int rank);

	/**
	 * task: Waffenupgrades
	 * see above {@link #getNFiredProjectilesByWeaponOfRank(int)}
	 * @param rank value between 1 and 4
	 * @return the firerate of a player plane weapon of rank
	 */
	float getFireRate(int rank);
	
	// ***************
	// Structures
	// ***************

	/**
	 * task: Strukturen
	 * @return number of structure in the currently loaded level
	 */
	int getNStructure();

	/**
	 * task: Strukturen
	 * The index starts with 0 at the first structure and ends with
	 * {@link #getNStructure()}-1 at the last one. 
	 * It is incrementally increasing with each structure element.
	 * @param index of the structure in the level
	 * @return the position of the index's structure
	 */
	Point2D.Float getStructurePosition(int index);

	/**
	 * task: Strukturen
	 * @param index
	 * @return the rotation of the structure at index
	 */
	float getStructureRotation(int index);

	/**
	 * task: Strukturen
	 * @param index
	 * @return the scale of the structure at index
	 */
	float getStructureScale(int index);

	/**
	 * task: Strukturen
	 * @param index
	 * @return the ID of the structure at index
	 */
	String getStructureID(int index);

	/**
	 * task: Strukturen
	 * @param index
	 * @return the orientation of the structure at index
	 */
	int getStructureOrientation(int index);

	/**
	 * task: Strukturen
	 * @param index
	 * @return if  of the structure at index is invincible
	 */
	boolean getStructureIsInvincible(int index);

	/**
	 * task: Strukturen
	 * @param index
	 * @return the current health of the structure at index
	 */
	float getStructureHealth(int index);

	/**
	 * task: Strukturen
	 * @param index
	 * @return the maximum health of the structure at index
	 */
	float getStructureMaxHealth(int index);

	/**
	 * task: Strukturen
	 * @param index
	 * @return the current shields of the structure at index
	 */
	float getStructureShields(int index);

	/**
	 * task: Strukturen
	 * @param index
	 * @return the maximum shields of the structure at index
	 */
	float getStructureMaxShields(int index);

	/**
	 * tasks: Strukturen, Items
	 * @param index
	 * @return the item of the structure at index
	 */
	float getStructureItem(int index);
	
	// ***************
	// SpawnPlanesActions
	// ***************

	/**
	 * task: Erzeugen von Formationen
	 * @return the number of spawn planes actions
	 */
	int getNSpawnPlanesActions();

	/**
	 * task: Erzeugen von Formationen
	 * @param index
	 * @return the position of the spawn planes action at index
	 */
	Point2D.Float getSpawnPlanesActionPosition(int index);

	/**
	 * task: Erzeugen von Formationen
	 * @param index
	 * @return the frequency of the spawn planes action at index
	 */
	float getSpawnPlanesActionFrequency(int index);

	/**
	 * task: Erzeugen von Formationen
	 * @param index
	 * @return the number of planes that the spawn planes action at index spawns
	 */
	int getSpawnPlanesActionNPlanes(int index);

}