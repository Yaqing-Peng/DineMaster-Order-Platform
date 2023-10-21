package com.jasmine.controller;

import com.jasmine.entity.Result;
import com.jasmine.pojo.User;
import com.jasmine.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping
    public Result get(){
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result update(@PathVariable Integer id){
        return Result.success();
    }

    @PostMapping
    public Result add(@RequestBody User user){
        int add = userService.add(user);
        log.info("add a user:{}", user);
        if(add <= 0){
            return Result.error("insert failed...");
        }
        return Result.success();
    }

}
