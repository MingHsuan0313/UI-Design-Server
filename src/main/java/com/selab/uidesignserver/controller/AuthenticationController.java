package com.selab.uidesignserver.controller;

import com.fasterxml.uuid.Generators;
import com.selab.uidesignserver.entity.uiComposition.UsersTable;
import com.selab.uidesignserver.repositoryService.AuthenticationService;
import com.sun.org.apache.xpath.internal.operations.Bool;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping(value="")
    public Boolean authenticate(@RequestBody String data, HttpServletRequest request){
        System.out.println("Hello authentication");
        HttpSession session = request.getSession();

        if(!session.isNew()){
            System.out.println("Has session ID");
            return true;
        }
        JSONObject userNamePasswordObject = new JSONObject(data);
        String username = userNamePasswordObject.getString("username");
        String password = userNamePasswordObject.getString("password");
        if(authenticationService.authenticate(username, password)){
            session.setAttribute("userID", authenticationService.getUserByUserName(username).getUserID());
            return true;
        }else{
            return false;
        }
    }

    @PostMapping(value = "/register")
    public Boolean register(@RequestBody String data){
        System.out.println("Hello register");
        JSONObject userNamePasswordObject = new JSONObject(data);
        String username = userNamePasswordObject.getString("username");
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
