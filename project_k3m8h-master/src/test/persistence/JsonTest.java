package persistence;

import model.CalorieCounter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkCalorieCounter(String meal, int cal, int goal, CalorieCounter calcount) {
        assertEquals(meal, calcount.getMeal());
        assertEquals(cal, calcount.getCalories());
        assertEquals(goal, calcount.getGoal());

    }
}
