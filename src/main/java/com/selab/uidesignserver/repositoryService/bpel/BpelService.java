package com.selab.uidesignserver.repositoryService.bpel;

import com.selab.uidesignserver.entity.bpel.BpelJsonIR;
import com.selab.uidesignserver.entity.bpel.BpelProject;

import java.util.List;

public interface BpelService {
    // project
    List<BpelProject> getProjectList();

    BpelProject createProject(BpelProject project);

    BpelProject getProject(String id);

    BpelProject updateProject(String id, BpelProject project);

    void deleteProject(String id);

    // bpel json ir
    List<BpelJsonIR> getBpelJsonIRList(String projectId);

    BpelJsonIR createBpelJsonIR(String projectId, BpelJsonIR bpelJsonIR);

    BpelJsonIR getBpelJsonIR(String id);

    BpelJsonIR updateBpelJsonIR(String id, BpelJsonIR bpelJsonIR);

    void deleteBpelJsonIR(String id);
}
