package com.jasmine.service.impl;

import com.jasmine.mapper.UserMapper;
import com.jasmine.pojo.User;
import com.jasmine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public int add(User user) {
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        int insert = userMapper.insert(user);
        return insert;
    }
}
