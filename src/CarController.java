import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/*
* This class represents the Controller part in the MVC pattern.
* It's responsibilities is to listen to the View and responds in a appropriate manner by
* modifying the model state and the updating the view.
 */

public class CarController {
    // member fields:

    // The delay (ms) corresponds to 20 updates a sec (hz)
    private final int delay = 50;
    // The timer is started with a listener (see below) that executes the statements
    // each step between delays.
    private Timer timer = new Timer(delay, new TimerListener());

    // The frame that represents this instance View of the MVC pattern
    CarView frame;
    // A list of cars, modify if needed
    ArrayList<Car> cars = new ArrayList<>();
    ArrayList<Workshop> workshops = new ArrayList<>();
    ArrayList<Car> carsMarkedForRemoval = new ArrayList<>();

    //methods:

    static void main(String[] args) {
        // Instance of this class
        CarController cc = new CarController();

        // Add the cars and set their starting positions below each other by 100 units (compensated for image height of the previous car)
        cc.cars.add(new Volvo240());
        cc.cars.add(new Saab95());
        cc.cars.getLast().setPosition(new Position(0,160));
        cc.cars.add(new Scania());
        cc.cars.getLast().setPosition(new Position(0,320));

        cc.workshops.add(new VolvoWorkshop(new Position(300, 300), new Rotation(0)));
        cc.workshops.getFirst().openDoors();

        // Start a new view and send a reference of self
        cc.frame = new CarView("CarSim 1.0", cc);

        // Start the timer
        cc.timer.start();
    }

    /* Each step the TimerListener moves all the cars in the list and tells the
    * view to update its images. Change this method to your needs.
    * */
    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!carsMarkedForRemoval.isEmpty()) {
                Car markedCar = carsMarkedForRemoval.getFirst();
                carsMarkedForRemoval.removeFirst();
                cars.remove(markedCar);
            }
            for (Car car : cars) {
                int[] carImageDimensions = frame.drawPanel.getImageDimensions(car);
                int[] carPosition = new int []{(int) Math.round(car.getPosition().getX()), (int) Math.round(car.getPosition().getY())};

                car.move();
                frame.drawPanel.moveit(car, carPosition[0], carPosition[1]);

                // Handle car hit detection (Workshops and Screen Borders)
                borderDetection(car, carPosition, carImageDimensions);
                workshopDetection(car, carPosition, carImageDimensions);

                // repaint() calls the paintComponent method of the panel
                frame.drawPanel.repaint();
            }
        }
    }

    void borderDetection(Car car, int[] carPos, int[] carDim) {
        int[] dimensions = frame.drawPanel.getDimensions();
        // Trust me this is deffo the #bestwaytodoit. When it hits a wall, it just swings the car around to face the other direction!
        if (carPos[0] < 0 || carPos[0] + carDim[0] >= dimensions[0] || carPos[1] < 0 || carPos[1] + carDim[1] >= dimensions[1]) {
            flipCar(car);
        }
    }

    void workshopDetection(Car car, int[] carPos, int[] carDim) {
        int[] workPos = new int[] {frame.drawPanel.volvoWorkshopPoint.x, frame.drawPanel.volvoWorkshopPoint.y};
        int[] workDim = new int[] {frame.drawPanel.volvoWorkshopImage.getWidth(), frame.drawPanel.volvoWorkshopImage.getHeight()};

        // Calculating if the car and workshop overlap on the x-axis. Separating the axis-es for code cleanliness :]
        if (carPos[0] + carDim[0] > workPos[0] && carPos[0] < workPos[0] + workDim[0] ) {
            // Calculating if the car and workshop overlap on the y-axis
            if (carPos[1] + carDim[1] > workPos[1] && carPos[1] < workPos[1] + workDim[1]) {
                boolean loaded = workshops.getFirst().load(car);
                if (!loaded) {
                    System.out.println("Failed to load car " + car + " Into workshop " + workshops.getFirst());
                    flipCar(car);
                } else {
                    System.out.println("Loaded car " + car);
                    carsMarkedForRemoval.add(car);
                    frame.drawPanel.moveit(car, workPos[0]+20, workPos[1]);
                }
            }
        }
    }

    void switchLanes() {
        Position newPosition = cars.getLast().getPosition();
        for (Car car : cars) {
            Position positionStorage = car.getPosition();
            car.setPosition(new Position(positionStorage.getX(), newPosition.getY()));
            newPosition = positionStorage;
        }
    }

    void flipCar(Car car) {
        System.out.println("Flipping car: " + car);
        Rotation initialRotation = car.getRotation();
        car.setRotation(new Rotation(initialRotation.getRotation() + Math.PI));
        for (int i = 0; i<3; i++) {
            car.move();
        }
    }

    void startCars() {
        for (Car car : cars) {
            car.startEngine();
        }
    }

    void stopCars() {
        for (Car car : cars) {
            car.stopEngine();
        }
    }

    // Calls the gas method for each car once
    void gas(int amount) {
        double gas = ((double) amount) / 100;
        for (Car car : cars) {
            if (car.getCurrentSpeed() > 0) { car.gas(gas); }
        }
    }

    void brake(int amount) {
        double brake = ((double) amount) / 100;
        for (Car car : cars) {
            car.brake(brake);
        }
    }

    void turbo(boolean state) {
        for (Car car : cars) {
            if (car instanceof Saab95) {
                if (state) {
                    ((Saab95) car).setTurboOn();
                } else {
                    ((Saab95) car).setTurboOff();
                }
            }
        }
    }

    void liftBed() {
        for (Car car : cars) {
            if (car instanceof Scania) {
                ((Scania) car).dumpFlatBed();
                System.out.println("Dumping flat bed, state: " + ((Scania) car).getFlatBedAngle());
            }
        }
    }

    void lowerBed() {
        for (Car car : cars) {
            if (car instanceof Scania) {
                ((Scania) car).levelFlatBed();
                System.out.println("Lowering flat bed, state: " + ((Scania) car).getFlatBedAngle());
            }
        }
    }
}
