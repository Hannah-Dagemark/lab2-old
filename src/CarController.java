import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/*
* This class represents the Controller part in the MVC pattern.
* It's responsibilities is to listen to the View and responds in a appropriate manner by
* modifying the model state and the updating the view.
 */

class CarController {
    // member fields:

    // A list of cars, modify if needed
    Dimension simualtionDimension;

    ArrayList<Car> cars = new ArrayList<>();
    ArrayList<Workshop> workshops = new ArrayList<>();
    ArrayList<Car> carsMarkedForRemoval = new ArrayList<>();

    //methods:

    CarController(Dimension simualtionDimension) {
        this.simualtionDimension = simualtionDimension;
    }

    /* Each step the TimerListener moves all the cars in the list and tells the
    * view to update its images. Change this method to your needs.
    * */
    void simulationTick() {
        if (!carsMarkedForRemoval.isEmpty()) {
            Car markedCar = carsMarkedForRemoval.getFirst();
            carsMarkedForRemoval.removeFirst();
            cars.remove(markedCar);
        }
        for (Car car : cars) {
            BufferedImage carImage = car.getImage();
            int[] carPosition = new int []{(int) Math.round(car.getPosition().getX()), (int) Math.round(car.getPosition().getY())};

            car.move();

            // Handle car hit detection (Workshops and Screen Borders)
            borderDetection(car);

            for (Workshop workshop : workshops) {
                boolean hasHit = workshop.detection(car);
                if (!hasHit) {
                    continue;
                }

                boolean loaded = workshop.load(car);
                if (!workshop.findItemInLoad(car) && !workshop.load(car)) {
                    System.out.println("Failed to load car " + car + " Into workshop " + this);
                    flipCar(car);
                } else {
                    // System.out.println("Loaded car " + car);
                    //carsMarkedForRemoval.add(car);
                    Position offset = new Position(workshop.getPosition().getX() + 20, workshop.getPosition().getY());
                    car.setPosition(offset);
                    car.stopEngine();
                }

            }
        }
    }

    SimulationState getSimulationState() {
        SimulationState state = new SimulationState();
        for (Car car : cars) {
            state.importNewModel(car, car.getImage());
        }
        for (Workshop workshop : workshops) {
            state.importNewModel(workshop, workshop.getImage());
        }
        return state;
    }

    void borderDetection(Car car) {
        Position carPos = car.getPosition();
        BufferedImage carImage = car.getImage();
        // Trust me this is deffo the #bestwaytodoit. When it hits a wall, it just swings the car around to face the other direction!
        if (carPos.getX() < 0 || carPos.getX() + carImage.getWidth() >= simualtionDimension.getWidth() || carPos.getY() < 0 || carPos.getY() + carImage.getHeight() >=  simualtionDimension.getHeight()) {
            flipCar(car);
        }
    }

    /*void workshopDetection(Car car, int[] carPos, int[] carDim) {
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
    }*/

    void switchLanes() {
        Position newPosition = cars.getLast().getPosition();
        for (Car car : cars) {
            Position positionStorage = car.getPosition();
            car.setPosition(new Position(positionStorage.getX(), newPosition.getY()));
            newPosition = positionStorage;
        }
    }

    private void flipCar(Car car) {
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
