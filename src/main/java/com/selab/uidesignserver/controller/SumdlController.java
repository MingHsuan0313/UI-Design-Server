package com.selab.uidesignserver.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.selab.uidesignserver.entity.uiComposition.SumdlsTable;

@RestController
@RequestMapping("/sumdl")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class SumdlController {
    @DeleteMapping(value = "/trunc")
    public String truncate() throws SQLException {
        return "truncate sumdl tables";
    }

    @GetMapping(value = "")
    public List <SumdlsTable> getSumdls() {

    }

    @DeleteMapping(value = "")
    public String deleteSumdls() {
        return "delete sumdl successfully";
    }

    @PostMapping(value = "")
    public String insertSumdl() {
        return "insert theme successfully";
    }
}