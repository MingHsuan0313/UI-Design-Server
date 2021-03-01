package com.selab.uidesignserver.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.selab.uidesignserver.entity.uiComposition.ThemesTable;

@RestController
@RequestMapping("/theme")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class ThemeController {
    @GetMapping(value = "/trunc")
    public String truncate() throws SQLException {
        return "truncate theme tables";
    }

    @GetMapping(value = "")
    public List<ThemesTable> getThemes() {
    }

    @DeleteMapping(value = "")
    public String deleteThemes() {
        return "delete theme successfully";
    }

    @PostMapping(value = "")
    public String insertTheme() {
        return "insert theme successfully";
    }
}