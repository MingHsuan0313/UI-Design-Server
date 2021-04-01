package com.selab.uidesignserver.controller;

import com.selab.uidesignserver.repositoryService.AuthenticationService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
