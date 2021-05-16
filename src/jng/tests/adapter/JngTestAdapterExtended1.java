package jng.tests.adapter;

import java.awt.geom.Point2D;
import java.util.List;

import org.newdawn.slick.SlickException;

import jng.actions.SpawnPlanesAction;
import jng.entities.Attributes;
import jng.entities.Structure;
import jng.factories.WeaponFactory;
import jng.tests.adapter.interfaces.IJngTestAdapterExtended1;
import jng.weapons.LaunchPad;
import jng.world.World;

public class JngTestAdapterExtended1 extends JngTestAdapterMinimal implements IJngTestAdapterExtended1 {

	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended1#getPlayerPlaneLifes()
	 */
	@Override
	public int getPlayerPlaneLifes()
	{
		return getPlayerPlane().getLifes();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended1#getEnemyPlaneWeaponType(int)
	 */
	@Override
	public int getEnemyPlaneWeaponType(int index)
	{
		return getEnemyPlanes().get(index).getWeaponType();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended1#getBorderOffset()
	 */
	@Override
	public float getBorderOffset() {
		return 20.0f;
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended1#getPlayerPlaneLeftUpperCorner()
	 */
	@Override
	public Point2D.Float getPlayerPlaneLeftUpperCorner() {
		return new Point2D.Float(getPlayerPlane().getShape().getMinX(),
				getPlayerPlane().getShape().getMinY());
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended1#getPlayerPlaneBottomRightCorner()
	 */
	@Override
	public Point2D.Float getPlayerPlaneBottomRightCorner() {
		return new Point2D.Float(getPlayerPlane().getShape().getMinX(),
				getPlayerPlane().getShape().getMinY());
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended1#getNFiredProjectilesByWeaponOfRank(int)
	 */
	@Override
	public int getNFiredProjectilesByWeaponOfRank(int rank) {
		try {
			return WeaponFactory.createPrimaryWeaponOfRank(getPlayerPlane(), rank).getLaunchPads().size();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended1#getWeaponStrengthOfRank(int)
	 */
	@Override
	public float getWeaponStrengthOfRank(int rank) {
		float strength = 0;
		List<LaunchPad> lps;
		try {
			lps = WeaponFactory.createPrimaryWeaponOfRank(getPlayerPlane(), rank).getLaunchPads();
			for (LaunchPad lp : lps) {
				strength += lp.getProjectile().getStrength();
			}
			return strength;
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return 0.0f;
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended1#getFireRate(int)
	 */
	@Override
	public float getFireRate(int rank) {
		return Attributes.playerPlanePrimaryWeaponFrequency;
	}
	
	private List<Structure> getStructures()
	{
		return World.getInstance().getStructures();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended1#getNStructure()
	 */
	@Override
	public int getNStructure()
	{
		return getStructures().size();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended1#getStructurePosition(int)
	 */
	@Override
	public Point2D.Float getStructurePosition(int index)
	{
		Structure s = getStructures().get(index);
		return new Point2D.Float(s.getPosition().x, s.getPosition().y);
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended1#getStructureRotation(int)
	 */
	@Override
	public float getStructureRotation(int index)
	{
		return getStructures().get(index).getRotation();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended1#getStructureScale(int)
	 */
	@Override
	public float getStructureScale(int index)
	{
		return getStructures().get(index).getScale();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended1#getStructureID(int)
	 */
	@Override
	public String getStructureID(int index)
	{
		return getStructures().get(index).getId();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended1#getStructureOrientation(int)
	 */
	@Override
	public int getStructureOrientation(int index)
	{
		return getStructures().get(index).getOrientation().ordinal();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended1#getStructureIsInvincible(int)
	 */
	@Override
	public boolean getStructureIsInvincible(int index)
	{
		return getStructures().get(index).isInvincible();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended1#getStructureHealth(int)
	 */
	@Override
	public float getStructureHealth(int index)
	{
		return getStructures().get(index).getHealth();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended1#getStructureMaxHealth(int)
	 */
	@Override
	public float getStructureMaxHealth(int index)
	{
		return getStructures().get(index).getMaxHealth();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended1#getStructureShields(int)
	 */
	@Override
	public float getStructureShields(int index)
	{
		return getStructures().get(index).getArmor();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended1#getStructureMaxShields(int)
	 */
	@Override
	public float getStructureMaxShields(int index)
	{
		return getStructures().get(index).getMaxArmor();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended1#getStructureItem(int)
	 */
	@Override
	public float getStructureItem(int index)
	{
		return getStructures().get(index).getItem().getType().ordinal();
	}
	
	protected List<SpawnPlanesAction> getSpawnPlanesActions()
	{
		return World.getInstance().getSpawnPlanesActions();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended1#getNSpawnPlanesActions()
	 */
	@Override
	public int getNSpawnPlanesActions()
	{
		return getSpawnPlanesActions().size();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended1#getSpawnPlanesActionPosition(int)
	 */
	@Override
	public Point2D.Float getSpawnPlanesActionPosition(int index)
	{
		SpawnPlanesAction spa = getSpawnPlanesActions().get(index);
		return new Point2D.Float(spa.getPosition().x, spa.getPosition().y);
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended1#getSpawnPlanesActionFrequency(int)
	 */
	@Override
	public float getSpawnPlanesActionFrequency(int index)
	{
		return getSpawnPlanesActions().get(index).getFrequency();
	}
	
	/* (non-Javadoc)
	 * @see de.tud.gdi1.jng.tests.adapter.IJngTestAdapterExtended1#getSpawnPlanesActionNPlanes(int)
	 */
	@Override
	public int getSpawnPlanesActionNPlanes(int index)
	{
		return getSpawnPlanesActions().get(index).getPlanes().size();
	}
	
}
