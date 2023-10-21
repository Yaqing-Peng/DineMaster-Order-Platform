package com.jasmine.service;

import com.jasmine.pojo.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    int add(User user);
}
