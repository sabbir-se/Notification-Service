package com.dsi.notification.util;

import com.dsi.notification.model.Notification;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.List;

/**
 * Created by sabbir on 10/17/16.
 */
public class Utility {

    private static final Logger logger = Logger.getLogger(Utility.class);

    public static boolean isNullOrEmpty(String s){
        return s == null || s.isEmpty();
    }

    public static boolean isNullOrEmpty(List list){
        return list == null || list.size() == 0;
    }

    public static Notification getNotificationFromJSON(JSONObject notificationObj) throws JSONException {
        Notification notification = new Notification();
        notification.setContentJson(notificationObj.getString("contentJson"));
        notification.setMaxRetryCount(notificationObj.getInt("maxRetryCount"));
        notification.setProcessed(notificationObj.getBoolean("processed"));
        notification.setRetryInterval(notificationObj.getInt("retryInterval"));
        notification.setVersion(1);

        return notification;
    }
}
