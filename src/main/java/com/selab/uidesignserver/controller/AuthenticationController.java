package com.selab.uidesignserver.controller;

import com.fasterxml.uuid.Generators;
import com.google.gson.JsonObject;
import com.selab.uidesignserver.entity.uiComposition.GroupsTable;
import com.selab.uidesignserver.entity.uiComposition.ProjectsTable;
import com.selab.uidesignserver.entity.uiComposition.UsersGroupsTable;
import com.selab.uidesignserver.entity.uiComposition.UsersTable;
import com.selab.uidesignserver.repositoryService.AuthenticationService;
import com.selab.uidesignserver.repositoryService.InternalRepresentationService;
import com.sun.org.apache.xpath.internal.operations.Bool;

import org.jclouds.rest.annotations.ResponseParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    InternalRepresentationService internalRepresentationService;

    @PostMapping(value = "/login")

    public ResponseEntity<String> authenticate(@RequestBody String data, HttpServletRequest request) {

        System.out.println("Hello authentication");
        HttpSession session = request.getSession();

        JSONObject userNamePasswordObject = new JSONObject(data);
        String username = userNamePasswordObject.getString("username");
        String password = userNamePasswordObject.getString("password");
        if(authenticationService.authenticate(username, password)) {
            JSONObject responseObject = new JSONObject();
            String userId = authenticationService.getUserByUserName(username).getUserID();
            responseObject.put("userId", userId);
            session.setAttribute("userId", userId);
            List<String> openedThemeIDList = new ArrayList<String>();

            session.setAttribute("openedThemeIDList", openedThemeIDList);
            ((List<String>)session.getAttribute("openedThemeIDList")).forEach(
                    name -> System.out.println(name)
            );

            return ResponseEntity.status(HttpStatus.OK).body(responseObject.toString());
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("authentication failed: user or password not match");
    }

    // delete userA
    // delete all group-user relations with userA
    // delete groupA
    @DeleteMapping(value = "/deregister")
    public ResponseEntity<String> deRegister(@RequestBody String data) {
        System.out.println("Deregister");
        JSONObject userNamePasswordObject = new JSONObject(data);
        String username = userNamePasswordObject.getString("username");
        UsersTable user = authenticationService.getUserByUserName(username);
        GroupsTable group = authenticationService.getGroupByName(username);
        if (user != null) {
            if (user.getPassword().equals(userNamePasswordObject.getString("password"))) {
                List<UsersGroupsTable> userGroupRelations = authenticationService.getGroupsByUser(user.getUserID());
                for(int index = 0; index < userGroupRelations.size(); index++) {
                    UsersGroupsTable userGroupRelation = userGroupRelations.get(index);
                    String userID = userGroupRelation.getUsersTable().getUserID();
                    String groupID = userGroupRelation.getGroupsTable().getGroupID();
                    authenticationService.deleteUserGroupRelation(userID, groupID);
                }
                authenticationService.deleteUser(user.getUserID());
                authenticationService.deleteGroup(group.getGroupID());
                return ResponseEntity.status(HttpStatus.OK).body("delete user: " + username + "successfully");
            } else
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("delete user: " + username + "not correctly (password not matched");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");
    }

    // type1: register successfully
    // type2: register duplicate username
    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody String data) {
        System.out.println("Hello register");
        JSONObject userNamePasswordObject = new JSONObject(data);
        String username = userNamePasswordObject.getString("username");
        String password = userNamePasswordObject.getString("password");
        if (authenticationService.getUserByUserName(username) == null) {
            UUID uuid = Generators.randomBasedGenerator().generate();

            // create user
            String userID = "User-" + uuid.toString();
            System.out.println(userID);
            UsersTable usersTable = new UsersTable(userID, username, password);
            authenticationService.insertUser(usersTable);

            return ResponseEntity.status(HttpStatus.OK).body("Hello " + username + " register successed!");
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Duplicate username");
    }

    @PostMapping(value = "/group")
    public ResponseEntity<String> createGroup(@RequestBody String data, @RequestHeader("userID") String userID) {
        JSONObject responseObject = new JSONObject(data);
        UUID uuid = Generators.randomBasedGenerator().generate();
        String groupID = "Group-" + uuid.toString();
        String groupName = responseObject.getString("name");
        if (authenticationService.getGroupByName(groupName) == null) {
            GroupsTable groupsTable = new GroupsTable(groupID, groupName);
            authenticationService.insertGroup(groupsTable);
            UsersTable usersTable = authenticationService.getUser(userID);
            UsersGroupsTable relation = new UsersGroupsTable(usersTable, groupsTable);
            authenticationService.insertUserGroupRelation(relation);
            return ResponseEntity.status(HttpStatus.OK).body("create group success");
        }
        else 
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("failed: this group name has been used!");
    }

    @PutMapping(value="/group")
    public ResponseEntity<String> inviteToProjectGroup(@RequestHeader("projectID") String projectID, @RequestHeader("userName") String userName) {
        System.out.println("invite group here");;
        ProjectsTable projectTable = internalRepresentationService.getProject(projectID);
        GroupsTable groupTable = projectTable.getGroupsTable();
        UsersTable usersTable = authenticationService.getUserByUserName(userName);
        if(this.authenticationService.getRelationByGroupAndUserID(groupTable.getGroupID(),usersTable.getUserID()).size() > 0)
            return ResponseEntity.status(HttpStatus.OK).body("user has been involved in group");

        if(usersTable == null)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("user is not found".toString());
        UsersGroupsTable relation = new UsersGroupsTable(usersTable, groupTable);
        authenticationService.insertUserGroupRelation(relation);
        return ResponseEntity.status(HttpStatus.OK).body("invite successfully".toString());
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<String> logout( @RequestHeader("userID") String userID, @RequestBody String data, HttpSession session) {
        System.out.println("logout here");
        System.out.println(data);
        JSONObject object = new JSONObject(data);
        JSONArray themeIDs = new JSONArray();
        if(object.getJSONArray("themeIDs") != null) 
            themeIDs = object.getJSONArray("themeIDs");
        System.out.println("hello");
        List<String> themeIDList = new ArrayList<>();
        themeIDs.forEach(themeID->themeIDList.add((String)themeID));

        session.invalidate();
        if(authenticationService.logout(userID, themeIDList))
            return ResponseEntity.status(HttpStatus.OK).body("log out success");
        else
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("log out failed (invalid username)");
    }

    @GetMapping(value = "/project/members")
    public ResponseEntity<String> getGroupMembersByProjectId(@RequestHeader("projectId") String projectId) {
        ProjectsTable projectsTable = internalRepresentationService.getProject(projectId);
        if(projectsTable == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("project not found");
        }
        GroupsTable groupsTable = projectsTable.getGroupsTable();
        List<UsersGroupsTable> relations = authenticationService.getUsersByGroup(groupsTable.getGroupID());

        List<UsersTable> users = new ArrayList<UsersTable>();
        for(int index = 0; index < relations.size(); index++) {
            users.add(relations.get(index).getUsersTable());
        }

        return ResponseEntity.status(HttpStatus.OK).body(users.toString());
    }
}