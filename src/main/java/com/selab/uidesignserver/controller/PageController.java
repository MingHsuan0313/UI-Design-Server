package com.selab.uidesignserver.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.selab.uidesignserver.entity.uiComposition.NavigationsTable;
import com.selab.uidesignserver.entity.uiComposition.PagesTable;
import com.selab.uidesignserver.repositoryService.InternalRepresentationService;

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
public class PageController {
	
	@Autowired
	InternalRepresentationService internalRepresentationService;

	@PostMapping(value = "")
	public String insertPage(@RequestBody String data) throws IOException, TemplateException, SQLException {
		// JSONObject pdlObject = new JSONObject(data);
		// String id = pdlObject.getString("id");
		// String selector = pdlObject.getString("selector");
		// String layout = pdlObject.getString("layout");
		// String pdl = data;
		// PagesTable pagesTable = new PagesTable(Integer.parseInt(id),selector,layout,pdl);
		// internalRepresentationService.insertPage(pagesTable);
		return "insert page";
	}

	@DeleteMapping(value = "")
	public String deletePages() {
		String projectName = "Inventory System";
		internalRepresentationService.deletePages(projectName);
		return "delete pages successfully";
	}
	
	@GetMapping(value = "")
	public List<PagesTable> getPages() {
		String projectName = "Inventory System";
		return internalRepresentationService.getPages(projectName);	
	}

	@DeleteMapping(value = "/trunc")
	public String truncate() throws SQLException {
		internalRepresentationService.truncatePages();
		return "truncate tables";
	}
}