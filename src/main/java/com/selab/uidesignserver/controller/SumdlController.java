package com.selab.uidesignserver.controller;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.boot.model.naming.ImplicitNamingStrategyComponentPathImpl;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.selab.uidesignserver.entity.uiComposition.SumdlsTable;
import com.selab.uidesignserver.repositoryService.InternalRepresentationService;

@RestController
@RequestMapping("/sumdl")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class SumdlController {
    @Autowired
    InternalRepresentationService internalRepresentationService;

    @GetMapping(value = "")
    public SumdlsTable getSumdl(@RequestHeader("projectName") String projectName) {
        return this.internalRepresentationService.getSumdl(projectName);
    }

    @DeleteMapping(value = "")
    public String deleteSumdl(@RequestHeader("projectName") String projectName) {
        this.internalRepresentationService.deleteSumdl(projectName);
        return "delete sumdl successfully";
    }

    @PostMapping(value = "")
    public String insertSumdl(@RequestBody String data, @RequestHeader("projectName") String projectName) {
        SumdlsTable sumdlTable = new SumdlsTable(data, projectName);
        this.internalRepresentationService.insertSumdl(sumdlTable);
        return "insert theme successfully";
    }

    @DeleteMapping(value = "/trunc")
    public String truncate() throws SQLException {
        this.internalRepresentationService.truncateSumdls();
        return "truncate sumdl tables";
    }
}