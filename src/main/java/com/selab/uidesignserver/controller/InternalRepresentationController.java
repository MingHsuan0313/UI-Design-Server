package com.selab.uidesignserver.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.selab.uidesignserver.entity.uiComposition.NavigationTable;
import com.selab.uidesignserver.entity.uiComposition.PagesTable;
import com.selab.uidesignserver.repositoryService.InternalRepresentationService;
import com.selab.uidesignserver.respository.PageDao;
import com.selab.uidesignserver.respository.TemplateDao;
import com.selab.uidesignserver.service.HTMLGenerator;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import freemarker.template.TemplateException;

@RestController
@RequestMapping("/page")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class InternalRepresentationController {
	
	@Autowired
	InternalRepresentationService internalRepresentationService;

	@PostMapping(value = "/")
	public String process(@RequestBody String data) throws IOException, TemplateException, SQLException {
		JSONObject pdlObject = new JSONObject(data);
		String id = pdlObject.getString("id");
		String selector = pdlObject.getString("selector");
		String layout = pdlObject.getString("layout");
		String pdl = data;
		PagesTable pagesTable = new PagesTable(Integer.parseInt(id),selector,layout,pdl);
		internalRepresentationService.insertPage(pagesTable);
		return "insert page";
	}
	
	@GetMapping(value = "/")
	public List<PagesTable> getTables() {
		return internalRepresentationService.getTables();	
	}

	@DeleteMapping(value = "/trunc")
	public String truncate() throws SQLException {
		internalRepresentationService.truncateTables();
		return "truncate tables";
	}
}