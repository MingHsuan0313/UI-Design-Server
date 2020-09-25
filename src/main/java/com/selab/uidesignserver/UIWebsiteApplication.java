package com.selab.uidesignserver;

import com.selab.uidesignserver.model.PageComponent;
import com.selab.uidesignserver.respository.NavigationDao;
import com.selab.uidesignserver.respository.PageDao;
import com.selab.uidesignserver.respository.ServiceComponentDao;
import com.selab.uidesignserver.respository.TemplateDao;
import com.selab.uidesignserver.service.HTMLGenerator;
import com.selab.uidesignserver.service.SearchWSDLService;
import com.selab.uidesignserver.model.matchMaking.RankingResult;

import com.selab.uidesignserver.service.CanvasToPictureUtil;
import freemarker.template.TemplateException;
import org.json.JSONObject;
////import org.apache.log4j.Logger;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@SpringBootApplication
@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")
public class UIWebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(UIWebsiteApplication.class, args);
	}
}
