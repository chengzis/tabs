package com.example.video_0927.api;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.video_0927.util.GsonUtil;
import com.example.video_0927.util.SharedPreferencesUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiRequest {

    private static final String TAG = "ApiRequest";
    Request request;

    private static android.os.Handler handler = new Handler(Looper.getMainLooper());

    //创建OkHttp客户端，使用建造者模式
    private OkHttpClient client = new OkHttpClient.Builder().build();

    public ApiRequest(Request request) {
        this.request = request;
    }

    /**
     * 创建请求
     *
     * @param url    url
     * @param params post请求的body
     * @return 返回request
     */
    public static ApiRequest newPost(String url, HashMap<String, Object> params) {

        //创建Json的Body
        RequestBody body = RequestBody.create(GsonUtil.toJson(params), MediaType.get("application/json;charset=utf-8"));
        //post请求
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();


        return new ApiRequest(request);

    }

    public interface CallBack {
        void onFailure(IOException e);

        void onResponse(String string);
    }

    public static ApiRequest newGet(String url) {
        //get请求，拼接参数
        HttpUrl.Builder urlBuilder = HttpUrl.get(url).newBuilder();

        HttpUrl.Builder builder = urlBuilder.addQueryParameter("token", SharedPreferencesUtil.get("token"));
        Log.d(TAG, "newGet: "+builder);

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .get()
                .build();

        return new ApiRequest(request);


    }

    public static ApiRequest newGet(String url,HashMap<String,Object>params) {
        //get请求，拼接参数
        HttpUrl.Builder urlBuilder = HttpUrl.get(url).newBuilder();

        HttpUrl.Builder builder = urlBuilder.addQueryParameter("token", SharedPreferencesUtil.get("token"));
        Log.d(TAG, "newGet: "+builder);

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            urlBuilder.addQueryParameter(entry.getKey(),entry.getValue().toString());

        }

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .get()
                .build();

        return new ApiRequest(request);


    }



    /**
     * 执行异步请求
     *
     * @param callback
     */
    public void enqueue(CallBack callback) {
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "onFailure: " + e);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //获取返回的json字符串
                String string = response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResponse(string);
                    }
                });
            }
        });


    }
}
