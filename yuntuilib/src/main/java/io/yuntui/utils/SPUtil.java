package io.yuntui.utils;

import android.content.Context;
import android.content.SharedPreferences;

import io.yuntui.Yuntui;

public class SPUtil {
    private final String SP_YUNTUI = "yuntui_dir";
    private final String YUNTUI_MODEL = "yuntui_model";
    private static volatile SPUtil instance = null;
    private SharedPreferences sharedPreferences;

    public static SPUtil getInstance() {
        if(instance == null) {
            synchronized (SPUtil.class) {
                if(instance == null) {
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
        if(sharedPreferences == null && Yuntui.context != null) {
            sharedPreferences =  Yuntui.context.getSharedPreferences(SP_YUNTUI, Context.MODE_PRIVATE);;
        }

        return sharedPreferences;
    }

    public void saveModel(String content) {
        if(getSPreferences() != null) {
            getSPreferences().edit().putString(YUNTUI_MODEL, content).apply();
        }
    }

    public String getModel() {
        if(getSPreferences() != null) {
            return getSPreferences().getString(YUNTUI_MODEL, "");
        }

        return "";
    }
}
