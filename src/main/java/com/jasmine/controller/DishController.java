package com.jasmine.controller;

import com.jasmine.pojo.Dish;
import com.jasmine.entity.Result;
import com.jasmine.pojo.User;
import com.jasmine.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/dishes")
public class DishController {

    @Autowired
    private DishService dishService;

    @GetMapping
    public Result getAll(){
        List<Dish> dishes = dishService.selectAll();
        log.info("get all dishes :{}", dishes);
        return Result.success(dishes);
    }
    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id){
        log.info("get dish with id:{}", id);
        Dish dish = dishService.select(id);
        return Result.success(dish);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("delete dish id:{}", id);
        int delete = dishService.delete(id);
        if(delete <= 0){
            return Result.error("failed to delete dish...");
        }
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Dish dish){
        int update = dishService.update(dish);
        if(update <= 0){
            return Result.error("failed to update dish info...");
        }
        return Result.success();
    }

    @PostMapping
    public Result add(@RequestBody Dish dish){
        log.info("add a dish:{}", dish);
        int add = dishService.add(dish);
        if(add <= 0){
            return Result.error("failed to insert dish...");
        }
        return Result.success();
    }
}
