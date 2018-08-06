package report.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TestResult {
    @JsonProperty("testName")
    private String testName;

    @JsonProperty("groups")
    private String[] groups;

    @JsonProperty("executedBy")
    private String executedBy;

    @JsonProperty("version")
    private String version;

    @JsonProperty("cycle")
    private String cycle;

    @JsonProperty("executionDate")
    private String executionDate;

    @JsonProperty("duration")
    private String duration;

    @JsonProperty("status")
    private Status status;

    @JsonProperty("steps")
    private List<String> steps;

    @JsonProperty("errorMsg")
    private String errorMsg;

    public void setTestName(String testName) {
        this.testName = testName.replaceAll("\\s+", "").trim();
    }


    public void setExecutedBy(String executedBy) {
        this.executedBy = executedBy;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public void setExecutionDate(String executionDate) {
        this.executionDate = executionDate;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getTestName() {
        return testName;
    }

    public String[] getGroups() {
        return groups;
    }

    public void setGroups(String[] groups) {
        this.groups = groups;
    }

    public String getExecutedBy() {
        return executedBy;
    }

    public String getVersion() {
        return version;
    }

    public String getCycle() {
        return cycle;
    }

    public String getExecutionDate() {
        return executionDate;
    }

    public String getDuration() {
        return duration;
    }

    public Status getStatus() {
        return status;
    }

    public List<String> getSteps() {
        return steps;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
