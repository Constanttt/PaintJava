package paint;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/*
 * This class contains the main function
 * It is used to launch the app
 */

public class Launcher {
	
	public static void main(String[] args) {
		Paint p = new Paint();
		JFrame jf = new JFrame("Paint");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setSize(1280, 720);
		
		PaintMenu menu = new PaintMenu(p);
		PaintPanel paintPanel = new PaintPanel(p);
		PaintOptionPanel paintOptionPanel = new PaintOptionPanel(p);
		
		jf.setJMenuBar(menu);
		jf.getContentPane().add(BorderLayout.CENTER, paintPanel);
		jf.getContentPane().add(BorderLayout.NORTH, paintOptionPanel);
		jf.setResizable(false);
		jf.setVisible(true);
	}

}
