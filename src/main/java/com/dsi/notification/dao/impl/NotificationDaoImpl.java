package com.dsi.notification.dao.impl;

import com.dsi.notification.dao.NotificationDao;
import com.dsi.notification.exception.CustomException;
import com.dsi.notification.exception.ErrorContext;
import com.dsi.notification.exception.ErrorMessage;
import com.dsi.notification.model.Notification;
import com.dsi.notification.model.NotificationProcess;
import com.dsi.notification.model.NotificationStatus;
import com.dsi.notification.model.NotificationType;
import com.dsi.notification.util.Constants;
import com.dsi.notification.util.Utility;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by sabbir on 10/17/16.
 */
public class NotificationDaoImpl implements NotificationDao {

    private Session session;

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void saveNotification(Notification notification) throws CustomException {
        try {
            session.save(notification);

        } catch (Exception e){
            ErrorContext errorContext = new ErrorContext(null, "Notification", e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.NOTIFICATION_SERVICE_0002,
                    Constants.NOTIFICATION_SERVICE_0002_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void updateNotification(Notification notification) throws CustomException {
        try {
            session.update(notification);

        } catch (Exception e){
            ErrorContext errorContext = new ErrorContext(null, "Notification", e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.NOTIFICATION_SERVICE_0003,
                    Constants.NOTIFICATION_SERVICE_0003_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteNotification(String notificationID) throws CustomException {
        try {
            Query query = session.createQuery("DELETE FROM Notification n WHERE n.notificationId =:notificationID");
            query.setParameter("notificationID", notificationID);

            query.executeUpdate();

        } catch (Exception e){
            ErrorContext errorContext = new ErrorContext(null, "Notification", e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.NOTIFICATION_SERVICE_0004,
                    Constants.NOTIFICATION_SERVICE_0004_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public List<NotificationProcess> getAllNotificationProcessByStatus() {
        Query query = session.createQuery("FROM NotificationProcess np WHERE np.status =:status ORDER BY " +
                "np.notificationProcessId ASC");
        query.setParameter("status", NotificationStatus.PROCESS.getValue());
        query.setFirstResult(0);
        query.setMaxResults(10);

        List<NotificationProcess> processList = query.list();
        if(processList != null){
            return processList;
        }
        return null;
    }

    @Override
    public List<NotificationProcess> getAllNotificationProcess(String typeId, String typeName, String templateId, String systemId, String notificationId) {

        Query query;
        if(!Utility.isNullOrEmpty(typeId)) {
            query = session.createQuery("FROM NotificationProcess np WHERE np.notification.notificationType.notificationTypeId =:typeId");
            query.setParameter("typeId", Long.parseLong(typeId));

        } else if(!Utility.isNullOrEmpty(typeName)){
            query = session.createQuery("FROM NotificationProcess np WHERE np.notification.notificationType.name =:typeName");
            query.setParameter("typeName", typeName);

        } else if(!Utility.isNullOrEmpty(templateId)){
            query = session.createQuery("FROM NotificationProcess np WHERE np.notification.notificationTemplate.notificationTemplateId =:templateId");
            query.setParameter("templateId", Long.parseLong(templateId));

        } else if(!Utility.isNullOrEmpty(systemId)){
            query = session.createQuery("FROM NotificationProcess np WHERE np.notification.systemId =:systemId");
            query.setParameter("systemId", systemId);

        } else if(!Utility.isNullOrEmpty(notificationId)){
            query = session.createQuery("FROM NotificationProcess np WHERE np.notification.notificationId =:notificationId");
            query.setParameter("notificationId", Long.parseLong(notificationId));

        } else {
            query = session.createQuery("FROM NotificationProcess ");
        }

        List<NotificationProcess> processList = query.list();
        if(processList != null){
            return processList;
        }
        return null;
    }

    @Override
    public boolean getNotificationTemplateStatus(Long templateId) {
        Query query = session.createQuery("SELECT nt.isActive FROM NotificationTemplate nt WHERE nt.notificationTemplateId =:templateId");
        query.setParameter("templateId", templateId);

        return (boolean) query.uniqueResult();
    }

    @Override
    public List<Notification> getAllNotification(String typeID, String typeName, String templateID,
                                                 String systemID, String notificationID) {

        Query query;
        if(!Utility.isNullOrEmpty(typeID)){
            query = session.createQuery("FROM Notification n WHERE n.notificationType.notificationTypeId =:typeID");
            query.setParameter("typeID", typeID);

        } else if(!Utility.isNullOrEmpty(typeName)){
            query = session.createQuery("FROM Notification n WHERE n.notificationType.name =:typeName");
            query.setParameter("typeName", typeName);

        } else if(!Utility.isNullOrEmpty(templateID)){
            query = session.createQuery("FROM Notification n WHERE n.notificationTemplate.notificationTemplateId =:templateID");
            query.setParameter("templateID", templateID);

        } else if(!Utility.isNullOrEmpty(systemID)){
            query = session.createQuery("FROM Notification n WHERE n.systemId =:systemID");
            query.setParameter("systemID", systemID);

        } else if(!Utility.isNullOrEmpty(notificationID)){
            query = session.createQuery("FROM Notification n WHERE n.notificationId =:notificationID");
            query.setParameter("notificationID", Long.parseLong(notificationID));

        } else {
            query = session.createQuery("FROM Notification ");
        }

        List<Notification> notificationList = query.list();
        if(notificationList != null){
            return notificationList;
        }
        return null;
    }

    @Override
    public void saveNotificationProcess(NotificationProcess process) throws CustomException {
        try {
            session.save(process);

        } catch (Exception e){
            ErrorContext errorContext = new ErrorContext(null, "NotificationProcess", e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.NOTIFICATION_SERVICE_0002,
                    Constants.NOTIFICATION_SERVICE_0002_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void updateNotificationProcess(NotificationProcess process) throws CustomException {
        try {
            session.update(process);

        } catch (Exception e){
            ErrorContext errorContext = new ErrorContext(null, "NotificationProcess", e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.NOTIFICATION_SERVICE_0003,
                    Constants.NOTIFICATION_SERVICE_0003_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public List<NotificationType> getAllNotificationType() {

        Query query = session.createQuery("FROM NotificationType ");

        List<NotificationType> typeList = query.list();
        if(typeList != null){
            return typeList;
        }
        return null;
    }
}
