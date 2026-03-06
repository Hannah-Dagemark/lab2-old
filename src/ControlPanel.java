import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel implements updateComposite {
    private final Dimension dimension;
    private final CarController carController;
    private int gasAmount = 0;
    private String selectedModel;

    private final JLabel gasLabel = new JLabel("Amount of gas");
    private final JLabel carLabel = new JLabel("Select to Add or Remove");

    private final JButton gasButton = new JButton("Gas");
    private final JButton brakeButton = new JButton("Brake");
    private final JButton turboOnButton = new JButton("Saab Turbo on");
    private final JButton turboOffButton = new JButton("Saab Turbo off");
    private final JButton liftBedButton = new JButton("Scania Lift Bed");
    private final JButton lowerBedButton = new JButton("Scania Lower Bed");

    private final JButton startButton = new JButton("Start all cars");
    private final JButton stopButton = new JButton("Stop all cars");
    private final JButton switchButton = new JButton("Switch car lanes");
    private final JButton addButton = new JButton("Add new car");
    private final JButton removeButton = new JButton("Remove car");

    public ControlPanel(Dimension dimension, CarController carController) {
        this.dimension = dimension;
        this.carController = carController;

        this.setPreferredSize(dimension);
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        initComponents();
    }

    // Sets everything in place and fits everything
    private void initComponents() {

        JPanel gasPanel = new JPanel();
        JPanel carSelectPanel = new JPanel();
        JPanel buttonPanel = new JPanel(new GridLayout(2,3));

        SpinnerModel carSelectSpinnerModel = new SpinnerListModel(carController.getCarModels());
        JSpinner carSpinner = new JSpinner(carSelectSpinnerModel);
        carSpinner.addChangeListener(e -> selectedModel = (String) ((JSpinner)e.getSource()).getValue());

        carSelectPanel.setPreferredSize(new Dimension((int)dimension.getWidth()/10, 50));
        carSelectPanel.setLayout(new BorderLayout());
        carSelectPanel.add(carLabel, BorderLayout.PAGE_START);
        carSelectPanel.add(carSpinner, BorderLayout.PAGE_END);

        SpinnerModel gasSpinnerModel =
                new SpinnerNumberModel(0, //initial value
                        0, //min
                        100, //max
                        1);//step
        JSpinner gasSpinner = new JSpinner(gasSpinnerModel);
        gasSpinner.addChangeListener(e -> gasAmount = (int) ((JSpinner)e.getSource()).getValue());

        gasPanel.setPreferredSize(new Dimension((int)dimension.getWidth()/10, 50));
        gasPanel.setLayout(new BorderLayout());
        gasPanel.add(gasLabel, BorderLayout.PAGE_START);
        gasPanel.add(gasSpinner, BorderLayout.PAGE_END);

        this.add(gasPanel);

        buttonPanel.add(gasButton, 0);
        buttonPanel.add(turboOnButton, 1);
        buttonPanel.add(liftBedButton, 2);
        buttonPanel.add(brakeButton, 3);
        buttonPanel.add(turboOffButton, 4);
        buttonPanel.add(lowerBedButton, 5);
        buttonPanel.setPreferredSize(new Dimension(((int)dimension.getWidth()*3/10), 200));
        buttonPanel.setBackground(Color.CYAN);

        this.add(buttonPanel);

        startButton.setBackground(Color.blue);
        startButton.setForeground(Color.black);
        startButton.setPreferredSize(new Dimension((int)dimension.getWidth()/10,200));
        this.add(startButton);


        stopButton.setBackground(Color.red);
        stopButton.setForeground(Color.black);
        stopButton.setPreferredSize(new Dimension((int)dimension.getWidth()/10,200));
        this.add(stopButton);

        switchButton.setBackground(Color.orange);
        switchButton.setForeground(Color.black);
        switchButton.setPreferredSize(new Dimension((int)dimension.getWidth()/10,200));
        this.add(switchButton);

        this.add(carSelectPanel);

        addButton.setBackground(Color.green);
        addButton.setForeground(Color.black);
        addButton.setPreferredSize(new Dimension((int)dimension.getWidth()/10,200));
        this.add(addButton);

        removeButton.setBackground(Color.red);
        removeButton.setForeground(Color.black);
        removeButton.setPreferredSize(new Dimension((int)dimension.getWidth()/10,200));
        this.add(removeButton);

        gasButton.addActionListener(_ -> carController.gas(gasAmount));

        brakeButton.addActionListener(_ -> carController.brake(gasAmount));

        turboOnButton.addActionListener(_ -> carController.turbo(true));

        turboOffButton.addActionListener(_ -> carController.turbo(false));

        liftBedButton.addActionListener(_ -> carController.liftBed());

        lowerBedButton.addActionListener(_ -> carController.lowerBed());

        startButton.addActionListener(_ -> carController.startCars());

        stopButton.addActionListener(_ -> carController.stopCars());

        switchButton.addActionListener(_ -> carController.switchLanes());

        addButton.addActionListener(_ -> carController.addCar(selectedModel));

        removeButton.addActionListener(_ -> carController.removeCar());
    }
}
