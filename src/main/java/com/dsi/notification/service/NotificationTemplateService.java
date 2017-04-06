package com.dsi.notification.service;

import com.dsi.notification.exception.CustomException;
import com.dsi.notification.model.NotificationTemplate;

import java.util.List;

/**
 * Created by sabbir on 10/17/16.
 */
public interface NotificationTemplateService {

    void saveNotificationTemplate(NotificationTemplate notificationTemplate) throws CustomException;
    void updateNotificationTemplate(List<NotificationTemplate> notificationTemplates) throws CustomException;
    void deleteNotificationTemplate(String notificationTemplateID) throws CustomException;
    List<NotificationTemplate> getAllNotificationTemplate(String typeID, String typeName, String systemID,
                                                          String templateID, String templateName) throws CustomException;
}
