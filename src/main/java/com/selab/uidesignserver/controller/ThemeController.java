package com.selab.uidesignserver.controller;

import java.sql.SQLException;
import java.util.List;

import com.selab.uidesignserver.entity.uiComposition.GroupsTable;
import com.selab.uidesignserver.entity.uiComposition.UsersTable;
import com.selab.uidesignserver.entity.uiComposition.PagesTable;
import com.selab.uidesignserver.entity.uiComposition.ProjectsTable;
import com.selab.uidesignserver.repositoryService.AuthenticationService;

import org.json.JSONArray;
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

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/theme")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class ThemeController {
    @Autowired
    InternalRepresentationService internalRepresentationService;

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping(value = "/refresh/used")
    public String refresh() {
        this.internalRepresentationService.refreshAllThemes();
        return "unset all theme's used bit";
    }

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
        System.out.println("delete theme");
        ProjectsTable projectsTable = internalRepresentationService.getProjectByProjectName(projectName);
        String projectID = projectsTable.getProjectID();
        if(this.internalRepresentationService.deleteThemesByProjectId(projectID))
            return "delete themes";
        else {
            return "delete themes failed (not found)";
        }
    }

    @DeleteMapping(value = "/themeId")
    public String deleteThemeByID(@RequestHeader("projectName") String projectName, @RequestHeader("themeID") String themeID) {
        System.out.println("delete ny ud");
        if(this.internalRepresentationService.deleteThemesByProjectId(themeID))
            return "delete themes";
        else {
            return "delete themes failed (not found)";
        }
    }

    @PostMapping(value = "/themes")
    public String deleteThemesByIds(@RequestBody String data) {
        JSONArray themeIds = new JSONObject(data).getJSONArray("themeIDs");
        String[] ids = new String[themeIds.length()];
        for(int index = 0; index < ids.length; index++) {
            String id = themeIds.getJSONObject(index).getString("id");
            ids[index] = id;
        }
        this.internalRepresentationService.deleteThemesByIds(ids);
        return "Hello";
    }

    @PostMapping(value = "")
    public String insertTheme(@RequestBody String data, @RequestHeader("projectName") String projectName, HttpSession session) {
        ProjectsTable projectsTable = internalRepresentationService.getProjectByProjectName(projectName);
        JSONObject themeObject = new JSONObject(data);
        String themeID = themeObject.getString("themeID");
        String userID = themeObject.getString("userID");
        UsersTable usersTable = authenticationService.getUser(userID);
        String themeName = themeObject.getString("themeName");
        ThemesTable themeTable = new ThemesTable(themeID, themeName, projectsTable, usersTable, true);
        this.internalRepresentationService.insertTheme(themeTable);
        List<String> openedThemeIDList = (List<String>) session.getAttribute("openedThemeIDList");
        openedThemeIDList.add(themeID);
        session.setAttribute("openedThemeIDList", openedThemeIDList);
        return "insert theme successfully";
    }

    @PostMapping(value = "/setUnused")
    public String setUnused(@RequestBody String themeID, @RequestHeader("projectName") String projectName, HttpSession session) {
        System.out.println("set theme unused");
        ThemesTable themesTable = internalRepresentationService.getThemeById(themeID);
        themesTable.setUsed(false);
        internalRepresentationService.insertTheme(themesTable);
        return "set the theme unused successfully";
    }
}
