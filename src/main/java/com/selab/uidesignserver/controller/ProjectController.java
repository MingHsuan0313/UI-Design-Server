package com.selab.uidesignserver.controller;

import com.cdancy.jenkins.rest.shaded.javax.ws.rs.core.Response;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.context.Theme;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/project")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class ProjectController {
    @Autowired
    InternalRepresentationService internalRepresentationService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping(value = "/open")
    public ResponseEntity<String> openProject(@RequestBody String data, @RequestHeader("projectName") String projectName,
            @RequestHeader("userID") String userID, HttpSession session) {

        JSONArray themesArray = new JSONArray(data);
        JSONArray responseData = new JSONArray();
        List<String> openedThemeIDList = (List<String>) session.getAttribute("openedThemeIDList");

        for (Object themeID : themesArray) {
            // Set theme to used
            ThemesTable themesTable = internalRepresentationService.getThemeById((String) themeID);
            JSONObject themeInfo = new JSONObject();
            if (themesTable.getUsed() == false) {
                themesTable.setUsed(true);
                internalRepresentationService.insertTheme(themesTable);
                List<PagesTable> pagesTables = internalRepresentationService.getPagesByThemeID((String) themeID);
                JSONArray pageInfoArray = new JSONArray();
                for (PagesTable pagesTable : pagesTables) {
                    JSONObject pageInfo = new JSONObject();
                    JSONObject DLs = new JSONObject();
                    String ndl = internalRepresentationService.getNavigationByPageID(pagesTable.getId()).getNdl();
                    String sumdl = internalRepresentationService.getSUMDLsByPageID(pagesTable.getId()).getSumdl();
                    DLs.put("ndl", ndl);
                    DLs.put("sumdl", sumdl);
                    DLs.put("pdl", pagesTable.getPdl());
                    pageInfo.put("pageName", pagesTable.getName());
                    pageInfo.put("pageID", pagesTable.getId());
                    pageInfo.put("DLs", DLs);
                    pageInfoArray.put(pageInfo);
                }
                themeInfo.put("themeID", themesTable.getId());
                themeInfo.put("themeName", themesTable.getThemeName());
                themeInfo.put("pages", pageInfoArray);
                System.out.println(themesTable.getId());
                openedThemeIDList.add(themesTable.getId());

                responseData.put(themeInfo);
            }else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                     .body("Theme " + themesTable.getThemeName() + " is being used now.");
            }
            session.setAttribute("openedThemeIDList", openedThemeIDList);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData.toString());
    }

    @PutMapping(value = "/layout")
    public ResponseEntity<String> setLayout(@RequestHeader("projectName") String projectName, @RequestHeader("layout") String layout) {
        if(this.internalRepresentationService.setLayout(projectName, layout))
            return ResponseEntity.status(HttpStatus.OK).body("set Layout");
        else
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("project not found");
    }

    @PostMapping(value = "/save")
    public ResponseEntity<String> saveProject(@RequestBody String data, @RequestHeader("projectName") String projectName,
            @RequestHeader("userID") String userID, HttpSession session) {
            
            JSONObject projectObject = new JSONObject(data);
            String layout = "";
            if(projectObject.getString("layout") != null) {
                layout = projectObject.getString("layout");
            }
            JSONArray themeArray = projectObject.getJSONArray("themes"); 
            for(Object themeObject: themeArray){
                String themeID = ((JSONObject)themeObject).getString("themeID");
                String themeName = ((JSONObject)themeObject).getString("themeName");
                JSONArray pageArray = ((JSONObject)themeObject).getJSONArray("pages");
                for(Object pageObject: pageArray){
                    String pageID = ((JSONObject)pageObject).getString("pageID");
                    String pageName = ((JSONObject)pageObject).getString("pageName");
                    String pdl = ((JSONObject)pageObject).getString("pdl");
                    String sumdl = ((JSONObject)pageObject).getString("sumdl");
                    String ndl = ((JSONObject)pageObject).getString("ndl");
                    PagesTable pagesTable = internalRepresentationService.getPageByPageID(pageID);
                    if(pagesTable!=null){
                        
                    }else{

                    }
                }
            }


        return ResponseEntity.status(HttpStatus.OK).body("save project");
    }

    @PostMapping(value = "/create")
    public ResponseEntity<String> createProject(@RequestHeader("userID") String userID,
            @RequestHeader("projectName") String projectName) {
        System.out.println("create project");
        String layout = "";
        if (internalRepresentationService.getProjectByProjectName(projectName) != null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("project name has been used");
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
        ProjectsTable project = new ProjectsTable(projectId, projectName, groupsTable, layout);
        internalRepresentationService.insertProject(project);
        return ResponseEntity.status(HttpStatus.OK).body(projectId);
    }

    @PutMapping(value = "/group")
    public ResponseEntity<String> changeProjectGroup(@RequestHeader("userID") String userID,
            @RequestHeader("projectID") String projectID, @RequestHeader("groupID") String targetGroupID) {
        ProjectsTable project = internalRepresentationService.getProject(projectID);
        if(project == null)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("projectID not found");
        GroupsTable group = authenticationService.getGroup(targetGroupID);
        if(group == null)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("groupID not found");
        project.setGroupsTable(group);
        this.internalRepresentationService.modifyProject(project);
        return ResponseEntity.status(HttpStatus.OK).body("change group success");
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

    @GetMapping(value = "/themes")
    public List<ThemesTable> getThemesByProjectID(@RequestHeader("userID") String userID,
            @RequestHeader("projectID") String projectID) {
        return internalRepresentationService.getThemesByProjectID(projectID);
    }

    @PostMapping(value = "/projects")
    public String deleteProjectsByIds(@RequestBody String data) {
        JSONArray projectIds = new JSONObject(data).getJSONArray("projectIDs");
        String[] ids = new String[projectIds.length()];
        for(int index = 0; index < projectIds.length(); index++) {
            String id = projectIds.getJSONObject(index).getString("id");
            ids[index] = id;
        }
        this.internalRepresentationService.deleteProjectsByIds(ids);
        return "hello";
    }

    @PostMapping(value = "/setMainPage")
    public ResponseEntity<String> setMainPage(@RequestHeader("userID") String userID,
                              @RequestHeader("projectName") String projectName, 
                              @RequestBody String data){
        String projectID = internalRepresentationService.getProjectByProjectName(projectName).getProjectID();
        ProjectsTable projectsTable = internalRepresentationService.getProject(projectID);
        JSONObject object = new JSONObject(data);
        String pageID = object.getString("pageID");
        if(internalRepresentationService.setMainPage(projectsTable, pageID)){
            return ResponseEntity.status(HttpStatus.OK).body("Successfully set main page for the project.");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Already have another main page for the project.");
    }

    @PostMapping(value = "/deleteMainPage")
    public ResponseEntity<String> deleteMainPage(@RequestHeader("userID") String userID,
                              @RequestHeader("projectName") String projectName, 
                              @RequestBody String data){
        String projectID = internalRepresentationService.getProjectByProjectName(projectName).getProjectID();
        ProjectsTable projectsTable = internalRepresentationService.getProject(projectID);
        JSONObject object = new JSONObject(data);
        String pageID = object.getString("pageID");
        if(internalRepresentationService.deleteMainPage(projectsTable, pageID)){
            return ResponseEntity.status(HttpStatus.OK).body("Successfully remove main page for the project.");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cannot remove the main page since the given page is not the main page");
    }



    
}
