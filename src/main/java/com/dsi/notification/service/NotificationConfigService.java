package com.dsi.notification.service;

import com.dsi.notification.exception.CustomException;
import com.dsi.notification.model.NotificationConfig;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by sabbir on 10/17/16.
 */
public interface NotificationConfigService {

    NotificationConfig getNotificationConfigByType(Long typeID);
    List<NotificationConfig> getAllNotificationConfig(String typeID, String typeName,
                                                      String configID, String systemID) throws CustomException;
}
