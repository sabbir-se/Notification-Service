package com.dsi.notification.service;

import com.dsi.notification.exception.CustomException;

/**
 * Created by sabbir on 11/9/16.
 */
public interface NotificationFactory {

    Object getInstance(String className) throws CustomException;
}
