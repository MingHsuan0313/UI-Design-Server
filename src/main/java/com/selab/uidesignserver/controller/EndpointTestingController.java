package com.selab.uidesignserver.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.selab.uidesignserver.service.FreeMarkerUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@RestController
@RequestMapping("/testing")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class EndpointTestingController {
    private String apiServerUrl;
    private String projectName;

    public EndpointTestingController() {
        this.apiServerUrl = "http://140.112.90.144:7122/";
        this.projectName = "InventorySystemBackendMarksTonyModify";
    }

    @PostMapping(value = "/testingFreeMarker")
    public String testingFreeMarker(@RequestBody String data) throws TemplateException, IOException {
        Template mainCodeTemplate = FreeMarkerUtil.getInstance().getTemplate("freemarkerTest.ftl");
        Map<String, Object> templateData = new HashMap<>();
        JSONArray requestBodyObject = new JSONArray(data);
        ArrayList<JSONObject> schools = new ArrayList<JSONObject>();
        for (int index = 0; index < requestBodyObject.length(); index++) {
            schools.add(requestBodyObject.getJSONObject(index));
        }
        templateData.put("schools", schools);
        System.out.print(schools.toString());
        Writer writer = new StringWriter();
        mainCodeTemplate.process(templateData, writer);
        return writer.toString();
    }

    public ArrayList<JSONObject> generateParams(JSONObject serviceComponent) {
        ArrayList<JSONObject> params = new ArrayList<JSONObject>();
        JSONArray arguments = serviceComponent.getJSONArray("arguments");
        System.out.println(serviceComponent.getJSONArray("arguments").toString());
        if (arguments.length() == 0)
            return params;
        else {
            for (int index = 0; index < arguments.length(); index++) {
                JSONObject argument = arguments.getJSONObject(index);
                // means this argument is complexType
                if(argument.getString("name").split("-").length > 1)
                    continue;
                System.out.println("argument: " + argument.getString("name"));
                System.out.println(argument.toString());
                params.add(argument);
            }
        }
        return params;
    }
    
    public void setFakeDataForVarValue(JSONArray serviceComponentPool) {
        
    }
    
    public JSONObject setComplexArgUrl(JSONObject complexArgTypeUrl) {
        JSONObject complexArg = new JSONObject();
        Iterator<String> complexArgNames = complexArgTypeUrl.keys();
        while(complexArgNames.hasNext()) {
            String complexArgName = complexArgNames.next();
            String initUrl = this.apiServerUrl + "/" + this.projectName + "/" + complexArgTypeUrl.getJSONObject(complexArgName).getString("initUrl");
            complexArgTypeUrl.getJSONObject(complexArgName).put("initUrl",initUrl);
            JSONArray args = complexArgTypeUrl.getJSONObject(complexArgName).getJSONArray("args");
            for(int index = 0;index < args.length();index++) {
                String setterUrl = this.apiServerUrl + "/" + this.projectName + "/" + args.getJSONObject(index).getString("setterUrl");
                args.getJSONObject(index).put("setterUrl",setterUrl);
            }
        }
        return complexArgTypeUrl;
    }

    @PostMapping(value = "/testingServiceComponentPool")
    public String testingServiceComponentPool(@RequestBody String data) throws TemplateException, IOException {
        System.out.println("Start Generating Testcases File");
        String templateFileName = "jestTestCase.ftl";
        Template mainCodeTemplate = FreeMarkerUtil.getInstance().getTemplate(templateFileName);
        Map<String, Object> templateData = new HashMap<>();

        JSONArray serviceComponentPool = new JSONArray(data);
        this.setFakeDataForVarValue(serviceComponentPool);

        ArrayList<JSONObject> operations = new ArrayList<JSONObject>();

        for (int index = 0; index < serviceComponentPool.length(); index++) {
            JSONObject serviceComponent = serviceComponentPool.getJSONObject(index);
            System.out.println("TestCases" + index + " ServiceComponent: " + serviceComponent.getString("name"));
            if (serviceComponent.getString("name").equals("login"))
                continue;

            String initServiceUrl = this.apiServerUrl + "/" + this.projectName + "/"
                    + serviceComponent.getString("initServiceUrl");
            String invokeServiceUrl = this.apiServerUrl + "/" + this.projectName + "/"
                    + serviceComponent.getString("invokeServiceUrl");
            JSONObject operation = new JSONObject();
            JSONObject argComplexTypeUrl = this.setComplexArgUrl(serviceComponent.getJSONObject("argComplexTypeUrl"));
            System.out.println("****************");
            System.out.println(argComplexTypeUrl.toString());
            System.out.println("****************");

            if (!argComplexTypeUrl.keys().hasNext()) {
                // System.out.println(serviceComponent.getString("name") + " " + "empty");
                operation.put("argComplexTypeUrl", "");
            } else {
                operation.put("argComplexTypeUrl", argComplexTypeUrl);
            }
            Iterator<String> keys = argComplexTypeUrl.keys();
            ArrayList<String> argNames = new ArrayList<String>();
            while (keys.hasNext()) {
                argNames.add(keys.next());
            }
            ArrayList<JSONObject> params = this.generateParams(serviceComponent);
            operation.put("name", serviceComponent.getString("name"));
            operation.put("initServiceUrl", initServiceUrl);
            operation.put("invokeServiceUrl", invokeServiceUrl);
            operation.put("complexArgs", argNames);
            operation.put("params",params);
            operation.put("httpMethod",serviceComponent.getString("httpMethod"));
            operations.add(operation);
        }
        // System.out.println(operations.toString());
        templateData.put("operations", operations);
        Writer writer = new StringWriter();
        mainCodeTemplate.process(templateData, writer);
        // Gson gson = new Gson();
        // System.out.println(gson.toJson(operations));
        // System.out.println(writer.toString());
        return writer.toString();
    }
}
