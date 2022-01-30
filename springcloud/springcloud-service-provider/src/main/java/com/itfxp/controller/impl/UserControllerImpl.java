package com.itfxp.controller.impl;

import com.itfxp.bean.User;
import com.itfxp.controller.UserController;
import com.itfxp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/user")
public class UserControllerImpl implements UserController {
    @Autowired
    UserService userService;
    @GetMapping("/queryUsers")
    public List<User> queryUsers() {
        /*try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        return userService.queryUsers();
    }
    @GetMapping("/queryUserById/{id}")
    public User  queryUserById(@PathVariable("id") Integer id) {
        User user = userService.queryUserById(id);
        return user;
    }

}
