package persistence;


import model.CalorieCounter;
import model.CalorieCounterList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    // Method taken from JSONWriterTest class in  
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // test writing an invalid file
    @Test
    void testWriterInvalidFile() {
        try {
            CalorieCounterList wr = new CalorieCounterList(2000);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    // Method taken from JSONWriterTest class in  
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // writing an empty CalorieCounterList
    @Test
    void testWriterCalorieCounterList() {
        try {
            CalorieCounterList wr = new CalorieCounterList(2000);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            wr = reader.read();
            assertEquals(2000, wr.getGoal());
            assertEquals(0, wr.getCalorieCounterList().size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    // Method taken from JSONWriterTest class in  
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Test
    void testWriterGeneralCalorieCounterList() {
        try {
            CalorieCounterList wr = new CalorieCounterList(2000);
            wr.addCalorieCounter(new CalorieCounter("meal", 200));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            wr = reader.read();
            assertEquals(2000, wr.getGoal());
            assertEquals(1, wr.getCalorieCounterList().size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}