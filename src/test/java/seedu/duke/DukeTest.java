package seedu.duke;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import seedu.duke.ui.MainUi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

class DukeTest {
    
    @Test
    public void dummyTest() {
        assertTrue(true);
    }

    @Test
    public void testAdd1Resource() {
        ByteArrayOutputStream newOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newOutputStream));

        String inputToCmd = "add p/CS2113 Group Project url/https://ay2021s2-cs2113-w10-3.github.io/tp/ d/Team Project\n"
                + "list-all\n"
                + "exit";

        System.setIn(new ByteArrayInputStream(inputToCmd.getBytes()));

        Duke.main(null);

        String targetString = MainUi.DUKE_STANDARD_HEADING
                + "The resource is added into the new project \"CS2113 Group Project\".\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "Here is the list of all project(s) and it's resource(s)!\n"
                + "--------------------------------------------------------" + "\n"
                + "Project 1: CS2113 Group Project\n"
                + "Resource(s):\n"
                + "1): "
                + "[" + LocalDate.now() + "] "
                + "https://ay2021s2-cs2113-w10-3.github.io/tp/ (Description: Team Project)\n"
                + "--------------------------------------------------------\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + MainUi.EXIT_MESSAGE + "\n";

        assertEquals(newOutputStream.toString(), targetString);

        System.setOut(System.out);
    }

    @Test
    public void testAdd2Resource() {
        ByteArrayOutputStream newOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newOutputStream));

        String inputToCmd = "add p/CS2113 Group Project url/https://ay2021s2-cs2113-w10-3.github.io/tp/ d/Team Project\n"
                + "add p/CS2113 Group Project url/other website\n"
                + "list-all\n"
                + "exit";

        System.setIn(new ByteArrayInputStream(inputToCmd.getBytes()));

        Duke.main(null);

        String targetString = MainUi.DUKE_STANDARD_HEADING
                + "The resource is added into the new project \"CS2113 Group Project\".\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "The resource is added to the existing project \"CS2113 Group Project\".\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "Here is the list of all project(s) and it's resource(s)!\n"
                + "--------------------------------------------------------" + "\n"
                + "Project 1: CS2113 Group Project\n"
                + "Resource(s):\n"
                + "1): "
                + "[" + LocalDate.now() + "] "
                + "https://ay2021s2-cs2113-w10-3.github.io/tp/ (Description: Team Project)\n"
                + "2): "
                + "[" + LocalDate.now() + "] "
                + "other website\n"
                + "--------------------------------------------------------\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + MainUi.EXIT_MESSAGE + "\n";

        assertEquals(newOutputStream.toString(), targetString);

        System.setOut(System.out);
    }

    @Test
    public void testAddAndOverwrite1Resource() {
        ByteArrayOutputStream newOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newOutputStream));

        String inputToCmd = "add p/CS2113 Group Project url/https://ay2021s2-cs2113-w10-3.github.io/tp/ d/Team Project\n"
                + "add p/CS2113 Group Project url/https://ay2021s2-cs2113-w10-3.github.io/tp/ d/Team Project for CS2113\n"
                + "list-all\n"
                + "exit";

        System.setIn(new ByteArrayInputStream(inputToCmd.getBytes()));

        Duke.main(null);

        String targetString = MainUi.DUKE_STANDARD_HEADING
                + "The resource is added into the new project \"CS2113 Group Project\".\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "The resource of the project \"CS2113 Group Project\" is overwritten.\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "Here is the list of all project(s) and it's resource(s)!\n"
                + "--------------------------------------------------------" + "\n"
                + "Project 1: CS2113 Group Project\n"
                + "Resource(s):\n"
                + "1): "
                + "[" + LocalDate.now() + "] "
                + "https://ay2021s2-cs2113-w10-3.github.io/tp/ (Description: Team Project for CS2113)\n"
                + "--------------------------------------------------------\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + MainUi.EXIT_MESSAGE + "\n";

        assertEquals(newOutputStream.toString(), targetString);

        System.setOut(System.out);
    }

    @Test
    public void testDeleteNotFoundResource() {
        ByteArrayOutputStream newOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newOutputStream));

        String inputToCmd = "add p/CS2113 Group Project url/https://ay2021s2-cs2113-w10-3.github.io/tp/ d/Team Project for CS2113\n"
                + "add p/CS2113 Group Project url/https://nus-cs2113-ay2021s2.github.io/website/admin/tp-expectations.html d/tp Website\n"
                + "list-all\n"
                + "delete p/CS2113 Group Project i/4\n"
                + "exit";

        System.setIn(new ByteArrayInputStream(inputToCmd.getBytes()));

        Duke.main(null);

        String targetString = MainUi.DUKE_STANDARD_HEADING
                + "The resource is added into the new project \"CS2113 Group Project\".\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "The resource is added to the existing project \"CS2113 Group Project\".\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "Here is the list of all project(s) and it's resource(s)!\n"
                + "--------------------------------------------------------" + "\n"
                + "Project 1: CS2113 Group Project\n"
                + "Resource(s):\n"
                + "1): "
                + "[" + LocalDate.now() + "] "
                + "https://ay2021s2-cs2113-w10-3.github.io/tp/ (Description: Team Project for CS2113)\n"
                + "2): "
                + "[" + LocalDate.now() + "] "
                + "https://nus-cs2113-ay2021s2.github.io/website/admin/tp-expectations.html (Description: tp Website)\n"
                + "--------------------------------------------------------\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "Resource is not found. Please enter a valid index. \n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + MainUi.EXIT_MESSAGE + "\n";

        assertEquals(newOutputStream.toString(), targetString);

        System.setOut(System.out);
    }

    @Test
    public void testDeleteNotFoundProject() {
        ByteArrayOutputStream newOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newOutputStream));

        String inputToCmd = "add p/CS2113 Group Project url/https://ay2021s2-cs2113-w10-3.github.io/tp/ d/Team Project for CS2113\n"
                + "add p/CS2113 Group Project url/https://nus-cs2113-ay2021s2.github.io/website/admin/tp-expectations.html d/tp Website\n"
                + "list-all\n"
                + "delete p/CS2113 Individual Project i/4\n"
                + "exit";

        System.setIn(new ByteArrayInputStream(inputToCmd.getBytes()));

        Duke.main(null);

        String targetString = MainUi.DUKE_STANDARD_HEADING
                + "The resource is added into the new project \"CS2113 Group Project\".\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "The resource is added to the existing project \"CS2113 Group Project\".\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "Here is the list of all project(s) and it's resource(s)!\n"
                + "--------------------------------------------------------" + "\n"
                + "Project 1: CS2113 Group Project\n"
                + "Resource(s):\n"
                + "1): "
                + "[" + LocalDate.now() + "] "
                + "https://ay2021s2-cs2113-w10-3.github.io/tp/ (Description: Team Project for CS2113)\n"
                + "2): "
                + "[" + LocalDate.now() + "] "
                + "https://nus-cs2113-ay2021s2.github.io/website/admin/tp-expectations.html (Description: tp Website)\n"
                + "--------------------------------------------------------\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "Project is not found ... \n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + MainUi.EXIT_MESSAGE + "\n";

        assertEquals(newOutputStream.toString(), targetString);

        System.setOut(System.out);
    }

    @Test
    public void testDeleteFromProject() {
        ByteArrayOutputStream newOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newOutputStream));

        String inputToCmd = "add p/CS2113 Group Project url/https://ay2021s2-cs2113-w10-3.github.io/tp/ d/Team Project for CS2113\n"
                + "add p/CS2113 Group Project url/https://nus-cs2113-ay2021s2.github.io/website/admin/tp-expectations.html d/tp Website\n"
                + "list-all\n"
                + "delete p/CS2113 Group Project i/1\n"
                + "list-all\n"
                + "exit";

        System.setIn(new ByteArrayInputStream(inputToCmd.getBytes()));

        Duke.main(null);

        String targetString = MainUi.DUKE_STANDARD_HEADING
                + "The resource is added into the new project \"CS2113 Group Project\".\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "The resource is added to the existing project \"CS2113 Group Project\".\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "Here is the list of all project(s) and it's resource(s)!\n"
                + "--------------------------------------------------------" + "\n"
                + "Project 1: CS2113 Group Project\n"
                + "Resource(s):\n"
                + "1): "
                + "[" + LocalDate.now() + "] "
                + "https://ay2021s2-cs2113-w10-3.github.io/tp/ (Description: Team Project for CS2113)\n"
                + "2): "
                + "[" + LocalDate.now() + "] "
                + "https://nus-cs2113-ay2021s2.github.io/website/admin/tp-expectations.html (Description: tp Website)\n"
                + "--------------------------------------------------------\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "The resource is deleted from the project \"CS2113 Group Project\".\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "Here is the list of all project(s) and it's resource(s)!\n"
                + "--------------------------------------------------------" + "\n"
                + "Project 1: CS2113 Group Project\n"
                + "Resource(s):\n"
                + "1): "
                + "[" + LocalDate.now() + "] "
                + "https://nus-cs2113-ay2021s2.github.io/website/admin/tp-expectations.html (Description: tp Website)\n"
                + "--------------------------------------------------------\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + MainUi.EXIT_MESSAGE + "\n";

        assertEquals(newOutputStream.toString(), targetString);

        System.setOut(System.out);
    }

    @ Test
    public void testEditNotFoundResource() {
        ByteArrayOutputStream newOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newOutputStream));

        String inputToCmd = "add p/CS2113 Group Project url/https://ay2021s2-cs2113-w10-3.github.io/tp/ d/Team Project for CS2113\n"
                + "edit p/CS2113 Group Project i/3 url/https://nus-cs2113-ay2021s2.github.io/dashboards/contents/tp-progress.html\n"
                + "exit";

        System.setIn(new ByteArrayInputStream(inputToCmd.getBytes()));

        Duke.main(null);

        String targetString = MainUi.DUKE_STANDARD_HEADING
                + "The resource is added into the new project \"CS2113 Group Project\".\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "Resource is not found. Please enter a valid index. " + "\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + MainUi.EXIT_MESSAGE + "\n";
        assertEquals(newOutputStream.toString(), targetString);

        System.setOut(System.out);
    }

    @ Test
    public void testEditNotFoundProject() {
        ByteArrayOutputStream newOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newOutputStream));

        String inputToCmd = "add p/CS2113 Group Project url/https://ay2021s2-cs2113-w10-3.github.io/tp/ d/Team Project for CS2113\n"
                + "edit p/CS2113 Individual Project i/1 url/https://nus-cs2113-ay2021s2.github.io/dashboards/contents/tp-progress.html\n"
                + "exit";

        System.setIn(new ByteArrayInputStream(inputToCmd.getBytes()));

        Duke.main(null);

        String targetString = MainUi.DUKE_STANDARD_HEADING
                + "The resource is added into the new project \"CS2113 Group Project\".\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "Project is not found ... " + "\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + MainUi.EXIT_MESSAGE + "\n";
        assertEquals(newOutputStream.toString(), targetString);

        System.setOut(System.out);
    }

    @ Test
    public void testEditResourceUrl() {
        ByteArrayOutputStream newOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newOutputStream));

        String inputToCmd = "add p/CS2113 Group Project url/https://ay2021s2-cs2113-w10-3.github.io/tp/ d/Team Project for CS2113\n"
                + "add p/CS2113 Group Project url/https://nus-cs2113-ay2021s2.github.io/website/admin/tp-expectations.html d/tp Website\n"
                + "list-all\n"
                + "edit p/CS2113 Group Project i/1 url/https://nus-cs2113-ay2021s2.github.io/dashboards/contents/tp-progress.html\n"
                + "list-all\n"
                + "exit";

        System.setIn(new ByteArrayInputStream(inputToCmd.getBytes()));

        Duke.main(null);

        String targetString = MainUi.DUKE_STANDARD_HEADING
                + "The resource is added into the new project \"CS2113 Group Project\".\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "The resource is added to the existing project \"CS2113 Group Project\".\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "Here is the list of all project(s) and it's resource(s)!\n"
                + "--------------------------------------------------------" + "\n"
                + "Project 1: CS2113 Group Project\n"
                + "Resource(s):\n"
                + "1): "
                + "[" + LocalDate.now() + "] "
                + "https://ay2021s2-cs2113-w10-3.github.io/tp/ (Description: Team Project for CS2113)\n"
                + "2): "
                + "[" + LocalDate.now() + "] "
                + "https://nus-cs2113-ay2021s2.github.io/website/admin/tp-expectations.html (Description: tp Website)\n"
                + "--------------------------------------------------------\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "The resource is successfully edited to : \n"
                + "    "
                + "[" + LocalDate.now() + "] "
                + "https://nus-cs2113-ay2021s2.github.io/dashboards/contents/tp-progress.html (Description: Team Project for CS2113)\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "Here is the list of all project(s) and it's resource(s)!\n"
                + "--------------------------------------------------------" + "\n"
                + "Project 1: CS2113 Group Project\n"
                + "Resource(s):\n"
                + "1): "
                + "[" + LocalDate.now() + "] "
                + "https://nus-cs2113-ay2021s2.github.io/dashboards/contents/tp-progress.html (Description: Team Project for CS2113)\n"
                + "2): "
                + "[" + LocalDate.now() + "] "
                + "https://nus-cs2113-ay2021s2.github.io/website/admin/tp-expectations.html (Description: tp Website)\n"
                + "--------------------------------------------------------\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + MainUi.EXIT_MESSAGE + "\n";

        assertEquals(newOutputStream.toString(), targetString);

        System.setOut(System.out);
    }

    @ Test
    public void testEditResourceLinkDescription() {
        ByteArrayOutputStream newOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newOutputStream));

        String inputToCmd = "add p/CS2113 Group Project url/https://ay2021s2-cs2113-w10-3.github.io/tp/ d/Team Project for CS2113\n"
                + "edit p/CS2113 Group Project i/1 url/https://nus-cs2113-ay2021s2.github.io/dashboards/contents/tp-progress.html d/tp Progress Dashboard\n"
                + "list-all\n"
                + "exit";

        System.setIn(new ByteArrayInputStream(inputToCmd.getBytes()));

        Duke.main(null);

        String targetString = MainUi.DUKE_STANDARD_HEADING
                + "The resource is added into the new project \"CS2113 Group Project\".\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "The resource is successfully edited to : \n"
                + "    "
                + "[" + LocalDate.now() + "] "
                + "https://nus-cs2113-ay2021s2.github.io/dashboards/contents/tp-progress.html (Description: tp Progress Dashboard)\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "Here is the list of all project(s) and it's resource(s)!\n"
                + "--------------------------------------------------------" + "\n"
                + "Project 1: CS2113 Group Project\n"
                + "Resource(s):\n"
                + "1): "
                + "[" + LocalDate.now() + "] "
                + "https://nus-cs2113-ay2021s2.github.io/dashboards/contents/tp-progress.html (Description: tp Progress Dashboard)\n"
                + "--------------------------------------------------------" + "\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + MainUi.EXIT_MESSAGE + "\n";
        assertEquals(newOutputStream.toString(), targetString);

        System.setOut(System.out);
    }

    @Test
    public void testListAllCommands() {
        ByteArrayOutputStream newOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newOutputStream));

        String helpExpectedOutput = "-----------------------------------------"
                + "-------------------------------\n"
                + "Here are the available commands:\n"
                + "add: Adds a resource to a project.\n"
                + "\tFormat: add p/PROJECT_NAME url/URL_LINK [d/LINK_DESCRIPTION]\n"
                + "delete: Deletes a resource from the resource list for a specified project.\n"
                + "\tFormat: delete p/PROJECT_NAME [i/INDEX]\n"
                + "edit: Edits a resource from the resource list for a specified project.\n"
                + "\tFormat: edit p/PROJECT_NAME i/INDEX [url/URL_LINK] [d/LINK_DESCRIPTION]\n"
                + "list: View the resource list for a specified project.\n"
                + "\tFormat: list PROJECT_NAME\n"
                + "list-all: Shows the resource list for all projects.\n"
                + "find: Find resources in a specified project or all projects related to a keyword.\n"
                + "\tFormat: find k/KEYWORD [p/PROJECT_NAME]\n"
                + "save: Saves the current projects and resources to a data file.\n"
                + "load: Loads the projects and resources from the data file if it exists.\n"
                + "exit: Exits the program.\n"
                + "------------------------------------------------------------------------\n\n";

        MainUi.listAllCommands();
        assertEquals(newOutputStream.toString(), helpExpectedOutput);

        System.setOut(System.out);
    }

    @Test
    public void testFindInAllProjects() {
        ByteArrayOutputStream newOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newOutputStream));

        String inputToCmd = "add p/CS2113 Group Project url/https://ay2021s2-cs2113-w10-3.github.io/tp/ d/Team Project for CS2113\n"
                + "add p/CS2113 Group Project url/https://nus-cs2113-ay2021s2.github.io/website/admin/tp-expectations.html d/tp Website\n"
                + "add p/Data Science Project url/https://www.kaggle.com/fedesoriano/stroke-prediction-dataset d/Kaggle Stroke Prediction Dataset Website\n"
                + "find k/Website\n"
                + "exit";

        System.setIn(new ByteArrayInputStream(inputToCmd.getBytes()));

        Duke.main(null);

        String targetString = MainUi.DUKE_STANDARD_HEADING
                + "The resource is added into the new project \"CS2113 Group Project\".\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "The resource is added to the existing project \"CS2113 Group Project\".\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "The resource is added into the new project \"Data Science Project\".\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "Here is the list of all project(s) and its resource(s) matching the keyword!\n"
                + "--------------------------------------------------------" + "\n"
                + "Project 1: CS2113 Group Project\n"
                + "1): "
                + "[" + LocalDate.now() + "] "
                + "https://nus-cs2113-ay2021s2.github.io/website/admin/tp-expectations.html (Description: tp Website)\n"
                + "--------------------------------------------------------\n"
                + "Project 2: Data Science Project\n"
                + "1): "
                + "[" + LocalDate.now() + "] "
                + "https://www.kaggle.com/fedesoriano/stroke-prediction-dataset (Description: Kaggle Stroke Prediction Dataset Website)\n"
                + "--------------------------------------------------------\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + MainUi.EXIT_MESSAGE + "\n";

        assertEquals(newOutputStream.toString(), targetString);

        System.setOut(System.out);
    }

    @Test
    public void testExit() {
        ByteArrayOutputStream newOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newOutputStream));

        String inputToCmd = "exit";

        System.setIn(new ByteArrayInputStream(inputToCmd.getBytes()));

        Duke.main(null);

        String helpExpectedOutput = MainUi.DUKE_STANDARD_HEADING
                + MainUi.EXIT_MESSAGE + "\n";

        assertEquals(newOutputStream.toString(), helpExpectedOutput);

        System.setOut(System.out);
    }

    @Test
    public void testFindInProject() {
        ByteArrayOutputStream newOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newOutputStream));

        String inputToCmd = "add p/CS2113 Group Project url/https://ay2021s2-cs2113-w10-3.github.io/tp/ d/Team Project for CS2113\n"
                + "add p/CS2113 Group Project url/https://nus-cs2113-ay2021s2.github.io/website/admin/tp-expectations.html d/tp Website\n"
                + "find k/Website p/CS2113 Group Project\n"
                + "exit";

        System.setIn(new ByteArrayInputStream(inputToCmd.getBytes()));

        Duke.main(null);

        String targetString = MainUi.DUKE_STANDARD_HEADING
                + "The resource is added into the new project \"CS2113 Group Project\".\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "The resource is added to the existing project \"CS2113 Group Project\".\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "--------------------------------------------------------" + "\n"
                + "Project: CS2113 Group Project\n"
                + "1): "
                + "[" + LocalDate.now() + "] "
                + "https://nus-cs2113-ay2021s2.github.io/website/admin/tp-expectations.html (Description: tp Website)\n"
                + "--------------------------------------------------------\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + MainUi.EXIT_MESSAGE + "\n";

        assertEquals(newOutputStream.toString(), targetString);

        System.setOut(System.out);
    }

    @Test
    public void testFindInNotFoundProject() {
        ByteArrayOutputStream newOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(newOutputStream));

        String inputToCmd = "add p/CS2113 Group Project url/https://ay2021s2-cs2113-w10-3.github.io/tp/ d/Team Project for CS2113\n"
                + "add p/CS2113 Group Project url/https://nus-cs2113-ay2021s2.github.io/website/admin/tp-expectations.html d/tp Website\n"
                + "find k/Website p/Data Science Project\n"
                + "exit";

        System.setIn(new ByteArrayInputStream(inputToCmd.getBytes()));

        Duke.main(null);

        String targetString = MainUi.DUKE_STANDARD_HEADING
                + "The resource is added into the new project \"CS2113 Group Project\".\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "The resource is added to the existing project \"CS2113 Group Project\".\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + "Project cannot be found! Please enter a valid project name!" + "\n"
                + MainUi.SIGNAL_FOR_USER_TO_INPUT
                + MainUi.EXIT_MESSAGE + "\n";

        assertEquals(newOutputStream.toString(), targetString);

        System.setOut(System.out);
    }

}
