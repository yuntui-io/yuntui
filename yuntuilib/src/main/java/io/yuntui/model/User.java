package io.yuntui.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by leo on 2018/4/4.
 */
public class User {

    public int userId;
    public String appUserId = "";
    public String pushId = "";
    public String deviceId = "";

    public Map<String, Object> userProperties = new HashMap<String, Object>();
    public Map<String, Object> sysProperties = new HashMap<String, Object>();

}
