package com.jasmine.service.impl;

import com.jasmine.constants.RedisKeyPrefix;
import com.jasmine.mapper.OrderMapper;
import com.jasmine.pojo.Order;
import com.jasmine.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImp implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RedisTemplate<String, Order> redisTemplate;

    @Override
    public int add(Order order) {
        order.setUpdateTime(new Date());
        order.setCreateTime(new Date());
        return orderMapper.insert(order);
    }

    @Override
    public int delete(Integer id) {
        int res = orderMapper.deleteByPrimaryKey(id);
        if(res == 1){
            String key = RedisKeyPrefix.orderPrefix + id;
            Boolean isKeyExists = redisTemplate.hasKey(key);
            if(Boolean.TRUE.equals(isKeyExists)){
                Boolean deleted = redisTemplate.delete(key);
                if(Boolean.FALSE.equals(deleted)){
                    log.error("Failed to delete order from Redis, order ID: {}", id);
                    throw new RuntimeException("Failed to delete order from Redis");
                }
            }
        }
        return res;
    }

    @Override
    public int update(Order order) {
        order.setUpdateTime(new Date());
        log.info("update order in mysql, order:{}", order);
        return orderMapper.updateByPrimaryKey(order);
    }

    @Override
    public List<Order> selectAll() {
        return orderMapper.selectAll();
    }

    @Override
    public Order select(Integer id) {
        String key =  RedisKeyPrefix.orderPrefix + id;
        Order order = redisTemplate.opsForValue().get(key);
        log.info("get order from redis,  order:{}", order);
        if(order == null){
            order = orderMapper.selectByPrimaryKey(id);
            log.info("order is not in redis, select from mysql, order:{}", order);
            if(order != null){
                redisTemplate.opsForValue().set(key,order);
            }
        }
        return order;
    }

}
