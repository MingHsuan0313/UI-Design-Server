package com.selab.uidesignserver.dto;

import com.cdancy.jenkins.rest.domain.job.Stage;

import java.util.List;

public class WebAppGeneratingStateDto {

    private String stages;  // third-party library class doesn't have a serializer, so use Gson
    private String deployedUrl;
    private String status;

    public String getStages() {
        return stages;
    }

    public String getDeployedUrl() {
        return deployedUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStages(String serializedStages) {
        this.stages = serializedStages;
    }

    public void setDeployedUrl(String deployedUrl) {
        this.deployedUrl = deployedUrl;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
