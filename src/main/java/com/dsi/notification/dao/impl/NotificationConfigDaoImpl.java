package com.dsi.notification.dao.impl;

import com.dsi.notification.dao.NotificationConfigDao;
import com.dsi.notification.model.NotificationConfig;
import com.dsi.notification.util.Utility;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by sabbir on 10/17/16.
 */
public class NotificationConfigDaoImpl implements NotificationConfigDao {

    private Session session;

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public NotificationConfig getNotificationConfigByType(Long typeID) {
        Query query = session.createQuery("FROM NotificationConfig nc WHERE nc.notificationType.notificationTypeId =:typeId");
        query.setParameter("typeId", typeID);

        NotificationConfig config = (NotificationConfig) query.uniqueResult();
        if(config != null){
            return config;
        }
        return null;
    }

    @Override
    public List<NotificationConfig> getAllNotificationConfig(String typeID, String typeName, String configID, String systemID) {

        Query query;
        if(!Utility.isNullOrEmpty(typeID)){
            query = session.createQuery("FROM NotificationConfig nc WHERE nc.notificationType.notificationTypeId =:typeID");
            query.setParameter("typeID", Long.parseLong(typeID));

        } else if(!Utility.isNullOrEmpty(typeName)){
            query = session.createQuery("FROM NotificationConfig nc WHERE nc.notificationType.name =:typeName");
            query.setParameter("typeName", typeName);

        } else if(!Utility.isNullOrEmpty(configID)){
            query = session.createQuery("FROM NotificationConfig nc WHERE nc.notificationConfigId =:configID");
            query.setParameter("configID", Long.parseLong(configID));

        } else if(!Utility.isNullOrEmpty(systemID)){
            query = session.createQuery("FROM NotificationConfig nc WHERE nc.systemId =:systemID");
            query.setParameter("systemID", systemID);

        } else {
            query = session.createQuery("FROM NotificationConfig ");
        }

        List<NotificationConfig> configList = query.list();
        if(configList != null){
            return configList;
        }
        return null;
    }
}
