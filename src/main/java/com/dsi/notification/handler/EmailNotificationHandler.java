package com.dsi.notification.handler;

import com.dsi.notification.model.EmailConfig;
import com.dsi.notification.model.Notification;
import com.dsi.notification.model.SMSConfig;
import com.dsi.notification.service.NotificationHandler;
import com.dsi.notification.util.Constants;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by sabbir on 11/9/16.
 */
public class EmailNotificationHandler implements NotificationHandler {

    private static final Logger logger = Logger.getLogger(EmailNotificationHandler.class);

    private EmailConfig emailConfig;
    private SMSConfig smsConfig;

    @Override
    public String constructBody(Notification notification) {
        String success;
        try{
            JSONObject contentObj = new JSONObject(notification.getContentJson());
            JSONArray recipientArray = contentObj.getJSONArray("Recipients");
            logger.info("Recipients email list:: " + recipientArray.toString());

            JSONObject templateObj = new JSONObject(notification.getNotificationTemplate().getTemplate());
            String subject = templateObj.getString("subject");
            String body = templateObj.getString("body");

            String username = emailConfig.getUsername();
            String password = emailConfig.getPassword();
            String host = emailConfig.getHost();

            Properties emailProp = new Properties();
            emailProp.setProperty("mail.smtp.host", host);
            emailProp.setProperty("mail.smtp.auth", emailConfig.getAuth());
            emailProp.setProperty("mail.smtp.port", emailConfig.getPort());
            emailProp.setProperty("mail.smtp.starttls.enable", emailConfig.getTtl());

            Session session = Session.getDefaultInstance(emailProp, null);
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(username, "DEM System"));

            if(recipientArray.length() > 0) {
                for (int i = 0; i < recipientArray.length(); i++) {
                    message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientArray.getString(i)));
                }
            }

            message.setSubject(constructSubjectOrBodyWithContent(subject, contentObj));
            message.setText(constructSubjectOrBodyWithContent(body, contentObj), "UTF-8", "html");

            Transport transport = session.getTransport(emailConfig.getTransport());
            transport.connect(host, username, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            success = "Success";

        } catch (Exception e){
            logger.error("Email confirmation failed: " + e.getMessage());
            success = null;
        }
        return success;
    }

    private String constructSubjectOrBodyWithContent(String body, JSONObject contentObj) throws JSONException {
        int contentKeyIndex, bodyIndex;
        String newBody = "";

        if(body.contains("{")) {
            for (bodyIndex = 0; bodyIndex < body.length(); bodyIndex++) {

                if (body.charAt(bodyIndex) == '{') {
                    contentKeyIndex = bodyIndex + 1;
                    String contentKey = "";
                    while (body.charAt(contentKeyIndex) != '}') {
                        contentKey += body.charAt(contentKeyIndex);
                        contentKeyIndex++;
                    }
                    bodyIndex = contentKeyIndex;

                    if(contentObj.has(contentKey)) {
                        newBody += contentObj.getString(contentKey);

                    } else {
                        newBody += ".....";
                    }

                } else {
                    newBody += body.charAt(bodyIndex);
                }
            }
            return newBody;
        }

        return body;
    }

    @Override
    public void setEmailConfigure(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }

    @Override
    public void setSMSConfigure(SMSConfig smsConfig) {
        this.smsConfig = smsConfig;
    }
}
