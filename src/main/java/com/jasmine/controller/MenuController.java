package com.jasmine.controller;

import com.jasmine.pojo.Menu;
import com.jasmine.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/dishes")
public class MenuController {

    @GetMapping
    public Result get(){
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Menu menu){
        return Result.success();
    }

    @PostMapping
    public Result add(@RequestBody Menu menu){
        return Result.success();
    }
}
