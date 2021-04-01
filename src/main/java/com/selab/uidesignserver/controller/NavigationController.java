package com.selab.uidesignserver.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;

import com.google.gson.JsonObject;
import com.selab.uidesignserver.entity.uiComposition.NavigationsTable;
import com.selab.uidesignserver.entity.uiComposition.PagesTable;
import com.selab.uidesignserver.entity.uiComposition.ProjectsTable;
import com.selab.uidesignserver.entity.uiComposition.ThemesTable;
import com.selab.uidesignserver.repositoryService.InternalRepresentationService;
import com.selab.uidesignserver.service.CanvasToPictureUtil;

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
@RequestMapping("/navigation")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class NavigationController {
	
	@Autowired
	InternalRepresentationService internalRepresentationService;
   
	@GetMapping(value = "")
	public NavigationsTable getNavigation(@RequestHeader("projectName") String projectName) {
		ProjectsTable projectsTable = internalRepresentationService.getProjectByProjectName(projectName);
		String projectID = projectsTable.getProjectID();
		return internalRepresentationService.getNavigation(projectID);
	}

	@DeleteMapping(value = "")
	public String deleteNavigation(@RequestHeader("projectName") String projectName) {
		ProjectsTable projectsTable = internalRepresentationService.getProjectByProjectName(projectName);
		String projectID = projectsTable.getProjectID();
		if(internalRepresentationService.deleteNavigation(projectID))
			return "delete navigations successfully";
		else
			return "delete navigations failed or not found";
	}

	@PostMapping(value = "")
	public String insertNavigation(@RequestBody String data, @RequestHeader("projectName") String projectName) throws IOException, TemplateException, SQLException {
		ProjectsTable projectsTable = internalRepresentationService.getProjectByProjectName(projectName);
		JSONObject dataObject = new JSONObject(data);
		String ndl = dataObject.getString("ndl");
		String themeID = dataObject.getString("themeID");
		String pageID = dataObject.getString("pageID");
		ThemesTable themesTable = internalRepresentationService.getThemeById(themeID);
		PagesTable pagesTable = internalRepresentationService.getPageByPageID(pageID);
		NavigationsTable navigationTable = new NavigationsTable(ndl, projectsTable, themesTable, pagesTable);
		internalRepresentationService.insertNaivigation(navigationTable);
		return "insert navigation";
	}

	@PostMapping(value = "/exportPicture")
	public String exportPicture(@RequestBody String data) throws IOException {
		String xmlTest = data;
		Base64.Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(CanvasToPictureUtil.transformToPNG(xmlTest));
	}

    @DeleteMapping(value = "/trunc")
	public String truncateNavigations() throws SQLException {
		internalRepresentationService.truncateNavigations();
		return "truncate tables";
	}
}