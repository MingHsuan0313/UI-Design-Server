package com.selab.uidesignserver.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.selab.uidesignserver.entity.uiComposition.PagesTable;
import com.selab.uidesignserver.entity.uiComposition.ThemesTable;
import com.selab.uidesignserver.repositoryService.InternalRepresentationService;

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

import freemarker.template.TemplateException;

@RestController
@RequestMapping("/page")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class PageController {
	
	@Autowired
	InternalRepresentationService internalRepresentationService;

	@PostMapping(value = "")
	public String insertPage(@RequestBody String data, @RequestHeader("projectName") String projectName, @RequestHeader("themeId") String themeId) throws IOException, TemplateException, SQLException {
		System.out.println("hello insert page");
		JSONObject pdlObject = new JSONObject(data);
		String id = pdlObject.getString("id");
		String name = pdlObject.getString("name");
		String pdl = data;
		ThemesTable themeTable = this.internalRepresentationService.getThemeById(themeId);
		System.out.println("hello table " + themeTable.getThemeName());
		PagesTable pagesTable = new PagesTable(id, name, pdl, projectName, themeTable);
		internalRepresentationService.insertPage(pagesTable);
		return "insert page";
	}

	@DeleteMapping(value = "")
	public String deletePages(@RequestHeader("projectName") String projectName) {
		if(internalRepresentationService.deletePages(projectName)) {
			return "delete pages successfully";
		}
		else {
			return "delete page failed (not found)";
		}
	}
	
	@GetMapping(value = "")
	public List<PagesTable> getPages(@RequestHeader("projectName") String projectName) {
		return internalRepresentationService.getPages(projectName);	
	}

	@DeleteMapping(value = "/trunc")
	public String truncate() throws SQLException {
		System.out.println("truncate page tables");
		internalRepresentationService.truncatePages();
		return "truncate tables";
	}
}