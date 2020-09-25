package com.selab.uidesignserver.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;

import com.selab.uidesignserver.respository.NavigationDao;
import com.selab.uidesignserver.service.CanvasToPictureUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NavigationController {
    @Autowired
    NavigationDao navigationDao;

    @PostMapping(value = "/navigate")
	public String navigate(@RequestBody String data) throws SQLException {
		navigationDao.store(data);
		return "store ndl";
	}

	@PostMapping(value = "/exportPicture")
	public String exportPicture(@RequestBody String data) throws IOException {
		String xmlTest = data;

		Base64.Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(CanvasToPictureUtil.transformToPNG(xmlTest));
	}
    
}
