package com.jasmine.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code;//1 success 0 fail

    private String msg;  //response message

    private Object data; //return data

    //success: add, delete, update
    public static Result success(){
        return new Result(1,"success",null);
    }
    //success: get
    public static Result success(Object data){
        return new Result(1,"success",data);
    }
    //fail
    public static Result error(String msg){
        return new Result(0,msg,null);
    }
}
