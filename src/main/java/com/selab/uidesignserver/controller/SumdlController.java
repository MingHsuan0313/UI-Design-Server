package com.selab.uidesignserver.controller;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.boot.model.naming.ImplicitNamingStrategyComponentPathImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @DeleteMapping(value = "/trunc")
    public String truncate() throws SQLException {
        return "truncate sumdl tables";
    }

    @GetMapping(value = "")
    public SumdlsTable getSumdl() {
        String projectName = "Inventory System";
        return internalRepresentationService.getSumdl(projectName);
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