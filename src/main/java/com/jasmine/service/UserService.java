package com.jasmine.service;

import com.jasmine.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    int add(User user);

    int delete(Integer id);

    User select(Integer id);

    List<User> selectAll();

    int update(User user);
}
