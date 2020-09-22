package com.selab.uidesignserver;

import com.selab.uidesignserver.model.PageComponent;
import com.selab.uidesignserver.respository.NavigationDao;
import com.selab.uidesignserver.respository.PageDao;
import com.selab.uidesignserver.respository.ServiceComponentDao;
import com.selab.uidesignserver.respository.TemplateDao;
import com.selab.uidesignserver.service.HTMLGenerator;
import com.selab.uidesignserver.service.CanvasToPictureUtil;
import com.selab.uidesignserver.service.EditCodeService;

import freemarker.template.TemplateException;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;

@SpringBootApplication
@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UIWebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(UIWebsiteApplication.class, args);
	}
}
