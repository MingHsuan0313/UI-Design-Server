package com.selab.uidesignserver.controller;

import java.util.ArrayList;
import java.util.List;

import com.selab.uidesignserver.dao.uiComposition.ThemesRepository;
import com.selab.uidesignserver.entity.uiComposition.ProjectsTable;
import com.selab.uidesignserver.entity.uiComposition.ThemesTable;
import com.selab.uidesignserver.entity.uiComposition.UsersGroupsTable;
import com.selab.uidesignserver.repositoryService.AuthenticationService;
import com.selab.uidesignserver.repositoryService.InternalRepresentationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class ProjectController {
    @Autowired InternalRepresentationService internalRepresentationService;

    @Autowired AuthenticationService authenticationService;

    @GetMapping(value = "")
    public String importProject(@RequestHeader("projectID") String projectID, @RequestHeader("userID") String userID) {
        return "";
    }

    @PostMapping(value = "")
    public String exportProject(@RequestBody String data, @RequestHeader("projectID") String projectID, @RequestHeader("userID") String userID) {
        return "";
    }

    @PatchMapping(value="/group")
    public String changeProjectGroup(@RequestHeader("userID") String targetUserID, @RequestHeader("groupID") String targetGroupID) {
        return "";
    }

    @GetMapping(value="/user")
    public List<ProjectsTable> getProjectsByUserID(@RequestHeader("userID") String userID) {
        List<ProjectsTable> projects = new ArrayList<ProjectsTable>();
        List<UsersGroupsTable> relations = this.authenticationService.getGroupsByUser(userID);
        for(int index = 0; index < relations.size(); index++) {
            UsersGroupsTable relation = relations.get(index);
            List<ProjectsTable> temp_projects = this.internalRepresentationService.getProjectsByGroupID(relation.getGroupsTable().getGroupID());
            for(int j = 0; j < temp_projects.size(); j++) {
                projects.add(temp_projects.get(j));
            }
        }
        return projects;
    }

    @GetMapping(value="themes")
    public List<ThemesTable> getThemesByProjectID(@RequestHeader("userID") String userID, @RequestHeader("projectID") String projectID) {
        return internalRepresentationService.getThemesByProjectID(projectID);
    }
}
