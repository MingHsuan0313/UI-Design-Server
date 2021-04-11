package com.selab.uidesignserver.controller;

import java.sql.SQLException;
import java.util.List;

import com.selab.uidesignserver.entity.uiComposition.GroupsTable;
import com.selab.uidesignserver.entity.uiComposition.UsersTable;
import com.selab.uidesignserver.entity.uiComposition.PagesTable;
import com.selab.uidesignserver.entity.uiComposition.ProjectsTable;
import com.selab.uidesignserver.repositoryService.AuthenticationService;
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
import com.selab.uidesignserver.entity.uiComposition.ThemesTable;
import com.selab.uidesignserver.repositoryService.InternalRepresentationService;

@RestController
@RequestMapping("/theme")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class ThemeController {
    @Autowired
    InternalRepresentationService internalRepresentationService;

    @Autowired
    AuthenticationService authenticationService;

    @DeleteMapping(value = "/trunc")
    public String truncate() throws SQLException {
        this.internalRepresentationService.truncateThemes();
        return "truncate theme tables";
    }

    @GetMapping(value = "")
    public List<ThemesTable> getThemes(@RequestHeader("projectName") String projectName) {
        ProjectsTable projectsTable = internalRepresentationService.getProjectByProjectName(projectName);
        String projectID = projectsTable.getProjectID();
        return this.internalRepresentationService.getThemes(projectID);
    }

    @DeleteMapping(value = "")
    public String deleteThemes(@RequestHeader("projectName") String projectName) {
        ProjectsTable projectsTable = internalRepresentationService.getProjectByProjectName(projectName);
        String projectID = projectsTable.getProjectID();
        if(this.internalRepresentationService.deleteThemes(projectID))
            return "delete themes";
        else {
            return "delete themes failed (not found)";
        }
    }

    @DeleteMapping(value = "themeId")
    public String deleteThemeByID(@RequestHeader("projectName") String projectName, @RequestHeader("themeID") String themeID) {
        if(this.internalRepresentationService.deleteThemes(themeID))
            return "delete themes";
        else {
            return "delete themes failed (not found)";
        }
    }

    @PostMapping(value = "")
    public String insertTheme(@RequestBody String data, @RequestHeader("projectName") String projectName) {
        ProjectsTable projectsTable = internalRepresentationService.getProjectByProjectName(projectName);
		JSONObject themeObject = new JSONObject(data);
        String themeID = themeObject.getString("themeID");
        String userID = themeObject.getString("userID");
        UsersTable usersTable = authenticationService.getUser(userID);
        String themeName = themeObject.getString("themeName");
        ThemesTable themeTable = new ThemesTable(themeID, themeName, projectsTable, usersTable, true);
        this.internalRepresentationService.insertTheme(themeTable);
        return "insert theme successfully";
    }


}