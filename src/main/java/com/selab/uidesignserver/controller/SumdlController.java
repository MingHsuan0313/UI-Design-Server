package com.selab.uidesignserver.controller;

import java.sql.SQLException;
import java.util.List;

import com.selab.uidesignserver.entity.uiComposition.*;
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
import com.selab.uidesignserver.repositoryService.InternalRepresentationService;

@RestController
@RequestMapping("/sumdl")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class SumdlController {
    @Autowired
    InternalRepresentationService internalRepresentationService;

    @GetMapping(value = "")
    public SumdlsTable getSumdl(@RequestHeader("projectName") String projectName) {
        ProjectsTable projectsTable = internalRepresentationService.getProjectByProjectName(projectName);
        String projectID = projectsTable.getProjectID();
        return this.internalRepresentationService.getSumdl(projectID);
    }

    @DeleteMapping(value = "")
    public String deleteSumdl(@RequestHeader("projectName") String projectName) {
        ProjectsTable projectsTable = internalRepresentationService.getProjectByProjectName(projectName);
        String projectID = projectsTable.getProjectID();
        if(this.internalRepresentationService.deleteSumdl(projectID))
            return "delete sumdl successfully";
        else
            return "delete sumdl failed or not found";
    }

    @PostMapping(value = "")
    public String insertSumdl(@RequestBody String data, @RequestHeader("projectName") String projectName) {
        ProjectsTable projectsTable = internalRepresentationService.getProjectByProjectName(projectName);
        String projectID = projectsTable.getProjectID();
        JSONObject dataObject = new JSONObject(data);
        String sumdl = dataObject.getString("sumdl");
        String themeID = dataObject.getString("themeID");
        String pageID = dataObject.getString("pageID");
        ThemesTable themesTable = internalRepresentationService.getThemeById(themeID);
        PagesTable pagesTable = internalRepresentationService.getPageByPageID(pageID);
        SumdlsTable sumdlTable = new SumdlsTable(sumdl, projectsTable, themesTable, pagesTable);
        this.internalRepresentationService.insertSumdl(sumdlTable);
        return "insert theme successfully";
    }

    @DeleteMapping(value = "/trunc")
    public String truncate() throws SQLException {
        this.internalRepresentationService.truncateSumdls();
        return "truncate sumdl tables";
    }
}