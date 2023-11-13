package com.jasmine.background;

import com.jasmine.constants.RedisKeyPrefix;
import com.jasmine.mapper.OrderMapper;
import com.jasmine.mapper.UserMapper;
import com.jasmine.pojo.Order;
import com.jasmine.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CacheUpdateService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Scheduled(fixedRate = 5000)//update mysql to redis every 5 seconds
    @Transactional
    public void syncUserToRedis() {
        Date date = new Date();
        List<User> users = userMapper.selectByUpdateDate(date);
        //log.info("get users from date:{}, user count:{}", date, users.size());

        for(User user : users) {
            String key = RedisKeyPrefix.userPrefix + user.getId();
            //set outdated time to 24 hours
            redisTemplate.opsForValue().set(key, user, 24, TimeUnit.HOURS);
            Boolean isKeyExists = redisTemplate.hasKey(key);
            if(!isKeyExists){
                log.error("failed to update user to redis from mysql, user: {}", user);
                throw new RuntimeException("failed to update user to redis from mysql");
            }
        }
    }

    @Scheduled(fixedRate = 5000)//update mysql to redis every 5 seconds
    @Transactional
    public void syncOrderToRedis() {
        Date date = new Date();
        List<Order> orders = orderMapper.selectByUpdateDate(date);
        //log.info("get orders from date:{}, order count:{}", date, orders.size());

        for(Order order : orders) {
            String key = RedisKeyPrefix.orderPrefix + order.getId();
            //set outdated time to 24 hours
            redisTemplate.opsForValue().set(key, order, 24, TimeUnit.HOURS);
            Boolean isKeyExists = redisTemplate.hasKey(key);
            if(!isKeyExists){
                log.error("failed to add order to redis from mysql, order: {}", order);
                throw new RuntimeException("failed to add order to redis from mysql");
            }
        }
    }
}
