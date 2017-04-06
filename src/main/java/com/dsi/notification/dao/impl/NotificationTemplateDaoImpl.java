package com.dsi.notification.dao.impl;

import com.dsi.notification.dao.NotificationTemplateDao;
import com.dsi.notification.exception.CustomException;
import com.dsi.notification.exception.ErrorContext;
import com.dsi.notification.exception.ErrorMessage;
import com.dsi.notification.model.NotificationTemplate;
import com.dsi.notification.util.Constants;
import com.dsi.notification.util.Utility;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dsi.notification.util.SessionUtil.getSession;

/**
 * Created by sabbir on 10/17/16.
 */
public class NotificationTemplateDaoImpl implements NotificationTemplateDao {

    private Session session;

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void saveNotificationTemplate(NotificationTemplate notificationTemplate) throws CustomException {
        try {
            session.save(notificationTemplate);

        } catch (Exception e){
            ErrorContext errorContext = new ErrorContext(null, "NotificationTemplate", e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.NOTIFICATION_SERVICE_0002,
                    Constants.NOTIFICATION_SERVICE_0002_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void updateNotificationTemplate(NotificationTemplate notificationTemplate) throws CustomException {
        try {
            session.update(notificationTemplate);

        } catch (Exception e){
            ErrorContext errorContext = new ErrorContext(null, "NotificationTemplate", e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.NOTIFICATION_SERVICE_0003,
                    Constants.NOTIFICATION_SERVICE_0003_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteNotificationTemplate(String notificationTemplateID) throws CustomException {
        try {
            Query query = session.createQuery("DELETE FROM NotificationTemplate nt WHERE nt.notificationTemplateId =:templateID");
            query.setParameter("templateID", notificationTemplateID);

            query.executeUpdate();

        } catch (Exception e){
            ErrorContext errorContext = new ErrorContext(null, "NotificationTemplate", e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.NOTIFICATION_SERVICE_0004,
                    Constants.NOTIFICATION_SERVICE_0004_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public List<NotificationTemplate> getAllNotificationTemplate(String typeID, String typeName, String systemID,
                                                                 String templateID, String templateName) {

        StringBuilder queryBuilder = new StringBuilder();
        Map<String, String> paramValue = new HashMap<>();
        boolean hasClause = false;

        queryBuilder.append("FROM NotificationTemplate nt ");

        if(!Utility.isNullOrEmpty(typeID)){
            queryBuilder.append(" WHERE nt.notificationType.notificationTypeId =:typeID");
            paramValue.put("typeID", typeID);
            hasClause = true;
        }

        if(!Utility.isNullOrEmpty(typeName)){
            if(hasClause) {
                queryBuilder.append(" AND nt.notificationType.name =:typeName");

            } else {
                queryBuilder.append(" WHERE nt.notificationType.name =:typeName");
                hasClause = true;
            }
            paramValue.put("typeName", "%" + typeName + "%");
        }

        if(!Utility.isNullOrEmpty(templateID)){
            if(hasClause) {
                queryBuilder.append(" AND nt.notificationTemplateId =:templateID");

            } else{
                queryBuilder.append(" WHERE nt.notificationTemplateId =:templateID");
                hasClause = true;
            }
            paramValue.put("templateID", templateID);

        }

        if(!Utility.isNullOrEmpty(systemID)) {
            if (hasClause) {
                queryBuilder.append(" AND nt.systemId =:systemID");

            } else {
                queryBuilder.append(" WHERE nt.systemId =:systemID");
                hasClause = true;
            }
            paramValue.put("systemID", systemID);
        }

        if(!Utility.isNullOrEmpty(templateName)){
            if(hasClause) {
                queryBuilder.append(" AND nt.templateName like :templateName");

            } else{
                queryBuilder.append(" WHERE nt.templateName like :templateName");
            }
            paramValue.put("templateName", "%" + templateName + "%");

        }

        Query query = session.createQuery(queryBuilder.toString());

        for (Map.Entry<String, String> entry : paramValue.entrySet()) {
            if(entry.getKey().equals("typeID") || entry.getKey().equals("templateID")){
                query.setParameter(entry.getKey(), Long.parseLong(entry.getValue()));

            } else {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }

        List<NotificationTemplate> templateList = query.list();
        if(templateList != null){
            return templateList;
        }
        return null;
    }

    @Override
    public NotificationTemplate getNotificationTemplateById(String templateId) {
        Query query = session.createQuery("FROM NotificationTemplate nt WHERE nt.notificationTemplateId =:templateId");
        query.setParameter("templateId", Long.parseLong(templateId));

        NotificationTemplate template = (NotificationTemplate) query.uniqueResult();
        if(template != null){
            return template;
        }
        return null;
    }
}
