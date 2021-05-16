package jng.tests.adapter.interfaces;

import java.awt.geom.Point2D;

public interface IJngTestAdapterExtended2 extends IJngTestAdapterMinimal{

	/**
	 * task: Verschiedene Gegnerstärken
	 * @param rank
	 * @return the health of an enemy plane of the given rank
	 */
	float getHealthOfEnemyPlaneOfRank(int rank);

	/**
	 * task: Verschiedene Gegnerstärken
	 * @param rank
	 * @return the shields of an enemy plane of the given rank
	 */
	float getShieldsOfEnemyPlaneOfRank(int rank);

	/**
	 * task: Verschiedene Gegnerstärken
	 * @param rank
	 * @return the speed of an enemy plane of the given rank
	 */
	float getSpeedOfEnemyPlaneOfRank(int rank);
	
	// **********
	// Items
	// **********

	/**
	 * task: Items
	 * @param index
	 * @return the item type the plane has
	 */
	int getEnemyPlaneItem(int index);
	
	/**
	 * task: Items
	 * @return the number of Item objects in the level (not counting
	 * 		items in planes or structures).
	 */
	int getNItems();

	/**
	 * task: Items
	 * @param index of the item in the given level
	 * @return the item position
	 */
	Point2D.Float getItemPosition(int index);

	/**
	 * task: Items
	 * returns the item type:
	 * 0 - health
	 * 1 - shields
	 * 2 - weapon upgrade
	 * 3 - lifes
	 * @param index of the item in the given level
	 * @return item type (as specified above)
	 */
	int getItemType(int index);

	/**
	 * task: Items
	 * This methods effects the player plane in a way it would
	 * if the player plane gathers an item of the specified type.
	 * The effects of weapon upgrade or lifes have to implemented
	 * if "Waffenupgrades" or "Leben" are implemented but they are
	 * not tested here. Therefore, you can assume that type does
	 * only have the value 0 or 1.
	 * @param type itemType
	 * 		0 - health
	 * 		1 - shields
	 */
	void playerPlaneGathersItemOfType(int type);

	/**
	 * task: Raketen
	 * @return the number of rockets currently in the level.
	 */
	int getNRockets();

	/**
	 * task: Raketen
	 * returns the frequency in 1/ms with which rockets are fired.
	 * @return float
	 */
	float getRocketFireFrequency();

	/**
	 * task: Raketen
	 * handles what happens when the x key is pressed. In this
	 * case the player plane shoots a rocket. Therefore, the number
	 * of rockets in the level increases by 1.
	 */
	void handleKeyPressedX();

}