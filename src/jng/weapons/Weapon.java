package jng.weapons;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.SlickException;

import jng.actions.sound.SoundManager;
import jng.entities.GameEntity;
import jng.entities.Projectile;

public class Weapon {
	
	private int type;
	
	private float frequency;
	
	// 0 - right, 1 - left
	private boolean shootLeft;
	
	private List<GameEntity> target;
	
	private ArrayList<LaunchPad> launchPads;
	
	private int rank;
	
	public Weapon(float frequency) {
		this.frequency = frequency;
		target = null;
		launchPads = new ArrayList<LaunchPad>();
		rank = 1;
		type = 1;
		target = new ArrayList<GameEntity>();
	}

	
	@Override
	public String toString() {
		return "" + type;
	}

	public void fireWeapon(GameEntity pe, int stateID) throws SlickException
	{
		for (LaunchPad lp : launchPads)
			lp.fireLaunchPad(pe, stateID);
		// Sound
		if (!launchPads.isEmpty())
		{
			Projectile p = launchPads.get(0).getProjectile();
			if (p.getId().startsWith("ProjectileMG"))
				SoundManager.getInstance().playFireMG();	
			else if (p.getId().startsWith("ProjectileRocket"))
				SoundManager.getInstance().playFireRocket();
			else if (p.getId().startsWith("ProjectileTesla"))
				SoundManager.getInstance().playFireTesla();
			else if (p.getId().startsWith("ProjectileBomb"))
				SoundManager.getInstance().playFireBomb();
		}
	}

	public ArrayList<LaunchPad> getLaunchPads() {
		return launchPads;
	}

	public void setLaunchPads(ArrayList<LaunchPad> launchPads) {
		this.launchPads = launchPads;
	}

	public List<GameEntity> getTarget() {
		return target;
	}

	public boolean addTarget(GameEntity arg0) {
		return target.add(arg0);
	}

	public void setTarget(GameEntity target) {
		addTarget(target);
		for (LaunchPad lp : launchPads)
			lp.getTargets().add(target);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public float getFrequency() {
		return frequency;
	}

	public void setFrequency(float frequency) {
		this.frequency = frequency;
	}

	public boolean isShootLeft() {
		return shootLeft;
	}

	public void setShootLeft(boolean direction) {
		this.shootLeft = direction;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
}
