package ui;

import model.CalorieCounter;

import javax.swing.*;
import java.awt.*;

// renders the calorie counter list
public class CalorieCounterRender extends DefaultListCellRenderer {

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                                                  boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
//        Locale l = (Locale) value;
//        setText(l.getDisplayName());
        CalorieCounter meal = (CalorieCounter) value;
        String name = meal.getMeal();
        int cals = meal.getCalories();
        String mealText = "<html>Name: " + name + "<br/>Calories: " + cals;
        setText(mealText);
        return this;
    }
}


