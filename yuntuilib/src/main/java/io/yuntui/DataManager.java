package io.yuntui;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.yuntui.model.Event;
import io.yuntui.model.User;
import io.yuntui.utils.JSON;
import io.yuntui.utils.SPUtil;

/**
 * Created by leo on 2018/4/4.
 */
class DataManager {

    String appKey;

    private User user = new User();

    List<Event> events = Collections.synchronizedList(new ArrayList<Event>());

    User currentUser() {
        return user;
    }

    void saveUser(User user) {
        this.user = user;
    }

    List<Event> popAllEvents() {
        List<Event> result = new ArrayList<>();
        result.addAll(events);
        events.clear();
        return result;
    }

    int getEventCount() {
        return events.size();
    }

    void addEvents(List<Event> events) {
        this.events.addAll(events);
    }

    void loadDateFromFile(String appKey) {
        String modelContent = SPUtil.getInstance().getModel(appKey);
        if (!TextUtils.isEmpty(modelContent)) {
            Model model = JSON.fromJson(modelContent, Model.class);
            user = model.user;
            events = Collections.synchronizedList(model.events);
        }

    }

    void persistDataToFile(String appKey) {
        Model model = new Model();
        model.user = user;
        model.events = events;
        String content = JSON.toJson(model);
        SPUtil.getInstance().saveModel(appKey, content);
    }

    private static class Model {
        User user;
        List<Event> events;
    }
}
