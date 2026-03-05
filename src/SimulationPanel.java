import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

public class SimulationPanel extends JPanel {
    private final CarController carController;
    private SimulationState currentState;

    public SimulationPanel(Dimension dimension, CarController carController) {
        this.carController = carController;
        this.setDoubleBuffered(true);
        this.setPreferredSize(dimension);
        this.setBackground(Color.green);
    }

    // Sets everything in place and fits everything
    private void initComponents(String title) {
        // TODO
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (currentState == null) {
            return;
        }
        for (Map.Entry<Positionable, BufferedImage> modelImage : currentState.getState().entrySet()) {
            Positionable position = modelImage.getKey();
            BufferedImage image = modelImage.getValue();
            g.drawImage(image, (int)position.getPosition().getX(), (int)position.getPosition().getY(), null);
        }

        // REQUIRED for stuff to work with Linux or really anything non-windows that doesn't have automatic frame update pipeline flushing :]
        Toolkit.getDefaultToolkit().sync();
    }

    public void updateSimInterface() {
        SimulationState state = carController.getSimulationState();
        currentState = state;
        this.repaint();
    }

}
