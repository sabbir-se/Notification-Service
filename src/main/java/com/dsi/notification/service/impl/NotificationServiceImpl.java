package com.dsi.notification.service.impl;

import com.dsi.notification.dao.NotificationDao;
import com.dsi.notification.dao.impl.NotificationDaoImpl;
import com.dsi.notification.exception.CustomException;
import com.dsi.notification.exception.ErrorContext;
import com.dsi.notification.exception.ErrorMessage;
import com.dsi.notification.model.*;
import com.dsi.notification.service.NotificationService;
import com.dsi.notification.util.Constants;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by sabbir on 10/17/16.
 */
public class NotificationServiceImpl extends CommonService implements NotificationService {

    private static final Logger logger = Logger.getLogger(NotificationServiceImpl.class);

    private static final NotificationDao notificationDao = new NotificationDaoImpl();

    @Override
    public void saveNotification(List<Notification> notificationList) throws CustomException {

        Session session = getSession();
        notificationDao.setSession(session);

        for(Notification notification : notificationList){

            validateInputForCreation(notification, session);

            if(notificationDao.getNotificationTemplateStatus(notification.getNotificationTemplate()
                    .getNotificationTemplateId())){

                notificationDao.saveNotification(notification);
                logger.info("Save notification success");

                NotificationProcess process = new NotificationProcess();
                process.setNotification(notification);
                process.setRetryCount(0);
                process.setStatus(NotificationStatus.PROCESS.getValue());
                process.setVersion(1);
                notificationDao.saveNotificationProcess(process);

                logger.info("Save notification process success");

            } else {
                logger.info("Notification doesn't save, because template is in-active.");
            }
        }

        close(session);
    }

    private void validateInputForCreation(Notification notification, Session session) throws CustomException {
        if(notification.getNotificationTemplate().getNotificationTemplateId() == null){
            close(session);
            ErrorContext errorContext = new ErrorContext(null, "Notification", "Notification template id not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.NOTIFICATION_SERVICE_0001_DESCRIPTION,
                    Constants.NOTIFICATION_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(notification.getNotificationType().getNotificationTypeId() == null){
            close(session);
            ErrorContext errorContext = new ErrorContext(null, "Notification", "Notification type id not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.NOTIFICATION_SERVICE_0001_DESCRIPTION,
                    Constants.NOTIFICATION_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void updateNotification(Notification notification) throws CustomException {

        Session session = getSession();
        notificationDao.setSession(session);

        notificationDao.updateNotification(notification);
        logger.info("Update notification success");

        close(session);
    }

    @Override
    public List<Notification> getAllNotification(String typeID, String typeName, String templateID,
                                                 String systemID, String notificationID) throws CustomException {
        Session session = getSession();
        notificationDao.setSession(session);

        List<Notification> notificationList = notificationDao.getAllNotification(typeID, typeName, templateID, systemID, notificationID);
        if(notificationList == null){
            ErrorContext errorContext = new ErrorContext(null, "Notification", "Notification list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.NOTIFICATION_SERVICE_0005,
                    Constants.NOTIFICATION_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        logger.info("Notification list size:: " + notificationList.size());

        close(session);
        return notificationList;
    }

    @Override
    public void updateNotificationProcess(NotificationProcess process) throws CustomException {
        Session session = getSession();
        notificationDao.setSession(session);

        notificationDao.updateNotificationProcess(process);
        logger.info("Update notification process success");

        close(session);
    }

    @Override
    public List<NotificationProcess> getAllNotificationProcessByStatus() throws CustomException {
        Session session = getSession();
        notificationDao.setSession(session);

        List<NotificationProcess> processList = notificationDao.getAllNotificationProcessByStatus();
        if(processList == null){
            ErrorContext errorContext = new ErrorContext(null, "NotificationProcess", "Notification process list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.NOTIFICATION_SERVICE_0005,
                    Constants.NOTIFICATION_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        logger.info("Notification process list size:: " + processList.size());

        close(session);
        return processList;
    }

    @Override
    public List<NotificationProcess> getAllNotificationProcess(String typeId, String typeName, String templateId,
                                                               String systemId, String notificationId) throws CustomException {
        Session session = getSession();
        notificationDao.setSession(session);

        List<NotificationProcess> processList = notificationDao.getAllNotificationProcess(typeId, typeName, templateId, systemId, notificationId);
        if(processList == null){
            ErrorContext errorContext = new ErrorContext(null, "NotificationProcess", "Notification process list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.NOTIFICATION_SERVICE_0005,
                    Constants.NOTIFICATION_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        close(session);
        return processList;
    }

    @Override
    public List<NotificationType> getAllNotificationType() throws CustomException {

        Session session = getSession();
        notificationDao.setSession(session);

        List<NotificationType> typeList = notificationDao.getAllNotificationType();
        if(typeList == null){
            ErrorContext errorContext = new ErrorContext(null, "NotificationType", "Notification type list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.NOTIFICATION_SERVICE_0005,
                    Constants.NOTIFICATION_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        logger.info("Notification type list size:: " + typeList.size());

        close(session);
        return typeList;
    }
}
