package paint.palette;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputAdapter;

public class HSBColorChooserPanel extends AbstractColorChooserPanel {

	private class BrightnessSelectorIcon implements Icon {
	    private final Dimension size = new Dimension(SELECTOR_WIDTH, COMPONENTS_HEIGHT);

		@Override
	    public void paintIcon(Component c, Graphics g, int x, int y) {
	        float fixed = 1;
	        
	        for (int i = 0; i < SELECTOR_WIDTH; i++) {
	            for(int j = 0; j < COMPONENTS_HEIGHT; j++) {
	                g.setColor(Color.getHSBColor((float)(SELECTOR_WIDTH - i)  / SELECTOR_WIDTH, (float)(COMPONENTS_HEIGHT - j) / COMPONENTS_HEIGHT, fixed));
	                g.drawLine(i, j, i, j);
	            }
	        }
	    }

		@Override
	    public int getIconWidth() {
	        return size.width;
	    }

		@Override
	    public int getIconHeight() {
	        return size.height;
	    }

	}
	
	private class BrightnessSelector extends JLabel {

	    public BrightnessSelector() {
	        setIcon(new BrightnessSelectorIcon());
	    }

	    @Override
	    public void paint(final Graphics graphics) {
	        Color oldColor = graphics.getColor();
	        super.paint(graphics);
	        paintCircle(graphics);

	        graphics.setColor(oldColor);
	    }

	    private void paintCircle(final Graphics graphics) {
	        Color color = getColorSelectionModel().getSelectedColor();
	        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
	        	        
	        int x = 0;
	        int y = 0;
	        int r = 5;
	        
	        x = Math.round(SELECTOR_WIDTH * (1 - hsb[HUE]));
	        y = Math.round(COMPONENTS_HEIGHT * (1 - hsb[SATURATION]));

	        graphics.setColor(Color.WHITE);
	        graphics.drawArc(x - r, y - r, 2 * r, 2 * r, 0, 360);
	    }
	}
	
    private class BrightnessSliderImage extends JLabel {
        private final Dimension size = new Dimension(SLIDER_IMAGE_WIDTH, COMPONENTS_HEIGHT);

        public Dimension getPreferredSize() {
            return size;
        }

        public void paint(final Graphics graphics) {
            Color color = getColorSelectionModel().getSelectedColor();
            float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);

            for (int i = 0; i < COMPONENTS_HEIGHT; i++) {
                float t = (float)i / COMPONENTS_HEIGHT;
                graphics.setColor(Color.getHSBColor(hsb[0], 1.f, 1.f - t));
                graphics.drawLine(0, i, SLIDER_IMAGE_WIDTH, i);
            }

        }
    }

    private static final int COMPONENTS_HEIGHT = 200;
    private static final int SELECTOR_WIDTH = 200;
    private static final int SLIDER_IMAGE_WIDTH = 16;

    private static final int HUE = 0;
    private static final int SATURATION = 1;
    private static final int BRIGHTNESS = 2;

    private static final int MAX_HUE = 359;
    private static final int MAX_SATURATION = 100;
    private static final int MAX_BRIGHTNESS = 100;
    private static final int[] MAX = {MAX_HUE, MAX_SATURATION, MAX_BRIGHTNESS};

    private JSlider slider;
    private BrightnessSliderImage sliderImage;
    private BrightnessSelector selector;

    private boolean internalUpdateDisabled;

    public String getDisplayName() {
        return UIManager.getString("ColorChooser.hsbNameText");
    }

    public Icon getSmallDisplayIcon() {
        return null;
    }

    public Icon getLargeDisplayIcon() {
        return null;
    }

    public void updateChooser() {
        Color color = getColorSelectionModel().getSelectedColor();
        if (color == null) {
            return;
        }
        internalUpdateDisabled = true;
        
        updateSelector();
        internalUpdateDisabled = false;
    }

    protected void buildChooser() {

        JPanel left = buildSelectorPanel();

        JPanel fullPanel = new JPanel();
        fullPanel.add(left);
        this.add(fullPanel);
    }

    private void updateSelector() {
        ChangeListener listener = slider.getChangeListeners()[0];
        slider.removeChangeListener(listener);
        slider.addChangeListener(listener);

        sliderImage.repaint();
        selector.repaint();
    }

    private void updateColor(final int x, final int y) {
        Color color = getColorSelectionModel().getSelectedColor();
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);

        float xColor = (float)(SELECTOR_WIDTH - x) / SELECTOR_WIDTH;
        xColor = (xColor >= 1.f) ? 1.f : xColor;
        xColor = (xColor <= 0.f) ? 0.f : xColor;
        float yColor = (float)(COMPONENTS_HEIGHT - y) / COMPONENTS_HEIGHT;
        yColor = (yColor >= 1.f) ? 1.f : yColor;
        yColor = (yColor <= 0.f) ? 0.f : yColor;
        hsb[HUE] = xColor;
        hsb[SATURATION] = yColor;
        
        getColorSelectionModel().setSelectedColor(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
    }

    private JPanel buildSelectorPanel() {
        JPanel selectorPanel = new JPanel();
        selector = new BrightnessSelector();
        
        MouseInputAdapter selectorMouseAdapter = new MouseInputAdapter() {
            public void mousePressed(MouseEvent e) {
                updateColor(e.getX(), e.getY());
            }
            public void mouseDragged(MouseEvent e) {
                updateColor(e.getX(), e.getY());
            }
        };
        selector.addMouseListener(selectorMouseAdapter);
        selector.addMouseMotionListener(selectorMouseAdapter);
        selectorPanel.add(selector);

        slider = new JSlider(JSlider.VERTICAL);
        slider.setMinimum(0);
        slider.setPaintTrack(false);
        slider.setInverted(false);
        slider.setPreferredSize(new Dimension(slider.getPreferredSize().width,
                                                    COMPONENTS_HEIGHT + 24));
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (internalUpdateDisabled || slider.getValueIsAdjusting()) {
                    return;
                }
                Color color = getColorSelectionModel().getSelectedColor();
                float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
                hsb[BRIGHTNESS] = 1.f * slider.getValue() / MAX[BRIGHTNESS];
                getColorSelectionModel().setSelectedColor(Color.getHSBColor(hsb[0], hsb[1], hsb[2]));
            }
        });
        selectorPanel.add(slider);
        sliderImage = new BrightnessSliderImage();
        selectorPanel.add(sliderImage);
        return selectorPanel;
    }
}