package com.selab.uidesignserver.controller;

import com.selab.uidesignserver.entity.uiComposition.ServiceListBpelJsonIR;
import com.selab.uidesignserver.repositoryService.ServiceListBpelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/service-list-bpel")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class ServiceListBpelController {
    @Autowired
    ServiceListBpelService serviceListBpelService;

    @GetMapping("/json-ir")
    public ServiceListBpelJsonIR getServiceListBpelJsonIR(@RequestParam(name = "projectName") String projectName,
                                                          @RequestParam(name = "themeId") String themeId,
                                                          @RequestParam(name = "pageId") String pageId,
                                                          @RequestParam(name = "selector") String selector) {
        return serviceListBpelService.getServiceListBpelJsonIR(projectName, themeId, pageId, selector);
    }

    @PostMapping("/json-ir")
    public String createServiceListBpelJsonIR(@RequestParam(name = "projectName") String projectName,
                                              @RequestParam(name = "themeId") String themeId,
                                              @RequestParam(name = "pageId") String pageId,
                                              @RequestBody ServiceListBpelJsonIR serviceListBpelJsonIR,
                                              HttpServletResponse response) {
        ServiceListBpelJsonIR slbj = serviceListBpelService.createServiceListBpelJsonIR(
                Objects.requireNonNull(projectName), Objects.requireNonNull(themeId), Objects.requireNonNull(pageId),
                serviceListBpelJsonIR);
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader(Location.TAG, Location.BPEL_JSON_IR_CREATED(slbj.getId()));
        return "create service list BPEL JSON IR successfully";
    }

    @DeleteMapping("/json-ir")
    public String deleteServiceListBpelJsonIRUnderScope(@RequestParam(name = "projectName", required = false) String projectName,
                                                        @RequestParam(name = "themeId", required = false) String themeId,
                                                        @RequestParam(name = "pageId", required = false) String pageId,
                                                        HttpServletResponse response) {
        if (projectName != null) {
            serviceListBpelService.deleteServiceListBpelJsonIRByProject(projectName);
            return String.format("delete all service list BPEL JSON IRs under projectName=%s", projectName);
        } else if (themeId != null) {
            serviceListBpelService.deleteServiceListBpelJsonIRByTheme(themeId);
            return String.format("delete all service list BPEL JSON IRs under themeId=%s", themeId);
        } else if (pageId != null) {
            serviceListBpelService.deleteServiceListBpelJsonIRByPage(pageId);
            return String.format("delete all service list BPEL JSON IRs under pageId=%s", pageId);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "Client didn't provide any scope to delete service list BPEL JSON IRs";
        }
    }

    @PutMapping("/json-ir/{id}")
    public String updateServiceBpelJsonIR(@PathVariable String id,
                                          @RequestBody ServiceListBpelJsonIR serviceListBpelJsonIR,
                                          HttpServletResponse response) {
        ServiceListBpelJsonIR slbj = serviceListBpelService.updateServiceListBpelJsonIR(id, serviceListBpelJsonIR);
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader(Location.TAG, Location.BPEL_JSON_IR_CREATED(slbj.getId()));
        return "update service list BPEL JSON IR successfully";
    }

    @DeleteMapping("/json-ir/{id}")
    public String deleteServiceListBpelJsonIR(@PathVariable String id) {
        serviceListBpelService.deleteServiceListBpelJsonIR(id);
        return "delete BPEL JSON IR successfully";
    }

    /* helpers for logout save theme (1)get (2)delete (3)re-create */
    @GetMapping("/json-ir/logout")
    public List<ServiceListBpelJsonIR> getServiceBpelJsonIRUnderTheme(@RequestParam(name = "themeId") String themeId) {
        return serviceListBpelService.getServiceBpelJsonIRUnderTheme(themeId);
    }

    @PostMapping("/json-ir/logout")
    public String createServiceBpelJsonIRByProjectId(@RequestParam(name = "projectId") String projectId,
                                                    @RequestParam(name = "themeId") String themeId,
                                                    @RequestParam(name = "pageId") String pageId,
                                                    @RequestBody ServiceListBpelJsonIR serviceListBpelJsonIR,
                                                    HttpServletResponse response) {
        ServiceListBpelJsonIR slbj = serviceListBpelService.createServiceBpelJsonIRByProjectId(
                Objects.requireNonNull(projectId), Objects.requireNonNull(themeId), Objects.requireNonNull(pageId),
                serviceListBpelJsonIR);
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader(Location.TAG, Location.BPEL_JSON_IR_CREATED(slbj.getId()));
        return "create service list BPEL JSON IR by projectId successfully";
    }

    private static class Location {
        public static final String TAG = "Location";

        private static final String CONTROLLER_ROUTE = "/selab/service-list-bpel";
        private static final String BPEL_JSON_IR_ROUTE = "/json-ir";

        public static String BPEL_JSON_IR_CREATED(Object id) {
            return String.format(CONTROLLER_ROUTE + BPEL_JSON_IR_ROUTE + "/%s", id);
        }
    }
}
