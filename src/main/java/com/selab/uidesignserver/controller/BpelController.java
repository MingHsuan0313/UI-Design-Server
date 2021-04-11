package com.selab.uidesignserver.controller;

import com.selab.uidesignserver.entity.bpel.BpelJsonIR;
import com.selab.uidesignserver.entity.bpel.BpelProject;
import com.selab.uidesignserver.repositoryService.bpel.BpelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/bpel")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class BpelController {
    @Autowired
    BpelService bpelService;

    @GetMapping("/project")
    public List<BpelProject> getProjectList() {
        return bpelService.getProjectList();
    }

    @PostMapping("/project")
    public String createProject(@RequestBody BpelProject project, HttpServletResponse response) {
        BpelProject bp = bpelService.createProject(Objects.requireNonNull(project));
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader(Location.TAG, Location.PROJECT_CREATED(bp.getId()));
        return "create project successfully";
    }

    @GetMapping("/project/{id}")
    public BpelProject getProject(@PathVariable String id) {
        return bpelService.getProject(id);
    }

    @PutMapping("/project/{id}")
    public String updateProject(@PathVariable String id, @RequestBody BpelProject bpelProject,
                                HttpServletResponse response) {
        BpelProject bp = bpelService.updateProject(id, Objects.requireNonNull(bpelProject));
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader(Location.TAG, Location.PROJECT_CREATED(bp.getId()));
        return "update project successfully";
    }

    @DeleteMapping("/project/{id}")
    public String deleteProject(@PathVariable String id) {
        bpelService.deleteProject(id);
        return "delete project successfully";
    }

    @GetMapping("/json-ir")
    public List<BpelJsonIR> getBpelJsonIRList(@RequestParam String projectId) {
        return bpelService.getBpelJsonIRList(Objects.requireNonNull(projectId));
    }

    @PostMapping("/json-ir")
    public String createBpelJsonIR(@RequestParam String projectId, @RequestBody BpelJsonIR bpelJsonIR,
                                   HttpServletResponse response) {
        BpelJsonIR bj = bpelService.createBpelJsonIR(
                Objects.requireNonNull(projectId), Objects.requireNonNull(bpelJsonIR));
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader(Location.TAG, Location.BPEL_JSON_IR_CREATED(bj.getId()));
        return "create BPEL JSON IR successfully";
    }

    @GetMapping("/json-ir/{id}")
    public BpelJsonIR getBpelJsonIR(@PathVariable String id) {
        return bpelService.getBpelJsonIR(id);
    }

    @PutMapping("/json-ir/{id}")
    public String updateBpelJsonIR(@PathVariable String id, @RequestBody BpelJsonIR bpelJsonIR,
                                   HttpServletResponse response) {
        BpelJsonIR bj = bpelService.updateBpelJsonIR(id, Objects.requireNonNull(bpelJsonIR));
        response.setStatus(HttpServletResponse.SC_CREATED);
        response.setHeader(Location.TAG, Location.BPEL_JSON_IR_CREATED(bj.getId()));
        return "update BPEL JSON IR successfully";
    }

    @DeleteMapping("/json-ir/{id}")
    public String deleteBpelJsonIR(@PathVariable String id) {
        bpelService.deleteBpelJsonIR(id);
        return "delete BPEL JSON IR successfully";
    }

    private static class Location {
        public static final String TAG = "Location";

        private static final String CONTROLLER_ROUTE = "/selab/bpel";
        private static final String PROJECT_ROUTE = "/project";
        private static final String BPEL_JSON_IR_ROUTE = "/json-ir";

        public static String PROJECT_CREATED(Object id) {
            return String.format(CONTROLLER_ROUTE + PROJECT_ROUTE + "/%s", id);
        }
        public static String BPEL_JSON_IR_CREATED(Object id) {
            return String.format(CONTROLLER_ROUTE + BPEL_JSON_IR_ROUTE + "/%s", id);
        }
    }
}
