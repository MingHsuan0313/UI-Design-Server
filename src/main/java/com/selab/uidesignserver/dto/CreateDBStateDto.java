package com.selab.uidesignserver.dto;

public class CreateDBStateDto {
    private String stages; // third-party library class doesn't have a serializer, so use Gson
    private String taskStatus; // prevent naming conflict with HTTP response status
    private boolean isTimeout = false;

    public String getStages() {
        return stages;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public boolean isTimeout() {
        return isTimeout;
    }

    public void setStages(String serializedStages) {
        this.stages = serializedStages;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public void setTimeout(boolean timeout) {
        isTimeout = timeout;
    }
}
