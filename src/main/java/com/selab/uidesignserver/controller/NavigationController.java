package com.selab.uidesignserver.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;

import com.selab.uidesignserver.entity.uiComposition.NavigationsTable;
import com.selab.uidesignserver.repositoryService.InternalRepresentationService;
import com.selab.uidesignserver.service.CanvasToPictureUtil;

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
		return internalRepresentationService.getNavigation(projectName);
	}

	@DeleteMapping(value = "")
	public String deleteNavigations(@RequestHeader("projectName") String projectName) {
		internalRepresentationService.deleteNavigation(projectName);
		return "delete navigations successfully";
	}

	@PostMapping(value = "")
	public String insertNavigation(@RequestBody String data, @RequestHeader("projectName") String projectName) throws IOException, TemplateException, SQLException {
		NavigationsTable navigationTable = new NavigationsTable(data, projectName);
		internalRepresentationService.insertNaivigation(navigationTable);
		return "insert page";
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