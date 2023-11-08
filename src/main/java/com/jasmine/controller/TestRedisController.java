package com.jasmine.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/redis")
public class TestRedisController {
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/string")
    public String stringTest(){
        redisTemplate.opsForValue().set("str", "Hello World");
        String str = (String) redisTemplate.opsForValue().get("str");
        return str;
    }

    @GetMapping("/list")
    public List<Integer> listTest(){
        ListOperations<String, Integer> listOperations = redisTemplate.opsForList();
        listOperations.leftPush("list", 1000);
        listOperations.leftPush("list", 1000);
        listOperations.leftPush("list", 2000);
        listOperations.leftPush("list", 2000);
        listOperations.leftPush("list", 3000);
        listOperations.leftPush("list", 3000);
        List<Integer> list = listOperations.range("list", 0, 5);
        return list;
    }

    @GetMapping("/set")
    public Set<Integer> setTest(){
        SetOperations<String, Integer> setOperations = redisTemplate.opsForSet();
        setOperations.add("set", 1000);
        setOperations.add("set", 2000);
        setOperations.add("set", 3000);
        setOperations.add("set", 4000);
        setOperations.add("set", 5000);
        setOperations.add("set", 5000);
        Set<Integer> set = setOperations.members("set");
        return set;
    }

    @GetMapping("/zset")
    public Set<Integer> zsetTest(){
        ZSetOperations<String, Integer> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add("zset", 1000,1);
        zSetOperations.add("zset", 2000,2);
        zSetOperations.add("zset", 3000,3);
        zSetOperations.add("zset", 4000,4);
        zSetOperations.add("zset", 5000,5);
        zSetOperations.add("zset", 6000,6);
        Set<Integer> set = zSetOperations.range("zset", 0, 5);
        return set;
    }

    @GetMapping("/hash")
    public Integer hashTest(){
        HashOperations<String,String,Integer > hashOperations = redisTemplate.opsForHash();
        hashOperations.put("hash", "first",1);
        hashOperations.put("hash", "second",2);
        hashOperations.put("hash", "third",3);

        Integer num = hashOperations.get("hash", "first");
        System.out.println(num);
        return num;
    }
}
