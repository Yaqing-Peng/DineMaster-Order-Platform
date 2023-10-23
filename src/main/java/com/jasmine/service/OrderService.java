package com.jasmine.service;

import com.jasmine.pojo.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    List<Order> selectAll();

    Order select(Integer id);

    int delete(Integer id);

    int update(Order order);

    int add(Order order);
}
