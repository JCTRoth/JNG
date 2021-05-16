package jng.tests.adapter;

import java.awt.geom.Point2D;
import java.util.List;

import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import eea.engine.entity.Entity;
import jng.entities.Attributes;
import jng.entities.Item;
import jng.factories.ItemFactory;
import jng.factories.PlaneFactory;
import jng.tests.adapter.interfaces.IJngTestAdapterExtended2;
import jng.ui.Jng;
import jng.world.World;

public class JngTestAdapterExtended2 extends JngTestAdapterMinimal implements IJngTestAdapterExtended2 {

	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended2#getHealthOfEnemyPlaneOfRank(int)
	 */
	@Override
	public float getHealthOfEnemyPlaneOfRank(int rank) {
		try {
			rank += 7;
			return PlaneFactory.createPlaneOfType(-1, rank).getHealth();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return 0.0f;
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended2#getShieldsOfEnemyPlaneOfRank(int)
	 */
	@Override
	public float getShieldsOfEnemyPlaneOfRank(int rank) {
		try {
			rank += 7;
			return PlaneFactory.createPlaneOfType(-1, rank).getArmor();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return 0.0f;
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended2#getSpeedOfEnemyPlaneOfRank(int)
	 */
	@Override
	public float getSpeedOfEnemyPlaneOfRank(int rank) {
		try {
			rank += 7;
			return PlaneFactory.createPlaneOfType(-1, rank).getSpeed();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return 0.0f;
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended2#getEnemyPlaneItem(int)
	 */
	@Override
	public int getEnemyPlaneItem(int index)
	{
		if (getEnemyPlanes().get(index).getItem() == null)
			return -1;
		else
			return getEnemyPlanes().get(index).getItem().getType().ordinal();
	}
	
	private List<Item> getItems() {
		return World.getInstance().getItems();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended2#getNItems()
	 */
	@Override
	public int getNItems() {
		return getItems().size();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended2#getItemPosition(int)
	 */
	@Override
	public Point2D.Float getItemPosition(int index) {
		Item i = getItems().get(index);
		return new Point2D.Float(i.getPosition().x, i.getPosition().y);
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended2#getItemType(int)
	 */
	@Override
	public int getItemType(int index) {
		return getItems().get(index).getType().ordinal();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended2#playerPlaneGathersItemOfType(int)
	 */
	@Override
	public void playerPlaneGathersItemOfType(int type) {
		getPlayerPlane().resolveEntityHit(
				ItemFactory.createItem(Item.Type.values()[type]), 
				Jng.GAMEPLAY_STATE);
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended2#getNRockets()
	 */
	@Override
	public int getNRockets() {
		int counter = 0;
		for (Entity e : 
			entityManager.getEntitiesByState(Jng.GAMEPLAY_STATE)) {
			if (e.getId().equals("ProjectileRocket"))
				counter++;
		}
		return counter;
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended2#getRocketFireFrequency()
	 */
	@Override
	public float getRocketFireFrequency() {
		return Attributes.playerPlaneRocketFrequency;
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended2#handleKeyPressedX()
	 */
	@Override
	public void handleKeyPressedX() {
		handleKeyDown(0, Input.KEY_X);
	}
}
