package com.selab.uidesignserver.repositoryService;

import com.selab.uidesignserver.entity.uiComposition.ServiceListBpelJsonIR;

public interface ServiceListBpelService {
    ServiceListBpelJsonIR getServiceListBpelJsonIR(String projectName, String themeId, String pageId,
                                                   String selector);

    ServiceListBpelJsonIR createServiceListBpelJsonIR(String projectName, String themeId, String pageId,
                                ServiceListBpelJsonIR serviceListBpelJsonIR);

    ServiceListBpelJsonIR updateServiceListBpelJsonIR(String id, ServiceListBpelJsonIR serviceListBpelJsonIR);

    void deleteServiceListBpelJsonIR(String id);

    void deleteServiceListBpelJsonIRByProject(String projectName);

    void deleteServiceListBpelJsonIRByTheme(String themeId);

    void deleteServiceListBpelJsonIRByPage(String pageId);
}
