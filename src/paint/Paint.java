package paint;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * This class contains the drawing variables
 * These variables are to be modified/accessed by other classes
 */

public class Paint {

	public Color drawingColor;
	public int brushSize;
	public Graphics2D g2;
	public BufferedImage bi;
	
	public Paint() {
		super();
		bi = new BufferedImage(1280, 402, BufferedImage.TYPE_INT_ARGB);
		
		this.brushSize = 1;
	}
	
	public void changeColor(Color c) {
		this.drawingColor = c;
	}
	
	public void changeBrushSize(int i) {
		this.brushSize = i;
	}
	
	public void saveToFile() {
		RenderedImage ri = bi;
		try {
			ImageIO.write(ri, "PNG", new File("test.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
