package com.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * restful api 返回的数据
 *
 * @author:HGF
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse {

    /** 
    * 表示当前相应的状态是成功或失败
    */ 
    private String code;

    /**
    * 表示当响应失败后给前端的错误提示
    */
    private String msg;

    /**
    * 表示当响应成功后给前端的数据
    */
    private Object data;

    public static CommonResponse setOk(Object data){
        return new CommonResponse("200","请求成功",data);
    }

    public static CommonResponse setOk(String msg,Object data){
        return new CommonResponse("200",msg,data);
    }

    public static CommonResponse error(String msg){
        return new CommonResponse("500",msg,"网络异常，请稍后再试");
    }

    public static CommonResponse error(Integer code,String msg){
        return new CommonResponse(String.valueOf(code),msg,null);
    }

}
