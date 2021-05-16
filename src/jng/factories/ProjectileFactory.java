package jng.factories;

import org.newdawn.slick.SlickException;

import jng.entities.Attributes;
import jng.entities.Projectile;

public class ProjectileFactory {

	/**
	 * creates a machine gun projectile. 
	 * @param strength there are 3 strength types (1-3). 1 are yellow projectils and the weakest.
	 * 			2 are blue ones. They are slightly stronger. 3 are violet and the strongest.
	 * @return Projectile
	 * @throws SlickException
	 */
	public static Projectile createMGProjectile(int strength) throws SlickException
	{
		switch(strength)
		{
		case 1:
			return new Projectile(-1, null, 0.6f, Attributes.standardProjectileDamageYellow, false,
					"/assets/projectileShot.png", "ProjectileMGYellow");
		case 2:
			return new Projectile(-1, null, 0.6f, Attributes.standardProjectileDamageBlue, false,
					"/assets/projectileShotBlue.png", "ProjectileMGBlue");
		case 3:
			return new Projectile(-1, null, 0.6f, Attributes.standardProjectileDamageViolet, false,
					"/assets/projectileShotViolet.png", "ProjectileMGViolet");
		}
		return new Projectile(-1, null, 0.6f, Attributes.standardProjectileDamageYellow, false,
				"/assets/projectileShot.png", "ProjectileMGYellow");
	}
	
	public static Projectile createBomb() throws SlickException
	{
		return new Projectile(-1, null, 0.4f, Attributes.standardBombDamage, true,
				"/assets/bomb.png", "ProjectileBomb");
	}
	
	public static Projectile createBombLeft() throws SlickException
	{
		return new Projectile(-1, null, 0.4f, Attributes.standardBombDamage, true,
				"/assets/bombLeft.png", "ProjectileBomb");
	}
	
	public static Projectile createRocket() throws SlickException
	{
		return new Projectile(-1, null, 0.4f, Attributes.standardRocketDamage, false,
				"/assets/rocket.png", "ProjectileRocket");
	}
	
	public static Projectile createTeslaProjectile() throws SlickException
	{
		return new Projectile(-1, null, 0.3f, Attributes.standardProjectileDamageYellow, false,
				"/assets/projectileTesla.png", "ProjectileTesla");
	}
}
