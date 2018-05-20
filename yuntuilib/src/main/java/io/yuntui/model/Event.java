package io.yuntui.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by leo on 2018/4/4.
 */
public class Event {

    public int userId;

    public String eventName;

    public String sessionId;

    public String eventTime; // yyyy-MM-dd HH:mm:ss.SSS

    public Map<String, Object> eventProperties = new HashMap<String, Object>();

}