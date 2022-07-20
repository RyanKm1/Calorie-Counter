package persistence;

import model.CalorieCounter;
import model.CalorieCounterList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonReader {

    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    // Method taken from JSONReader class in  
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public CalorieCounterList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCalorieCounterList(jsonObject);
    }

    // Method taken from JSONReader class in  
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads file as a string, and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // Method taken from JSONReader class in  
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: parses workroom from JSON object and returns it
    private CalorieCounterList parseCalorieCounterList(JSONObject jsonObject) {
        int goal = jsonObject.getInt("goal");
        CalorieCounterList calorieCounterList = new CalorieCounterList(goal);
        addCalorieCounterList(calorieCounterList, jsonObject);
        return calorieCounterList;
    }

    // Method taken from JSONReader class in  
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: calorieCounterList
    // EFFECTS: parses CalorieCounter from JSON object and adds them to CalorieCounterList
    private void addCalorieCounterList(CalorieCounterList calorieCounterList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("CalorieCounterList");
        for (Object json : jsonArray) {
            JSONObject nextCal = (JSONObject) json;
            addCalorieCounter(calorieCounterList, nextCal);
        }
    }

    // Method taken from JSONReader class in  
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // MODIFIES: calorieCounterList
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addCalorieCounter(CalorieCounterList calorieCounterList, JSONObject jsonObject) {
        String meal = jsonObject.getString("meal");
        int calories = jsonObject.getInt("calories");
        CalorieCounter c = new CalorieCounter(meal, calories);
        calorieCounterList.addCalorieCounter(c);
    }
}
