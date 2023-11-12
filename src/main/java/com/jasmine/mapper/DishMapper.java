package com.jasmine.mapper;

import com.jasmine.pojo.Dish;
import com.jasmine.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface DishMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dish record);

    Dish selectByPrimaryKey(Integer id);

    List<Dish> selectAll();

    int updateByPrimaryKey(Dish record);

}