package jng.tests;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.component.RenderComponent;

public class TestRenderComponent extends RenderComponent {

	private float width;

	private float height;

	/**
	 * This little hack allows us to give entities a 2D expansion without using
	 * org.newdawn.slick.Image that throws an exception when using it in a
	 * thread that has not graphics context.
	 * 
	 * Note: if the image loading fails, try to skip the first "/" from your
	 * image path by using imagePath.substring(1)
	 * 
	 * @param imagePath
	 */
	public TestRenderComponent(String imagePath) {
		super("TestRenderComponent");
		try {
			Dimension d = getImageDimension(new File(imagePath));
			width = (float) d.getWidth();
			height = (float) d.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets image dimensions for given file
	 * 
	 * source: http://stackoverflow.com/a/12164026
	 * 
	 * @param imgFile
	 *            image file
	 * @return dimensions of image
	 * @throws IOException
	 *             if the file is not a known image
	 */
	public static Dimension getImageDimension(File imgFile) throws IOException {
		int pos = imgFile.getName().lastIndexOf(".");
		if (pos == -1)
			throw new IOException("No extension for file: "
					+ imgFile.getAbsolutePath());
		String suffix = imgFile.getName().substring(pos + 1);
		Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);
		if (iter.hasNext()) {
			ImageReader reader = iter.next();
			try {
				ImageInputStream stream = new FileImageInputStream(imgFile);
				reader.setInput(stream);
				int width = reader.getWidth(reader.getMinIndex());
				int height = reader.getHeight(reader.getMinIndex());
				return new Dimension(width, height);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				reader.dispose();
			}
		}

		throw new IOException("Not a known image file: "
				+ imgFile.getAbsolutePath());
	}

	@Override
	public Vector2f getSize() {
		return new Vector2f(width * owner.getScale(), height * owner.getScale());
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sb,
			Graphics graphicsContext) {
		// do nothing
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		// do nothing
	}
}
