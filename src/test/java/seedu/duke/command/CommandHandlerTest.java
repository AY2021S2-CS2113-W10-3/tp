package seedu.duke.command;

import org.junit.jupiter.api.Test;
import seedu.duke.Duke;
import seedu.duke.parser.InputParser;
import seedu.duke.project.Project;
import seedu.duke.ui.MainUi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandHandlerTest {

    /**
     * Test Case 1 for printResourceListForAProject.
     */
    @Test
    public void testCase1ForPrintResourceListForAProject() {
        ByteArrayOutputStream newOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newOutputStream));

        //test case 1
        String inputToCmd = "add p/CS2113 Documentation url/https://ay2021s2-cs2113-w10-3.github.io/tp/ d/Team Project\n"
                + "list CS2113 Documentation\n"
                + "exit";

        System.setIn(new ByteArrayInputStream(inputToCmd.getBytes()));

        Duke.main(null);

        String targetString = MainUi.DUKE_STANDARD_HEADING
                + "The resource is added into the new project \"CS2113 Documentation\".\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "--------------------------------------------------------" + CommandHandler.NEW_LINE
                + "Project: CS2113 Documentation\n"
                + "Resource(s):\n"
                + "1): "
                + "[" + LocalDate.now() + "] "
                + "https://ay2021s2-cs2113-w10-3.github.io/tp/ (Description: Team Project)\n"
                + "--------------------------------------------------------\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + MainUi.EXIT_MESSAGE + CommandHandler.NEW_LINE;

        assertEquals(newOutputStream.toString(), targetString);

        System.setOut(System.out);
    }

    /**
     * Test Case 2 for printResourceListForAProject.
     */
    @Test
    public void testCase2ForPrintResourceListForAProject() {
        ByteArrayOutputStream newOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newOutputStream));

        String inputToCmd = "list CS2113 Documentation\n"
                + "exit";

        System.setIn(new ByteArrayInputStream(inputToCmd.getBytes()));

        Duke.main(null);

        String targetString = MainUi.DUKE_STANDARD_HEADING
                + "Project not found in database!" + CommandHandler.NEW_LINE
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + MainUi.EXIT_MESSAGE + CommandHandler.NEW_LINE;

        assertEquals(newOutputStream.toString(), targetString);

        System.setOut(System.out);
    }

    /**
     * Test Case 3 for printResourceListForAProject.
     */
    @Test
    public void testCase3ForPrintResourceListForAProject() {
        ByteArrayOutputStream newOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newOutputStream));

        String inputToCmd = "list \n"
                + "exit";

        System.setIn(new ByteArrayInputStream(inputToCmd.getBytes()));

        Duke.main(null);

        String targetString = MainUi.DUKE_STANDARD_HEADING
                + "You did not key in the Project Name! Please type \"help\" for more details."
                + CommandHandler.NEW_LINE
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + MainUi.EXIT_MESSAGE + CommandHandler.NEW_LINE;

        assertEquals(newOutputStream.toString(), targetString);

        System.setOut(System.out);
    }

    /**
     * Test cases for processProjectName.
     */
    @Test
    public void testProcessProjectName() {
        //test case 1
        ArrayList<Project> projects1 = new ArrayList<>();
        InputParser parser1 = new InputParser("list CZ2003 Documentation");
        CommandHandler commandHandler1 = new CommandHandler(parser1, projects1);
        assertEquals("CZ2003 Documentation", commandHandler1.processProjectName(commandHandler1.getInfoFragments()));

        //test case 2
        ArrayList<Project> projects2 = new ArrayList<>();
        InputParser parser2 = new InputParser("list ");
        CommandHandler commandHandler2 = new CommandHandler(parser2, projects2);
        assertEquals("", commandHandler2.processProjectName(commandHandler2.getInfoFragments()));

        //test case 3
        ArrayList<Project> projects3 = new ArrayList<>();
        InputParser parser3 = new InputParser("list CS2113");
        CommandHandler commandHandler3 = new CommandHandler(parser3, projects3);
        assertEquals("CS2113", commandHandler3.processProjectName(commandHandler3.getInfoFragments()));
    }

    /**
     * Test cases for checkIfProjectNameEmpty.
     */
    @Test
    public void checkIfProjectNameEmpty() {
        //test case 1
        ArrayList<Project> projects1 = new ArrayList<>();
        String userInput1 = "list CZ2003 Documentation";
        InputParser parser1 = new InputParser(userInput1);
        CommandHandler commandHandler1 = new CommandHandler(parser1, projects1);
        String projectName1 = commandHandler1.processProjectName(commandHandler1.getInfoFragments());
        assertEquals(false, commandHandler1.checkIfProjectNameEmpty(projectName1));

        //test case 2
        ArrayList<Project> projects2 = new ArrayList<>();
        String userInput2 = "list ";
        InputParser parser2 = new InputParser(userInput2);
        CommandHandler commandHandler2 = new CommandHandler(parser2, projects2);
        String projectName2 = commandHandler1.processProjectName(commandHandler2.getInfoFragments());
        assertEquals(true, commandHandler2.checkIfProjectNameEmpty(projectName2));

    }

    @Test
    public void printResourceList() {
        //TODO
    }

    @Test
    public void printResourceListForAllProjects() {
        //TODO
    }
}