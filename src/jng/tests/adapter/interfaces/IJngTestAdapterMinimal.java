package jng.tests.adapter.interfaces;

import java.awt.geom.Point2D;
import java.util.List;

public interface IJngTestAdapterMinimal {
	
	/* *************************************************** 
	 * ********* initialize, run, stop the game **********
	 * *************************************************** */

	/**
	 * Diese Methode initialisiert das Spiel im Debug-Modus, d.h. es wird
	 * ein AppGameContainer gestartet, der keine Fenster erzeugt und aktualisiert.
	 * 
	 * Sie müssen diese Methode erweitern
	 */
	void initializeGame();

	/**
	 * Start a new game. This method is called after
	 * {@link #parseLevel(String)} and starts a new game with the
	 * given map.
	 */
	void startGame();

	/**
	 * Stop the game.
	 */
	void stopGame();

	/**
	 * Run the game for a specified duration.
	 * Laesst das Spiel fuer eine angegebene Zeit laufen
	 * 
	 * @param ms duration of runtime of the game
	 */
	void runGame(int ms);

	/**
	 * task: Spiel pausieren
	 * Pauses the running game. No entity should
	 * move now.
	 */
	void pauseGame();

	/**
	 * task: Spiel pausieren
	 * Unpauses the game.
	 */
	void unpauseGame();

	/**
	 * tasks: Bildschirmbewegung, Spiel pausieren
	 * @return the x-coordinate of the current camera position.
	 */
	float getCameraPosition();

	/**
	 * task: Siegbedingung, Niederlagenbedingung
	 * @return if the game has finished
	 */
	boolean isGameFinished();

	/**
	 * task: Siegbedingung, Niederlagenbedingung
	 * @return true if the game is won else false
	 */
	boolean isGameWon();
	
	// ***************
	// LEVEL PARSING
	// ***************

	/**
	 * task: Parsen von Levels
	 */
	void parseLevel(String level);

	// **************
	// Level
	// **************
	
	/**
	 * task: Parsen von Levels
	 * @return the level name
	 */
	String getLevelName();

	/**
	 * task: Parsen von Levels
	 * returns the level width. The game ends when the camera
	 * reaches the width.
	 * @return level width
	 */
	int getLevelWidth();

	/**
	 * task: Parsen von Levels
	 * @return the speed the level camera moves with.
	 */
	float getLevelCameraSpeed();

	/**
	 * task: Parsen von Levels
	 * @return the level display size
	 */
	Point2D.Float getLevelDisplaySize();
	
	// ****************
	// Player plane
	// ****************

	/**
	 * task: Parsen von Levels
	 * @return the position (x, y) of the player plane
	 */
	Point2D.Float getPlayerPlanePosition();

	/**
	 * @return the rotation in degrees (0-360) of the player plane
	 */
	float getPlayerPlaneRotation();

	/**
	 * @return the scaling factory of the player plane
	 */
	float getPlayerPlaneScale();

	/**
	 * returns the orientation of the player plane
	 * 	0 - allied
	 * 	1 - enemy
	 *  2 - neutral
	 * @return the orientation of the player plane
	 */
	int getPlayerPlaneOrientation();

	/**
	 * @return true if the player plane is invicible else false
	 */
	boolean getPlayerPlaneIsInvicible();

	/**
	 * @return the player plane speed
	 */
	float getPlayerPlaneSpeed();

	/**
	 * @return the current player plane health
	 */
	float getPlayerPlaneHealth();

	/**
	 * @return the maximum player plane health
	 */
	float getPlayerPlaneMaxHealth();

	/**
	 * @return the current player plane shields
	 */
	float getPlayerPlaneShields();

	/**
	 * @return the maximum player plane shields
	 */
	float getPlayerPlaneMaxShields();

	// ****************
	// Enemy planes
	// ****************
	
	/**
	 * Enemy planes are all the GameEntity instances which's EntityID
	 * start with "plane".
	 * @return number of planes
	 */
	int getNEnemyPlanes();

	/**
	 * returns the position of the enemy plane at the given index. The index
	 * defines the i'th enemy plane given in the level string.
	 * @param index
	 * @return position of the enemy plane
	 */
	Point2D.Float getEnemyPlanePosition(int index);

	/**
	 * task: Parsen von Levels
	 * @param index
	 * @return the rotation of the enemy plane at the given index
	 */
	float getEnemyPlaneRotation(int index);

	/**
	 * task: Parsen von Levels
	 * @param index
	 * @return the scale of the enemy plane at the given index
	 */
	float getEnemyPlaneScale(int index);

	/**
	 * task: Parsen von Levels
	 * @param index
	 * @return the orientation of the enemy plane at the given index
	 */
	int getEnemyPlaneOrientation(int index);

	/**
	 * task: Parsen von Levels
	 * @param index
	 * @return if the enemy plane at the given index is invincible
	 */
	boolean getEnemyPlaneIsInvicible(int index);

	/**
	 * task: Parsen von Levels
	 * @param index
	 * @return the speed of the enemy plane at the given index
	 */
	float getEnemyPlaneSpeed(int index);

	/**
	 * task: Parsen von Levels
	 * @param index
	 * @return the ID of the enemy plane at the given index
	 */
	String getEnemyPlaneEntityID(int index);

	/**
	 * task: Parsen von Levels
	 * @param index
	 * @return the current health of the enemy plane at the given index
	 */
	float getEnemyPlaneHealth(int index);

	/**
	 * task: Parsen von Levels
	 * @param index
	 * @return the maximum health of the enemy plane at the given index
	 */
	float getEnemyPlaneMaxHealth(int index);

	/**
	 * task: Parsen von Levels
	 * @param index
	 * @return the current shields of the enemy plane at the given index
	 */
	float getEnemyPlaneShields(int index);

	/**
	 * task: Parsen von Levels
	 * @param index
	 * @return the maximum shields of the enemy plane at the given index
	 */
	float getEnemyPlaneMaxShields(int index);

	/**
	 * task: Parsen von Levels
	 * @param index
	 * @return if the enemy plane at the given index is shooting
	 */
	boolean getEnemyPlaneFireModeOn(int index);
	
	// ********************
	// Simple Enemy AI
	// ********************

	/**
	 * task: Parsen von Levels, Einfache Gegner KI
	 * @param index
	 * @return
	 */
	List<Point2D.Float> getEnemyPlaneMovementPath(int index);

	/**
	 * task: Einfache Gegner KI
	 * Note that you should guarantee that the enemy plane shoots its
	 * weapon every 1/frequency time units. Those are the same time units
	 * that are used in {@link #runGame(int)}.
	 * @param index
	 * @return the frequency the specified enemy plane shoots with
	 */
	float getEnemyPlanesShootingFrequency(int index);

	/**
	 * Sets the position of the player plane
	 * @param position
	 */
	void setPlayerPlanePosition(Point2D.Float position);

	/**
	 * task: Niederlagenbedingung
	 * sets the health of the player plane
	 * @param health
	 */
	void setPlayerPlaneHealth(float health);

	/**
	 * damages the player plane with a given damage
	 * @param damage
	 */
	void damagePlayerPlane(float damage);

	/**
	 * task: Einfache Gegner KI
	 * @param index of the enemy plane in the given level.
	 * @return the projectile positions of all projectiles of the specified
	 * enemy plane.
	 */
	List<Point2D.Float> getProjectilesPositionsOfEnemyPlane(
			int index);

	/**
	 * task: Waffenfeuer
	 * @param index
	 * @return the speed with which the projectiles are flying that are shot
	 * 		by the enemy plane at index
	 */
	float getProjectileSpeedOfEnemyPlane(int index);

	/**
	 * task: Waffenfeuer
	 * returns the number of projectils that the primary weapon from your
	 * plane shoots.
	 * @return
	 */
	int getNPrimaryProjectiles();
	
	// ******************************
	// Key input
	// ******************************

	/**
	 * task: Waffenfeuer
	 * Press the key to fire the primary weapon. For single player
	 * this is the y-key. If you like you can of course choose another
	 * one. At least your plane should fire your primary weapon.
	 */
	void handleKeyFirePrimary();

	/**
	 * task: Flugzeugsteuerung
	 * handles what happens if the up key is pressed. In this case
	 * the player plane should move up.
	 */
	void handleKeyPressedUp();

	/**
	 * task: Flugzeugsteuerung
	 * handles what happens if the up key is pressed. In this case
	 * the player plane should move down.
	 */
	void handleKeyPressedDown();

	/**
	 * task: Flugzeugsteuerung
	 * handles what happens if the up key is pressed. In this case
	 * the player plane should move left.
	 */
	void handleKeyPressedLeft();

	/**
	 * task: Flugzeugsteuerung
	 * handles what happens if the up key is pressed. In this case
	 * the player plane should move right.
	 */
	void handleKeyPressedRight();

}