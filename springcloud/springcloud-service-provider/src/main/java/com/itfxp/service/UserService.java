package com.itfxp.service;

import com.itfxp.bean.User;

import java.util.List;

public interface UserService {
    public List<User> queryUsers();
    public User queryUserById(Integer id);
}
