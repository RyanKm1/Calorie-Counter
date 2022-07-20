package ui;

import model.CalorieCounter;
import model.CalorieCounterList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// console UI for program
public class CalApp {
    private static final String JSON_STORE = "./data/workroom.json";
    Scanner sc = new Scanner(System.in);
    private CalorieCounterList caList;
    private int goal;
    private int calLeft;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // runs runCalApp, which keeps the program running
    public CalApp() throws FileNotFoundException {
        caList = new CalorieCounterList(goal);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        caList.setGoal(goal);
        runCalApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runCalApp() {
        boolean keepGoing = true;
        String command = null;
        init();
        while (keepGoing) {
            menuChoice();
            command = sc.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                mainMenu(command);
            }
        }
        System.out.println("\nGoodbye!");
    }

    //MODIFIES:this.goal, this.caList
    //EFFECTS: outputs daily calorie goal to user
    private void init() {
        String command = null;
        loadChoice();
        command = sc.next();
        command = command.toLowerCase();
        loadMenu(command);
    }

    private void calorieGoal() {
        System.out.println("Please add daily Calorie Goal");
        //this.goal = goal;
        this.goal = sc.nextInt();
        //sc.nextLine();
        //this.caList = new CalorieCounterList(goal);
        caList.setGoal(goal);
        System.out.println("Your daily Calorie goal is " + goal);
    }

    //EFFECTS: displays menu to user
    private void menuChoice() {
        System.out.println("-------------------------------------------------");
        System.out.println("Press a to add meals");
        System.out.println("Press d to delete meals");
        System.out.println("Press v to calculate the average calories per meal");
        System.out.println("Press c to check how many calories are left");
        System.out.println("Press s to save file");
        System.out.println("Press q to quit");

        //System.out.println("Press l to load previous file");
    }

    //EFFECTS: displays menu to user. User chooses whether to add, delete, check average, or check calories left
    private void mainMenu(String command) {
        sc.nextLine();
        //String str = sc.nextLine();
        if (command.equals("a")) {
            addMeals();
        } else if (command.equals("d")) {
            deleteMeals();
        } else if (command.equals("v")) {
            averageCal();
        } else if (command.equals("c")) {
            checkCalLeft();
        } else if (command.equals("s")) {
            saveCalCount();
//        } else if (command.equals("p")) {
        } else {
            System.out.println("Please choose one of the Selections");
        }
    }

    private void loadChoice() {
        System.out.println("Load previous file (Y/N)?");
    }

    private void loadMenu(String command) {
        //sc.nextLine();
        //String str = sc.nextLine();
        if (command.equals("y")) {
            loadCalCount();
            mainMenu(command);
        } else if (command.equals("n")) {
            calorieGoal();
            mainMenu(command);
        } else {
            System.out.println("Please choose one of the Selections");
            //loadChoice();

        }
    }

    //EFFECTS: outputs how much of the goal is left
    private void checkCalLeft() {
        System.out.println("-------------------------------------------------");
        System.out.println("You have " + caList.getGoal() + " calories left!");
    }

    //MODIFIES: this.goal, meal and cal
    //EFFECTS: adds new CalorieCounter to list and outputs list of meals
    private void addMeals() {
        System.out.println("Add Meal Name");
        String meal = sc.nextLine();
        System.out.println("Add Meal Calories");
        int cal = sc.nextInt();
        ///////////////////////////
        caList.setGoal(caList.getGoal() - cal);
        ///////////////////////////
        CalorieCounter c = new CalorieCounter(meal, cal);
        caList.addCalorieCounter(c);
        caList.listOfMealsString(meal, cal, goal);
        System.out.println(caList.listOfMealsString(meal,cal,goal));

    }

    //REQUIRES:
    //MODIFIES: this.goal, calories, and meal
    //EFFECTS: removes CalorieCounter from the list
    private void deleteMeals() {
        System.out.println("Please print the meal name");
        String meal = sc.nextLine();
        System.out.println("Please print the meal calories");
        int calories = sc.nextInt();
        if (caList.removeCalorieCounter(meal, calories)) {
            caList.setGoal(caList.getGoal() + calories);
            System.out.println("Meal has been deleted.");
        } else {
            System.out.println("Meal has not been deleted. Please try again.");
        }

    }

    //EFFECTS: displays average amount of calories to user
    private void averageCal() {    ///need for loop to add all cal and divide by number of items
        System.out.println("-------------------------------------------------");
        System.out.println("Average amount of calories per meal: " + caList.averageCalorieOfMeals());
    }

    // Method taken from JSONReader class in  
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: saves the CalorieCounterList to file
    private void saveCalCount() {
        try {
            jsonWriter.open();
            jsonWriter.write(caList);
            jsonWriter.close();
            System.out.println("Saved "  + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // Method taken from JSONReader class in  
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: loads CalorieCounterList from file
    private void loadCalCount() {
        try {
            caList = jsonReader.read();
            System.out.println("Loaded " + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
