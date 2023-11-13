package com.jasmine.mapper;

import com.jasmine.pojo.Dish;
import com.jasmine.pojo.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    Order selectByPrimaryKey(Integer id);

    List<Order> selectAll();

    int updateByPrimaryKey(Order record);

    List<Order> selectByUpdateDate(Date date);
}