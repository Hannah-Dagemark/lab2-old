import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;

// This panel represents the animated part of the view with the car images.

public class DrawPanel extends JPanel{

    Map<Car, BufferedImage> CarImages = new HashMap<>();
    Map<Car, Point> CarPoints = new HashMap<>();

    BufferedImage volvoWorkshopImage;
    Point volvoWorkshopPoint = new Point(300,300);

    private final int[] dimensions;

    void moveit(Car car, int x, int y){
        if (CarPoints.containsKey(car)){
            CarPoints.put(car, new Point(x,y));
        }
    }

    // Initializes the panel and reads the images
    public DrawPanel(int x, int y, Car[] cars) {
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(x, y));
        this.dimensions = new int[] {x,y};
        this.setBackground(Color.green);
        // Print an error message in case file is not found with a try/catch block
        for (Car car : cars) {
            CarPoints.put(car, new Point(0,0));
            try {
                System.out.println("Attempting to find image:" + "pics/" + car.getModelName() + ".jpg");
                CarImages.put(car, ImageIO.read(DrawPanel.class.getResourceAsStream("pics/" + car.getModelName() + ".jpg")));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        try {
            volvoWorkshopImage = ImageIO.read(DrawPanel.class.getResourceAsStream("pics/VolvoBrand.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    // This method is called each time the panel updates/refreshes/repaints itself
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Map.Entry<Car, BufferedImage> carImage : CarImages.entrySet()) {
            Car key = carImage.getKey();
            BufferedImage value = carImage.getValue();
            if (CarPoints.containsKey(key)) {
                g.drawImage(value, CarPoints.get(key).x, CarPoints.get(key).y, null);
            } else {
                System.out.println("Couldn't find point for entry :" + carImage + " in :" + CarPoints.keySet());
            }
        }

        g.drawImage(volvoWorkshopImage, volvoWorkshopPoint.x, volvoWorkshopPoint.y, null);

        // REQUIRED for stuff to work with Linux or really anything non-windows that doesn't have automatic frame update pipeline flushing :]
        Toolkit.getDefaultToolkit().sync();
    }

    public int[] getImageDimensions(Car car) {
        if (CarImages.containsKey(car)) {
            BufferedImage carImage = CarImages.get(car);
            return new int[] {carImage.getWidth(), carImage.getHeight()};
        } else {
            System.out.println("\u001B[31m WARN: Couldn't get dimensions for image: " + car + " key not found in Car Images! Defaulting to safety [1,1]");
            return new int[] {1,1};
        }
    }

    public int[] getDimensions() {
        return dimensions;
    }
}
