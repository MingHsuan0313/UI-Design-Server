package com.selab.uidesignserver.controller;

import com.selab.uidesignserver.dto.WebAppGeneratingStateDto;
import com.selab.uidesignserver.webAppService.GenerateWebAppService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(value = "/getCurrentGeneratingState")
    public @ResponseBody WebAppGeneratingStateDto getCurrentGeneratingState(@RequestParam("instanceId") String instanceId) {
        return generateWebAppService.getCurrentGeneratingState(instanceId);
    }
}
