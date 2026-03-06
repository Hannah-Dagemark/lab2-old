import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;

public class Main {
    private static final int X = 1200;
    private static final int Y = 800;

    // The delay (ms) corresponds to 20 updates a sec (hz)
    private static final int delay = 50;

    // The timer is started with a anonymous listener (see below) that executes the UI tick
    // each step between delays
    static Timer timer;
    static CarController cc;
    static CarFrame frame;

    static void main(String[] args) {
        Dimension dimension = new Dimension(X, Y);
        cc = new CarController(dimension);

        WorkshopFactory workshopFactory = new WorkshopFactory();

        // Add the cars and set their starting positions below each other by 100 units (compensated for image height of the previous car)

        Workshop staticTestWorkshop = (Workshop) workshopFactory.createPositionable(workshopFactory.availableTypes()[0]);
        staticTestWorkshop.openDoors();
        staticTestWorkshop.setPosition(new Position(300,300));

        cc.workshops.add(staticTestWorkshop);

        frame = new CarFrame("CarSim 2.0", dimension, cc);

        timer = new Timer(delay, _ -> {
            cc.simulationTick();
            frame.updateFrame();
        });
        timer.setRepeats(true);
        timer.start();
    }
}
