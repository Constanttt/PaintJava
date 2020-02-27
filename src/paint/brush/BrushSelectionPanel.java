package paint.brush;

import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import paint.Paint;

public class BrushSelectionPanel extends JPanel implements ChangeListener{
	private Paint p;
	private JSlider bSize;
	private int brushSize;
	
	public BrushSelectionPanel(Paint p) {
		this.p = p;
    	brushSize = 1;
		bSize = createSlider("brush size");
		add(bSize);
	}
	
	protected JSlider createSlider(String name) {
		JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 255, 0);
		slider.addChangeListener(this);
		Hashtable labelTable = new Hashtable();
		labelTable.put(0, new JLabel(name));
		slider.setLabelTable(labelTable);
		slider.setPaintLabels(true);
		return slider;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == bSize) {
			brushSize = bSize.getValue();
			p.changeBrushSize(brushSize);
		}
	}
}
