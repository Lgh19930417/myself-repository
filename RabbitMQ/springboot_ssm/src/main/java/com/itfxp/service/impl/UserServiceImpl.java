package com.itfxp.service.impl;

import com.itfxp.bean.UserInfo;
import com.itfxp.mapper.UserMapper;
import com.itfxp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public List<UserInfo> findAllUsers() {
        List<UserInfo> allUsers = userMapper.findAllUsers();
        return allUsers;
    }
}
