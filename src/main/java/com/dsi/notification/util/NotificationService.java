package com.dsi.notification.util;

import com.dsi.checkauthorization.filter.CheckAuthorizationFilter;
import com.dsi.notification.filter.ResponseCORSFilter;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by sabbir on 10/17/16.
 */
public class NotificationService extends ResourceConfig {

    public NotificationService() {
        packages("com.dsi.notification");
        register(ResponseCORSFilter.class);
        register(CheckAuthorizationFilter.class);

        SessionUtil.getSession();
    }
}
