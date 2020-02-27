package paint;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/*
 * This class is the main area
 * This is the area where the drawing is made
 */

public class PaintPanel extends JPanel implements MouseListener, MouseMotionListener{
	
	public Paint p;
	private int prevX, prevY;
	public Graphics2D g2, g2Draw;
	
	public PaintPanel(Paint p) {
		super();
		this.p = p;
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	/*protected void changeColor(Color c) {
		p.changeColor(c);
	}*/


	//Initialization of the drawable area
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = getWidth();
		int height = getHeight();
		g.drawRect(0, 0, width, height);
	}
	
	/* 
	 * Event when Mouse is moved when pressed
	 * Is triggered each pixel
	 * drawLine is used to create a line between the previous pixel and the current pixel
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		g2.drawLine(prevX, prevY, x, y);
		g2Draw.drawLine(prevX, prevY, x, y);
		
		prevX = x;
		prevY = y;
	}

	/*
	 * When Mouse is pressed
	 * Initialization of prevX and prevY values
	 * It is the start event of mouseDragged
	 * drawLine is used to create a dot (in case of a simple click)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		g2 = (Graphics2D) p.bi.getGraphics();
		g2Draw = (Graphics2D) getGraphics();
		g2.setStroke(new BasicStroke(p.brushSize));
		g2Draw.setStroke(new BasicStroke(p.brushSize));
		g2.setColor(p.drawingColor);
		g2Draw.setColor(p.drawingColor);
		prevX = e.getX();
		prevY = e.getY();
		g2.drawLine(prevX, prevY, prevX, prevY);
		g2Draw.drawLine(prevX, prevY, prevX, prevY);
	}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
	@Override
	public void mouseMoved(MouseEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}
