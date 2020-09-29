package com.selab.uidesignserver.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.selab.uidesignserver.ServiceComponentService.EditCodeService;
import com.selab.uidesignserver.entity.ServiceComponentTable;
import com.selab.uidesignserver.repositoryService.ServiceComponentService;
import com.selab.uidesignserver.respository.ServiceComponentDao;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import freemarker.template.TemplateException;

@RestController
@RequestMapping("/services")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class ServiceComponentController {

    private ServiceComponentService serviceComponentService;

    @Autowired
    public ServiceComponentController(ServiceComponentService theServiceComponentService) {
        serviceComponentService = theServiceComponentService;
    }

    // uiCategory: input-control, informative, containers, navigation
    // uiType: form, button, text, icon...etc
    // parameterCount: # of sub UI Component eg: form with four input-text
    // matchmaking: using query stage2 or not
    @GetMapping(value = "/getServices")
    public String getServices(@RequestParam("uiCategory") String uiCategory, @RequestParam("uiName") String uiName,
            @RequestParam("uiType") String uiType, @RequestParam("parameterCount") String argumentCount,
            @RequestParam("matchmaking") String isMatchmaking) throws SQLException {
        return serviceComponentService.getServiceComponentsWithRestriction(Integer.parseInt(argumentCount)).toString();
    }

    @GetMapping(value = "/getCode")
    public String getCode(@RequestParam("serviceID") String serviceID) throws SQLException {
        return serviceComponentService.getServiceComponentCode(Integer.parseInt(serviceID));
    }

    @GetMapping(value = "/getArguments")
    public List<String> getArguments(@RequestParam("serviceID") String serviceID) throws SQLException {
        return serviceComponentService.getArgumentsByServiceID(Integer.parseInt(serviceID));
    }

    // return build log message
    @PostMapping(value = "/editServiceComponent")
    public String editServiceComponent(@RequestBody String data) throws IOException, TemplateException {
        JSONObject requestBodyObject = new JSONObject(data);
        String editedServiceComponentCode = requestBodyObject.getString("code");
        // class name with package name
        String serviceComponentClassName = requestBodyObject.getString("class");
        EditCodeService editCodeService = new EditCodeService(serviceComponentClassName);

        // locate absolute file path depends on package and class name
        // editCodeService.createTempServiceComponent(editedServiceComponentCode);
        // String signatureUnique = editCodeService.addEditServiceComponent();
        // if(signatureUnique.length() == 0) {
        // JSONObject responseObject = new JSONObject();
        // responseObject.put("statusCode",-1);
        // responseObject.put("log","Signature is the same");
        // return responseObject.toString();
        // }

        // EditCodeService editCodeService = new
        // EditCodeService(dataJsonObject.getString("filePath"));
        // editCodeService.updateEditedJavaFile(dataJsonObject.getString("code"));
        // String response = editCodeService.buildCode();
        String response = editCodeService.triggerJenkinsBuild();
        System.out.println(response);

        return "";
    }
    // @PostMapping(value = "/editCode")
    // public String postModifiedCode(@RequestBody String data) throws IOException,
    // TemplateException {
    // JSONObject dataJsonObject = new JSONObject(data);
    // String editedCode = dataJsonObject.getString("code");
    // String serviceComponetClassName = dataJsonObject.getString("class");
    // System.out.println("edit code here");
    // System.out.println(editedCode);
    // EditCodeService editCodeService = new
    // EditCodeService(serviceComponetClassName);
    // editCodeService.createTempServiceComponent(editedCode);
    // String signatureUnique = editCodeService.addEditServiceComponent();
    // // if(signatureUnique.length() == 0) {
    // // JSONObject responseObject = new JSONObject();
    // // responseObject.put("statusCode",-1);
    // // responseObject.put("log","Signature is the same");
    // // return responseObject.toString();
    // // }

    // // EditCodeService editCodeService = new
    // EditCodeService(dataJsonObject.getString("filePath"));
    // // editCodeService.updateEditedJavaFile(dataJsonObject.getString("code"));
    // String response = editCodeService.buildCode();
    // return response;
    // }
}
