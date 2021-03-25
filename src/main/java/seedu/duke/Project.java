package seedu.duke;

import seedu.duke.resource.Resource;

import java.util.ArrayList;

public class Project {
    private final String projectName;
    private final ArrayList<Resource> resources = new ArrayList<>();

    public Project(String projectName, String projLink, String linkDescription) {
        this.projectName = projectName;
        this.resources.add(new Resource(projLink, linkDescription));
    }

    public Project(String projectName) {
        this.projectName = projectName;
    }

    public ArrayList<Resource> getResources() {
        return resources;
    }

    public String getProjectName() {
        return projectName;
    }

    public void addResources(String projectUrl, String urlDescription) {
        resources.add(new Resource(projectUrl, urlDescription));
    }

    public boolean isUrlAlreadyExist(String projectUrl) {
        for (Resource resource : resources) {
            if (resource.getResourceLink().equals(projectUrl)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkResourceExistsByIndex(int idx) {
        return !(idx >= resources.size() || idx < 0);
    }

    public void loadResource(Resource resource) {
        resources.add(resource);
    }

    public int getNumberOfResources() {
        return resources.size();
    }

    @Override
    public String toString() {
        return getProjectName();
    }
}
