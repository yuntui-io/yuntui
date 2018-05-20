package io.yuntui.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by leo on 2018/4/4.
 */
public class JSON {

    public static <T> String toJson(T t) {
        if (t == null) {
            return "";
        }

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(t);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return disposeVirtualCases(json, clazz);
    }

    private static <T> T disposeVirtualCases(String strJSON, Class<T> clazz) {
        try {
            return new GsonBuilder().create().fromJson(strJSON, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

