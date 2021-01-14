package com.selab.uidesignserver.webAppService;

import com.cdancy.jenkins.rest.JenkinsClient;
import com.cdancy.jenkins.rest.domain.common.IntegerResponse;
import com.cdancy.jenkins.rest.domain.queue.Executable;
import com.cdancy.jenkins.rest.domain.queue.QueueItem;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class GenerateWebAppService {
    private final String JENKINS_URL = "http://140.112.90.144/jenkins/";
    private final String TOKEN_PARAM = "token";
    private final String CAUSE_PARAM = "cause";
    // self-defined properties
    private final String JOB_NAME = "webapp-generator-with-git-VCS";
    private final String TOKEN = "SelabWebAppGeneratorWithGitVCSToken";
    private final String PROJECT_NAME_PARAM = "projectName";
    private final String CAUSE_UI_CLIENT_TRIGGER_MSG = "ui-client-trigger";
    // timeout of the build task
    // TODO: 3 min, modify the value if pipeline deals with more tasks and cost more time in the future
    private final long BUILD_TIMEOUT = 1000 * 60 * 3;

    public String trigger(String projectName) {
        try {
            JenkinsClient client = JenkinsClient.builder().endPoint(JENKINS_URL).token(TOKEN).build();
            Map<String, List<String>> params = new HashMap<>();
            params.put(TOKEN_PARAM, Collections.singletonList(TOKEN));
            params.put(PROJECT_NAME_PARAM, Collections.singletonList(projectName));
            params.put(CAUSE_PARAM, Collections.singletonList(CAUSE_UI_CLIENT_TRIGGER_MSG));

            IntegerResponse instanceId = client.api().jobsApi().buildWithParameters(null, JOB_NAME, params);
            // polling task queue to get the build instance
            while (true) {
                QueueItem queuedInstance = client.api().queueApi().queueItem(instanceId.value());
                if (queuedInstance.cancelled()) break;

                Executable executableBuild = queuedInstance.executable();
                if (executableBuild != null) {
                    long startTime = System.currentTimeMillis();

                    // polling the build result
                    while (true) {
                        String result = client.api().jobsApi()
                                .buildInfo(null, JOB_NAME, executableBuild.number()).result();
                        if (result != null) {
                            return result;  // SUCCESS, FAILURE, UNSTABLE, NOT_BUILT, ABORTED
                        }

                        // check timeout
                        long currentTime = System.currentTimeMillis();
                        if (currentTime - startTime > BUILD_TIMEOUT) {
                            return String.format("Timeout: the build task cost over %d", BUILD_TIMEOUT);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error: cannot trigger Jenkins job. Check if the configuration of Jenkins job has been modified?";
    }
}
