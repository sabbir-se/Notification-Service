package com.dsi.notification.util;

import com.wordnik.swagger.config.ConfigFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by sabbir on 9/1/16.
 */
public class Bootstrap extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {

        super.init(config);
        Properties prop = new Properties();
        InputStream input;
        try {
            input = getClass().getClassLoader().getResourceAsStream("Api.properties");
            prop.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ConfigFactory.config().setApiVersion(prop.getProperty("user.swagger.api.version"));
        ConfigFactory.config().setBasePath(prop.getProperty("user.swagger.api.base.path"));
        ConfigFactory.config().setApiPath("api-docs");
        ConfigFactory.config().setSwaggerVersion(com.wordnik.swagger.core.SwaggerSpec.version());
    }
}
