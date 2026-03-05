import javax.swing.*;
import java.awt.*;

public class PanelManager {
    private final ControlPanel controlPanel;
    private final SimulationPanel simulationPanel;

    public PanelManager(Dimension dimension, CarController cc) {
        simulationPanel = new SimulationPanel(new Dimension(dimension.width, dimension.height-240), cc);
        controlPanel = new ControlPanel(new Dimension(dimension.width, 240), cc);
    }

    public void updatePanels() {
        simulationPanel.updateSimInterface();
    }

    public JComponent[] getPanels() {
        return new JComponent[] {
                simulationPanel,
                controlPanel
        };
    }
}
