package com.jasmine.controller;

import com.jasmine.entity.Result;
import com.jasmine.pojo.Dish;
import com.jasmine.pojo.Order;
import com.jasmine.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public Result getAll(){
        List<Order> orders = orderService.selectAll();
        log.info("get all orders :{}", orders);
        return Result.success(orders);
    }
    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id){
        log.info("get order with id:{}", id);
        Order order = orderService.select(id);
        return Result.success(order);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        log.info("delete order id:{}", id);
        int delete = orderService.delete(id);
        if(delete <= 0){
            return Result.error("failed to delete order...");
        }
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Order order){
        int update = orderService.update(order);
        if(update <= 0){
            return Result.error("failed to update order info...");
        }
        return Result.success();
    }

    @PostMapping
    public Result add(@RequestBody Order order){
        log.info("add a order:{}", order);
        int add = orderService.add(order);
        if(add <= 0){
            return Result.error("failed to insert order...");
        }
        return Result.success();
    }
}
