package jng.ui.buttonGameState;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import jng.ui.Controls;

public abstract class BasicButtonGameState extends BasicGameState {
	
	public static final Vector2f buttonSize = new Vector2f(160, 80);
	
	private List<Button> buttons = new ArrayList<Button>();
	
	protected List<Label> labels = new ArrayList<Label>();

	protected StateBasedEntityManager entityManager;
	
	protected int stateID;
	
	public BasicButtonGameState(int sid)
	{
		stateID = sid;
		entityManager = StateBasedEntityManager.getInstance();
	}
	
	public Entity addBackground(String imagePath) throws SlickException
	{
		Entity background = new Entity("background");	// Entitaet fuer Hintergrund
    	background.setPosition(new Vector2f(Controls.displayResolution.x/2,
    			Controls.displayResolution.y/2));	// Startposition des Hintergrunds
    	Controls.loadImage(background, imagePath);
    	background.setScale(Math.min(Controls.displayResolution.y/background.getShape().getHeight(), 
    			Controls.displayResolution.x/background.getShape().getWidth()));
    	entityManager = StateBasedEntityManager.getInstance();
    	
    	return background;
	}
	
	public Entity addButton(
			int x, 
			int y,
			Vector2f buttonSize, 
			String text, 
			String... imagePath) throws SlickException
	{
    	Entity entity = new Entity(text);
    	
    	// Setze Position und Bildkomponente
    	entity.setPosition(new Vector2f(x, y));
    	if (imagePath.length > 0)
    	{
    		Controls.loadImage(entity, imagePath[0]);
    	}
    	
    	entity.setSize(buttonSize);
    	
    	buttons.add(new Button(entity, x, y, text));
    	return entity;
	}
	
	/**
     * Wird vor dem Frame ausgefuehrt
     */
	@Override
	public void update(
			GameContainer container, 
			StateBasedGame game, 
			int delta)
			throws SlickException 
	{
		entityManager.updateEntities(container, game, delta);
	}

	@Override
	public void render(
			GameContainer container, 
			StateBasedGame game, 
			Graphics g)
			throws SlickException 
	{
		g.setColor(Color.white);
		entityManager.renderEntities(container, game, g);
		for (Button b : buttons)
		{
			g.setColor(Color.white);
			g.drawString(b.text, b.x -
					g.getFont().getWidth(b.text) / 2, 
					b.y - 
					g.getFont().getHeight(b.text) / 2);
			g.setColor(Color.black);
			g.drawRect(b.entity.getShape().getMinX()-1, 
					b.entity.getShape().getMinY()-1, 
					b.entity.getShape().getWidth(), 
					b.entity.getShape().getHeight());
		}
		g.setColor(Color.white);
		for (Label s : labels)
			g.drawString(s.text, 
					s.position.x - g.getFont().getWidth(s.text) / 2, 
					s.position.y - g.getFont().getHeight(s.text) / 2);
	}
	
	@Override
	public int getID() 
	{
		return stateID;
	}
	
	private class Button
	{
		Entity entity;
		public int x;
		public int y;
		public String text;
		
		public Button(Entity entity, int x, int y, String text) 
		{
			super();
			this.entity = entity;
			this.x = x;
			this.y = y;
			this.text = text;
		}
	}

	public List<Label> getLabels() {
		return labels;
	}

	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}
	
	public class Label
	{
		String text;
		
		Vector2f position;

		public Label(String text, Vector2f position) {
			super();
			this.text = text;
			this.position = position;
		}
		
		
	}

}
