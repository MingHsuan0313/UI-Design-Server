package com.selab.uidesignserver.controller;

import java.sql.SQLException;
import java.util.List;

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
    InternalRepresentationService internalReprsentationService;

    @GetMapping(value = "/trunc")
    public String truncate() throws SQLException {
        this.internalReprsentationService.truncateThemes();
        return "truncate theme tables";
    }

    @GetMapping(value = "/get")
    public List<ThemesTable> getThemes(@RequestHeader("projectName") String projectName) {
        return this.internalReprsentationService.getThemes(projectName);
    }

    @DeleteMapping(value = "/delete")
    public String deleteThemes(@RequestHeader("projectName") String projectName) {
        if(this.internalReprsentationService.deleteThemes(projectName))
            return "delete themes";
        else {
            return "delete themes failed (not found)";
        }
    }

    @PostMapping(value = "/insert")
    public String insertTheme(@RequestBody String data, @RequestHeader("projectName") String projectName) {
		JSONObject themeObject = new JSONObject(data);
        String id = themeObject.getString("id");
        String themeName = themeObject.getString("name");
        ThemesTable themeTable = new ThemesTable(id, projectName, themeName);
        this.internalReprsentationService.insertTheme(themeTable);
        return "insert theme successfully";
    }
}