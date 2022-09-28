package com.example.video_0927.api;

/**
 * 保存api接口地址信息
 */
public interface Api {

    /**
     * baseUrl 基础的url
     */
    String BASE_URL = "http://192.168.1.12:8080/renren-fast";

    /**
     * 登录接口
     */
    String LOGIN = BASE_URL + "/app/login";

    /**
     * 获取首页的tab分类
     */
    String VIDEO_CATEGORY_LIST = BASE_URL + "/app/videocategory/list";

    /**
     * 根据分类获取视频列表
     */
    String VIDEO_LIST_BY_CATEGORY = BASE_URL + "/app/videolist/getListByCategoryId";

    /**
     * 资讯列表
     */
    String NEWS_LIST = BASE_URL + "/app/news/api/list";

}
