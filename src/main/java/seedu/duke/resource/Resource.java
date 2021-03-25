package seedu.duke.resource;

import java.time.LocalDate;

public class Resource {

    private String resourceLink;
    private String resourceDescription;
    private LocalDate dateOfCreation;

    public Resource(String resourceLink, String resourceDescription) {
        this.resourceLink = resourceLink;
        this.resourceDescription = resourceDescription;
        this.dateOfCreation = LocalDate.now();
    }

    public Resource(String resourceLink, String resourceDescription, LocalDate dateOfCreation) {
        this.resourceLink = resourceLink;
        this.resourceDescription = resourceDescription;
        this.dateOfCreation = dateOfCreation;
    }

    public String getResourceLink() {
        return resourceLink;
    }

    public boolean checkIfDescriptionExists() {
        if (resourceDescription == null) {
            return false;
        }
        return true;
    }

    public String getResourceDescription() {
        if (checkIfDescriptionExists()) {
            return " (Description: " + resourceDescription + ")";
        }
        return "";
    }

    public String getResourceDescriptionOnly() {
        return resourceDescription;
    }

    public void setResourceLink(String resourceLink) {
        this.resourceLink = resourceLink;
    }

    public void setResourceDescription(String resourceDescription) {
        this.resourceDescription = resourceDescription;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    @Override
    public String toString() {
        return "[" + dateOfCreation + "] " + getResourceLink() + getResourceDescription();
    }
}
