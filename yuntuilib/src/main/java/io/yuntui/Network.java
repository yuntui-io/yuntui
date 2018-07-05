package io.yuntui;

import android.util.Log;

import io.yuntui.utils.JSON;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

/**
 * Created by leo on 2018/4/4.
 */
class Network {

    private static final String SERVER_HOST = "http://autopushapi.bxapp.cn";

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = "Network";
    String appKey;

    OkHttpClient client = new OkHttpClient();

    void post(String path, Object body, Callback callback) {
        String url = SERVER_HOST + path;
        String content = JSON.toJson(body);
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, content);

        Request request = new Request.Builder()
                .header("X-AppKey", appKey)
                .header("X-YutuiSDKVersion", "android@0.0.1")
                .url(url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(callback);
        Log.d(TAG, "Send:Path = " + path + ",body = " + content);
    }


    static class ResponseBody {
        public int code;
        public String msg;
        public Object data;
    }
}
