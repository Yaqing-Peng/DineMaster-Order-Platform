package com.jasmine.service.impl;

import com.jasmine.mapper.OrderMapper;
import com.jasmine.pojo.Order;
import com.jasmine.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImp implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public List<Order> selectAll() {
        return orderMapper.selectAll();
    }

    @Override
    public Order select(Integer id) {
       return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public int delete(Integer id) {
        return orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Order order) {
        order.setUpdateTime(new Date());
        return orderMapper.updateByPrimaryKey(order);
    }

    @Override
    public int add(Order order) {
        order.setUpdateTime(new Date());
        order.setCreateTime(new Date());
        return orderMapper.insert(order);
    }
}
