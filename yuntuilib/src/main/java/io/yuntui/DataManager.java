package io.yuntui;

import android.text.TextUtils;

import java.util.ArrayList;
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

    List<Event> events = new ArrayList();

    User currentUser() {
        return user;
    }

    void saveUser(User user) {
        this.user = user;
    }

    List<Event> popAllEvents() {
        List<Event> result = new ArrayList<Event>();
        result.addAll(events);
        events.clear();
        return result;
    }

    void addEvents(List<Event> events) {
        this.events.addAll(events);
    }

    void loadDateFromFile() {
        String modelContent = SPUtil.getInstance().getModel();
        if(!TextUtils.isEmpty(modelContent)) {
            //Model model = JSON.toObject(modelContent, Model.class);
            Model model = JSON.fromJson(modelContent, Model.class);
            user = model.user;
            events = model.events;
        }

    }

    void persistDataToFile() {
        Model model = new Model();
        model.user = user;
        model.events = events;
        //String conent = JSON.toString(model);
        String conent = JSON.toJson(model);
        SPUtil.getInstance().saveModel(conent);
    }

    private static class Model {
        User user;
        List<Event> events;
    }
}
