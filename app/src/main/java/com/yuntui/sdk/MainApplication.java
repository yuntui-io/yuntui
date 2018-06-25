package com.yuntui.sdk;

import android.app.Application;


import io.yuntui.Yuntui;

/**
 * Created by pengfei.zhou on 2018/6/25.
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Yuntui.shared.setup("test_app_key", this);

    }
}
