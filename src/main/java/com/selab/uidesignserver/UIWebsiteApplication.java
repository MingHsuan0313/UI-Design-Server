package com.selab.uidesignserver;

import com.selab.uidesignserver.model.PageComponent;
import com.selab.uidesignserver.respository.NavigationDao;
import com.selab.uidesignserver.respository.PageDao;
import com.selab.uidesignserver.respository.TemplateDao;
import com.selab.uidesignserver.service.HTMLGenerator;
import com.selab.uidesignserver.service.CanvasToPictureUtil;
import freemarker.template.TemplateException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.*;
import java.util.*;

@SpringBootApplication
@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UIWebsiteApplication {

	@Autowired
	HTMLGenerator htmlGenerator;

	@Autowired
	PageDao pageDao;

	@Autowired
	TemplateDao templateDao;

	@Autowired
	NavigationDao navigationDao;

	public static void main(String[] args) {
		SpringApplication.run(UIWebsiteApplication.class, args);
	}


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

	@GetMapping(value="/trunc")
	public String truncate() throws SQLException {
		templateDao.truncateTable();
		return "truncate tables";
	}

	@PostMapping(value="/navigate")
	public String navigate(@RequestBody String data) throws SQLException {
		navigationDao.store(data);
		return "store ndl";
	}

	@PostMapping(value = "/exportPicture")
	public String exportPicture(@RequestBody String data) throws IOException{
		String xmlTest = data;

		Base64.Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(CanvasToPictureUtil.transformToPNG(xmlTest));
	}




}
