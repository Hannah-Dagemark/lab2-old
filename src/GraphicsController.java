import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GraphicsController implements updateComposite {
    private final ArrayList<JComponent> panels = new ArrayList<>();
    private final SimulationPanel simulationPanel;
    private final CarController carController;
    private Map<Positionable, BufferedImage> modelsImages = new HashMap<>();

    public GraphicsController(Dimension dimension, CarController carController) {
        simulationPanel = new SimulationPanel(new Dimension(dimension.width, dimension.height-240), this);
        ControlPanel controlPanel = new ControlPanel(new Dimension(dimension.width, 240), carController);
        this.carController = carController;
        panels.add(simulationPanel);
        panels.add(controlPanel);
    }

    public void updatePositionableImages() { modelsImages = carController.getSimulationState(); }

    Map<Positionable, BufferedImage>  getPositionableImages() {
        updatePositionableImages();
        return modelsImages;
    }

    public void updateSimUI() {
        simulationPanel.updateSimUI();
    }

    public JComponent[] getPanels() {
        return panels.toArray(new JComponent[0]);
    }
}
