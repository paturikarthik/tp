package seedu.lifetrack;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import seedu.lifetrack.calories.calorielist.CalorieList;
import seedu.lifetrack.calories.calorielist.InputEntry;
import seedu.lifetrack.calories.calorielist.OutputEntry;

public class CalorieListTest {

    private final String ADDED_ENTRY_HEADER = "\t The following entry has been added to your caloric list!";
    private final String DELETE_ENTRY_HEADER = "\t The following calorie record has been successfully deleted!";

    private final String DELETE_ENTRY_INVALID_INPUT = "\t Please enter a valid positive integer " +
            "within the range of all caloriesID shown in the calories list.\n" +
            "\t Example input: calories delete CALORIES_ID";

    @Test
    public void addEntry_validInput_entryAdded() {
        // Test setup
        CalorieList calorieList = new CalorieList();
        String validInputCalorieIn = "calories in Eat burger c/369 d/2024-03-14";
        String validInputCalorieOut = "calories out run c/679 d/2024-03-15";

        // Call method to test
        calorieList.addEntry(validInputCalorieIn);
        calorieList.addEntry(validInputCalorieOut);

        // Verify that the entry has been added to the list
        assertEquals(2, calorieList.getSize());
        InputEntry firstEntry = (InputEntry)calorieList.getEntry(0);
        OutputEntry secondEntry = (OutputEntry)calorieList.getEntry(1);

        // Check calories intake entry
        LocalDate dateIntake = LocalDate.parse("2024-03-14");
        assertTrue(firstEntry.getDate().isEqual(dateIntake));
        assertEquals("Eat burger", firstEntry.getDescription());
        assertEquals(369, firstEntry.getCalories());

        // Check calories outflow entry
        LocalDate dateOutflow = LocalDate.parse("2024-03-15");
        assertTrue(secondEntry.getDate().isEqual(dateOutflow));
        assertEquals("run", secondEntry.getDescription());
        assertEquals(679, secondEntry.getCalories());
    }
    
    //@@author a-wild-chocolate
    @Test
    public void testDeleteCalorieValidIndex() {
        CalorieList calorieList = new CalorieList();
        calorieList.addEntry("calories out Run c/200 d/2024-03-14");
        int initialSize = calorieList.getSize();
        calorieList.deleteEntry("calories delete 1");
        assertEquals(initialSize - 1, calorieList.getSize());
        calorieList.addEntry("calories out Run c/200 d/2024-03-14");
        calorieList.addEntry("calories in Eat c/200 d/2024-03-14");
        initialSize = calorieList.getSize();
        calorieList.deleteEntry("calories delete 2");
        assertEquals(initialSize - 1, calorieList.getSize());
    }

    @Test
    public void testDeleteCalorieInvalidIndex() {
        CalorieList calorieList = new CalorieList();
        calorieList.addEntry("calories out Run c/200 date/2024-03-14");
        int initialSize = calorieList.getSize();
        calorieList.deleteEntry("calories delete 2"); // Index out of bounds
        calorieList.deleteEntry("calories delete -1");
        assertEquals(initialSize, calorieList.getSize());
    }

    //@@author shawnpong
    @Test
    public void testPrintCalorieListEmpty() {
        String lineSeparator = System.lineSeparator();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        CalorieList calorieList = new CalorieList();
        calorieList.printCalorieList();
        System.setOut(System.out);
        String expectedOutput = "\t Your caloric list is empty. " +
                "Add new entries to populate your list :)" + lineSeparator;
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void testPrintCalorieListNonEmpty() {
        String lineSeparator = System.lineSeparator();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        CalorieList calorieList = new CalorieList();
        calorieList.addEntry("calories in burger king c/200 d/2024-03-14");
        calorieList.printCalorieList();
        System.setOut(System.out);
        String expectedOutput = ADDED_ENTRY_HEADER + lineSeparator +
                "\t " + calorieList.getEntry(0).toString() + lineSeparator +
                "\t Your Caloric List:" + lineSeparator + lineSeparator +
                "\t Your Caloric Inflow List:" + lineSeparator +
                "\t 1. \t caloriesID: 1, Date: 2024-03-14, Description: burger king, Calories: 200" + lineSeparator +
                lineSeparator + "\t Your Caloric Outflow List:" + lineSeparator;
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void testPrintCalorieListMultipleEntries() {
        String lineSeparator = System.lineSeparator();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        CalorieList calorieList = new CalorieList();
        calorieList.addEntry("calories in burger king c/200 d/2024-03-14");
        calorieList.addEntry("calories out Walk c/150 d/2024-03-14");
        calorieList.addEntry("calories in acai c/500 d/2024-03-14");
        calorieList.addEntry("calories out Run c/250 d/2024-03-14");
        calorieList.addEntry("calories in commhall dinner c/300 d/2024-03-14");
        calorieList.printCalorieList();
        System.setOut(System.out);
        StringBuilder expectedOutput = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            expectedOutput.append(ADDED_ENTRY_HEADER)
                    .append(lineSeparator).append("\t ").append(calorieList.getEntry(i).toString())
                    .append(lineSeparator);
        }
        expectedOutput.append("\t Your Caloric List:")
                .append(lineSeparator)
                .append(lineSeparator)
                .append("\t Your Caloric Inflow List:")
                .append(lineSeparator)
                .append("\t 1. \t caloriesID: 1, Date: 2024-03-14, Description: burger king, Calories: 200")
                .append(lineSeparator)
                .append("\t 2. \t caloriesID: 3, Date: 2024-03-14, Description: acai, Calories: 500")
                .append(lineSeparator)
                .append("\t 3. \t caloriesID: 5, Date: 2024-03-14, Description: commhall dinner, Calories: 300")
                .append(lineSeparator)
                .append(lineSeparator)
                .append("\t Your Caloric Outflow List:")
                .append(lineSeparator)
                .append("\t 1. \t caloriesID: 2, Date: 2024-03-14, Description: Walk, Calories: 150")
                .append(lineSeparator)
                .append("\t 2. \t caloriesID: 4, Date: 2024-03-14, Description: Run, Calories: 250")
                .append(lineSeparator);
        assertEquals(expectedOutput.toString(), outputStream.toString());
        assertEquals(5, calorieList.getSize());
    }

    @Test
    public void testAddEntry_addDifferentDates_datesSortedCorrectly() {
        String lineSeparator = System.lineSeparator();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        CalorieList calorieList = new CalorieList();
        calorieList.addEntry("calories in burger king c/200 d/2024-03-14");
        calorieList.addEntry("calories out Walk c/150 d/2022-02-22");
        calorieList.addEntry("calories in acai c/500 d/2022-02-22");
        calorieList.addEntry("calories out Run c/250 d/2024-03-14");
        calorieList.addEntry("calories in commhall dinner c/300 d/2021-01-11");
        calorieList.addEntry("calories out play pool c/69 d/2021-01-11");
        calorieList.printCalorieList();

        System.setOut(System.out);
        StringBuilder expectedOutput = new StringBuilder();

        // expected output string for adding entries
        for (int i = 1; i < 7; i++) {
            expectedOutput.append(ADDED_ENTRY_HEADER)
                    .append(lineSeparator).append("\t ")
                    .append(calorieList.getEntry(calorieList.getIndexFromEntryID(i)).toString())
                    .append(lineSeparator);
        }

        // expected output string for printing calorie list
        expectedOutput.append("\t Your Caloric List:")
                .append(lineSeparator)
                .append(lineSeparator)
                .append("\t Your Caloric Inflow List:")
                .append(lineSeparator)
                .append("\t 1. \t caloriesID: 5, Date: 2021-01-11, Description: commhall dinner, Calories: 300")
                .append(lineSeparator)
                .append("\t 2. \t caloriesID: 3, Date: 2022-02-22, Description: acai, Calories: 500")
                .append(lineSeparator)
                .append("\t 3. \t caloriesID: 1, Date: 2024-03-14, Description: burger king, Calories: 200")
                .append(lineSeparator)
                .append(lineSeparator)
                .append("\t Your Caloric Outflow List:")
                .append(lineSeparator)
                .append("\t 1. \t caloriesID: 6, Date: 2021-01-11, Description: play pool, Calories: 69")
                .append(lineSeparator)
                .append("\t 2. \t caloriesID: 2, Date: 2022-02-22, Description: Walk, Calories: 150")
                .append(lineSeparator)
                .append("\t 3. \t caloriesID: 4, Date: 2024-03-14, Description: Run, Calories: 250")
                .append(lineSeparator);
        assertEquals(expectedOutput.toString(), outputStream.toString());
        assertEquals(6, calorieList.getSize());
    }

    @Test
    public void testDeleteEntry_deleteUsingEntryID_correctlyDeletesBasedOnEntryID() {
        String lineSeparator = System.lineSeparator();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        CalorieList calorieList = new CalorieList();
        calorieList.addEntry("calories in burger king c/200 d/2022-02-22");
        calorieList.addEntry("calories out Walk c/150 d/2022-02-22");
        calorieList.addEntry("calories in acai c/500 d/2022-02-22");

        System.setOut(System.out);
        StringBuilder expectedOutput = new StringBuilder();
        // expected output string for adding entries
        for (int i = 1; i < 4; i++) {
            expectedOutput.append(ADDED_ENTRY_HEADER)
                    .append(lineSeparator).append("\t ")
                    .append(calorieList.getEntry(calorieList.getIndexFromEntryID(i)).toString())
                    .append(lineSeparator);
        }
        // expected output string for deleting entry
        expectedOutput.append(DELETE_ENTRY_HEADER)
                        .append(lineSeparator).append("\t ")
                        .append(calorieList.getEntry(calorieList.getIndexFromEntryID(3)).toString())
                        .append(lineSeparator);

        calorieList.deleteEntry("calories delete 3");
        calorieList.printCalorieList();

        // expected output string for printing calories list
        expectedOutput.append("\t Your Caloric List:")
                .append(lineSeparator)
                .append(lineSeparator)
                .append("\t Your Caloric Inflow List:")
                .append(lineSeparator)
                .append("\t 1. \t caloriesID: 1, Date: 2022-02-22, Description: burger king, Calories: 200")
                .append(lineSeparator)
                .append(lineSeparator)
                .append("\t Your Caloric Outflow List:")
                .append(lineSeparator)
                .append("\t 1. \t caloriesID: 2, Date: 2022-02-22, Description: Walk, Calories: 150")
                .append(lineSeparator);
        assertEquals(expectedOutput.toString(), outputStream.toString());
        assertEquals(2, calorieList.getSize());
    }

    @Test
    public void testAddEntry_addAndDeleteEntries_entryIDIncrementsProperly() {
        String lineSeparator = System.lineSeparator();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        CalorieList calorieList = new CalorieList();
        calorieList.addEntry("calories in burger king c/200 d/2022-02-22");
        calorieList.addEntry("calories out Walk c/150 d/2022-02-22");
        calorieList.addEntry("calories in acai c/500 d/2022-02-22");

        System.setOut(System.out);
        StringBuilder expectedOutput = new StringBuilder();
        // expected output string for first 3 added entries
        for (int i = 1; i < 4; i++) {
            expectedOutput.append(ADDED_ENTRY_HEADER)
                    .append(lineSeparator).append("\t ")
                    .append(calorieList.getEntry(calorieList.getIndexFromEntryID(i)).toString())
                    .append(lineSeparator);
        }

        // expected output string for first deleted entry
        expectedOutput.append(DELETE_ENTRY_HEADER)
                .append(lineSeparator).append("\t ")
                .append(calorieList.getEntry(calorieList.getIndexFromEntryID(3)).toString())
                .append(lineSeparator);

        calorieList.deleteEntry("calories delete 3");
        calorieList.addEntry("calories in yong tau foo c/688 d/2022-02-22 m/10,10,10");

        // expected output string for fourth added entry
        expectedOutput.append(ADDED_ENTRY_HEADER)
                .append(lineSeparator).append("\t ")
                .append(calorieList.getEntry(calorieList.getIndexFromEntryID(4)).toString())
                .append(lineSeparator);

        calorieList.printCalorieList();

        // expected output string for printing calorie list
        expectedOutput.append("\t Your Caloric List:")
                .append(lineSeparator)
                .append(lineSeparator)
                .append("\t Your Caloric Inflow List:")
                .append(lineSeparator)
                .append("\t 1. \t caloriesID: 1, Date: 2022-02-22, Description: burger king, Calories: 200")
                .append(lineSeparator)
                .append("\t 2. \t caloriesID: 4, Date: 2022-02-22, Description: yong tau foo, Calories: 688 " +
                        "(C: 10, P: 10, F: 10)")
                .append(lineSeparator)
                .append(lineSeparator)
                .append("\t Your Caloric Outflow List:")
                .append(lineSeparator)
                .append("\t 1. \t caloriesID: 2, Date: 2022-02-22, Description: Walk, Calories: 150")
                .append(lineSeparator);
        assertEquals(expectedOutput.toString(), outputStream.toString());
        assertEquals(3, calorieList.getSize());
    }


    @Test
    public void testDeleteEntry_invalidInputs_exceptionThrown() {
        String lineSeparator = System.lineSeparator();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        CalorieList calorieList = new CalorieList();
        calorieList.addEntry("calories in burger king c/200 d/2022-02-22");
        calorieList.addEntry("calories out Walk c/150 d/2022-02-22");
        calorieList.addEntry("calories in acai c/500 d/2022-02-22");

        System.setOut(System.out);
        StringBuilder expectedOutput = new StringBuilder();
        // expected output string for adding entries
        for (int i = 1; i < 4; i++) {
            expectedOutput.append(ADDED_ENTRY_HEADER)
                    .append(lineSeparator).append("\t ")
                    .append(calorieList.getEntry(calorieList.getIndexFromEntryID(i)).toString())
                    .append(lineSeparator);
        }

        // Invalid Inputs for calories delete
        calorieList.deleteEntry("calories delete     ");
        calorieList.deleteEntry("calories delete");
        calorieList.deleteEntry("calories delete a b");
        calorieList.deleteEntry("calories delete 1 2");
        calorieList.deleteEntry("calories delete $12");

        // expected output string for invalid entry inputs
        for (int i = 0; i < 5; i++) {
            expectedOutput.append(DELETE_ENTRY_INVALID_INPUT).append(lineSeparator);
        }

        assertEquals(expectedOutput.toString(), outputStream.toString());
        assertEquals(3, calorieList.getSize());
    }
}
