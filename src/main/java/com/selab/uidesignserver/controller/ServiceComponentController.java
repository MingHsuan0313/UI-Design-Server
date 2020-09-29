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

	@Autowired
	ServiceComponentDao serviceComponentDao;

    @GetMapping(value = "/service/testing")
    public List<ServiceComponentTable> test() throws SQLException {
        return serviceComponentService.findAll();
    }

    // @GetMapping(value = "/service/testing3")
    // public List<ServiceComponent> test3() throws SQLException {
    //     return serviceComponentService.test();
    // }
    
    // @GetMapping(value ="/service/testing4")
    // public List<String> test4(
    // @RequestParam("serviceID") String theID
    // ) throws SQLException {
    //     System.out.println("Hello World");
    //     int id = Integer.parseInt(theID);
    //     return serviceComponentService.findCodeByServiceID(id);
    // }

    @GetMapping(value = "/getServices")
    public String getServices(@RequestParam("uiCategory") String uiCategory,
            @RequestParam("uiName") String uiName,
            @RequestParam("uiType") String uiType,
            @RequestParam("parameterCount") String parameterCount,
            @RequestParam("matchmaking") String isMatchmaking)
            throws SQLException {
        // System.out.println("Hello");
        // System.out.println(uiCategory);
        // System.out.println(parameterCount);
        // System.out.println(isMatchmaking);
        // return "dd";
        return serviceComponentDao.getInputServices(uiCategory,parameterCount, isMatchmaking);
    }

    @GetMapping(value = "/getOutputServices")
    public String getFrameworkTypes(@RequestParam("matchmaking") String isMatchmaking) throws SQLException {
        return serviceComponentDao.getOutputServices(isMatchmaking);
    }

    // @GetMapping(value = "/getArguments")
    // public String getArguments(@RequestParam("serviceID") String serviceID) throws SQLException {
    //     // System.out.println("Hello arguments");
    //     // System.out.println(serviceID);
    //     return serviceComponentDao.getArguments(serviceID);
    // }

    // @GetMapping(value = "/getCode")
    // public String getCodeByServiceID(@RequestParam("serviceID") String serviceID) throws SQLException {
    //     System.out.println("Hello World");
    //     return serviceComponentDao.getCodeByServiceID(serviceID);
    // }

    @PostMapping(value = "/editCode")
	public String postModifiedCode(@RequestBody String data) throws IOException, TemplateException {
		JSONObject dataJsonObject = new JSONObject(data);
        String editedCode = dataJsonObject.getString("code");
        String serviceComponetClassName = dataJsonObject.getString("class");
        System.out.println("edit code here");
        System.out.println(editedCode);
        EditCodeService editCodeService = new EditCodeService(serviceComponetClassName);
        editCodeService.createTempServiceComponent(editedCode);
        String signatureUnique = editCodeService.addEditServiceComponent();
        // if(signatureUnique.length() == 0) {
        //     JSONObject responseObject = new JSONObject();
        //     responseObject.put("statusCode",-1);
        //     responseObject.put("log","Signature is the same");
        //     return responseObject.toString();
        // }

		// EditCodeService editCodeService = new EditCodeService(dataJsonObject.getString("filePath"));
		// editCodeService.updateEditedJavaFile(dataJsonObject.getString("code"));
		String response = editCodeService.buildCode();
		return response;
    }
}
