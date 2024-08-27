import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PizzaShopGUI extends JFrame implements ActionListener {
    private JComboBox<String> pizzaOptions;
    private JComboBox<String> drinkOptions;
    private JComboBox<String> breadOptions;
    private JButton orderButton;

    private double originalTotalCost = 0.0; // Store the original total cost
    private double totalCost = 0.0;

    private String[] pizzaMenu = {"none (₱0)","Margherita (₱200)", "Pepperoni (₱250)", "Vegetarian (₱220)", "Hawaiian (₱230)", "BBQ Chicken (₱260)", "Supreme (₱280)", "Four Cheese (₱240)", "Meat Lovers (₱270)", "Seafood Delight (₱290)", "Mushroom and Olive (₱220)"};
    private String[] drinkMenu = {"none (₱0)","Coke (₱20)", "Sprite (₱25)", "Water (₱15)", "Iced Tea (₱30)","Lemonade (₱35)", "Orange Juice (₱40)", "Mango Shake (₱45)", "Pineapple Juice (₱40)", "Milkshake (₱50)", "Coffee (₱30)"};
    private String[] breadMenu = {"none (₱0)","Garlic Bread (₱40)", "Cheesy Bread (₱50)", "Plain Bread (₱30)", "Italian Bread (₱45)", "Herb Bread (₱40)", "Whole Wheat Bread (₱35)", "Baguette (₱50)", "Focaccia (₱60)", "Ciabatta (₱55)", "Pita Bread (₱45)"};

    private JSpinner pizzaQuantitySpinner;
    private JSpinner drinkQuantitySpinner;
    private JSpinner breadQuantitySpinner;

    public PizzaShopGUI() {
        setTitle("Pizza Shop");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Pizza:"), gbc);

        gbc.gridx = 1;
        pizzaOptions = new JComboBox<>(pizzaMenu);
        pizzaOptions.addActionListener(this);
        panel.add(pizzaOptions, gbc);

        gbc.gridx = 2;
        pizzaQuantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        panel.add(pizzaQuantitySpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Drink:"), gbc);

        gbc.gridx = 1;
        drinkOptions = new JComboBox<>(drinkMenu);
        drinkOptions.addActionListener(this);
        panel.add(drinkOptions, gbc);

        gbc.gridx = 2;
        drinkQuantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        panel.add(drinkQuantitySpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Bread:"), gbc);

        gbc.gridx = 1;
        breadOptions = new JComboBox<>(breadMenu);
        breadOptions.addActionListener(this);
        panel.add(breadOptions, gbc);

        gbc.gridx = 2;
        breadQuantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        panel.add(breadQuantitySpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        orderButton = new JButton("Place Order");
        orderButton.addActionListener(this);
        orderButton.setEnabled(false); // Initially disable the order button
        panel.add(orderButton, gbc);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pizzaOptions || e.getSource() == drinkOptions || e.getSource() == breadOptions) {
            enableOrderButton();
        } else if (e.getSource() == orderButton) {
            placeOrder();
        }
    }

    private void enableOrderButton() {
        boolean enable = !(pizzaOptions.getSelectedItem().toString().equals("none (₱0)") &&
                drinkOptions.getSelectedItem().toString().equals("none (₱0)") &&
                breadOptions.getSelectedItem().toString().equals("none (₱0)"));
        orderButton.setEnabled(enable);
    }

    private void placeOrder() {
        String pizzaSelection = pizzaOptions.getSelectedItem().toString();
        int pizzaQuantity = (int) pizzaQuantitySpinner.getValue();

        String drinkSelection = drinkOptions.getSelectedItem().toString();
        int drinkQuantity = (int) drinkQuantitySpinner.getValue();

        String breadSelection = breadOptions.getSelectedItem().toString();
        int breadQuantity = (int) breadQuantitySpinner.getValue();

        double pizzaPrice = getPrice(pizzaSelection);
        double drinkPrice = getPrice(drinkSelection);
        double breadPrice = getPrice(breadSelection);

        // Calculate total cost without considering loyalty reward
        originalTotalCost = (pizzaPrice * pizzaQuantity) + (drinkPrice * drinkQuantity) + (breadPrice * breadQuantity);
        totalCost = originalTotalCost;

        // Apply Combo Deal discount
        totalCost -= comboDealDiscount(pizzaSelection, drinkSelection, breadSelection);

        // Payment
        String payment = JOptionPane.showInputDialog(this, "Enter payment amount (₱" + totalCost + "):");
        double paymentAmount = Double.parseDouble(payment);

        if (paymentAmount < totalCost) {
            JOptionPane.showMessageDialog(this, "Insufficient payment. Please try again.");
        } else {
            double change = paymentAmount - totalCost;
            String summary = "Order Summary:\n" +
                    "Pizza: " + pizzaSelection + " x" + pizzaQuantity + "\n" +
                    "Drink: " + drinkSelection + " x" + drinkQuantity + "\n" +
                    "Bread: " + breadSelection + " x" + breadQuantity + "\n" +
                    "Original Total Price: ₱" + originalTotalCost + "\n" +
                    (loyaltyReward(pizzaQuantity) > 0.0 ? "Loyalty Reward: Free Coke\n" : "") + // Include loyalty reward in summary if applicable
                    "Final Price: ₱" + totalCost + "\n" +
                    "Payment: ₱" + paymentAmount + "\n" +
                    "Change: ₱" + change;
            JOptionPane.showMessageDialog(this, summary);
            resetInterface();
        }
    }

    // Combo Deal Promotion: ₱30 off when ordering a pizza, a drink, and a bread
    private double comboDealDiscount(String pizzaSelection, String drinkSelection, String breadSelection) {
        if (!pizzaSelection.equals("none (₱0)") && !drinkSelection.equals("none (₱0)") && !breadSelection.equals("none (₱0)")) {
            return 30.0; // Change the discount amount here
        }
        return 0.0;
    }

    // Loyalty Reward Promotion: Free Coke for every 5 pizzas
    private double loyaltyReward(int pizzaQuantity) {
        int cokeCount = pizzaQuantity / 5; // Calculate how many Cokes should be rewarded
        return getPrice("Coke (₱20)") * cokeCount; // Return the total price of the rewarded Cokes
    }

    private double getPrice(String item) {
        String[] parts = item.split("\\₱");
        return Double.parseDouble(parts[1].substring(0, parts[1].length() - 1));
    }

    private void resetInterface() {
        pizzaOptions.setSelectedIndex(0);
        drinkOptions.setSelectedIndex(0);
        breadOptions.setSelectedIndex(0);
        pizzaQuantitySpinner.setValue(1);
        drinkQuantitySpinner.setValue(1);
        breadQuantitySpinner.setValue(1);
        orderButton.setEnabled(false);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new PizzaShopGUI());
    }
}
