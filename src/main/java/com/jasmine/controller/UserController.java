package com.jasmine.controller;

import com.jasmine.entity.Result;
import com.jasmine.pojo.User;
import com.jasmine.service.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    /*Redis cache*/

    //add one user
    @PostMapping("/set")
    public Result setKey(@RequestBody User user) {
        log.info("add user to redis, user info:{}", user);
        redisTemplate.opsForValue().set("user", user);
        log.info("successfully added user to redis");
        return Result.success();
    }

    //add a list of users
    @PostMapping("/setList")
    public Result setList(@RequestBody User user) {
        log.info("add user to redis, user info:{}", user);
        ListOperations<String, User> listOperations = redisTemplate.opsForList();
        listOperations.leftPush("users", user);
        List<User> list = listOperations.range("users", 0, 5);
        return Result.success(list);
    }

    //get user by redis key
    @GetMapping("/get/{key}")
    public Result getByKey(@PathVariable String key) {
        User user = (User) redisTemplate.opsForValue().get(key);
        log.info("get user from redis, user info:{}", user);
        return Result.success(user);
    }

    //delete user by redis key
    @DeleteMapping("/delete/{key}")
    public Result deleteByKey(@PathVariable String key) {
        redisTemplate.delete(key);
        log.info("delete user from redis, key:{}", key);
        boolean res = redisTemplate.hasKey(key);
        return Result.success(res);
    }

    /*MySql data*/

    @GetMapping
    public Result getAll(){
        List<User> users = userService.selectAll();
        log.info("get all users :{}", users);
        return Result.success(users);
    }
    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id){
        log.info("get user with id:{}", id);
        User user = userService.select(id);
        return Result.success(user);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("delete user id:{}", id);
        int delete = userService.delete(id);
        if(delete <= 0){
            return Result.error("failed to delete user...");
        }
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody User user){
        int update = userService.update(user);
        if(update <= 0){
            return Result.error("failed to update user info...");
        }
        return Result.success();
    }

    @PostMapping
    public Result add(@RequestBody User user){
        log.info("add a user:{}", user);
        int add = userService.add(user);
        if(add <= 0){
            return Result.error("failed to insert user...");
        }
        return Result.success();
    }

}
