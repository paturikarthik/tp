package seedu.lifetrack.calories.calorielist;

import seedu.lifetrack.Entry;
import seedu.lifetrack.system.exceptions.ErrorMessages;
import seedu.lifetrack.system.exceptions.InvalidInputException;
import seedu.lifetrack.system.parser.ParserCalories;
import seedu.lifetrack.system.storage.FileHandler;
import seedu.lifetrack.ui.CalorieListUi;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CalorieList {

    private static Logger logr = Logger.getLogger(CalorieList.class.getName());

    private final int SIZE_OF_DELETE = 16;
    private ArrayList<Entry> calorieArrayList;
    private FileHandler fileHandler;

    //constructor for JUnit tests
    public CalorieList() {
        calorieArrayList = new ArrayList<>();
    }

    /**
     * Constructs a new CalorieList object using a file path.
     * This constructor is intended for usage in a terminal environment.
     *
     * @param filePath the path to the file containing calorie entries
     */
    public CalorieList(String filePath) {
        try {
            fileHandler = new FileHandler(filePath);
            calorieArrayList = fileHandler.getCalorieEntriesFromFile();
        } catch (FileNotFoundException e) {
            calorieArrayList = new ArrayList<>();
            System.out.println(ErrorMessages.getFileNotFoundMessage());
        }
    }

    /**
     * Updates the file with the current list of calorie entries.
     * If the file handler is not initialized, no action is taken.
     */
    private void updateFile() {
        if (fileHandler != null) {
            fileHandler.writeEntries(calorieArrayList);
        }
    }

    public Entry getEntry(int index) {
        return calorieArrayList.get(index);
    }


    /**
     * Deletes a calorie entry from the list based on the provided index.
     * Index should be in an integer from 1 to size of the list.
     *
     * @param line the string containing the index of calorie record user want to delete
     */
    //@@author a-wild-chocolate
    public void deleteEntry(String line) {
        assert (line.startsWith("calories delete") ) : "ensures that input is correct";

        try {
            int index = Integer.parseInt(line.substring(SIZE_OF_DELETE).trim());
            Entry toDelete = calorieArrayList.get(index-1);
            calorieArrayList.remove((index-1));  // transfer to scope 0 to size-1
            updateFile();
            CalorieListUi.successfulDeletedMessage(toDelete);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(CalorieListUi.deleteLogIndexMessage());
        } catch (NumberFormatException e) {
            System.out.println(CalorieListUi.deleteLogNumberMessage());
        }
    }
    //@@author

    /**
     * Parses a string input representing calorie intake and adds it to the calorie list.
     *
     * This method takes a string input representing calorie intake information and
     * attempts to parse it using the parseCaloriesIn method from the Parser class.
     * If the input format is incorrect or contains missing components, it catches
     * the InvalidInputException and prints an error message. Otherwise, it adds
     * the parsed Entry object to the calorieArrayList.
     *
     * @param input the input string containing date, time, activity, and calorie count
     */
    public void addEntry(String input) {
        assert (input.startsWith("calories in") || input.startsWith("calories out")) : "ensures that input is correct";
        logr.setLevel(Level.SEVERE);
        try {
            Entry newEntry = ParserCalories.parseCaloriesInput(input);
            calorieArrayList.add(newEntry);
            updateFile();
            CalorieListUi.printNewCalorieEntry(newEntry);
        } catch (InvalidInputException e) {
            logr.log(Level.WARNING, e.getMessage(), e);
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prints the list of calorie entries along with its activity description.
     * If the list is empty, it prints a message indicating that the list is empty.
     * Otherwise, it prints each entry's activity description and calorie count.
     */
    public void printCalorieList() {
        if (calorieArrayList.isEmpty()) {
            CalorieListUi.emptyListMessage();
        } else {
            CalorieListUi.calorieListHeader();
            for (int i = 0; i < calorieArrayList.size(); i++) {
                Entry entry = calorieArrayList.get(i);
                System.out.println("\t " + (i + 1) + ". " + entry);
            }
        }
    }

    /**
     * Returns the size of the list of calorie entries.
     *
     * @return the number of calorie entries in the list
     */
    public int getSize() {
        return calorieArrayList.size();
    }

    /**
     * Calculates and returns the total number of calories consumed from all entries in the list.
     *
     * @return the total number of calories consumed
     */
    public int getCaloriesConsumed() {
        int totalCalories = 0;
        for (int i = 0; i < calorieArrayList.size(); i++) {
            InputEntry tempEntry = (InputEntry) calorieArrayList.get(i);
            totalCalories += tempEntry.getCalories();
        }
        return totalCalories;
    }
}
