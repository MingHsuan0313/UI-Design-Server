package com.selab.uidesignserver.webAppService;

import com.cdancy.jenkins.rest.JenkinsClient;
import com.cdancy.jenkins.rest.domain.common.IntegerResponse;
import com.cdancy.jenkins.rest.domain.job.Workflow;
import com.cdancy.jenkins.rest.domain.queue.Executable;
import com.cdancy.jenkins.rest.domain.queue.QueueItem;
import com.google.gson.Gson;
import com.selab.uidesignserver.dto.WebAppGeneratingStateDto;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class GenerateWebAppService {

    private JenkinsClient client;
    private String projectName;

    /**
     * @param projectName The name of webApp.
     * @return Building instance id.
     */
    public String trigger(String projectName) {
        try {
            this.projectName = projectName;
            client = JenkinsClient.builder().endPoint(Config.JENKINS_URL).token(Config.TOKEN).build();
            Map<String, List<String>> params = new HashMap<>();
            params.put(Config.TOKEN_PARAM, Collections.singletonList(Config.TOKEN));
            params.put(Config.PROJECT_NAME_PARAM, Collections.singletonList(projectName));
            params.put(Config.CAUSE_PARAM, Collections.singletonList(Config.CAUSE_UI_CLIENT_TRIGGER_MSG));
            IntegerResponse instanceId = client.api().jobsApi().buildWithParameters(null, Config.JOB_NAME, params);

            return String.valueOf(instanceId.value());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error: cannot trigger Jenkins job. Check if the configuration of Jenkins job has been modified?";
    }


    /**
     * Get the status of all building stages and specify the result of the web app generation.
     * @param instanceId The building instance id.
     * @return
     * If status is SUCCESS, set the deployed url of the webApp.
     * If status if FAILURE or ABORTED, set the result to the error message.
     */
    public WebAppGeneratingStateDto getCurrentGeneratingState(String instanceId) {
        WebAppGeneratingStateDto ret = new WebAppGeneratingStateDto();

        if (client == null) {
            ret.setStatus("You should generate webApp first to build up the JenkinsClient instance");
            return ret;
        }

        while (true) {
            QueueItem queuedInstance = client.api().queueApi().queueItem(Integer.parseInt(instanceId));
            if (queuedInstance.cancelled()) {
                ret.setStatus(BUILD_STATUS.ABORTED.toString());
                return ret;
            }

            Executable executableBuild = queuedInstance.executable();
            if (executableBuild == null)    continue;

            Workflow workflow = client.api().jobsApi().workflow(null, Config.JOB_NAME, executableBuild.number());
            // get building stages
            ret.setStages(new Gson().toJson(workflow.stages()));
            // check status
            String status = workflow.status();
            if (status.equals(BUILD_STATUS.SUCCESS.toString())) {
                ret.setStatus(BUILD_STATUS.SUCCESS.toString());
                ret.setDeployedUrl(Config.DEPLOY_URL + projectName + "/");
            } else if (status.equals(BUILD_STATUS.IN_PROGRESS.toString())){
                // check timeout first
                long currentTime = System.currentTimeMillis();
                if (currentTime - workflow.startTimeMillis() > Config.BUILD_TIMEOUT) {
                    ret.setStatus(String.format("Timeout: the build task cost over %d s", Config.BUILD_TIMEOUT / 1000));
                    return ret;
                }
                ret.setStatus(BUILD_STATUS.IN_PROGRESS.toString());
            } else if (status.equals(BUILD_STATUS.NOT_EXECUTED.toString())) {
                ret.setStatus(String.format("Queueing. %s", BUILD_STATUS.NOT_EXECUTED.toString()));
            } else {
                ret.setStatus(String.format("Failed. Build Result: %s", status));
            }
            return ret;
        }
    }

    private class Config {
        public static final String JENKINS_URL = "http://140.112.90.144/jenkins/";
        public static final String TOKEN_PARAM = "token";
        public static final String CAUSE_PARAM = "cause";
        public static final String DEPLOY_URL = "http://140.112.90.144:8088/webapp/";
        // self-defined properties
        public static final String JOB_NAME = "webapp-generator-with-git-VCS";
        public static final String TOKEN = "SelabWebAppGeneratorWithGitVCSToken";
        public static final String PROJECT_NAME_PARAM = "projectName";
        public static final String CAUSE_UI_CLIENT_TRIGGER_MSG = "ui-client-trigger";
        // timeout of the build task
        // TODO: 3 min, modify the value if pipeline deals with more tasks and cost more time in the future
        public static final long BUILD_TIMEOUT = 1000 * 60 * 3;
    }

    /**
     * Referred to Jenkins Plugin.
     */
    private enum BUILD_STATUS {
        SUCCESS, FAILURE, NOT_EXECUTED, IN_PROGRESS, ABORTED
    };
}
