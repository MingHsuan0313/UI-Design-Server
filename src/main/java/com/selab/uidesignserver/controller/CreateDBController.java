package com.selab.uidesignserver.controller;

import java.io.FileWriter;
import java.io.IOException;

import com.cdancy.jenkins.rest.shaded.javax.ws.rs.core.Response;
import com.selab.uidesignserver.dto.CreateDBStateDto;
import com.selab.uidesignserver.service.CreateDBService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/createDB")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class CreateDBController {
    private String path = "";
    private CreateDBService createDBService;

    @Autowired
    public CreateDBController(CreateDBService createDBService) {
        this.createDBService = createDBService;
    }

    @PostMapping(value = "")
    public ResponseEntity<String> createDBSchema(@RequestBody String data) {
        JSONObject requestBody = new JSONObject(data);
        System.out.println("create db here");
        JSONArray databases = requestBody.getJSONArray("databases");
        String filePath = "/home/selab/customized-entity/data/" + requestBody.getString("filePath");
        this.path = requestBody.getString("filePath");
        System.out.println("file path: " + this.path);
        if (this.writeFile(filePath, databases.toString())) {
            return ResponseEntity.status(HttpStatus.OK).body("create file success");
        } else {
            System.out.println("write file failed");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Write File Failed");
        }
    }

    public boolean writeFile(String filePath, String databaseSchema) {
        try {
            FileWriter myWriter = new FileWriter(filePath);
            myWriter.write(databaseSchema);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }
    }

    @GetMapping(value = "/trigger")
    public String triggerCreateDBPipeline(@RequestParam("filePath") String path) {
        if(this.path.length() == 0)
            return "not create file yet";
        return this.createDBService.trigger(path);
    }

    @GetMapping(value = "/getCurrentCreatingState")
    public @ResponseBody CreateDBStateDto getCurrentGeneratingState(@RequestParam("instanceId") String instanceId) {
        return this.createDBService.getCurrentCreatingState(instanceId);
    }
}
