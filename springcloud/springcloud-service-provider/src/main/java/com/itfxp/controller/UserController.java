package com.itfxp.controller;

import com.itfxp.bean.User;

import java.util.List;

public interface UserController {
    public List<User> queryUsers();
    public User  queryUserById(Integer id);

}
