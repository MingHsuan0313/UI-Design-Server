package com.selab.uidesignserver.controller;

import com.fasterxml.uuid.Generators;
import com.selab.uidesignserver.entity.uiComposition.UsersTable;
import com.selab.uidesignserver.repositoryService.AuthenticationService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping(value="")
    public Boolean authenticate(@RequestBody String data){
        System.out.println("Hello authentication");
        JSONObject userNamePasswordObject = new JSONObject(data);
        String username = userNamePasswordObject.getString("userName");
        String password = userNamePasswordObject.getString("password");
        return authenticationService.authenticate(username, password);
    }

    @PostMapping(value = "/register")
    public Boolean register(@RequestBody String data){
        System.out.println("Hello register");
        JSONObject userNamePasswordObject = new JSONObject(data);
        String username = userNamePasswordObject.getString("userName");
        String password = userNamePasswordObject.getString("password");
        if(authenticationService.getUserByUserName(username)==null){
            UUID uuid = Generators.randomBasedGenerator().generate();
            String userID = "User-" + uuid.toString();
            System.out.println(userID);
            UsersTable usersTable = new UsersTable(userID, username, password);
            authenticationService.insertUser(usersTable);
            return true;
        }
        return false;
    }

}
