package com.dsi.notification.dao;

import com.dsi.notification.exception.CustomException;
import com.dsi.notification.model.Notification;
import com.dsi.notification.model.NotificationProcess;
import com.dsi.notification.model.NotificationType;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by sabbir on 10/17/16.
 */
public interface NotificationDao {

    void setSession(Session session);
    void saveNotification(Notification notification) throws CustomException;
    void updateNotification(Notification notification) throws CustomException;
    void deleteNotification(String notificationID) throws CustomException;
    List<Notification> getAllNotification(String typeID, String typeName, String templateID,
                                          String systemID, String notificationID);

    void saveNotificationProcess(NotificationProcess process) throws CustomException;
    void updateNotificationProcess(NotificationProcess process) throws CustomException;
    List<NotificationProcess> getAllNotificationProcessByStatus();
    List<NotificationProcess> getAllNotificationProcess(String typeId, String typeName, String templateId,
                                                        String systemId, String notificationId);

    boolean getNotificationTemplateStatus(Long templateId);
    List<NotificationType> getAllNotificationType();
}
