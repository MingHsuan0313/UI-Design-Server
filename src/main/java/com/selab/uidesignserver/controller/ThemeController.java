package com.selab.uidesignserver.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
        return "truncate theme tables";
    }

    @GetMapping(value = "")
    public List<ThemesTable> getThemes() {
        String projectName = "Inventory System";
        return this.internalReprsentationService.getThemes(projectName);
    }

    @DeleteMapping(value = "")
    public String deleteThemes() {
        String projectName = "Inventory System";
        this.internalReprsentationService.deleteThemes(projectName);
        return "delete themes";
    }

    @PostMapping(value = "")
    public String insertTheme() {
        String projectName = "Inventory System";
        return "insert theme successfully";
    }
}