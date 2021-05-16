package jng.tests.adapter;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Input;

import eea.engine.entity.Entity;
import jng.entities.Attributes;
import jng.entities.GameEntity;
import jng.entities.Projectile;
import jng.tests.adapter.interfaces.IJngTestAdapterExtended3;
import jng.ui.Jng;
import jng.world.World;

public class JngTestAdapterExtended3 extends JngTestAdapterMinimal implements IJngTestAdapterExtended3 {
	
	private List<GameEntity> getTowers() {
		List<GameEntity> towers = World.getInstance().getEnemies();
		towers.removeAll(getEnemyPlanes());
		return towers;
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended3#getProjectilePositionsOfTower(int)
	 */
	@Override
	public List<Point2D.Float> getProjectilePositionsOfTower(int index) {
		List<Point2D.Float> list = new ArrayList<Point2D.Float>();
		for (Entity e : entityManager.getEntitiesByState(Jng.GAMEPLAY_STATE)) {
			if (e instanceof Projectile) {
				Projectile p = (Projectile) e;
				if (!p.getSource().getId().startsWith("plane")) {
						list.add(new Point2D.Float(
								p.getPosition().x,
								p.getPosition().y));
				}
			}
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended3#getProjectileRotationOfTower(int)
	 */
	@Override
	public List<Float> getProjectileRotationOfTower(int index) {
		List<Float> list = new ArrayList<Float>();
		for (Entity e : entityManager.getEntitiesByState(Jng.GAMEPLAY_STATE)) {
			if (e instanceof Projectile) {
				Projectile p = (Projectile) e;
				if (!p.getSource().getId().startsWith("plane")) {
					list.add(p.getRotation());
				}
			}
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended3#getTowerWeaponFrequency(int)
	 */
	@Override
	public float getTowerWeaponFrequency(int index) {
		return getTowers().get(index).getWeapons().get(0).getFrequency();
	}
	
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended3#getNTowers()
	 */
	@Override
	public int getNTowers() {
		return getTowers().size();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended3#getTowerPosition(int)
	 */
	@Override
	public Point2D.Float getTowerPosition(int index) {
		GameEntity ge = getTowers().get(index);
		return new Point2D.Float(ge.getPosition().x, ge.getPosition().y);
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended3#getTowerRotation(int)
	 */
	@Override
	public float getTowerRotation(int index) {
		return getTowers().get(index).getRotation();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended3#getTowerScale(int)
	 */
	@Override
	public float getTowerScale(int index) {
		return getTowers().get(index).getScale();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended3#getTowerOrientation(int)
	 */
	@Override
	public int getTowerOrientation(int index) {
		return getTowers().get(index).getOrientation().ordinal();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended3#getTowerIsInvicible(int)
	 */
	@Override
	public boolean getTowerIsInvicible(int index) {
		return getTowers().get(index).isInvincible();
	}

	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended3#getTowerSpeed(int)
	 */
	@Override
	public float getTowerSpeed(int index) {
		return getTowers().get(index).getSpeed();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended3#getTowerEntityID(int)
	 */
	@Override
	public String getTowerEntityID(int index) {
		return getTowers().get(index).getId();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended3#getTowerHealth(int)
	 */
	@Override
	public float getTowerHealth(int index) {
		return getTowers().get(index).getHealth();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended3#getTowerMaxHealth(int)
	 */
	@Override
	public float getTowerMaxHealth(int index) {
		return getTowers().get(index).getMaxHealth();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended3#getTowerShields(int)
	 */
	@Override
	public float getTowerShields(int index) {
		return getTowers().get(index).getArmor();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended3#getTowerMaxShields(int)
	 */
	@Override
	public float getTowerMaxShields(int index) {
		return getTowers().get(index).getMaxArmor();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended3#getTowerFireModeOn(int)
	 */
	@Override
	public boolean getTowerFireModeOn(int index) {
		return getTowers().get(index).getAiBehaviour().isFireModeOn();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended3#getTowerWeaponType(int)
	 */
	@Override
	public int getTowerWeaponType(int index) {
		return getTowers().get(index).getWeaponType();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended3#handleKeyPressedC()
	 */
	@Override
	public void handleKeyPressedC() {
		handleKeyDown(0, Input.KEY_C);
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended3#getBombFireFrequency()
	 */
	@Override
	public float getBombFireFrequency() {
		return Attributes.playerPlaneBombFrequency;
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended3#getNBombs()
	 */
	@Override
	public int getNBombs() {
		int counter = 0;
		for (Entity e : 
			entityManager.getEntitiesByState(Jng.GAMEPLAY_STATE)) {
			if (e.getId().equals("ProjectileBomb"))
				counter++;
		}
		return counter;
	}
}
