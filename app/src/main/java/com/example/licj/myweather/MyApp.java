package com.example.licj.myweather;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


/**
 * Created by licj on 2016/6/10.
 */
public class MyApp extends Application
{
    public static RequestQueue volleyQueue;
    @Override
    public void onCreate() {
        super.onCreate();

        /* Volley配置 */
        // 建立Volley的Http请求队列
        volleyQueue = Volley.newRequestQueue(getApplicationContext());
    }

    // 开放Volley的HTTP请求队列接口
    public static RequestQueue getRequestQueue() {
        return volleyQueue;
    }
}