package com.itfxp.controller;

import com.itfxp.bean.UserInfo;
import com.itfxp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping("/findAllUsers")
    public List<E> findAllUsers(){
        List<UserInfo> allUsers = userService.findAllUsers();
        return allUsers;
    }

}
