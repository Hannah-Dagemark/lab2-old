import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    private final int X;

    private int gasAmount = 0;

    private final int[] dimensions;

    private final CarController carController;

    public ControlPanel(int x, int y, CarController carController) {
        this.dimensions = new int[] {x,y};
        this.carController = carController;
        X = x;
        initComponents();
    }

    private final JLabel gasLabel = new JLabel("Amount of gas");

    private final JButton gasButton = new JButton("Gas");
    private final JButton brakeButton = new JButton("Brake");
    private final JButton turboOnButton = new JButton("Saab Turbo on");
    private final JButton turboOffButton = new JButton("Saab Turbo off");
    private final JButton liftBedButton = new JButton("Scania Lift Bed");
    private final JButton lowerBedButton = new JButton("Scania Lower Bed");

    private final JButton startButton = new JButton("Start all cars");
    private final JButton stopButton = new JButton("Stop all cars");
    private final JButton switchButton = new JButton("Switch car lanes");

    private void initComponents() {
        this.setPreferredSize(new Dimension(dimensions[0],dimensions[1]));
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        JPanel gasPanel = new JPanel();
        JPanel buttonPanel = new JPanel(new GridLayout(2,3));

        SpinnerModel spinnerModel =
                new SpinnerNumberModel(0, //initial value
                        0, //min
                        100, //max
                        1);//step
        JSpinner gasSpinner = new JSpinner(spinnerModel);
        gasSpinner.addChangeListener(e -> gasAmount = (int) ((JSpinner)e.getSource()).getValue());

        gasPanel.setPreferredSize(new Dimension(X/6, 50));
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
        buttonPanel.setPreferredSize(new Dimension((X/3), 200));
        buttonPanel.setBackground(Color.CYAN);

        this.add(buttonPanel);

        startButton.setBackground(Color.blue);
        startButton.setForeground(Color.black);
        startButton.setPreferredSize(new Dimension(X/6,200));
        this.add(startButton);

        stopButton.setBackground(Color.red);
        stopButton.setForeground(Color.black);
        stopButton.setPreferredSize(new Dimension(X/6,200));
        this.add(stopButton);

        switchButton.setBackground(Color.orange);
        switchButton.setForeground(Color.black);
        switchButton.setPreferredSize(new Dimension(X/6,200));
        this.add(switchButton);

        gasButton.addActionListener(_ -> carController.gas(gasAmount));

        brakeButton.addActionListener(_ -> carController.brake(gasAmount));

        turboOnButton.addActionListener(_ -> carController.turbo(true));

        turboOffButton.addActionListener(_ -> carController.turbo(false));

        liftBedButton.addActionListener(_ -> carController.liftBed());

        lowerBedButton.addActionListener(_ -> carController.lowerBed());

        startButton.addActionListener(_ -> carController.startCars());

        stopButton.addActionListener(_ -> carController.stopCars());

        switchButton.addActionListener(_ -> carController.switchLanes());
    }
}
