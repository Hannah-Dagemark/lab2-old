import java.awt.*;
import javax.swing.*;

public class Main {
    private static final int X = 1200;
    private static final int Y = 800;

    // The delay (ms) corresponds to 50 updates a sec (hz)
    private static final int delay = 20;

    // The timer is started with an anonymous listener (see below) that executes the UI tick
    // each step between delays
    static Timer timer;
    static CarController carController;
    static GraphicsController graphicsController;
    static JFrame frame;

    static void main() {
        Dimension dimension = new Dimension(X, Y);
        carController = new CarController(dimension);

        WorkshopFactory workshopFactory = new WorkshopFactory();

        Workshop staticTestWorkshop = (Workshop) workshopFactory.createPositionable(workshopFactory.availableTypes()[0]);
        staticTestWorkshop.openDoors();
        staticTestWorkshop.setPosition(new Position(300,300));
        carController.addWorkshop(staticTestWorkshop);

        frame = new JFrame("CarSim 2.0");
        frame.setPreferredSize(dimension);
        frame.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        graphicsController = new GraphicsController(dimension, carController);

        for (JComponent panel : graphicsController.getPanels()) {
            frame.add(panel);
        }
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        timer = new Timer(delay, _ -> {
            carController.updateSimUI();
            graphicsController.updateSimUI();
        });
        timer.setRepeats(true);
        timer.start();
    }
}
