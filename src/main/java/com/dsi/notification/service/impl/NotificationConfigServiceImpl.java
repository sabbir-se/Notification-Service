package com.dsi.notification.service.impl;

import com.dsi.notification.dao.NotificationConfigDao;
import com.dsi.notification.dao.impl.NotificationConfigDaoImpl;
import com.dsi.notification.exception.CustomException;
import com.dsi.notification.exception.ErrorContext;
import com.dsi.notification.exception.ErrorMessage;
import com.dsi.notification.model.NotificationConfig;
import com.dsi.notification.service.NotificationConfigService;
import com.dsi.notification.util.Constants;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by sabbir on 10/17/16.
 */
public class NotificationConfigServiceImpl extends CommonService implements NotificationConfigService {

    private static final Logger logger = Logger.getLogger(NotificationConfigServiceImpl.class);

    private static final NotificationConfigDao configDao = new NotificationConfigDaoImpl();

    @Override
    public NotificationConfig getNotificationConfigByType(Long typeID) {
        Session session = getSession();
        configDao.setSession(session);

        NotificationConfig config = configDao.getNotificationConfigByType(typeID);

        close(session);
        return config;
    }

    @Override
    public List<NotificationConfig> getAllNotificationConfig(String typeID, String typeName,
                                                             String configID, String systemID) throws CustomException {

        Session session = getSession();
        configDao.setSession(session);

        List<NotificationConfig> configList = configDao.getAllNotificationConfig(typeID, typeName, configID, systemID);
        if(configList == null){
            ErrorContext errorContext = new ErrorContext(null, "NotificationConfig", "NotificationConfig list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.NOTIFICATION_SERVICE_0005,
                    Constants.NOTIFICATION_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        logger.info("NotificationConfig list size:: " + configList.size());

        close(session);
        return configList;
    }
}
