package com.dsi.notification.cronjob;

import com.dsi.notification.exception.CustomException;
import com.dsi.notification.model.*;
import com.dsi.notification.service.NotificationConfigService;
import com.dsi.notification.service.NotificationFactory;
import com.dsi.notification.service.NotificationHandler;
import com.dsi.notification.service.NotificationService;
import com.dsi.notification.service.impl.NotificationConfigServiceImpl;
import com.dsi.notification.service.impl.NotificationFactoryImpl;
import com.dsi.notification.service.impl.NotificationServiceImpl;
import com.dsi.notification.util.Constants;
import com.dsi.notification.util.Utility;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;

import java.util.List;

/**
 * Created by sabbir on 11/8/16.
 */
public class NotificationCron {

    private static final Logger logger = Logger.getLogger(NotificationCron.class);

    private static final NotificationService notificationService = new NotificationServiceImpl();
    private static final NotificationConfigService configService = new NotificationConfigServiceImpl();
    private static final NotificationFactory notificationFactory = new NotificationFactoryImpl();

    private static EmailConfig emailConfig;
    private static SMSConfig smsConfig;

    public static void main(String[] args) {

        boolean once = true;
        boolean flag = true;

        while(flag) {

            try {
                if(once) {

                    logger.info("Read notification type");
                    List<NotificationType> notificationTypes = notificationService.getAllNotificationType();

                    if (!Utility.isNullOrEmpty(notificationTypes)) {
                        logger.info("Notification type list: " + notificationTypes.size());

                        for (NotificationType type : notificationTypes) {
                            logger.info("Notification type name: " + type.getName());

                            NotificationConfig notificationConfig = configService.getNotificationConfigByType(type.getNotificationTypeId());
                            if (notificationConfig != null) {
                                logger.info("Notification config: " + notificationConfig.getConfigJson());

                                if (type.getName().equals(Constants.EMAIL_TYPE)) {
                                    configureEmail(notificationConfig.getConfigJson());

                                } else if (type.getName().equals(Constants.SMS_TYPE)) {
                                    //TODO initialize sms service.

                                }
                            }
                        }
                    }
                    once = false;
                }

                logger.info("Read latest 10 notification that are not sending yet.");
                List<NotificationProcess> processList = notificationService.getAllNotificationProcessByStatus();

                if (!Utility.isNullOrEmpty(processList)) {
                    logger.info("Notification list size: " + processList.size());

                    for (NotificationProcess process : processList) {
                        if (process.getRetryCount() < process.getNotification().getMaxRetryCount()) {
                            process.setRetryCount(process.getRetryCount() + 1);

                            String notificationHandlerClassName = process.getNotification().getNotificationType().getLoader();
                            logger.info("Notification handler class name: " + notificationHandlerClassName);

                            NotificationHandler notificationHandler = (NotificationHandler) notificationFactory.
                                    getInstance(notificationHandlerClassName);

                            if (process.getNotification().getNotificationType().getName().equals(Constants.EMAIL_TYPE)) {

                                notificationHandler.setEmailConfigure(emailConfig);
                                String result = notificationHandler.constructBody(process.getNotification());

                                logger.info("Email result: " + result);
                                if (result != null) {
                                    process.setStatus(NotificationStatus.SUCCESS.getValue());
                                }

                            } else if (process.getNotification().getNotificationType().getName().equals(Constants.SMS_TYPE)) {
                                notificationHandler.setSMSConfigure(smsConfig);

                            }

                        } else {
                            logger.info("Retry limit max over.");
                            process.setStatus(NotificationStatus.FAILED.getValue());
                        }

                        notificationService.updateNotificationProcess(process);
                    }
                } else {
                    Thread.sleep(10000);
                }

            } catch (CustomException | InterruptedException ce){
                ce.printStackTrace();
                flag = false;
            }
        }
    }

    private static void configureEmail(String configJson) {

        emailConfig = new EmailConfig();
        try{
            JSONObject configObj = new JSONObject(configJson);
            emailConfig.setUsername(configObj.getString("username"));
            emailConfig.setPassword(configObj.getString("password"));
            emailConfig.setTransport(configObj.getString("transport"));
            emailConfig.setHost(configObj.getString("host"));
            emailConfig.setAuth(configObj.getString("auth"));
            emailConfig.setPort(configObj.getString("port"));
            emailConfig.setTtl(configObj.getString("ttl"));

        } catch (Exception e){
            logger.info("JSON read/write failed: " + e.getMessage());
        }
    }
}
