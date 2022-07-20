# Calorie Counter

## Proposal

The program that I am proposing is a Calorie Counter. This calorie counter will input your daily calorie goal, and help track
meals. The user should be able to add meals, and the number of calories eaten. 
The user will be able to: 
- change the calories,
- delete meals, 
- view how many calories are left in the day. 
The counter will identify how many calories are left, and if the user is over the daily goal.

## User Stories

- As a user, I want to be able to add meals and calories to the calorie counter
- As a user, I want to be able to delete meals and calories from the calorie counter
- As a user, I want to be able to view how many calories are left 
- As a user, I want to be able to view the average of the meals eaten

- As a user, I want to be able to save the amount of calories eaten, the average amount of calories per meal, 
  and how many calories are left
- As a user, I want to be able to load this saved state and continue working through the calorie goal.

## Phase 4: Task 2

Sample Log:
Mon Mar 28 11:17:40 PDT 2022
Ham Sandwich with 400 calories has been added
Mon Mar 28 11:17:52 PDT 2022
Alfredo Pasta with 500 calories has been added
Mon Mar 28 11:17:58 PDT 2022
Snacks with 300 calories has been added
Mon Mar 28 11:18:05 PDT 2022
Drinks with 100 calories has been added
Mon Mar 28 11:18:09 PDT 2022
Snacks with 300 calories has been removed
Mon Mar 28 11:18:11 PDT 2022
Chicken with 300 calories has been removed

Process finished with exit code 0

## Phase 4: Task 3

Future improvements to code (Refactoring):
- For the GUI class, a way to increase cohesion is to create separate classes for JLabels and buttons, and having classes that extend these classes
  - This helps reduce coupling and reduce duplication. 