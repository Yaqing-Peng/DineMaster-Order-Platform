package com.jasmine.service.impl;

import com.jasmine.constants.RedisKeyPrefix;
import com.jasmine.mapper.UserMapper;
import com.jasmine.pojo.User;
import com.jasmine.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate<String, User> redisTemplate;
    
    @Override
    public int add(User user) {
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        return userMapper.insert(user);
    }

    @Override
    @Transactional
    public int delete(Integer id) {
        int res = userMapper.deleteByPrimaryKey(id);
        if(res == 1){
            String key = RedisKeyPrefix.userPrefix + id;
            Boolean isKeyExists = redisTemplate.hasKey(key);
            if(Boolean.TRUE.equals(isKeyExists)){
                Boolean deleted = redisTemplate.delete(key);
                if(Boolean.FALSE.equals(deleted)){
                    log.error("Failed to delete user from Redis, user ID: {}", id);
                    throw new RuntimeException("Failed to delete user from Redis");
                }
            }
        }
        return res;
    }

    @Override
    public int update(User user) {
        user.setUpdateTime(new Date());
        log.info("update user in mysql, user:{}", user);
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    @Transactional
    public User select(Integer id) {
        String key =  RedisKeyPrefix.userPrefix + id;
        User user = redisTemplate.opsForValue().get(key);
        log.info("select user from redis, user name:{}", id);
        if(user == null){
            user = userMapper.selectByPrimaryKey(id);
            log.info("user is not in redis, select from mysql, user name:{}", id);
            if(user != null){
                redisTemplate.opsForValue().set(key,user);
            }
        }
        return user;
    }
}
