package io.yuntui.utils;

import android.content.Context;
import android.content.SharedPreferences;

import io.yuntui.Yuntui;

public class SPUtil {
    private final String SP_YUNTUI = "yuntui_dir";
    private final String YUNTUI_MODEL = "yuntui_model";
    private final String DEVICE_ID = "deviceId";
    private static volatile SPUtil instance = null;
    private SharedPreferences sharedPreferences;

    public static SPUtil getInstance() {
        if (instance == null) {
            synchronized (SPUtil.class) {
                if (instance == null) {
                    instance = new SPUtil();
                }
            }
        }

        return instance;
    }

    private SPUtil() {
        getSPreferences();
    }

    private SharedPreferences getSPreferences() {
        if (sharedPreferences == null && Yuntui.context != null) {
            sharedPreferences = Yuntui.context.getSharedPreferences(SP_YUNTUI, Context.MODE_PRIVATE);
            ;
        }

        return sharedPreferences;
    }

    public void saveModel(String appKey, String content) {
        if (getSPreferences() != null) {
            getSPreferences().edit().putString(YUNTUI_MODEL + new StringBuffer(appKey).reverse().toString(), content).apply();
        }
    }

    public String getModel(String appKey) {
        if (getSPreferences() != null) {
            return getSPreferences().getString(YUNTUI_MODEL + new StringBuffer(appKey).reverse().toString(), "");
        }

        return "";
    }

    public String getDeviceId() {
        if (getSPreferences() != null) {
            return getSPreferences().getString(DEVICE_ID, "");
        }

        return "";
    }

    public void setDeviceId(String deviceId) {
        if (getSPreferences() != null) {
            getSPreferences().edit().putString(DEVICE_ID, deviceId).apply();
        }
    }
}
