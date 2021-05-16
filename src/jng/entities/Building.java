package jng.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import jng.enums.Orientation;

public class Building extends Structure{
	
	private Color color;
	
	private int nColumns;
	
	private int width;

	public Building(String entityID, int nColumns, Color color, int stateID, Orientation orientation) throws SlickException {
		super(entityID, "/assets/buildings/building" + nColumns + ".png", stateID, orientation);
		this.color = color;
		this.nColumns = nColumns;
		switch(nColumns)
		{
		case 2:
			width = 76;
			break;
		case 3:
			width = 112;
			break;
		case 4:
			width = 146;
			break;
		case 5:
			width = 186;
			break;
		case 6:
			width = 221;
		default:
				
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb,
			Graphics g) {
		g.setColor(color);
		g.fillRect(getShape().getMinX(), getShape().getMinY(), width, 300);
		super.render(gc, sb, g);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getnColumns() {
		return nColumns;
	}

	public void setnColumns(int nColumns) {
		this.nColumns = nColumns;
	}

	public int getWidth() {
		return width;
	}
	
	
}
