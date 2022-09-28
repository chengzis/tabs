package com.example.video_0927.entity.response;

/**
 * 登录返回的json字段
 */
public class BaseApiResponse {

    public String msg;
    public int code;

    /**
     * 判断是否请求成功
     * @return  0表示请求成功
     */
    public boolean isSuccess(){
    return code ==0;
}




}
