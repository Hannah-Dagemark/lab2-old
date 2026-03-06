import javax.swing.*;
import java.awt.*;

public class CarFrame extends JFrame implements updateComposite {
    private final PanelManager panelManager;

    public CarFrame(String frameName, Dimension dimension, CarController cc) {
        panelManager = new PanelManager(dimension, cc);
        this.setTitle(frameName);
        this.setPreferredSize(dimension);
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JComponent[] components = panelManager.getPanels();
        for (JComponent component : components) {
            this.add(component);
        }
        // Make the frame pack all it's components by respecting the sizes if possible.
        this.pack();
        // Get the computer screen resolution
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        // Center the frame
        this.setLocation(dim.width/2-this.getWidth()/2, dim.height/2-this.getHeight()/2);
        // Make the frame visible
        this.setVisible(true);
        // Make sure the frame exits when "x" is pressed
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void updateSimUI() {
        panelManager.updateSimUI();
    }
}
