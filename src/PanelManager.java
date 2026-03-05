import javax.swing.*;
import java.awt.*;

public class PanelManager {
    private ControlPanel controlPanel;
    private SimulationPanel simulationPanel;

    public PanelManager(Dimension dimension, CarController cc) {
        controlPanel = new ControlPanel(dimension, cc);
        simulationPanel = new SimulationPanel(dimension, cc);
    }

    public void updatePanels() {
        simulationPanel.updateSimInterface();
    }

    public JComponent[] getPanels() {
        return new JComponent[] {
                controlPanel,
                simulationPanel
        };
    }
}
