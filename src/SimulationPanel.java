import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class SimulationPanel extends JPanel implements updateComposite {
    private final GraphicsController graphicsController;
    private Map<Positionable, BufferedImage> currentState;

    public SimulationPanel(Dimension dimension, GraphicsController graphicsController) {
        this.graphicsController = graphicsController;
        this.setDoubleBuffered(true);
        this.setPreferredSize(dimension);
        this.setBackground(Color.green);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (currentState == null) {
            return;
        }
        for (Map.Entry<Positionable, BufferedImage> modelImage : currentState.entrySet()) {
            Positionable position = modelImage.getKey();
            BufferedImage image = modelImage.getValue();
            g.drawImage(image, (int)position.getPosition().getX(), (int)position.getPosition().getY(), null);
        }

        // REQUIRED for stuff to work with Linux or really anything non-windows that doesn't have automatic frame update pipeline flushing :]
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void updateSimUI() {
        currentState = graphicsController.getPositionableImages();
        this.repaint();
    }
}
