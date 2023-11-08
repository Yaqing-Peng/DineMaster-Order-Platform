package com.jasmine.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private Integer id;

    private String name;

    private Byte gender;

    private Date birthday;

    private String phone;

    private Integer status;

    private Date createTime;

    private Date updateTime;

}