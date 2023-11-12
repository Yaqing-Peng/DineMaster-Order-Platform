package com.jasmine.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dish implements Serializable {
    private Integer id;

    private String dishName;

    private String description;

    private Double price;

    private String picture;

    private Byte cookTime;

    private Integer status;

    private Date createTime;

    private Date updateTime;

}