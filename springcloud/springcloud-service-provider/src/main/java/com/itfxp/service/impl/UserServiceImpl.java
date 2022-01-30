package com.itfxp.service.impl;

import com.itfxp.bean.User;
import com.itfxp.mapper.UserMapper;
import com.itfxp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    public List<User> queryUsers() {
        return userMapper.selectAll();
    }

    public User queryUserById(Integer id) {
        User user = new User();
        user.setId(id);
        User user1 = userMapper.selectOne(user);
        return user1;
    }
}
