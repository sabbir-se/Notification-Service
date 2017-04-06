package com.dsi.notification.service;

import com.dsi.notification.exception.CustomException;
import com.dsi.notification.model.Notification;
import com.dsi.notification.model.NotificationProcess;
import com.dsi.notification.model.NotificationType;

import java.util.List;

/**
 * Created by sabbir on 10/17/16.
 */
public interface NotificationService {

    void saveNotification(List<Notification> notificationList) throws CustomException;
    void updateNotification(Notification notification) throws CustomException;
    List<Notification> getAllNotification(String typeID, String typeName, String templateID,
                                          String systemID, String notificationID) throws CustomException;

    void updateNotificationProcess(NotificationProcess process) throws CustomException;
    List<NotificationProcess> getAllNotificationProcessByStatus() throws CustomException;
    List<NotificationProcess> getAllNotificationProcess(String typeId, String typeName, String templateId,
                                                        String systemId, String notificationId) throws CustomException;

    List<NotificationType> getAllNotificationType() throws CustomException;
}
