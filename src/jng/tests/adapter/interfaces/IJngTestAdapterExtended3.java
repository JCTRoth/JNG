package jng.tests.adapter.interfaces;

import java.awt.geom.Point2D;
import java.util.List;

public interface IJngTestAdapterExtended3 extends IJngTestAdapterMinimal{

	// ************
	// Towers
	// ************
	
	/**
	 * task: Gegnerischer Geschützturm
	 * returns all positions of projectiles fired by the tower at the
	 * given index. Towers are all objects of type GameEntity whome's
	 * indexs do not start with "plane".
	 * @param index
	 * @return positions of projectiles
	 */
	List<Point2D.Float> getProjectilePositionsOfTower(int index);

	/**
	 * task: Gegnerischer Geschützturm
	 * returns all rotations in degrees (0-360) of projectiles fired 
	 * by the tower at the given index. Towers are all objects of 
	 * type GameEntity whome's indexs do not start with "plane".
	 * @param index
	 * @return rotations of projectiles
	 */
	List<Float> getProjectileRotationOfTower(int index);

	/**
	 * returns the frequency with which weapon of the tower at the given index
	 * shoots its particles
	 * @param index
	 * @return
	 */
	float getTowerWeaponFrequency(int index);

	/**
	 * task: Gegnerischer Geschützturm
	 * Towers are all GameEntity instances which's entity indexs do
	 * NOT start with "plane"
	 * @return number of tower instances in the loaded level.
	 */
	int getNTowers();

	/**
	 * task: Gegnerischer Geschützturm
	 * @param index
	 * @return the position of the tower at index
	 */
	Point2D.Float getTowerPosition(int index);

	/**
	 * task: Gegnerischer Geschützturm
	 * @param index
	 * @return the rotation of the tower at index
	 */
	float getTowerRotation(int index);

	/**
	 * task: Gegnerischer Geschützturm
	 * @param index
	 * @return the scale of the tower at index
	 */
	float getTowerScale(int index);

	/**
	 * task: Gegnerischer Geschützturm
	 * @param index
	 * @return the orientation of the tower at index
	 */
	int getTowerOrientation(int index);

	/**
	 * task: Gegnerischer Geschützturm
	 * @param index
	 * @return if the tower at index is invincible
	 */
	boolean getTowerIsInvicible(int index);

	/**
	 * task: Gegnerischer Geschützturm
	 * @param index
	 * @return the speed of the tower at index
	 */
	float getTowerSpeed(int index);

	/**
	 * task: Gegnerischer Geschützturm
	 * @param index
	 * @return the ID of the tower at index
	 */
	String getTowerEntityID(int index);

	/**
	 * task: Gegnerischer Geschützturm
	 * @param index
	 * @return the current health of the tower at index
	 */
	float getTowerHealth(int index);

	/**
	 * task: Gegnerischer Geschützturm
	 * @param index
	 * @return the maximum health of the tower at index
	 */
	float getTowerMaxHealth(int index);

	/**
	 * task: Gegnerischer Geschützturm
	 * @param index
	 * @return the current shields  of the tower at index
	 */
	float getTowerShields(int index);

	/**
	 * task: Gegnerischer Geschützturm
	 * @param index
	 * @return the maximum shields  of the tower at index
	 */
	float getTowerMaxShields(int index);

	/**
	 * task: Gegnerischer Geschützturm
	 * @param index
	 * @return if the tower at index shoots
	 */
	boolean getTowerFireModeOn(int index);

	/**
	 * task: Gegnerischer Geschützturm
	 * @param index
	 * @return the weapon type of the tower at index
	 */
	int getTowerWeaponType(int index);

	// ***********
	// Bombs
	// ***********
	
	/**
	 * task: Bomben
	 * handles what happens when the c key is pressed. In this
	 * case the player plane drops a bomb underneath it. Then, 
	 * the number of bombs in the level increases by 1.
	 */
	void handleKeyPressedC();

	/**
	 * task: Bomben
	 * @return the frequency with which the player plane drops bombs.
	 */
	float getBombFireFrequency();

	/**
	 * task: Bomben
	 * @return the number of bombs currently in the level.
	 */
	int getNBombs();

}