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
import org.springframework.web.bind.annotation.*;

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
    public String authenticate(@RequestBody String data) {
        System.out.println("Hello authentication");
        JSONObject userNamePasswordObject = new JSONObject(data);
        String username = userNamePasswordObject.getString("username");
        String password = userNamePasswordObject.getString("password");
        if(authenticationService.authenticate(username, password)) {
            return authenticationService.getUserByUserName(username).getUserID();
        }
        return "authentication failed";
    }

    // delete userA
    // delete all group-user relations with userA
    // delete groupA
    @DeleteMapping(value = "/deregister")
    public String deRegister(@RequestBody String data) {
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
                return "delete user: " + username + "successfully";
            } else
                return "delete user: " + username + "not correctly (password not matched";
        }
        return "user not found";
    }

    // type1: register successfully
    // type2: register duplicate username
    @PostMapping(value = "/register")
    public String register(@RequestBody String data) {
        System.out.println("Hello register");
        JSONObject userNamePasswordObject = new JSONObject(data);
        String username = userNamePasswordObject.getString("username");
        String password = userNamePasswordObject.getString("password");
        if (authenticationService.getUserByUserName(username) == null) {
            UUID uuid = Generators.randomBasedGenerator().generate();

            // create group
            String groupID = "Group-" + uuid.toString();
            GroupsTable groupsTable = new GroupsTable(groupID, username);
            authenticationService.insertGroup(groupsTable);

            // create user
            String userID = "User-" + uuid.toString();
            System.out.println(userID);
            UsersTable usersTable = new UsersTable(userID, username, password);
            authenticationService.insertUser(usersTable);

            // create relation between group and user
            UsersGroupsTable usersGroupsTable = new UsersGroupsTable(usersTable, groupsTable);
            authenticationService.insertUserGroupRelation(usersGroupsTable);
            return "Hello " + username + " register successed!";
        }
        return "Duplicate username";
    }

    @PostMapping(value = "/group")
    public String createGroup(@RequestBody String data, @RequestHeader("userID") String userID) {
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
            return "create group success";
        }
        else 
            return "create failed: this group name has been used!";
    }

    @PatchMapping(value="/group")
    public String inviteToProjectGroup(@RequestHeader("projectID") String projectID, @RequestHeader("userID") String userID) {
        ProjectsTable projectTable = internalRepresentationService.getProject(projectID);
        GroupsTable groupTable = projectTable.getGroupsTable();
        UsersTable usersTable = authenticationService.getUser(userID);
        UsersGroupsTable relation = new UsersGroupsTable(usersTable, groupTable);
        authenticationService.insertUserGroupRelation(relation);
        return "";
    }

    @PostMapping(value = "/logout")
    public Boolean logout(@RequestHeader("userID") String userID, @RequestBody String data) {
        JSONObject object = new JSONObject(data);
        JSONArray themeIDs = object.getJSONArray("themeIDs");
        List<String> themeIDList = new ArrayList<>();
        themeIDs.forEach(themeID->themeIDList.add((String)themeID));
        return authenticationService.logout(userID, themeIDList);
    }

}
