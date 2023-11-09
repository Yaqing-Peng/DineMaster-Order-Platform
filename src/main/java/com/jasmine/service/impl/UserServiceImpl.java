package com.jasmine.service.impl;

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
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    
    @Override
    @Transactional
    public int add(User user) {
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        int insert = userMapper.insert(user);
        if(insert == 1){
            String key = String.valueOf(user.getId());
            redisTemplate.opsForValue().set(key, user);
            Boolean isKeyExists = redisTemplate.hasKey(key);
            if(!isKeyExists){
                log.info("fail to add user to redis, user: {}", user);
                throw new RuntimeException("Failed to add user to Redis");
            }
        }
        return insert;
    }

    @Override
    @Transactional
    public int delete(Integer id) {
        int i = userMapper.deleteByPrimaryKey(id);
        if(i == 1){
            String key = String.valueOf(id);
            Boolean isKeyExists = redisTemplate.hasKey(key);
            if(isKeyExists){
                Boolean deleted = redisTemplate.delete(key);
                if(!deleted){
                    log.error("Failed to delete user from Redis, user ID: {}", id);
                    throw new RuntimeException("Failed to delete user from Redis");
                }
            }
        }
        return i;
    }

    @Override
    @Transactional
    public User select(Integer id) {
        String key = String.valueOf(id);
        User user = (User) redisTemplate.opsForValue().get(key);
        if(user == null){
            user = userMapper.selectByPrimaryKey(id);
            if(user != null){
                redisTemplate.opsForValue().set(key,user);
            }
        }
        return user;
    }

    @Override
    public List<User> selectAll() {

        return userMapper.selectAll();
    }

    @Override
    @Transactional
    public int update(User user) {
        user.setUpdateTime(new Date());
        int i = userMapper.updateByPrimaryKey(user);
        if(i == 1){
            String key = String.valueOf(user.getId());
            redisTemplate.opsForValue().set(key, user);
            User redisUser = (User) redisTemplate.opsForValue().get(key);
            if(redisUser == null && !Objects.equals(redisUser, user)){
                log.error("Data inconsistency between Redis and database for user ID: {}", user.getId());
                throw new RuntimeException("Data inconsistency between Redis and database");
            }
        }
        return i;
    }
}
