package com.jasmine.controller;

import com.jasmine.entity.Result;
import com.jasmine.pojo.User;
import com.jasmine.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
