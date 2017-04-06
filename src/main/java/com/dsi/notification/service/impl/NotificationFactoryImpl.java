package com.dsi.notification.service.impl;

import com.dsi.notification.exception.CustomException;
import com.dsi.notification.exception.ErrorContext;
import com.dsi.notification.exception.ErrorMessage;
import com.dsi.notification.service.NotificationFactory;
import com.dsi.notification.util.Constants;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;

/**
 * Created by sabbir on 11/9/16.
 */
public class NotificationFactoryImpl implements NotificationFactory {

    private static final Logger logger = Logger.getLogger(NotificationFactoryImpl.class);

    @Override
    public Object getInstance(String className) throws CustomException {
        try{
            Class<?> clazz = Class.forName(className);
            Constructor<?> cons = clazz.getConstructor();
            return cons.newInstance();

        } catch (Exception e){
            logger.error("Failed to initialize instance: " + e.getMessage());
            ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.NOTIFICATION_SERVICE_0012,
                    Constants.NOTIFICATION_SERVICE_0012_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }
}
