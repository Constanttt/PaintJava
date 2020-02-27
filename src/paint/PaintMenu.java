package paint;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import javax.imageio.ImageIO;
/*
 * This class is the top menu
 * This is where there is all the options
 */

public class PaintMenu extends JMenuBar {

	private Paint p;
	
	public PaintMenu(Paint p) {
		super();
		this.p = p;
		
		JMenu m1 = new JMenu("File");
		JMenu m2 = new JMenu("Help");
		
		JMenuItem m10 = new JMenuItem("New");
		JMenuItem m11 = new JMenuItem("Open");
		JMenuItem m12 = new JMenuItem("Save");
		JMenuItem m13 = new JMenuItem("Save As");
		JMenuItem m14 = new JMenuItem("Exit");
		
		JMenuItem m20 = new JMenuItem("About");
		
		m12.addActionListener((event) -> p.saveToFile());
		m14.addActionListener((event) -> System.exit(0));
		
		this.add(m1);
		this.add(m2);
		
		m1.add(m10);
		m1.add(m11);
		m1.add(m12);
		m1.add(m13);
		m1.add(m14);
		
		m2.add(m20);
	}

}
