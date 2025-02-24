import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class VacationCalc extends JFrame implements ActionListener {
    // Form components
    private JComboBox<String> stateComboBox;
    private JTextField carRentalField, houseRentalField, nightsField, peopleField;
    private JButton calculateButton;

    public VacationCalc() {
        setTitle("Vacation Calculator");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create panel for form elements
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // State selection
        panel.add(new JLabel("Select State:"));
        stateComboBox = new JComboBox<>(new String[] {"Select a state", "AL", "AK", "AZ", "AR", "CA"});
        panel.add(stateComboBox);

        // Car Rental Cost per Day
        panel.add(new JLabel("Car Rental ($/day):"));
        carRentalField = new JTextField();
        panel.add(carRentalField);

        // House Rental Cost per Night
        panel.add(new JLabel("House Rental ($/night):"));
        houseRentalField = new JTextField();
        panel.add(houseRentalField);

        // Number of Nights
        panel.add(new JLabel("Number of Nights:"));
        nightsField = new JTextField();
        panel.add(nightsField);

        // Number of People
        panel.add(new JLabel("Number of People:"));
        peopleField = new JTextField();
        panel.add(peopleField);

        // Calculate button spans two columns
        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(this);
        panel.add(calculateButton);
        panel.add(new JLabel()); // filler

        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Validate and parse input values
        try {
            String state = (String) stateComboBox.getSelectedItem();
            if (state == null || state.equals("Select a state")) {
                JOptionPane.showMessageDialog(this, "Please select a valid state.");
                return;
            }
            double carRental = Double.parseDouble(carRentalField.getText());
            double houseRental = Double.parseDouble(houseRentalField.getText());
            int nights = Integer.parseInt(nightsField.getText());
            int people = Integer.parseInt(peopleField.getText());
            
            if (nights <= 0 || people <= 0) {
                JOptionPane.showMessageDialog(this, "Number of nights and people must be positive.");
                return;
            }
            
            // Determine state tax rate (example: CA has a different rate)
            double stateTaxRate = 0.05;  // default 5%
            if ("CA".equals(state)) {
                stateTaxRate = 0.075;   // 7.5% for California
            }
            
            // Calculate costs
            double carTotal = carRental * nights;
            double houseTotal = houseRental * nights;
            double subtotal = carTotal + houseTotal;
            double tax = subtotal * stateTaxRate;
            double total = subtotal + tax;
            double perPerson = total / people;
            
            // Build result message
            String message = String.format(
                "State: %s%n" +
                "Car Rental Total: $%.2f%n" +
                "House Rental Total: $%.2f%n" +
                "Subtotal: $%.2f%n" +
                "Tax: $%.2f%n" +
                "Total: $%.2f%n" +
                "Cost Per Person: $%.2f",
                state, carTotal, houseTotal, subtotal, tax, total, perPerson
            );
            
            // Display the result in a dialog
            JOptionPane.showMessageDialog(this, message, "Calculation Result", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Run the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            VacationCalc app = new VacationCalc();
            app.setVisible(true);
        });
    }
}
