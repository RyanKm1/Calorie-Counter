package persistence;


import model.CalorieCounter;
import model.CalorieCounterList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    // Method taken from JSONReaderTest class in  
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // reader with no file
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            CalorieCounterList wr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    // Method taken from JSONReaderTest class in  
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // test an empty calorie counter list
    @Test
    void testReaderEmptyCalorieCounterList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWorkRoom.json");
        try {
            CalorieCounterList wr = reader.read();
            assertEquals(0, wr.averageCalorieOfMeals());
            assertEquals(0, wr.getCalorieCounterList().size());
            assertEquals(0,wr.getGoal());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    // Method taken from JSONReaderTest class in  
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // test an general calorie counter list
    @Test
    void testReaderGeneralCalorieCounterList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkRoom.json");
        try {
            CalorieCounterList wr = reader.read();
            assertEquals(100, wr.getGoal());
            List<CalorieCounter> calorieCounterList = wr.getCalorieCounterList();
            assertEquals(1, calorieCounterList.size());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}