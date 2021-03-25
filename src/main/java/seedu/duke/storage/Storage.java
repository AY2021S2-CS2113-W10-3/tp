package seedu.duke.storage;

import seedu.duke.Project;
import seedu.duke.resource.Resource;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Storage {
    private static ArrayList<Project> projects = new ArrayList<>();

    public static void updateStorage(ArrayList<Project> projects) {
        try {
            java.io.FileWriter fileWriter  = new java.io.FileWriter("data.txt");
            for (Project project: projects) {
                fileWriter.write(projectAsWritableStr(project));
                fileWriter.write(System.lineSeparator());

                for (Resource resource: project.getResources()) {
                    fileWriter.write(resourceAsWritableStr(resource));
                    fileWriter.write(System.lineSeparator());
                }
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Datafile not found!");
        }
    }

    private static String projectAsWritableStr(Project project) {
        return String.format("Project|%s|%d",
                project.getProjectName(),
                project.getNumberOfResources());
    }

    private static String resourceAsWritableStr(Resource resource) {
        return String.format("%s|%s|%s",
                resource.getResourceLink(),
                resource.getResourceDescription(),
                resource.getDateOfCreation().toString());
    }

    public static ArrayList<Project> readFromStorage() {
        createProjectsFromStorage();
        return projects;
    }

    private static void createProjectsFromStorage() {
        try {
            File dataFile = new File("data.txt");
            if (!dataFile.exists()) {
                dataFile.createNewFile();
            }
            Scanner scanner = new Scanner(dataFile);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.startsWith("Project")) {
                    Project project = createProject(line);
                    int numberOfResources = getNumberOfResources(line);
                    for (int i = 0; i < numberOfResources; i++) {
                        line = scanner.nextLine();
                        Resource resource = createResource(line);
                        project.loadResource(resource);
                    }
                    projects.add(project);
                }

            }

        } catch (IOException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

    private static Project createProject(String input) {
        String[] parts = input.split(Pattern.quote("|"));
        String projectName = parts[1];
        return new Project(projectName);
    }

    private static int getNumberOfResources(String input) {
        String[] parts = input.split(Pattern.quote("|"));
        int numberOfResources = Integer.parseInt(parts[2]);
        return numberOfResources;
    }

    private static Resource createResource(String input) {
        String[] parts = input.split(Pattern.quote("|"));
        String link = parts[0];
        String description = parts[1];
        LocalDate dateOfCreation = LocalDate.parse(parts[2]);
        return new Resource(link, description, dateOfCreation);
    }



}
