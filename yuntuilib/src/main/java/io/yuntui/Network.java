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

    void post(String path, Object body, Callback callback) throws IOException {
        String url = SERVER_HOST + path;
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, JSON.toJson(body));

        Request request = new Request.Builder()
                .header("X-AppKey", appKey)
                .header("X-YutuiSDKVersion", "android@0.0.1")
                .url(url)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(callback);
    }

    void post(String path, Object body) throws IOException {
        post(path, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e(TAG, "Request on fail:" + call.request().toString());
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (response == null || response.code() != 200) {
                    Log.e(TAG, "Request on fail:" + call.request().toString());
                }
            }
        });
    }

    static class ResponseBody {
        public int code;
        public String msg;
        public Object data;
    }
}
