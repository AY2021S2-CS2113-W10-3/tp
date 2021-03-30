package seedu.duke.command;

import seedu.duke.Duke;
import seedu.duke.project.Project;
import seedu.duke.exception.InvalidArgumentException;
import seedu.duke.exception.NoProjectNameException;
import seedu.duke.exception.ProjectNotFoundException;
import seedu.duke.parser.CommandParser;
import seedu.duke.parser.InputParser;
import seedu.duke.resource.Resource;
import seedu.duke.storage.Storage;
import seedu.duke.ui.MainUi;

import java.util.ArrayList;

public class CommandHandler {
    private static final String ADD_COMMAND = "add";
    private static final String DELETE_COMMAND = "delete";
    private static final String EXIT_COMMAND = "exit";
    private static final String LIST_ALL_COMMAND = "list-all";
    private static final String HELP_COMMAND = "help";
    private static final String LIST_ONE_PROJECT_COMMAND = "list";
    private static final String FIND_COMMAND = "find";
    private static final String EDIT_COMMAND = "edit";
    private static final String SAVE_COMMAND = "save";
    private static final String LOAD_COMMAND = "load";
    public static final String NEW_LINE = "\n";
    public static final String PROJECT_NOT_FOUND_ERROR_MESSAGE = "Project not found in database!" + NEW_LINE;
    public static final String NO_INPUT_FOR_PROJECT_NAME_ERROR_MESSAGE = "You did not key in the Project Name! "
            + "Please type \"help\" for more details." + NEW_LINE;
    public static final String DIVIDER = "--------------------------------------------------------";
    String command;
    static String[] infoFragments;
    private final ArrayList<Project> projects;

    public CommandHandler(InputParser userInput, ArrayList<Project> projects) {
        this.command = userInput.getCommand();
        this.infoFragments = userInput.getInfoFragments();
        this.projects = projects;
    }

    public static String[] getInfoFragments() {
        return infoFragments;
    }

    public boolean processCommand() {
        boolean isLoop = true;
        switch (this.command) {
        case ADD_COMMAND:
            processInputBeforeAdding();
            break;
        case DELETE_COMMAND:
            processInputBeforeDeleting();
            break;
        case EXIT_COMMAND:
            MainUi.showExitMessage();
            isLoop = false;
            break;
        case LIST_ALL_COMMAND:
            printResourceListForAllProjects();
            break;
        case LIST_ONE_PROJECT_COMMAND:
            try {
                printResourceListForAProject();
            } catch (NoProjectNameException e) {
                System.out.print(NO_INPUT_FOR_PROJECT_NAME_ERROR_MESSAGE);
            } catch (ProjectNotFoundException e) {
                System.out.print(PROJECT_NOT_FOUND_ERROR_MESSAGE);
            }
            break;
        case EDIT_COMMAND:
            processInputBeforeEditing();
            break;
        case FIND_COMMAND:
            processInputBeforeFinding();
            break;
        case HELP_COMMAND:
            listAllCommands();
            break;
        case SAVE_COMMAND:
            Storage.updateStorage(Duke.getProjects());
            break;
        case LOAD_COMMAND:
            Duke.setProjects(Storage.readFromStorage());
            break;
        default:
            promptUserInvalidInput();
            break;
        }
        return isLoop;
    }

    //@@author jovanhuang
    /**
     * This method will return the project name from userInput.
     *
     * @return Project Name is the name of the project.
     */
    public String processProjectName(String[] infoFragments) {
        String projectName = String.join(" ", infoFragments);
        return projectName;
    }

    //@@author NgManSing
    private void processInputBeforeAdding() {
        String[] keywords = {"p/", "url/", "d/"};
        int firstOptionalKeyword = 2;
        String[] projectInfo;
        try {
            projectInfo = CommandParser.decodeInfoFragments(infoFragments, keywords, firstOptionalKeyword);
        } catch (InvalidArgumentException e) {
            e.printErrorMsg();
            return;
        }
        addResource(projectInfo);
    }

    //@@author NgManSing
    private void addResource(String[] projectInfo) {
        assert projectInfo != null;
        String projectName = projectInfo[0];
        String projectUrl = projectInfo[1];
        String descriptionOfUrl = projectInfo[2];
        int projectIndex = searchExistingProjectIndex(projectName);

        if (projectIndex == -1) {
            createNewProject(projectName, projectUrl, descriptionOfUrl);
            return;
        }

        if (isUrlAlreadyExist(projectIndex, projectUrl)) {
            overwriteResource(projectName, projectUrl, descriptionOfUrl, projectIndex);
        } else {
            addNewResource(projectName, projectUrl, descriptionOfUrl, projectIndex);
        }
    }

    //@@author NgManSing
    private void createNewProject(String projectName, String projectUrl, String descriptionOfUrl) {
        projects.add(new Project(projectName, projectUrl, descriptionOfUrl));
        System.out.printf("The resource is added into the new project \"%s\".\n", projectName);
    }

    //@@author NgManSing
    private void overwriteResource(String projectName, String projectUrl, String descriptionOfUrl, int projectIndex) {
        projects.remove(projectIndex);
        projects.add(projectIndex, new Project(projectName, projectUrl, descriptionOfUrl));
        System.out.printf("The resource of the project \"%s\" is overwritten.\n", projectName);
    }

    //@@author NgManSing
    private void addNewResource(String projectName, String projectUrl, String descriptionOfUrl, int projectIndex) {
        projects.get(projectIndex).addResources(projectUrl, descriptionOfUrl);
        System.out.printf("The resource is added to the existing project \"%s\".\n", projectName);
    }

    //@@author NgManSing
    private int searchExistingProjectIndex(String projectName) {
        for (int i = 0; i < projects.size(); i++) {
            if (projects.get(i).getProjectName().equals(projectName)) {
                return i;
            }
        }
        return -1;
    }

    //@@author NgManSing
    private boolean isUrlAlreadyExist(int projectIndex, String projectUrl) {
        return projects.get(projectIndex).isUrlAlreadyExist(projectUrl);
    }

    private void processInputBeforeDeleting() {
        String[] keywords = {"p/", "i/"};
        int firstOptionalKeyword = 1;
        String[] projectInfo;
        try {
            projectInfo = CommandParser.decodeInfoFragments(infoFragments, keywords, firstOptionalKeyword);
        } catch (InvalidArgumentException e) {
            e.printErrorMsg();
            return;
        }

        deleteResource(projectInfo);
    }

    public void deleteResource(String[] projectInfo) {
        Project targetedProj = null;
        String projectName = projectInfo[0];
        int idx;

        for (Project project : projects) {
            if (project.getProjectName().equals(projectName)) {
                targetedProj = project;
                break;
            }
        }
        if (targetedProj == null) {
            System.out.print("Project is not found ... " + NEW_LINE);
            return;
        }

        try {
            if (projectInfo[1] != null) {
                idx = Integer.parseInt(projectInfo[1]) - 1;
                targetedProj.getResources().remove(idx);
                System.out.printf("The resource is deleted from the project \"%s\".\n", projectName);
            } else {
                // If index is not indicated, remove all resources from the specified project.
                targetedProj.getResources().removeAll(targetedProj.getResources());
                System.out.printf("All the resources in %s has been deleted.\n", projectName);
                return;
            }
        } catch (Exception e) {
            System.out.print("Resource is not found. Please enter a valid index. " + NEW_LINE);
            return;
        }
    }

    private void processInputBeforeEditing() {
        String[] keywords = {"p/", "i/", "url/", "d/"};
        int firstOptionalKeyword = 1;
        String[] projectInfo;
        try {
            projectInfo = CommandParser.decodeInfoFragments(infoFragments, keywords, firstOptionalKeyword);
        } catch (InvalidArgumentException e) {
            e.printErrorMsg();
            return;
        }

        editResource(projectInfo);
    }

    public void editResource(String[] projectInfo) {
        Project targetedProj = null;
        Resource targetedResource = null;
        String projectName = projectInfo[0];
        Boolean isEdited = false;
        int idx = -1;

        for (Project project : projects) {
            if (project.getProjectName().equals(projectName)) {
                targetedProj = project;
                break;
            }
        }
        if (targetedProj == null) {
            System.out.print("Project is not found ... " + NEW_LINE);
            return;
        }

        try {
            idx = Integer.parseInt(projectInfo[1]) - 1;
            targetedResource = targetedProj.getResources().get(idx);
            if (projectInfo[2] != null) {
                targetedResource.setResourceLink(projectInfo[2]);
                isEdited = true;
            }
            if (projectInfo[3] != null) {
                targetedResource.setResourceDescription(projectInfo[3]);
                isEdited = true;
            }
            if (projectInfo[2] == null & projectInfo[3] == null) {
                System.out.print("The resource is not edited." + NEW_LINE);
            }
        } catch (Exception e) {
            System.out.print("Resource is not found. Please enter a valid index. " + NEW_LINE);
            return;
        }

        if (isEdited) {
            System.out.printf("The resource is successfully edited to : \n");
            System.out.printf("    " + targetedResource.toString() + NEW_LINE);
        }
    }

    private void promptUserInvalidInput() {
        System.out.print("Invalid input! Please type \"help\" for more details." + NEW_LINE);
    }

    //@@author jovanhuang
    /**
     * This method will print the resources for a particular project.
     * @throws NoProjectNameException when user did not enter project name.
     * @throws ProjectNotFoundException when project is not found in database.
     */
    private void printResourceListForAProject() throws NoProjectNameException, ProjectNotFoundException {
        String projectName = processProjectName();
        boolean isProjectNameEmpty = checkIfProjectNameEmpty(projectName);
        if (isProjectNameEmpty) {
            throw new NoProjectNameException();
        }
        for (Project project : projects) {
            if (project.getProjectName().equals(projectName)) {
                printDivider();
                System.out.print("Project: " + projectName + NEW_LINE);
                ArrayList<Resource> resources = project.getResources();
                printResourceList(resources);
                printDivider();
                return;
            }
        }
        throw new ProjectNotFoundException();
    }

    //@@author jovanhuang
    /**
     * This method will check if project name is empty.
     * @param projectName This string user's input for projectName.
     * @return true if empty, false if not empty.
     */
    public boolean checkIfProjectNameEmpty(String projectName) {
        boolean isProjectNameEmpty = projectName.equals("");
        return isProjectNameEmpty;
    }

    //@@author jovanhuang
    /**
     * This method will print divider.
     */
    private void printDivider() {
        System.out.print(DIVIDER + NEW_LINE);
    }

    //@@author jovanhuang
    /**
     * This method will print the resource list for all projects.
     */
    public void printResourceListForAllProjects() {
        int projectCount = 0;
        System.out.print("Here is the list of all project(s) and it's resource(s)!" + NEW_LINE);
        printDivider();
        for (Project project : projects) {
            projectCount += 1;
            System.out.print("Project " + projectCount + ": " + project + NEW_LINE);
            ArrayList<Resource> resources = project.getResources();
            printResourceList(resources);
            printDivider();
        }
        assert true;
    }

    //@@author jovanhuang
    /**
     * This is a helper method that loops through a resource list and print it out.
     * @param resources an arraylist containing resources for a project.
     */
    public static void printResourceList(ArrayList<Resource> resources) {
        System.out.print("Resource(s):" + NEW_LINE);
        int resourceCount = 1;
        for (Resource resource : resources) {
            System.out.print(resourceCount + "): " + resource + NEW_LINE);
            resourceCount += 1;
        }
        assert true;
    }

    //@@author
    public void listAllCommands() {
        MainUi.listAllCommands();
    }

    private void processInputBeforeFinding() {
        String[] keywords = {"k/", "p/"};
        int firstOptionalKeyword = 1;
        String[] keywordInfo;
        try {
            keywordInfo = CommandParser.decodeInfoFragments(infoFragments, keywords, firstOptionalKeyword);
        } catch (InvalidArgumentException e) {
            e.printErrorMsg();
            return;
        }

        findResources(keywordInfo);
    }

    private void findResources(String[] keywordInfo) {
        if (keywordInfo[1] == null) {
            String keyword = keywordInfo[0];
            printAllProjectsAndResourcesMatchingKeyword(keyword);
        } else {
            String keyword = keywordInfo[0];
            String projectName = keywordInfo[1];
            printResourcesInProjectMatchingKeyword(projectName, keyword);
        }
    }

    private void printAllProjectsAndResourcesMatchingKeyword(String keyword) {
        int projectCount = 0;
        System.out.print("Here is the list of all project(s) and its resource(s) matching the keyword!" + NEW_LINE);
        printDivider();
        for (Project project : projects) {
            projectCount += 1;
            System.out.print("Project " + projectCount + ": " + project + NEW_LINE);
            ArrayList<Resource> resources = project.getResources();
            printResourcesMatchingKeyword(resources, keyword);
            printDivider();
        }
    }

    private void printResourcesInProjectMatchingKeyword(String projectName, String keyword) {
        Boolean isProject = Boolean.FALSE;
        for (Project project : projects) {
            if (project.getProjectName().equals(projectName)) {
                isProject = Boolean.TRUE;
                printDivider();
                System.out.print("Project: " + projectName + NEW_LINE);
                ArrayList<Resource> resources = project.getResources();
                printResourcesMatchingKeyword(resources, keyword);
                printDivider();
            }
        }
        if (!isProject) {
            System.out.print("Project cannot be found! Please enter a valid project name!" + NEW_LINE);
        }
    }

    private void printResourcesMatchingKeyword(ArrayList<Resource> resources, String keyword) {
        int resourceCount = 1;
        for (Resource resource : resources) {
            if (checkKeywordMatch(resource, keyword)) {
                System.out.print(resourceCount + "): " + resource + NEW_LINE);
                resourceCount += 1;
            }
        }
        if (resourceCount == 1) {
            System.out.printf("No resources matching keyword \"%s\" found!\n", keyword);
        }
    }

    private Boolean checkKeywordMatch(Resource resource, String keyword) {
        if (resource.getResourceDescriptionOnly().toLowerCase().contains(keyword.toLowerCase())) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
