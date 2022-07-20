package model;

import org.json.JSONObject;
import persistence.Writable;

public class CalorieCounter implements Writable {

    //private int totalCalories;     //total amount of calories left
    private int goal;              // calorie daily goal
    private String meal;           //meal name
    private int calLeft;           // calories left
    private int calories;          // calories of meal

    // constructor
    public CalorieCounter(String meal, int calories) {
        this.meal = meal;
        this.calories = calories;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    // Method taken from
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES:
    // EFFECTS: creates new JSONobject and puts a meal and the amunt of calories
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("meal", meal);
        json.put("calories", calories);
        return json;
    }

}

