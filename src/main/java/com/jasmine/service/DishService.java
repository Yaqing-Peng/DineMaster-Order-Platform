package com.jasmine.service;

import com.jasmine.pojo.Dish;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DishService {
    List<Dish> selectAll();

    Dish select(Integer id);

    int delete(Integer id);

    int update(Dish dish);

    int add(Dish dish);
}
