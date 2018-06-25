package io.yuntui;

import android.content.Context;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.yuntui.model.Event;
import io.yuntui.model.User;
import io.yuntui.utils.DeviceUtil;
import io.yuntui.utils.JSON;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by leo on 2018/4/4.
 */
public class Yuntui {

    public static Yuntui shared = new Yuntui();

    private DataManager dataManager = new DataManager();

    private Network network = new Network();

    private String appKey;

    private String sessionId = UUID.randomUUID().toString();

    private Map<String, Object> pushPayload = new HashMap<String, Object>();

    public static Context context;

    private Yuntui() {

    }

    public void setup(String appKey, Context ctx) {
        this.appKey = appKey;
        network.appKey = appKey;
        dataManager.appKey = appKey;
        context = ctx.getApplicationContext();

        dataManager.loadDateFromFile(appKey);
        Map<String, Object> sysProperties = new HashMap<String, Object>();
        sysProperties.put("platform", "android");
        sysProperties.put("osVersion", DeviceUtil.getOsVersion());
        sysProperties.put("bundleId", DeviceUtil.getPackageName(context));
        sysProperties.put("appVersion", DeviceUtil.getVersionName(context));

        dataManager.currentUser().sysProperties = sysProperties;
        dataManager.currentUser().deviceId = DeviceUtil.getDeviceId(context);//"59FB402C-578D-40CA-AE29-C0D03463870D"; // TODO

        if (dataManager.currentUser().userId == 0) {
            createUser();
        }

    }

    public void setUserProperties(Map<String, Object> properties) {
        dataManager.currentUser().userProperties = properties;
    }

    public void setPushId(String pushId) {
        dataManager.currentUser().pushId = pushId;
    }

    public void setAppUserId(String appUserId) {
        dataManager.currentUser().appUserId = appUserId;
    }

    public void handlePushPayload(Map<String, Object> payload) {
        if (!payload.containsKey("@yuntui")) {
            return;
        }
        pushPayload = (Map<String, Object>) payload.get("@yuntui");
        for (Event event : dataManager.events) {
            if (event.sessionId.equals(sessionId)) {
                event.eventProperties.putAll(pushPayload);
            }
        }
    }

    public void logEvent(String name) {
        logEvent(name, new HashMap<String, Object>());
    }

    public void logEvent(String name, Map<String, Object> properties) {
        Event event = new Event();
        event.sessionId = sessionId;
        event.userId = dataManager.currentUser().userId;
        event.eventName = name;
        properties.putAll(pushPayload);
        event.eventProperties = properties;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        event.eventTime = formatter.format(new Date());
        dataManager.addEvents(Arrays.asList(event));
        pushEvents();
    }

    private void createUser() {
        User user = dataManager.currentUser();
        if (user.userId != 0) {
            return;
        }
        try {
            network.post("/api/v1/user/create", user, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String jsonString = response.body().string();

                    Network.ResponseBody responseBody = JSON.fromJson(jsonString, Network.ResponseBody.class);
                    dataManager.currentUser().userId = (int) Float.parseFloat(responseBody.data.toString());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateUser() {
        User user = dataManager.currentUser();
        if (user.userId == 0) {
            return;
        }
        try {
            network.post("/api/v1/user/update", user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pushEvents() {
        if (dataManager.currentUser().userId == 0) {
            return;
        }
        List<Event> events = dataManager.popAllEvents();
        if (events.size() == 0) {
            return;
        }
        try {
            network.post("/api/v1/event/create", events);
        } catch (Exception e) {
            dataManager.addEvents(events);
        }
    }

    // TODO
    public void handleOpenApp() {
        sessionId = UUID.randomUUID().toString();
        logEvent("@open_app");
    }

    // TODO
    public void handleCloseApp() {
        logEvent("@close_app");
        updateUser();
        pushEvents();
        dataManager.persistDataToFile(appKey);
        pushPayload = new HashMap<String, Object>();
    }
}
