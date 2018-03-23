package org.mehaexample.asdDemo.model.alignprivate;

import java.util.Date;

public class Projects {
    private int projectId;
    private String neuId;
    private String projectName;
    private Date startDate;
    private Date endDate;
    private String description;

    public Projects() {
        super();
    }

    public Projects(String neuId, String projectName, Date startDate, Date endDate, String description) {
        this.neuId = neuId;
        this.projectName = projectName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getNeuId() {
        return neuId;
    }

    public void setNeuId(String neuId) {
        this.neuId = neuId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
