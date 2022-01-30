package com.itfxp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/test")
public class TestController {
    @ResponseBody
    @RequestMapping("/test")
    public String test(){
        return "哈哈哈";
    }
}
