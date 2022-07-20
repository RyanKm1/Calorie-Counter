package ui;

import java.io.FileNotFoundException;
import java.io.IOException;

// runs the program
public class Ui {
    public static void main(String[] args) throws IOException {
        try {
            new CalAppGui();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: Unable to run application.");
        }
    }
}
