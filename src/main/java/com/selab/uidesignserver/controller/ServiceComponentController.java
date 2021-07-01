package com.selab.uidesignserver.controller;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.selab.uidesignserver.ServiceComponentService.EditCodeService;
import com.selab.uidesignserver.entity.serviceComponent.*;
import com.selab.uidesignserver.entity.uiComposition.*;

import com.selab.uidesignserver.repositoryService.ServiceComponentService;
import com.selab.uidesignserver.service.FreeMarkerUtil;

import org.json.JSONArray;
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
import org.w3c.dom.Document;

import javax.xml.parsers.*;

import freemarker.template.Template;
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

    @GetMapping(value = "/getCode")
    public String getCode(@RequestParam("serviceID") String serviceID) throws SQLException {
        return serviceComponentService.getServiceComponentCode(Integer.parseInt(serviceID));
    }

    @GetMapping(value = "/getArguments")
    public String getArguments(@RequestParam("serviceID") String serviceID) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "timhsieh", "ji3yjo4dj4x87");
        List<JSONObject> arguments = new ArrayList<JSONObject>();
        String query = "select * from Argument left join Argument_Annotation on Argument.argumentID = Argument_Annotation.argumentID left join AnnotationType on Argument_Annotation.annotationID = AnnotationType.annotationID where serviceID = "
                + serviceID + ";";
        System.out.println(query);
        Statement statement = connection.createStatement();
        statement.executeUpdate("use ServiceDB");
        ResultSet rs = statement.executeQuery(query);
        Map<String, String> nameMap = new HashMap<>();

        while (rs.next()) {
            if (nameMap.containsKey(rs.getString("name"))) {
                continue;
            }
            JSONObject argument = new JSONObject();
            if (rs.getString("name").equals("response"))
                continue;
            argument.put("name", rs.getString("name"));
            if(rs.getString("type") == null)
                argument.put("annotationType","");
            else
                argument.put("annotationType", rs.getString("type"));
            nameMap.put(rs.getString("name"), "");
            if (rs.getString("complexTypeID") != null) {
                // complexTypeID 4 = java.lang.Integer
                if (rs.getString("complexTypeID").equals(("4"))) {
                    argument.put("primitiveType", "int");
                    argument.put("complexType", "");
                    argument.put("isComplexType", false);
                    arguments.add(argument);
                    continue;
                }
                // has complexType, need to be further processed
                argument.put("primitiveType", "");
                if (!argument.get("name").equals("validation")) {
                    Map<String, String> propertyMap = new HashMap<>();
                    Statement statement2 = connection.createStatement();
                    statement2.executeUpdate("use ServiceDB");
                    String query2 = "select * from ComplexType_Property where complexTypeID = "
                            + rs.getString("complexTypeID");
                    // System.out.println(query2);
                    ResultSet rs2 = statement2.executeQuery(query2);
                    while (rs2.next()) {
                        // System.out.println("PropertyID = " + rs2.getString("propertyID"));
                        propertyMap.put(rs2.getString("propertyID"), "");
                    }
                    List<JSONObject> classArguments = new ArrayList<JSONObject>();
                    for (Map.Entry<String, String> entry : propertyMap.entrySet()) {
                        String propertyID = entry.getKey();
                        Statement statement3 = connection.createStatement();
                        statement3.executeUpdate("use ServiceDB");
                        String query3 = "select * from Property where propertyID = " + propertyID + ";";
                        ResultSet rs3 = statement3.executeQuery(query3);
                        while (rs3.next()) {
                            JSONObject classArgument = new JSONObject();
                            classArgument.put("name", rs.getString("name") + "-" + rs3.getString("propertyName"));
                            classArgument.put("primitiveType", rs3.getString("primitiveType"));
                            classArgument.put("annotationType", "RequestParam");
                            classArgument.put("isComplexType", false);
                            classArguments.add(classArgument);
                        }
                    }
                    argument.put("arguments", classArguments);
                    argument.put("isComplexType", true);
                    arguments.add(argument);
                } else
                    argument.put("complexTypeID", rs.getString("complexTypeID"));
            } else {
                argument.put("primitiveType", rs.getString("primitiveType"));
                argument.put("complexType", "");
                argument.put("isComplexType", false);
                arguments.add(argument);
            }
        }
        connection.close();
        return arguments.toString();
    }


    // return build log message
    @PostMapping(value = "/editServiceComponent")
    public String editServiceComponent(@RequestBody String data) throws IOException, TemplateException {
        JSONObject requestBodyObject = new JSONObject(data);
        // System.out.println(requestBodyObject.getString("code"));
        String editedServiceComponentCode = requestBodyObject.getString("code");
        // class name with package name
        String serviceComponentClassName = requestBodyObject.getString("class");

        EditCodeService editCodeService = new EditCodeService(serviceComponentClassName);
        JSONObject editCodeState = editCodeService.editServiceComponent(editedServiceComponentCode);
        // means signature not unique
        if (editCodeState.getInt("statusCode") == -1)
            return editCodeState.toString();

        // if signature is unique than pass to build stage
        JSONObject buildResultObject = editCodeService.buildCode();

        buildResultObject.put("log", buildResultObject.getString("log"));
        buildResultObject.put("statusCode", buildResultObject.getInt("statusCode"));
        return buildResultObject.toString();
    }
}