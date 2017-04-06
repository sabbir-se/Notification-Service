package com.dsi.notification.dao;

import com.dsi.notification.exception.CustomException;
import com.dsi.notification.model.NotificationTemplate;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by sabbir on 10/17/16.
 */
public interface NotificationTemplateDao {

    void setSession(Session session);
    void saveNotificationTemplate(NotificationTemplate notificationTemplate) throws CustomException;
    void updateNotificationTemplate(NotificationTemplate notificationTemplate) throws CustomException;
    void deleteNotificationTemplate(String notificationTemplateID) throws CustomException;
    List<NotificationTemplate> getAllNotificationTemplate(String typeID, String typeName, String systemID,
                                                          String templateID, String templateName);
    NotificationTemplate getNotificationTemplateById(String templateId);
}
