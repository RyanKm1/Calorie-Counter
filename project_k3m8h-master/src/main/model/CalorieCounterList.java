package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// puts CalorieCounter into a List, and performs methods as a list
public class CalorieCounterList implements Writable {

    private List<CalorieCounter> totalMeals;
    private int goal;

    //Constructor
    public CalorieCounterList(int goal) {
        totalMeals = new ArrayList<>();
        this.goal = goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getGoal() {
        return this.goal;
    }

    //MODIFIES: this
    //EFFECTS: adds CalorieCounter item to list
    public void addCalorieCounter(CalorieCounter c) {
        //CalorieCounter c = new CalorieCounter(meal, calories, goal);
        totalMeals.add(c);
        EventLog.getInstance().logEvent(
                new Event(c.getMeal() + " with " + c.getCalories() + " calories has been added"));
    }


    //MODIFIES: this
    //EFFECTS: removes CalorieCounter item from list
    public boolean removeCalorieCounter(String meal, int calories) {
        for (CalorieCounter l : totalMeals) {
            if (meal.equals(l.getMeal()) && (calories == (l.getCalories()))) {
                totalMeals.remove(l); // removes from list
                EventLog.getInstance().logEvent(
                        new Event(l.getMeal() + " with " + l.getCalories() +  " calories has been removed"));
                return true;
            }
        }
        return false;

    }
//
//    public boolean removeCalorieCounterV(CalorieCounter c) {
//        totalMeals.remove(c);
//
//        for (CalorieCounter l: totalMeals) {
//            if ()
//        }
//    }



    //EFFECTS: returns the average amount of calories of the meals eaten
    public int averageCalorieOfMeals() {
        int num = 0;
        int sum = 0;
        for (CalorieCounter l : totalMeals) {
            num += 1;
            sum = l.getCalories() + sum;
            int average = sum / num;
            if (num == totalMeals.toArray().length) {
                //System.out.println("Average calories per meal = " + average);
                return average;
            }
        }
        return 0;
    }

    //returns totalMeals arrayList
    public List<CalorieCounter> getCalorieCounterList() {
        return totalMeals;
    }

    //EFFECTS: returns the meals eaten and their respective calories
    public List<String> listOfMealsString(String meal, int calories, int goal) {
        CalorieCounter c = new CalorieCounter(meal, calories);
        List<String> mealList = new ArrayList<>();

        for (CalorieCounter l : totalMeals) {
            meal = l.getMeal();
            String cal = Integer.toString(l.getCalories());
            String space = " ";
            String label1 = "MEAL: ";
            String label2 = "CALORIES: ";
            mealList.add(label1 + meal + space + label2 + cal);
            //mealList.add(meal + cal);
        }
        return mealList;
    }

//    public List<String> printList(CalorieCounterList c) {
//        for (CalorieCounter l: c.getCalorieCounterList()) {
//            String meal = l.getMeal();
//            int calories = l.getCalories();
//            int goal = c.getGoal();
//            String space = " ";
//            String label1 = "MEAL: ";
//            String label2 = "CALORIES: ";
//
//        }
//    }

    // Method taken from
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // REQUIRES:
    // MODIFIES:
    // EFFECTS: creates new JSONObject, and puts the Calorie COunter list and the goal.
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("CalorieCounterList", listToJson());
        json.put("goal", goal);
        return json;
    }

    // Method taken from
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray listToJson() {
        JSONArray jsonArray = new JSONArray();
        for (CalorieCounter t : totalMeals) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }


}
