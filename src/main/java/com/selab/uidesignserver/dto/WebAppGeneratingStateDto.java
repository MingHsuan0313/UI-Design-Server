package com.selab.uidesignserver.dto;

public class WebAppGeneratingStateDto {

    private String stages;  // third-party library class doesn't have a serializer, so use Gson
    private String deployedUrl;
    private String taskStatus;  // prevent naming conflict with HTTP response status

    public String getStages() {
        return stages;
    }

    public String getDeployedUrl() {
        return deployedUrl;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setStages(String serializedStages) {
        this.stages = serializedStages;
    }

    public void setDeployedUrl(String deployedUrl) {
        this.deployedUrl = deployedUrl;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
}
