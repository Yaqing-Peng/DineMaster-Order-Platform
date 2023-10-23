package com.jasmine.service.impl;

import com.jasmine.mapper.DishMapper;
import com.jasmine.pojo.Dish;
import com.jasmine.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Override
    public List<Dish> selectAll() {
        return dishMapper.selectAll();
    }

    @Override
    public Dish select(Integer id) {
        return dishMapper.selectByPrimaryKey(id);
    }

    @Override
    public int delete(Integer id) {
        return dishMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Dish dish) {
        dish.setUpdateTime(new Date());
        return dishMapper.updateByPrimaryKey(dish);
    }

    @Override
    public int add(Dish dish) {
        dish.setCreateTime(new Date());
        dish.setUpdateTime(new Date());
       return dishMapper.insert(dish);
    }
}
