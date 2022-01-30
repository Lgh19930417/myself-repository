package com.itfxp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/first")
public class TestController {
    @RequestMapping ("/method")
    public String method(){
        return "this is my first spring boot project!";
    }

}
