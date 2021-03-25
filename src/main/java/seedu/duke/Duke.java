package seedu.duke;

import seedu.duke.command.CommandHandler;
import seedu.duke.parser.InputParser;
import seedu.duke.storage.Storage;
import seedu.duke.ui.MainUi;

import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    private static ArrayList<Project> projects;
    private static Scanner scan;

    public static void main(String[] args) {
        initializeDuke();
        MainUi.printWelcomeText();
        boolean isLoop;
        do {
            MainUi.printSignalForUserToEnterInput();
            InputParser userInput = getUserInput();
            CommandHandler commandHandler = new CommandHandler(userInput, projects);
            isLoop = commandHandler.processCommand();
        } while (isLoop);
        Storage.updateStorage(projects);
    }

    private static void initializeDuke() {
        projects = Storage.readFromStorage();
        scan = new Scanner(System.in);
    }

    private static InputParser getUserInput() {
        String userInput = "dummy";
        if (scan.hasNextLine()) {
            userInput = scan.nextLine();
        }
        return new InputParser(userInput);
    }

    public static ArrayList<Project> getProjects() {
        return projects;
    }
}
