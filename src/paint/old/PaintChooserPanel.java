package paint.old;

import java.awt.Color;
import java.util.Hashtable;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 * This class is the custom ColorChooserPanel
 * It is used to select a color
 */

public class PaintChooserPanel extends AbstractColorChooserPanel implements ChangeListener {

	private JSlider r;
	private JSlider g;
	private JSlider b;
	
	private Color c;
	
	@Override
	protected void buildChooser() {
		
		c = new Color(0, 0, 0);
		
		r = createSlider("red");
		add(r);
		g = createSlider("green");
		add(g);
		b = createSlider("blue");
		add(b);
	}

	@Override
	public String getDisplayName() {
		return null;
	}

	@Override
	public Icon getLargeDisplayIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Icon getSmallDisplayIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateChooser() {
		// TODO Auto-generated method stub
	}
	
	/*
	 * Creation of the sliders
	 * Also adds a changeListener on each slider
	 */
	protected JSlider createSlider(String name) {
		JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
		slider.addChangeListener(this);
		Hashtable labelTable = new Hashtable();
		labelTable.put(0, new JLabel(name));
		slider.setLabelTable(labelTable);
		slider.setPaintLabels(true);
		return slider;
	}
	
	/*
	 * When a changeListener is added, this function is called
	 * e.getSource() is used to determine which element is triggered and take the corresponding action
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == r) {
			c = new Color(r.getValue(), c.getGreen(), c.getBlue());
		}
		else if (e.getSource() == g) {
			c = new Color(c.getRed(), g.getValue(), c.getBlue());
		}
		else if (e.getSource() == b) {
			c = new Color(c.getRed(), c.getGreen(), b.getValue());  
		}
		getColorSelectionModel().setSelectedColor(c);
	}
}
