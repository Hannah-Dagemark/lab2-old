import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    Dimension dimension;
    CarController carC;

    JPanel gasPanel = new JPanel();
    JSpinner gasSpinner = new JSpinner();
    int gasAmount = 0;
    JLabel gasLabel = new JLabel("Amount of gas");

    JButton gasButton = new JButton("Gas");
    JButton brakeButton = new JButton("Brake");
    JButton turboOnButton = new JButton("Saab Turbo on");
    JButton turboOffButton = new JButton("Saab Turbo off");
    JButton liftBedButton = new JButton("Scania Lift Bed");
    JButton lowerBedButton = new JButton("Scania Lower Bed");

    JButton startButton = new JButton("Start all cars");
    JButton stopButton = new JButton("Stop all cars");
    JButton switchButton = new JButton("Switch car lanes");

    public ControlPanel(Dimension dimension, CarController carC) {
        this.dimension = dimension;
        this.carC = carC;

        this.setLayout(new GridLayout(2,4));

        this.add(gasButton, 0);
        this.add(turboOnButton, 1);
        this.add(liftBedButton, 2);
        this.add(brakeButton, 3);
        this.add(turboOffButton, 4);
        this.add(lowerBedButton, 5);
        this.setPreferredSize(new Dimension(((int)dimension.getWidth()/2)-2, 200));
        this.setBackground(Color.CYAN);

        initComponents();
    }

    // Sets everything in place and fits everything
    private void initComponents() {
        // TODO

        SpinnerModel spinnerModel =
                new SpinnerNumberModel(0, //initial value
                        0, //min
                        100, //max
                        1);//step
        gasSpinner = new JSpinner(spinnerModel);
        gasSpinner.addChangeListener(e -> gasAmount = (int) ((JSpinner)e.getSource()).getValue());

        gasPanel.setLayout(new BorderLayout());
        gasPanel.add(gasLabel, BorderLayout.PAGE_START);
        gasPanel.add(gasSpinner, BorderLayout.PAGE_END);

        this.add(gasPanel);

        startButton.setBackground(Color.blue);
        startButton.setForeground(Color.black);
        startButton.setPreferredSize(new Dimension((int)dimension.getWidth()/5-15,200));
        this.add(startButton);


        stopButton.setBackground(Color.red);
        stopButton.setForeground(Color.black);
        stopButton.setPreferredSize(new Dimension((int)dimension.getWidth()/5-15,200));
        this.add(stopButton);

        switchButton.setBackground(Color.orange);
        switchButton.setForeground(Color.black);
        switchButton.setPreferredSize(new Dimension((int)dimension.getWidth()/5-15,200));
        this.add(switchButton);

        gasButton.addActionListener(_ -> carC.gas(gasAmount));

        brakeButton.addActionListener(_ -> carC.brake(gasAmount));

        turboOnButton.addActionListener(_ -> carC.turbo(true));

        turboOffButton.addActionListener(_ -> carC.turbo(false));

        liftBedButton.addActionListener(_ -> carC.liftBed());

        lowerBedButton.addActionListener(_ -> carC.lowerBed());

        startButton.addActionListener(_ -> carC.startCars());

        stopButton.addActionListener(_ -> carC.stopCars());

        switchButton.addActionListener(_ -> carC.switchLanes());
    }
}
