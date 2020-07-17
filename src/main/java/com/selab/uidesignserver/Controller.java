package com.selab.uidesignserver;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @RequestMapping("/")
    private String test() {
        return "Hello World";
    }
    
}