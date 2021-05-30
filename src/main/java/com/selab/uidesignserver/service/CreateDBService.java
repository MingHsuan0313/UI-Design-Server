package com.selab.uidesignserver.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cdancy.jenkins.rest.JenkinsClient;
import com.cdancy.jenkins.rest.domain.common.IntegerResponse;
import com.selab.uidesignserver.dto.CreateDBStateDto;
import com.cdancy.jenkins.rest.domain.job.Workflow;
import com.cdancy.jenkins.rest.domain.queue.Executable;
import com.cdancy.jenkins.rest.domain.queue.QueueItem;
import com.google.gson.Gson;

import org.springframework.stereotype.Service;

@Service
public class CreateDBService {

    private JenkinsClient client;

    public String trigger(String path) {
        try {
            System.out.println("Trigger!!!");
            this.client = JenkinsClient.builder().endPoint(Config.JENKINS_URL).token(Config.TOKEN).build();
            Map<String, List<String>> params = new HashMap<>();
            params.put(Config.TOKEN_PARAM, Collections.singletonList(Config.TOKEN));
            params.put(Config.CUSTOMIZED_PARAM, Collections.singletonList(Config.CUSTOMIZED));
            params.put("path", Collections.singletonList(path));
            IntegerResponse instanceId = client.api().jobsApi().buildWithParameters(null, Config.JOB_NAME, params);
            System.out.println(instanceId.value());
            return String.valueOf(instanceId.value());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Error: cannot trigger Jenkins job. Check if the configuration ofcreate_db_token Jenkins job has been modified?";
    }

    public CreateDBStateDto getCurrentCreatingState(String instanceId) {
        CreateDBStateDto ret = new CreateDBStateDto();

        if(this.client == null) {
            ret.setTaskStatus("Not Trigger Create DB Pipeline Yet");
            return ret;
        }

        while(true) {
            QueueItem queuedInstance = client.api().queueApi().queueItem(Integer.parseInt(instanceId));
            if (queuedInstance.cancelled()) {
                ret.setTaskStatus(BUILD_STATUS.ABORTED.toString());
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
                ret.setTaskStatus(BUILD_STATUS.SUCCESS.toString());
            } else if (status.equals(BUILD_STATUS.IN_PROGRESS.toString())){
                // check timeout first
                long currentTime = System.currentTimeMillis();
                if (currentTime - workflow.startTimeMillis() > Config.BUILD_TIMEOUT) {
                    ret.setTaskStatus(String.format("Timeout: the build task cost over %d s", Config.BUILD_TIMEOUT / 1000));
                    ret.setTimeout(true);
                    return ret;
                }
                ret.setTaskStatus(BUILD_STATUS.IN_PROGRESS.toString());
            } else if (status.equals(BUILD_STATUS.NOT_EXECUTED.toString())) {
                ret.setTaskStatus(String.format("Queueing. %s", BUILD_STATUS.NOT_EXECUTED.toString()));
            } else {
                // Failed
                ret.setTaskStatus(String.format(status));
            }
            return ret;
        }
    }

    private class Config {
        public static final String JENKINS_URL = "http://140.112.90.144/jenkins/";
        public static final String TOKEN_PARAM = "token";
        public static final String PROJECT_IDS_PARAM = "PROJECT_IDS";
        public static final String CUSTOMIZED_PARAM = "CUSTOMIZED";


        public static final String JOB_NAME = "create-db";
        public static final String TOKEN = "create_db_token";
        public static final String PROJECT_IDS = "1";
        public static final String CUSTOMIZED = "true";
        // timeout of the build task
        // TODO: 3 min, modify the value if pipeline deals with more tasks and cost more time in the future
        public static final long BUILD_TIMEOUT = 1000 * 60 * 3;

    }

    private enum BUILD_STATUS {
        SUCCESS, NOT_EXECUTED, IN_PROGRESS, ABORTED
    };
}
