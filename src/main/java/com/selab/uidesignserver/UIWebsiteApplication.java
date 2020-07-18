package com.selab.uidesignserver;

import com.selab.uidesignserver.model.PageComponent;
import com.selab.uidesignserver.service.HTMLGenerator;
import freemarker.template.TemplateException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UIWebsiteApplication {

	@Autowired
	HTMLGenerator htmlGenerator;

	public static void main(String[] args) {
		SpringApplication.run(UIWebsiteApplication.class, args);
	}


	@PostMapping(value = "/")
	public String hello(@RequestBody String data) throws IOException, TemplateException, SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306","root","");
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("use demo");
		stmt.executeUpdate("truncate table templates");
		PreparedStatement pps = connection.prepareStatement("insert into pages values(?,?) on duplicate key update pdl = ?");
		htmlGenerator.setPageUICDL(data);

		pps.setString(1, htmlGenerator.getPageUICDL().getString("selector"));
		pps.setString(2, data);
		pps.setString(3, data);
		pps.executeQuery();
		htmlGenerator.parse();
		htmlGenerator.getPageHTML();
		return "hello from server";
	}

}
