import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PanelManager implements updateComposite {
    private final ArrayList<JComponent> panelList = new ArrayList<>();

    public PanelManager(Dimension dimension, CarController cc) {
        // Which order each panel gets added matters!
        panelList.add(new SimulationPanel(new Dimension(dimension.width, dimension.height-240), cc));
        panelList.add(new ControlPanel(new Dimension(dimension.width, 240), cc));
    }

    public JComponent[] getPanels() {
        return panelList.toArray(new JComponent[0]);
    }

    @Override
    public void updateSimUI() {
        for (JComponent panel : panelList) {
            panel.updateUI();
        }
    }
}
