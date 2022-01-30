package com.itfxp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/service")
    public String providerController(){
        return "provicer Controller";
    }
}
