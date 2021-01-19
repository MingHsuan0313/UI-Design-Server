package com.selab.uidesignserver.controller;

import com.selab.uidesignserver.webAppService.GenerateWebAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webapp")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class WebAppController {
    private final GenerateWebAppService generateWebAppService;

    @Autowired
    public WebAppController(GenerateWebAppService generateWebAppService) {
        this.generateWebAppService = generateWebAppService;
    }

    @GetMapping(value = "/generate")
    public String generateWebApp(@RequestParam("projectName") String projectName) {
        return generateWebAppService.trigger(projectName);
    }

    @GetMapping(value = "/getCurrentStatus")
    public String getCurrentStatusOfWebApp(@RequestParam("instanceId") String instanceId) {
        return generateWebAppService.getCurrentStatus(instanceId);
    }
}
