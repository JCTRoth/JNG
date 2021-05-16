package jng.factories;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import jng.entities.AbstractEntity;
import jng.entities.Attributes;
import jng.entities.GameEntity;
import jng.entities.Projectile;
import jng.weapons.LaunchPad;
import jng.weapons.Weapon;

public class WeaponFactory {

	public static Weapon createMG(AbstractEntity source) throws SlickException
	{
		return createMGRight(source);
	}
	
	public static Weapon createMGRight(AbstractEntity source) throws SlickException
	{
		Weapon w = new Weapon(Attributes.playerPlanePrimaryWeaponFrequency);
		Projectile p = ProjectileFactory.createMGProjectile(1);
		w.getLaunchPads().add(new LaunchPad(new Vector2f(
				source.getShape().getMaxX()-source.getPosition().x, 
				source.getShape().getCenterY()-source.getPosition().y), 90.0f, p));
		
		return createPrimaryWeaponOfRank(source, 1);
	}
	
	public static Weapon createSecundaryRocketWeapon(AbstractEntity source) throws SlickException
	{
		Weapon w = new Weapon(Attributes.playerPlaneRocketFrequency);
		Projectile p = ProjectileFactory.createRocket();
		w.getLaunchPads().add(new LaunchPad(new Vector2f(
				source.getShape().getMaxX()-source.getPosition().x,
				source.getShape().getCenterY()-source.getPosition().y), 90.0f, p));
		
		return w;
	}
	
	public static Weapon createTeslaWeapon(AbstractEntity source) throws SlickException
	{
		Weapon w = new Weapon(Attributes.aiWeaponFrequencyMedium-0.0005f);
		Projectile p = ProjectileFactory.createTeslaProjectile();
		w.getLaunchPads().add(new LaunchPad(new Vector2f(
				source.getShape().getMaxX()-source.getPosition().x,
				source.getShape().getCenterY()-source.getPosition().y), 90.0f, p));
		
		return w;
	}
	
	public static Weapon createSecundaryBombWeapon(AbstractEntity pe) throws SlickException
	{
		Weapon w = new Weapon(Attributes.playerPlaneBombFrequency);
		Projectile p = ProjectileFactory.createBomb();
		w.getLaunchPads().add(new LaunchPad(new Vector2f(
				pe.getShape().getCenterX()-pe.getPosition().x,
				pe.getShape().getMaxY()-pe.getPosition().y), 90.0f, p));
		
		return w;
	}
	
	public static Weapon createSecundaryBombWeaponLeft(AbstractEntity pe) throws SlickException
	{
		Weapon w = new Weapon(Attributes.playerPlaneBombFrequency);
		Projectile p = ProjectileFactory.createBombLeft();
		w.getLaunchPads().add(new LaunchPad(new Vector2f(
				pe.getShape().getCenterX()-pe.getPosition().x,
				pe.getShape().getMaxY()-pe.getPosition().y), 90.0f, p));
		
		return w;
	}
	
	public static void setWeaponsOfType(
			GameEntity pe, 
			int type,
			GameEntity... targets) throws SlickException
	{
		pe.setWeaponType(type);
		Weapon w = WeaponFactory.createMGSingleFireLeft(pe);
		Projectile p = w.getLaunchPads().get(0).getProjectile();
		switch(type)
		{
		case 1:
			w.setFrequency(Attributes.aiWeaponFrequencySlow);
			p.setStrength(0.1f);
			break;
		case 2:
			w.setFrequency(Attributes.aiWeaponFrequencySlow);
			p.setStrength(0.1f);
			break;
		case 3:
			w.setFrequency(Attributes.aiWeaponFrequencySlow);
			p.setStrength(0.2f);
			break;
		case 4:
			w.setFrequency(Attributes.aiWeaponFrequencySlow);
			p.setStrength(0.3f);
			break;
		case 5:
			w.setFrequency(Attributes.aiWeaponFrequencySlow);
			p.setStrength(0.1f);
			break;
		case 6:
			w = WeaponFactory.createSecundaryBombWeaponLeft(pe);
			break;
		case 7:
			w = WeaponFactory.createMGSingleFireLeft(pe, ProjectileFactory.createMGProjectile(2));
			w.setFrequency(0.002f);
			break;
		case 8:
			w = WeaponFactory.createSecundaryBombWeapon(pe);
			break;
		case 9:
			w = WeaponFactory.createPrimaryWeaponOfRank(pe, 2);
			w.setFrequency(Attributes.aiWeaponFrequencyMedium);
			break;
		case 10:
			w = WeaponFactory.createPrimaryWeaponOfRank(pe, 1);
			for (GameEntity target : targets)
				w.setTarget(target);
			w.setFrequency(Attributes.aiWeaponFrequencyMedium);
			pe.getWeapons().add(0, WeaponFactory.createSecundaryBombWeapon(pe));
			break;
		case 11:
			w = WeaponFactory.createPrimaryWeaponOfRank(pe, 2);
			w.getLaunchPads().get(0).setProjectile(ProjectileFactory.createMGProjectile(2));
			w.getLaunchPads().get(1).setProjectile(ProjectileFactory.createMGProjectile(2));
			w.setFrequency(Attributes.aiWeaponFrequencyMedium);
			break;
		case 12:
			w.getLaunchPads().get(0).setProjectile(ProjectileFactory.createMGProjectile(3));
			w.setFrequency(Attributes.aiWeaponFrequencyMedium);
			break;
		case 13: // mg tower
			w = WeaponFactory.createMGSingleFireLeft(pe);
			w.getLaunchPads().get(0).setRelativePosition(new Vector2f(pe.getShape().getCenterX()-pe.getPosition().x,
					pe.getShape().getMinY()+10.0f-pe.getPosition().y));
			for (GameEntity target : targets)
				w.setTarget(target);
			w.setFrequency(Attributes.aiWeaponFrequencyMedium);
			break;
		case 14: // rocket tower
			w = WeaponFactory.createSecundaryRocketWeapon(pe);
			w.getLaunchPads().get(0).setRelativePosition(new Vector2f(pe.getShape().getCenterX()-pe.getPosition().x,
					pe.getShape().getMinY()+10.0f-pe.getPosition().y));
			for (GameEntity target : targets)
				w.setTarget(target);
			break;
		case 15:
			// rocketLeft
			w = WeaponFactory.createSecundaryRocketWeapon(pe);
			w.getLaunchPads().get(0).setRelativePosition(new Vector2f(pe.getShape().getMinX()-pe.getPosition().x,
					pe.getShape().getCenterY()-pe.getPosition().y));
			w.setFrequency(w.getFrequency()/2.0f);
			for (GameEntity target : targets)
				w.setTarget(target);
			pe.getWeapons().add(0, w);
			// rocketRight
			w = WeaponFactory.createSecundaryRocketWeapon(pe);
			w.getLaunchPads().get(0).setRelativePosition(new Vector2f(pe.getShape().getMaxX()-pe.getPosition().x,
					pe.getShape().getCenterY()-pe.getPosition().y));
			w.setFrequency(w.getFrequency()/2.0f);
			for (GameEntity target : targets)
				w.setTarget(target);
			pe.getWeapons().add(0, w);
			// tesla
			w = WeaponFactory.createTeslaWeapon(pe);
			w.getLaunchPads().get(0).setRelativePosition(new Vector2f(pe.getShape().getCenterX()-pe.getPosition().x,
					pe.getShape().getMinY()+7.0f-pe.getPosition().y));
			for (GameEntity target : targets)
				w.setTarget(target);
			break;
		
		}
		if (type < 13)
			WeaponFactory.setWeaponLeft(w, pe);
		pe.getWeapons().add(0, w);
	}
	
	public static Weapon createPrimaryWeaponOfRank(AbstractEntity source, int rank) throws SlickException
	{
		Weapon w = new Weapon(Attributes.playerPlanePrimaryWeaponFrequency);
		w.setRank(rank);
		Projectile pYellow = ProjectileFactory.createMGProjectile(1);
		Projectile pBlue = ProjectileFactory.createMGProjectile(2);
		Projectile pViolet = ProjectileFactory.createMGProjectile(3);
		float d = 5.0f;
		float mx = source.getShape().getMaxX()-source.getPosition().x;
		float cy = source.getShape().getCenterY()-source.getPosition().y;
		if (rank == 1)
			w.getLaunchPads().add(new LaunchPad(new Vector2f(mx, cy), 90.0f, pYellow));
		else if (rank == 2)
		{
			w.getLaunchPads().add(new LaunchPad(new Vector2f(mx, cy+d), 90.0f, pYellow));
			w.getLaunchPads().add(new LaunchPad(new Vector2f(mx, cy-d), 90.0f, pYellow));
		}
		else if (rank == 3)
		{
			w.getLaunchPads().add(new LaunchPad(new Vector2f(mx, cy), 90.0f, pYellow));
			w.getLaunchPads().add(new LaunchPad(new Vector2f(mx, cy+d), 90.0f, pYellow));
			w.getLaunchPads().add(new LaunchPad(new Vector2f(mx, cy-d), 90.0f, pYellow));
		}
		else if (rank == 4)
		{
			w.getLaunchPads().add(new LaunchPad(new Vector2f(mx, cy), 90.0f, pYellow));
			w.getLaunchPads().add(new LaunchPad(new Vector2f(mx, cy+d), 90.0f, pYellow));
			w.getLaunchPads().add(new LaunchPad(new Vector2f(mx, cy-d), 90.0f, pYellow));
			w.getLaunchPads().add(new LaunchPad(new Vector2f(mx, cy+d*2), 100.0f, pYellow));
		}
		else if (rank > 4)
		{
			Projectile[] pArray = null;
			switch (rank)
			{
			case 5:
				pArray = new Projectile[]{pYellow, pYellow, pYellow, pYellow, pYellow};
				break;
			case 6:
				pArray = new Projectile[]{pYellow, pYellow, pBlue, pYellow, pYellow};
				break;
			case 7:
				pArray = new Projectile[]{pYellow, pBlue, pBlue, pYellow, pYellow};
				break;
			case 8:
				pArray = new Projectile[]{pYellow, pBlue, pBlue, pBlue, pYellow};
				break;
			case 9:
				pArray = new Projectile[]{pBlue, pBlue, pBlue, pBlue, pYellow};
				break;
			case 10:
				pArray = new Projectile[]{pBlue, pBlue, pBlue, pBlue, pBlue};
				break;
			case 11:
				pArray = new Projectile[]{pBlue, pBlue, pViolet, pBlue, pBlue};
				break;
			case 12:
				pArray = new Projectile[]{pBlue, pViolet, pViolet, pBlue, pBlue};
				break;
			case 13:
				pArray = new Projectile[]{pBlue, pViolet, pViolet, pViolet, pBlue};
				break;
			case 14:
				pArray = new Projectile[]{pViolet, pViolet, pViolet, pViolet, pBlue};
				break;
			default:
				pArray = new Projectile[]{pViolet, pViolet, pViolet, pViolet, pViolet};
				break;
			}
			w.getLaunchPads().add(new LaunchPad(new Vector2f(mx, cy-d*2), 85.0f, pArray[0]));
			w.getLaunchPads().add(new LaunchPad(new Vector2f(mx, cy+d), 90.0f, pArray[1]));
			w.getLaunchPads().add(new LaunchPad(new Vector2f(mx, cy), 90.0f, pArray[2]));
			w.getLaunchPads().add(new LaunchPad(new Vector2f(mx, cy-d), 90.0f, pArray[3]));
			w.getLaunchPads().add(new LaunchPad(new Vector2f(mx, cy+d*2), 95.0f, pArray[4]));
		}
		
		return w;
	}
	
	public static void setWeaponLeft(Weapon w, AbstractEntity source)
	{
		for (LaunchPad lp : w.getLaunchPads())
		{
			lp.setRelativePosition(new Vector2f(source.getShape().getMinX(), lp.getRelativePosition().y));
			lp.setAngle(-90.0f);
		}
	}
	
	public static Weapon createMGSingleFireLeft(AbstractEntity source) throws SlickException
	{
		return createMGSingleFireLeft(source, ProjectileFactory.createMGProjectile(1));
	}
	
	public static Weapon createMGSingleFireLeft(AbstractEntity source, Projectile p) throws SlickException
	{
		Weapon w = new Weapon(Attributes.aiPlanePrimaryWeaponFrequency);
		w.getLaunchPads().add(new LaunchPad(new Vector2f(
				source.getShape().getMinX()-source.getPosition().x,
				source.getShape().getCenterY()-source.getPosition().y), 
				-90.0f, p));
		return w;
	}
	
}
