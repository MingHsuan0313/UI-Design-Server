package com.selab.uidesignserver.controller;

import java.sql.SQLException;

import com.selab.uidesignserver.ServiceComponentService.EditCodeService;
import com.selab.uidesignserver.respository.ServiceComponentDao;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceComponentController {

	@Autowired
	ServiceComponentDao serviceComponentDao;

    @GetMapping(value = "/getServices")
    public String getServices(@RequestParam("uiCategory") String uiCategory,
            @RequestParam("parameters") String parameters, @RequestParam("matchmaking") String isMatchmaking)
            throws SQLException {
        System.out.println("Hello");
        System.out.println(uiCategory);
        System.out.println(parameters);
        System.out.println(isMatchmaking);
        return serviceComponentDao.getInputServices(uiCategory, parameters, isMatchmaking);
    }

    @GetMapping(value = "/getOutputServices")
    public String getFrameworkTypes(@RequestParam("matchmaking") String isMatchmaking) throws SQLException {
        return serviceComponentDao.getOutputServices(isMatchmaking);
    }

    @GetMapping(value = "/getArguments")
    public String getArguments(@RequestParam("serviceID") String serviceID) throws SQLException {
        System.out.println("Hello arguments");
        System.out.println(serviceID);
        return serviceComponentDao.getArguments(serviceID);
    }

    @GetMapping(value = "/getCode")
    public String getCodeByServiceID(@RequestParam("serviceID") String serviceID) throws SQLException {
        return serviceComponentDao.getCodeByServiceID(serviceID);
    }

    @PostMapping(value = "/modifyCode")
	public String postModifiedCode(@RequestBody String data) {
		JSONObject dataJsonObject = new JSONObject(data);
        String editedCode = dataJsonObject.getString("code");
        String serviceComponetClassName = dataJsonObject.getString("class");
        EditCodeService editCodeService = new EditCodeService(serviceComponetClassName);
		// EditCodeService editCodeService = new EditCodeService(dataJsonObject.getString("filePath"));
		// editCodeService.updateEditedJavaFile(dataJsonObject.getString("code"));
		// editCodeService.buildCode();
		return "build process string";
    }
}
