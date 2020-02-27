package paint;

import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import paint.brush.BrushSelectionPanel;
import paint.old.PaintChooserPanel;
import paint.palette.HSBColorChooserPanel;

/*
 * This class is the option area
 * This is the area where the drawing informations are chosen
 */

public class PaintOptionPanel extends JPanel {
	
	private Paint p;
	private JColorChooser jcc;
	private BrushSelectionPanel bsp;
	
	public PaintOptionPanel(Paint p) {
		super();
		setBackground(Color.LIGHT_GRAY);
		
		/*
		 * We use only the custom ColorChooserPanel
		 * Removal of the ChooserPanel
		 * ChangeListener used to change the drawing color
		 */
		AbstractColorChooserPanel panels[] = { new HSBColorChooserPanel() };
		
		JColorChooser jcc = new JColorChooser();
		jcc.setPreviewPanel(new JPanel());
		jcc.setChooserPanels(panels);
		jcc.getSelectionModel().addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				p.changeColor(jcc.getColor());
			}
		});
		
		bsp = new BrushSelectionPanel(p);
		
		add(bsp);
		add(jcc);
	}

}
