package io.yuntui;

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

    String appKey;

    OkHttpClient client = new OkHttpClient();


    Object post(String path, Object body) throws IOException {
        String url = SERVER_HOST + path;
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, JSON.toJson(body));

        Request request = new Request.Builder()
                .header("X-AppKey", appKey)
                .header("X-YutuiSDKVersion", "android@0.0.1")
                .url(url)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if (response.code() != 200) {
            throw new IOException();
        }
//        System.out.println(response.body().string());

        String jsonString = response.body().string();
        //ResponseBody responseBody = JSON.toObject(jsonString, ResponseBody.class);
        ResponseBody responseBody = JSON.fromJson(jsonString, ResponseBody.class);
        if (responseBody.code != 200) {
            throw new IOException();
        }
        return responseBody.data;
    }

    static class ResponseBody {
        public int code;
        public String msg;
        public Object data;
    }
}
