package com.dsi.notification.dao;

import com.dsi.notification.model.NotificationConfig;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by sabbir on 10/17/16.
 */
public interface NotificationConfigDao {

    void setSession(Session session);
    NotificationConfig getNotificationConfigByType(Long typeID);
    List<NotificationConfig> getAllNotificationConfig(String typeID, String typeName, String configID, String systemID);
}
