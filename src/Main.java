import java.awt.*;
import javax.swing.*;

public class Main {
    private static final int X = 1200;
    private static final int Y = 800;

    // The delay (ms) corresponds to 50 updates a sec (hz)
    private static final int delay = 20;

    // The timer is started with a anonymous listener (see below) that executes the UI tick
    // each step between delays
    static Timer timer;
    static CarController carController;
    static GraphicsController graphicsController;
    static JFrame frame;

    static void main() {
        Dimension dimension = new Dimension(X, Y);
        carController = new CarController(dimension);

        // Add the cars and set their starting positions below each other by 100 units (compensated for image height of the previous car)
        carController.addCar(new Volvo240());
        carController.addCar(new Saab95());
        carController.addCar(new Scania());

        carController.workshops.add(new VolvoWorkshop(new Position(300, 300), new Rotation(0)));
        carController.workshops.getFirst().openDoors();

        frame = new JFrame("CarSim 2.0");
        frame.setPreferredSize(dimension);
        frame.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        graphicsController = new GraphicsController(dimension, carController);

        for (JComponent panel : graphicsController.getPanels()) {
            System.out.println("Adding panel: " + panel);
            frame.add(panel);
        }
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        timer = new Timer(delay, _ -> {
            carController.simulationTick();
            graphicsController.graphicsTick();
        });
        timer.setRepeats(true);
        timer.start();
    }
}
