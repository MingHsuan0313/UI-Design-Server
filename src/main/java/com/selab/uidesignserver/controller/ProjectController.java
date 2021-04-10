package com.selab.uidesignserver.controller;

import com.fasterxml.uuid.Generators;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.google.gson.JsonObject;
import com.selab.uidesignserver.dao.uiComposition.ThemesRepository;
import com.selab.uidesignserver.entity.uiComposition.GroupsTable;
import com.selab.uidesignserver.entity.uiComposition.ProjectsTable;
import com.selab.uidesignserver.entity.uiComposition.ThemesTable;
import com.selab.uidesignserver.entity.uiComposition.UsersGroupsTable;
import com.selab.uidesignserver.entity.uiComposition.*;
import com.selab.uidesignserver.repositoryService.AuthenticationService;
import com.selab.uidesignserver.repositoryService.InternalRepresentationService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.context.Theme;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class ProjectController {
    @Autowired
    InternalRepresentationService internalRepresentationService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping(value = "/open")
    public String openProject(@RequestBody String data, @RequestHeader("projectName") String projectName,
            @RequestHeader("userID") String userID) {
        JSONArray themesArray = new JSONArray(data);
        JSONObject responseData = new JSONObject();

        for (Object themeID : themesArray) {
            // Set theme to used
            ThemesTable themesTable = internalRepresentationService.getThemeById((String) themeID);
            if (themesTable.getUsed() == false) {
                themesTable.setUsed(true);
                internalRepresentationService.insertTheme(themesTable);
                List<PagesTable> pagesTables = internalRepresentationService.getPagesByThemeID((String) themeID);
                JSONObject DLsInPage = new JSONObject();
                for (PagesTable pagesTable : pagesTables) {
                    JSONObject DLs = new JSONObject();
                    String ndl = internalRepresentationService.getNavigationByPageID(pagesTable.getId()).getNdl();
                    String sumdl = internalRepresentationService.getSUMDLsByPageID(pagesTable.getId()).getSumdl();
                    DLs.put("ndl", ndl);
                    DLs.put("sumdl", sumdl);
                    DLs.put("pdl", pagesTable.getPdl());
                    DLsInPage.put(pagesTable.getId(), DLs);
                }
                responseData.put((String) themeID, DLsInPage);
            }
        }
        return responseData.toString();
    }

    @PostMapping(value = "/create")
    public String createProject(@RequestHeader("userID") String userID,
            @RequestHeader("projectName") String projectName) {
        System.out.println("create project");
        if (internalRepresentationService.getProjectByProjectName(projectName) != null) {
            return "project name has been used";
        }
        UUID uuid = Generators.randomBasedGenerator().generate();

        // create group
        String groupID = "Group-" + uuid.toString();
        GroupsTable groupsTable = new GroupsTable(groupID, projectName);
        authenticationService.insertGroup(groupsTable);

        // create relation between project group and project owner
        UsersTable usersTable = authenticationService.getUser(userID);
        UsersGroupsTable usersGroupsTable = new UsersGroupsTable(usersTable, groupsTable);
        authenticationService.insertUserGroupRelation(usersGroupsTable);

        // create project
        String projectId = "Project-" + uuid.toString();
        ProjectsTable project = new ProjectsTable(projectId, projectName, groupsTable);
        internalRepresentationService.insertProject(project);
        return projectId;
    }

    @PostMapping(value = "/save")
    public String saveProject(@RequestBody String data, @RequestHeader("projectID") String projectID,
            @RequestHeader("userID") String userID) {

        return "";
    }

    @PutMapping(value = "/group")
    public String changeProjectGroup(@RequestHeader("userID") String userID,
            @RequestHeader("projectID") String projectID, @RequestHeader("groupID") String targetGroupID) {
        ProjectsTable project = internalRepresentationService.getProject(projectID);
        GroupsTable group = authenticationService.getGroup(targetGroupID);
        project.setGroupsTable(group);
        this.internalRepresentationService.modifyProject(project);
        return "change group success";
    }

    @GetMapping(value = "/user")
    public List<ProjectsTable> getProjectsByUserID(@RequestHeader("userID") String userID) {
        List<ProjectsTable> projects = new ArrayList<ProjectsTable>();
        List<UsersGroupsTable> relations = this.authenticationService.getGroupsByUser(userID);
        for (int index = 0; index < relations.size(); index++) {
            UsersGroupsTable relation = relations.get(index);
            List<ProjectsTable> temp_projects = this.internalRepresentationService
                    .getProjectsByGroupID(relation.getGroupsTable().getGroupID());
            for (int j = 0; j < temp_projects.size(); j++) {
                projects.add(temp_projects.get(j));
            }
        }
        return projects;
    }

    @GetMapping(value = "themes")
    public List<ThemesTable> getThemesByProjectID(@RequestHeader("userID") String userID,
            @RequestHeader("projectID") String projectID) {
        return internalRepresentationService.getThemesByProjectID(projectID);
    }
}
