package com.selab.uidesignserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


@SpringBootApplication
@RestController
@RequestMapping("/selab")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class UIWebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(UIWebsiteApplication.class, args);
	}
}
