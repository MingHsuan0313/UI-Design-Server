package com.selab.uidesignserver.ServiceComponentService;

import com.cdancy.jenkins.rest.domain.queue.Executable;
import com.cdancy.jenkins.rest.JenkinsClient;
import com.cdancy.jenkins.rest.domain.job.Workflow;
import com.cdancy.jenkins.rest.domain.queue.QueueItem;
import com.cdancy.jenkins.rest.shaded.com.google.gson.Gson;
import com.selab.uidesignserver.dto.WebAppGeneratingStateDto;

import org.springframework.stereotype.Service;

@Service
public class JenkinsUtilService {
	private JenkinsClient client;	

	public boolean triggerEditServicePipeline() {
		return true;
	}

	public boolean triggerAddServicePipeline() {
		return true;
	}

    public WebAppGeneratingStateDto getCurrentGeneratingState(String instanceId) {
        WebAppGeneratingStateDto ret = new WebAppGeneratingStateDto();

        if (client == null) {
            ret.setTaskStatus("You should generate webApp first to build up the JenkinsClient instance");
            return ret;
        }

        while (true) {
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
                // ret.setDeployedUrl(Config.DEPLOY_URL + projectName + "/");
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
        public static final String TOKEN = "SelabWebAppGeneratorWithGitVCSToken";
        public static final String JOB_NAME = "webapp-generator-with-git-VCS";
        public static final String PROJECT_NAME = "projectName";
        // timeout of the build task
        // TODO: 3 min, modify the value if pipeline deals with more tasks and cost more time in the future
        public static final long BUILD_TIMEOUT = 1000 * 60 * 3;
    }

    /**
     * Referred to Jenkins Plugin.
     */
    private enum BUILD_STATUS {
        SUCCESS, NOT_EXECUTED, IN_PROGRESS, ABORTED
    };

}