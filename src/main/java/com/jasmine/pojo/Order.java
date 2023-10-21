package com.jasmine.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Integer id;

    private Integer userId;

    private Integer dishId;

    private Integer status;

    private Date createTime;

    private Date updateTime;

}