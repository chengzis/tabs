package com.example.video_0927.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class GsonUtil {
    private static Gson gson = new Gson();

    public static String toJson(Object obj){
        return gson.toJson(obj);
    }

    //public <T> T fromJson(String json, Class<T> classOfT)

    /**
     * 解析json字符串，会返回相应的类
     * @param json   json字符串 —— string
     * @param tClass 类.class  —— LoginResponse.class
     * @param <T>    返回值类型 —— LoginResponse
     * @return       返回T     —— loginResponse
     *
     *  LoginResponse loginResponse = GsonUtil.fromJson(string, LoginResponse.class);
     */
    public static <T>T fromJson(String json,Class<T>tClass){
        return gson.fromJson(json,tClass);
    }


    public static <T>T fromJson(String json, Type type){
        return gson.fromJson(json,type);
    }




}
