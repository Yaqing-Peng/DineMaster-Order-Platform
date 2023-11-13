package com.jasmine.service.impl;

import com.jasmine.constants.RedisKeyPrefix;
import com.jasmine.mapper.DishMapper;
import com.jasmine.pojo.Dish;
import com.jasmine.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private RedisTemplate<String, Dish> redisTemplate;

    @Override
    @Transactional
    public int add(Dish dish) {
        dish.setCreateTime(new Date());
        dish.setUpdateTime(new Date());
        int res = dishMapper.insert(dish);
        if (res <= 0) {
            log.error("failed to add dish to mysql, dish:{}", dish);
            return res;
        }
        return setDishRedis(dish, res);
    }

    @Override
    @Transactional
    public int delete(Integer id) {
        int res = dishMapper.deleteByPrimaryKey(id);
        if (res == 1) {
            String key = RedisKeyPrefix.dishPrefix + id;
            Boolean deleted = redisTemplate.delete(key);
            if(Boolean.FALSE.equals(deleted)){
                log.error("failed to delete dish from redis, dish id:{}", id);
                throw new RuntimeException("failed to delete dish from redis");
            }
        }
        return res;
    }

    @Override
    public int update(Dish dish) {
        dish.setUpdateTime(new Date());
        int res = dishMapper.updateByPrimaryKey(dish);
        if (res <= 0) {
            log.error("failed to update dish to mysql, dish:{}", dish);
            return res;
        }
        return setDishRedis(dish, res);
    }

    @Override
    public List<Dish> selectAll() {
        return dishMapper.selectAll();
    }

    @Override
    @Transactional
    public Dish select(Integer id) {
        String key = RedisKeyPrefix.dishPrefix + id;
        Dish dish = redisTemplate.opsForValue().get(key);
        log.info("get dish from redis, dish:{}", dish);
        if(dish == null){
            dish = dishMapper.selectByPrimaryKey(id);
            log.info("dish is not in redis, select from mysql, dish id:{}", id);
            if(dish != null){
                redisTemplate.opsForValue().set(key,dish);
            }
        }
        return dish;
    }

    private int setDishRedis(Dish dish, int res) {
        String key = RedisKeyPrefix.dishPrefix + dish.getId();
        redisTemplate.opsForValue().set(key, dish);
        if(Boolean.FALSE.equals(redisTemplate.hasKey(key))){
            log.error("failed to insert dish to redis, dish:{}", dish);
            throw new RuntimeException("failed to insert dish to redis");
        }
        return res;
    }
}
