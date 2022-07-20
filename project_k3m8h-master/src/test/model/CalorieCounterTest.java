package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


// tests all methods
class CalorieCounterTest {
    private CalorieCounter cal;
    private CalorieCounterList caList;

    @BeforeEach
    void runBefore() {
        cal = new CalorieCounter("Chicken", 400);
        caList = new CalorieCounterList(2000);
    }

    // test constructor
    @Test
    void testConstructor() {
        assertEquals(400, cal.getCalories());
        assertEquals("Chicken", cal.getMeal());
        assertEquals(2000, caList.getGoal());
        caList.setGoal(10);
        assertEquals(10, caList.getGoal());
    }

    //test set meal
    @Test
    void testSetMeal() {
        cal.setMeal("Beef");
        assertEquals("Beef", cal.getMeal());
    }

    //test set calories
    @Test
    void testSetCalories() {
        cal.setCalories(100);
        assertEquals(100, cal.getCalories());
    }

    //test set goals
    @Test
    void testSetGoals() {
        cal.setGoal(3000);
        assertEquals(3000, cal.getGoal());
    }

    //test addCalorieCounterList()
    @Test
    void testAddCalorieCounterList() {
        assertEquals(0,caList.getCalorieCounterList().size());
        CalorieCounter c = new CalorieCounter("beef",100);
        caList.addCalorieCounter(c);
        assertEquals(1,caList.getCalorieCounterList().size());
    }

    //test removeCalorieCounterList
    @Test
    void testRemoveCalorieCounterList() {
        CalorieCounter c = new CalorieCounter("beef",100);
        caList.addCalorieCounter(c);
        assertEquals(1,caList.getCalorieCounterList().size());
        assertTrue(caList.removeCalorieCounter("beef",100));
        assertEquals(0,caList.getCalorieCounterList().size());



        caList.addCalorieCounter(c);
        assertEquals(1,caList.getCalorieCounterList().size());
        assertFalse(caList.removeCalorieCounter("boof",100));
        assertEquals(1,caList.getCalorieCounterList().size());
        assertFalse(caList.removeCalorieCounter("beef",99));
        assertEquals(1,caList.getCalorieCounterList().size());

    }

    //test averageCalorieCounterList
    @Test
    void testAverageCalorieCounterList() {
        CalorieCounter c = new CalorieCounter("beef",100);
        assertEquals(0, caList.averageCalorieOfMeals());
        caList.addCalorieCounter(c);
        assertEquals(100, caList.averageCalorieOfMeals());
        CalorieCounter c1 = new CalorieCounter("chicken",200);
        caList.addCalorieCounter(c1);
        assertEquals(150,caList.averageCalorieOfMeals());
        CalorieCounter c2 = new CalorieCounter("chicken",400);
        caList.addCalorieCounter(c2);
        assertEquals((700 / 3),caList.averageCalorieOfMeals());
    }


    //test listOfMealStrings
    @Test
    void testListOfMealStrings() {
        CalorieCounter c = new CalorieCounter("beef",100);
        caList.addCalorieCounter(c);
        List<String> str = new ArrayList<>();
        str.add("MEAL: beef CALORIES: 100");
        assertEquals(str, caList.listOfMealsString("beef", 100,1500));


        caList.addCalorieCounter(c);
        str.add("MEAL: beef CALORIES: 100");
        assertEquals(str,caList.listOfMealsString("beef", 100,1500));

        CalorieCounter c1 = new CalorieCounter("chicken",200);
        caList.addCalorieCounter(c1);
        str.add("MEAL: chicken CALORIES: 200");

        assertEquals(str,caList.listOfMealsString("chicken", 100,1500));
        //assertEquals("chicken200",caList.listOfMealsString("chicken", 200,1500));
    }





}