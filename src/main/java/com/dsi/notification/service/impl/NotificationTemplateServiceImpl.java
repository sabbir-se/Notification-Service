package com.dsi.notification.service.impl;

import com.dsi.notification.dao.NotificationTemplateDao;
import com.dsi.notification.dao.impl.NotificationTemplateDaoImpl;
import com.dsi.notification.exception.CustomException;
import com.dsi.notification.exception.ErrorContext;
import com.dsi.notification.exception.ErrorMessage;
import com.dsi.notification.model.NotificationTemplate;
import com.dsi.notification.service.NotificationTemplateService;
import com.dsi.notification.util.Constants;
import com.dsi.notification.util.Utility;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by sabbir on 10/17/16.
 */
public class NotificationTemplateServiceImpl extends CommonService implements NotificationTemplateService {

    private static final Logger logger = Logger.getLogger(NotificationTemplateServiceImpl.class);

    private static final NotificationTemplateDao templateDao = new NotificationTemplateDaoImpl();

    @Override
    public void saveNotificationTemplate(NotificationTemplate notificationTemplate) throws CustomException {
        Session session = getSession();
        templateDao.setSession(session);

        templateDao.saveNotificationTemplate(notificationTemplate);
        logger.info("Save notification template success");

        close(session);
    }

    @Override
    public void updateNotificationTemplate(List<NotificationTemplate> notificationTemplates) throws CustomException {
        if(Utility.isNullOrEmpty(notificationTemplates)){
            ErrorContext errorContext = new ErrorContext(null, "NotificationTemplate",
                    "Notification template list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.NOTIFICATION_SERVICE_0001,
                    Constants.NOTIFICATION_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        Session session = getSession();
        templateDao.setSession(session);

        for(NotificationTemplate notificationTemplate : notificationTemplates){

            NotificationTemplate existNotificationTemp = templateDao.getNotificationTemplateById
                    (String.valueOf(notificationTemplate.getNotificationTemplateId()));

            existNotificationTemp.setActive(notificationTemplate.isActive());
            templateDao.updateNotificationTemplate(existNotificationTemp);

        }
        logger.info("Update notification template success");

        close(session);
    }

    @Override
    public void deleteNotificationTemplate(String notificationTemplateID) throws CustomException {
        Session session = getSession();
        templateDao.setSession(session);

        templateDao.deleteNotificationTemplate(notificationTemplateID);

        close(session);
    }

    @Override
    public List<NotificationTemplate> getAllNotificationTemplate(String typeID, String typeName, String systemID,
                                                                 String templateID, String templateName) throws CustomException {
        Session session = getSession();
        templateDao.setSession(session);

        List<NotificationTemplate> templateList = templateDao.getAllNotificationTemplate(typeID, typeName, systemID, templateID, templateName);
        if(templateList == null){
            ErrorContext errorContext = new ErrorContext(null, "NotificationTemplate", "Notification template list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.NOTIFICATION_SERVICE_0005,
                    Constants.NOTIFICATION_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        logger.info("Notification template list size:: " + templateList.size());

        close(session);
        return templateList;
    }
}
