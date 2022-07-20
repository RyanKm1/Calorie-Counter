package ui;

import javax.swing.*;
import java.awt.*;

// renders add meal window, and prompts use to add meals to list
public class AddMealWindow {

    private JFrame frame;
    private JLabel mealLabel;
    private JLabel calLabel;

    private JTextField mealTextLabel;
    private JTextField calTextLabel;

    private JPanel panel;


    // Constructor
    public AddMealWindow() {
        initWindow();

    }

    public void initWindow() {
        frame = new JFrame("Add Meal");
        panel = new JPanel();

        mealLabel = new JLabel("Enter Meal Name");
        calLabel = new JLabel("Enter Calories");

        mealTextLabel = new JTextField();
        mealTextLabel.setMaximumSize(new Dimension(200, 30));

        calTextLabel = new JTextField();
        calTextLabel.setMaximumSize(new Dimension(200, 30));

        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS)); //tune
        panel.setPreferredSize(new Dimension(100, 100));

        panel.add(mealLabel);
        panel.add(mealTextLabel);

        panel.add(calLabel);
        panel.add(calTextLabel);



    }

    public JPanel returnJPanel() {
        return panel;
    }

    public String getMealTextLabel() {
        return mealTextLabel.getText();
    }

    public int getCalTextLabel() {
        return Integer.parseInt(calTextLabel.getText());
    }





}
