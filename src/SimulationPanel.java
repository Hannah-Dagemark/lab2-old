import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

public class SimulationPanel extends JPanel {

    private Dimension dimensions;
    private CarController cc;
    private SimulationState currentState;

    public SimulationPanel(Dimension dimension, CarController cc) {
        this.cc = cc;
        this.setDoubleBuffered(true);
        this.setPreferredSize(dimension);
        this.dimensions = dimension;
        this.setBackground(Color.green);
        // Print an error message in case file is not found with a try/catch block
        /*for (Car car : cars) {
            CarPoints.put(car, new Point(0,0));
            try {
                System.out.println("Attempting to find image:" + "pics/" + car.getModelName() + ".jpg");
                CarImages.put(car, ImageIO.read(DrawPanel.class.getResourceAsStream("pics/" + car.getModelName() + ".jpg")));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }*/

        /*try {
            volvoWorkshopImage = ImageIO.read(DrawPanel.class.getResourceAsStream("pics/VolvoBrand.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }*/
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
        SimulationState state = cc.getSimulationState();
        currentState = state;
        this.repaint();
    }

}
