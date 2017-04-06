package com.dsi.notification.service;

import com.dsi.notification.model.EmailConfig;
import com.dsi.notification.model.Notification;
import com.dsi.notification.model.SMSConfig;

/**
 * Created by sabbir on 11/9/16.
 */
public interface NotificationHandler {

    String constructBody(Notification notification);
    void setEmailConfigure(EmailConfig emailConfig);
    void setSMSConfigure(SMSConfig smsConfig);
}
