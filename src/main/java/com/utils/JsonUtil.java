package com.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Json转换工具类
 *
 * @author:HGF
 */
public class JsonUtil {
    public static String toJson(Object object){
        GsonBuilder gsonBuilder=new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson=gsonBuilder.create();
        return gson.toJson(object);
    }
}
