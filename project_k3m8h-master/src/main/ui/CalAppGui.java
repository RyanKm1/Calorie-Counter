package ui;

import model.CalorieCounter;
import model.CalorieCounterList;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static javax.swing.JOptionPane.showInputDialog;

// GUI class for CalorieCounter, constructs the program
public class CalAppGui extends JFrame implements ListSelectionListener {
    private static final String JSON_STORE = "./data/workroom.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private static final int WIDTH = 600;
    private static final int LENGTH = 600;
    private JLabel label;
    private JTextField field;
    private DefaultListModel<CalorieCounter> mealList;
    private JList<CalorieCounter> mealCalCountList;

    private JLabel titlePlaceHolder;
    private JLabel totalCalories;
    private JTextField mealName;

    private CalorieCounterList caList;
    private int goal;

    private JMenuBar jmenubar;
    private JMenu fileMenu;
    private JMenu submenu;
    private JMenuItem save;
    private JMenuItem quit;

    private int accCal;
    private int barValue;


    // Constructor: initializes main, and the headers, and load and exit prompt.
    public CalAppGui() throws IOException {
        super("Calorie Counter");

        initMain();
        mainMenu();
        loadPrompt();
        exitPrompt();

//        initCalList();

////        pack();
//        setLocationRelativeTo(null);
//        setVisible(true);
//        setResizable(false);
    }

    // MODIFIES: calorie list and calorie goal
    // EFFECTS: creates load prompt for user
    public void loadPrompt() throws IOException {
        int loadOption = JOptionPane.showConfirmDialog(null,
                "Would you like to load your last log?", "Load File",
                JOptionPane.YES_NO_OPTION);
        try {
            if (loadOption == JOptionPane.YES_OPTION) {
                caList = jsonReader.read();
                updateCalorieCounterList();
                updateCal();
            } else {
                setCalorieGoal();
            }
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    // MODIFIES: cal list
    // EFFECTS: prompts user to save and exit
    public void exitPrompt() throws FileNotFoundException {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int saveOption = JOptionPane.showConfirmDialog(null,
                        "Would you like to save your file?", "Save File", JOptionPane.YES_NO_OPTION);
                if (saveOption == JOptionPane.YES_OPTION) {
                    try {
                        jsonWriter.open();
                        jsonWriter.write(caList);
                        jsonWriter.close();
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });
    }

    // MODIFIES: calorie goal
    // EFFECTS: sets calorie goal to user's liking
    public void setCalorieGoal() {
        JFrame frame = new JFrame();
        int goalInput = Integer.parseInt(showInputDialog(frame, "Please Enter daily Calorie Goal"));
        caList.setGoal(goalInput);
        updateCal();
    }

    // MODIFIES: this
    // EFFECTS: initializes backend
    public void initMain() {
        caList = new CalorieCounterList(goal);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        mealList = new DefaultListModel<>();
    }


    // EFFECTS: creates main menu with headers
    public void mainMenu() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, LENGTH));
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        frame.setJMenuBar(initMenuBar());

//        JMenuBar menuBar = initMenuBar();
//        panel.add(menuBar, BorderLayout.SOUTH);

        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel header = initHeader();
        JPanel totalCaloriesDisplay = initTotalCaloriesDisplay();
        JScrollPane mealList = initMealList();
        JPanel bottomHeader = initBottomHeader();

        panel.add(totalCaloriesDisplay);
        panel.add(header);

        panel.add(mealList, BorderLayout.NORTH);
        panel.add(bottomHeader);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates the buttons in the menu bar
    private JMenuBar initMenuBar() {
        initJMenu();

        save = new JMenuItem("save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jsonWriter.open();
                    jsonWriter.write(caList);
                    jsonWriter.close();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });


        quit = new JMenuItem("quit");
        quitButton();

        return initJMenu2();
    }

    // MODIFIES:
    // EFFECTS: part of initMenuBar(), creates quit and save
    public void quitButton() {
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quitOption();
                for (Event event : EventLog.getInstance()) {
                    System.out.println(event.toString());
                }
                System.exit(0);
            }
        });

    }

    // EFFECTS: part of quitbutton, provides quit option
    public void quitOption() {
        int saveOption = JOptionPane.showConfirmDialog(null,
                "Would you like to save your file?", "Save File", JOptionPane.YES_NO_OPTION);
        if (saveOption == JOptionPane.YES_OPTION) {
            try {
                jsonWriter.open();
                jsonWriter.write(caList);
                jsonWriter.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            for (Event event : EventLog.getInstance()) {
                System.out.println(event.toString());
            }
            System.exit(0);
        }
    }


    // MODIFIES:
    // EFFECTS: creates new jmenubar
    private void initJMenu() {
        jmenubar = new JMenuBar();

        jmenubar.setOpaque(true);
        jmenubar.setBackground(Color.BLUE);
        jmenubar.setForeground(Color.WHITE);
        fileMenu = new JMenu("File");
        submenu = new JMenu("Sub Menu");
    }

    // MODIFIES:
    // EFFECTS: adds the save and quit buttons
    private JMenuBar initJMenu2() {
        fileMenu.add(save);
        fileMenu.add(quit);
        jmenubar.add(fileMenu);

        fileMenu.setOpaque(true);
        fileMenu.setBackground(Color.BLUE);
        fileMenu.setForeground(Color.BLUE); // does not anything
        return jmenubar;
    }

    // MODIFIES: this
    // EFFECTS: creates the bottom header
    private JPanel initBottomHeader() {
        JPanel bottomTitle = new JPanel();

        JButton avgMealButton = new JButton("Analytics");
        avgMealButton.setFont(new Font("sans serif", Font.PLAIN, 15));
        avgMealButton.setActionCommand("Avg meal");
        avgMealButton.addActionListener(new ButtonAction());

        avgMealButton.setFont(new Font("sans serif", Font.PLAIN, 15));
        bottomTitle.add(avgMealButton, BorderLayout.CENTER);
        return bottomTitle;

    }

    // MODIFIES:
    // EFFECTS: initializes the total calories to display
    public JPanel initTotalCaloriesDisplay() {
        JPanel title = new JPanel();
        totalCalories = new JLabel("TOTAL CALORIES LEFT: " + caList.getGoal());
        totalCalories.setFont(new Font("sans serif", Font.PLAIN, 20));
        totalCalories.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        title.add(totalCalories);
        return title;
    }

    // MODIFIES:
    // EFFECTS: initializes the header with the add delete and update goal buttons
    public JPanel initHeader() {
        JPanel title = new JPanel();
        titlePlaceHolder = new JLabel("title");
        titlePlaceHolder.setFont(new Font("sans serif", Font.PLAIN, 15));

        title.setBorder(BorderFactory.createEtchedBorder(Color.GRAY, Color.white));

        JButton addMealButton = new JButton("Add New Meal");
        addMealButton.setFont(new Font("sans serif", Font.PLAIN, 15));
        addMealButton.setActionCommand("Add meal");
        addMealButton.addActionListener(new ButtonAction());

        JButton deleteMealButton = new JButton("Delete Meal");
        deleteMealButton.setFont(new Font("sans serif", Font.PLAIN, 15));
        deleteMealButton.setActionCommand("Delete meal");
        deleteMealButton.addActionListener(new ButtonAction());

        JButton updateGoalButton = new JButton("Update Goal");
        updateGoalButton.setFont(new Font("sans serif", Font.PLAIN, 15));
        updateGoalButton.setActionCommand("Update goal");
        updateGoalButton.addActionListener(new ButtonAction());

//        title.add(titlePlaceHolder);
        title.add(addMealButton, BorderLayout.CENTER);
        title.add(deleteMealButton, BorderLayout.CENTER);
        title.add(updateGoalButton, BorderLayout.CENTER);
        return title;
    }

    // MODIFIES: this
    // EFFECTS: creates the scroll pane, to display the meals eaten
    public JScrollPane initMealList() {
        mealList = new DefaultListModel<>();

        mealCalCountList = new JList<>(mealList);
        mealCalCountList.setCellRenderer(new CalorieCounterRender());

        mealCalCountList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mealCalCountList.setSelectedIndex(0);
        mealCalCountList.addListSelectionListener(this);
        mealCalCountList.setVisibleRowCount(10);       //////// modify whenever needed
        JScrollPane pane = new JScrollPane(mealCalCountList);
        pane.setPreferredSize(new Dimension(800, 500));
        add(pane, BorderLayout.NORTH);

        return pane;

    }

    // MODIFIES: this
    // EFFECTS: updates the calorie list
    private void updateCalorieCounterList() {
        mealList.clear();
        List<CalorieCounter> mealLog = caList.getCalorieCounterList();
        for (CalorieCounter meal : mealLog) {
            mealList.addElement(meal);
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the calories
    private void updateCal() {
        int cal = caList.getGoal();
        totalCalories.setText("TOTAL CALORIES: " + cal);
    }


    // MODIFIES:
    // EFFECTS: changes value (however not used)
    @Override
    public void valueChanged(ListSelectionEvent e) {

    }


    ///////////////////////////////////////////
    //////////// CLASS FOR BUTTONS ////////////
    ///////////////////////////////////////////

    class ButtonAction implements ActionListener {

        public ButtonAction() {

        }

        // MODIFIES:
        // EFFECTS: if this button is clicked, run the method respectiviley.
        @Override
        public void actionPerformed(ActionEvent e) {
            if (Objects.equals(e.getActionCommand(), "Add meal")) {
                addMeal();
            }
            if (Objects.equals(e.getActionCommand(), "Delete meal")) {
                deleteMeal();
            }
            if (Objects.equals(e.getActionCommand(), "Update goal")) {
                updateGoal();
            }
            if (Objects.equals(e.getActionCommand(), "Avg meal")) {
                displayChart();
            }
        }

        // MODIFIES: this
        // EFFECTS: adds meal inputted by the user
        private void addMeal() {
            AddMealWindow addMealWindow = new AddMealWindow();
            JPanel panel = addMealWindow.returnJPanel();

            int optionPaneValue = JOptionPane.showConfirmDialog(
                    null, panel,
                    "Add Meal",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (optionPaneValue == JOptionPane.OK_OPTION) {
                String name = addMealWindow.getMealTextLabel();
                int cal = addMealWindow.getCalTextLabel();
                CalorieCounter c = new CalorieCounter(name, cal);
                caList.addCalorieCounter(c);
                caList.setGoal(caList.getGoal() - cal);
//                accCal += cal;

                updateCalorieCounterList();
                updateCal();
            }
        }

        // MODIFIES: this
        // EFFECTS: deletes meal from the calorie list
        private void deleteMeal() {
            CalorieCounter calorieCounter = mealCalCountList.getSelectedValue();
            caList.removeCalorieCounter(calorieCounter.getMeal(), calorieCounter.getCalories());
            caList.setGoal(caList.getGoal() + calorieCounter.getCalories());
            updateCal();
            updateCalorieCounterList();
        }

        // MODIFIES: goal
        // EFFECTS: updates the goal
        private void updateGoal() {
            JFrame frame = new JFrame();
            updateCalorieCounterList();
            int goalInput = Integer.parseInt(showInputDialog(frame, "Please Enter daily Calorie Goal"));
            List<CalorieCounter> mealLog = caList.getCalorieCounterList();
            for (CalorieCounter meal : mealLog) {
                accCal += meal.getCalories();
            }
            caList.setGoal(goalInput - accCal);
            updateCal();
        }


    }

    // MODIFIES:
    // EFFECTS: updates the average amount of calories eaten
    private int updateAverage() {
        return caList.averageCalorieOfMeals();

    }

    ///////////////////////////////////////////
    //////////// CLASS FOR CHART //////////////
    ///////////////////////////////////////////

    class Chart extends JPanel {
        private Map<CalorieCounter, Integer> bars = new LinkedHashMap<CalorieCounter, Integer>();

        CalorieCounter calorieCounter;
        int value;

        // MODIFIES: this
        // EFFECTS: adds the bar in the chart
        public void addBar(CalorieCounter calorieCounter, int value) {
            bars.put(calorieCounter, value);
//            repaint();
        }

        // MODIFIES: this
        // EFFECTS: configures how the bars are painted, and the sizes
        @Override
        protected void paintComponent(Graphics g) {
            // paint bars
            int width = (getWidth() / bars.size());
//            int width = bars.size() * 10;
            int x = 1;
            for (CalorieCounter calorieCounter : bars.keySet()) {
                int value = bars.get(calorieCounter);
                int height = (int) ((getHeight() - 100) * ((double) value / 1500));
//                g.setColor(string);
                g.setColor(Color.BLUE);
                g.fillRect(x, getHeight() - height, width, height);
                g.setColor(Color.BLACK);
                g.drawRect(x, getHeight() - height, width, height);
                x += (width + 3);

                String labelButNotLabel = "Average calories per meal: " + updateAverage();

                g.drawString(labelButNotLabel, (getWidth() / 2) - labelButNotLabel.length() * 2, 60);
            }
        }


        // EFFECTS: returns the preferred dimension
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(10 + 500, 50);
        }

    }

    // MODIFIES: this
    // EFFECTS: method ot return the chart, and add the bars
    public void displayChart() {
        JFrame frame = new JFrame("Analytics");
        Chart chart = new Chart();
        updateCalorieCounterList();
        List<CalorieCounter> mealLog = caList.getCalorieCounterList();
        for (CalorieCounter meal : mealLog) {
            chart.addBar(meal, meal.getCalories());
        }
        chart.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        chart.setPreferredSize(getPreferredSize());
        frame.getContentPane().add(chart);
//        frame.setPreferredSize(new Dimension(600, 500));
        frame.setPreferredSize(getPreferredSize());
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
    }
}
