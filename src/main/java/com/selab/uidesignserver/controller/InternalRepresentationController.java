package com.selab.uidesignserver.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.selab.uidesignserver.respository.PageDao;
import com.selab.uidesignserver.respository.TemplateDao;
import com.selab.uidesignserver.service.HTMLGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import freemarker.template.TemplateException;

@RestController
public class InternalRepresentationController {
    @Autowired
    PageDao pageDao;
    
    @Autowired
    TemplateDao templateDao;

	@Autowired
	HTMLGenerator htmlGenerator;

	@PostMapping(value = "/")
	public String process(@RequestBody String data) throws IOException, TemplateException, SQLException {
		System.out.println(data);
		htmlGenerator.setPageUICDL(data);

		pageDao.addPage(data);
		htmlGenerator.parse();
		htmlGenerator.getPageHTML();
		return "hello from server";
	}

	@GetMapping(value = "/")
	public String getPages() throws IOException, TemplateException, SQLException {
		return pageDao.getPages();
	}

	@GetMapping(value = "/trunc")
	public String truncate() throws SQLException {
		templateDao.truncateTable();
		return "truncate tables";
	}
}
