package com.dsi.notification.dto.transformer;

import com.dsi.notification.dto.NotificationDto;
import com.dsi.notification.dto.NotificationTemplateDto;
import com.dsi.notification.exception.CustomException;
import com.dsi.notification.exception.ErrorContext;
import com.dsi.notification.exception.ErrorMessage;
import com.dsi.notification.model.Notification;
import com.dsi.notification.model.NotificationProcess;
import com.dsi.notification.model.NotificationTemplate;
import com.dsi.notification.model.NotificationType;
import com.dsi.notification.util.Constants;
import org.apache.commons.beanutils.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabbir on 11/9/16.
 */
public class NotificationTransformer {

    public NotificationDto getNotificationDtoFromProcess(NotificationProcess notificationProcess) throws CustomException {
        NotificationDto notificationDto = new NotificationDto();
        try{
            BeanUtils.copyProperties(notificationDto, notificationProcess.getNotification());
            BeanUtils.copyProperties(notificationDto, notificationProcess.getNotification().getNotificationType());
            BeanUtils.copyProperties(notificationDto, notificationProcess.getNotification().getNotificationTemplate());
            BeanUtils.copyProperties(notificationDto, notificationProcess);

        } catch (Exception e){
            ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.NOTIFICATION_SERVICE_0007,
                    Constants.NOTIFICATION_SERVICE_0007_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return notificationDto;
    }

    public List<NotificationDto> getAllNotificationDto(List<NotificationProcess> notificationList) throws CustomException {
        List<NotificationDto> dtoList = new ArrayList<>();
        for(NotificationProcess process : notificationList){
            dtoList.add(getNotificationDtoFromProcess(process));
        }
        return dtoList;
    }

    public Notification getNotification(NotificationDto notificationDto) throws CustomException {
        Notification notification = new Notification();
        try{
            BeanUtils.copyProperties(notification, notificationDto);

            NotificationTemplate template = new NotificationTemplate();
            BeanUtils.copyProperties(template, notificationDto);
            notification.setNotificationTemplate(template);

            NotificationType type = new NotificationType();
            BeanUtils.copyProperties(type, notificationDto);
            notification.setNotificationType(type);

        } catch (Exception e){
            ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.NOTIFICATION_SERVICE_0007,
                    Constants.NOTIFICATION_SERVICE_0007_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return notification;
    }

    public List<Notification> getAllNotification(List<NotificationDto> notificationDtoList) throws CustomException {
        List<Notification> notifications = new ArrayList<>();
        for(NotificationDto notificationDto : notificationDtoList){
            notifications.add(getNotification(notificationDto));
        }
        return notifications;
    }

    public NotificationTemplate getNotificationTemplate(NotificationTemplateDto templateDto) throws CustomException {
        NotificationTemplate template = new NotificationTemplate();
        try{
            BeanUtils.copyProperties(template, templateDto);

            NotificationType type = new NotificationType();
            BeanUtils.copyProperties(type, templateDto);
            template.setNotificationType(type);

        } catch (Exception e){
            ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.NOTIFICATION_SERVICE_0007,
                    Constants.NOTIFICATION_SERVICE_0007_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return template;
    }

    public List<NotificationTemplateDto> getAllNotificationTemplateDto(List<NotificationTemplate> notificationTemplates)
            throws CustomException {
        List<NotificationTemplateDto> templateDtoList = new ArrayList<>();
        for(NotificationTemplate template : notificationTemplates){
            templateDtoList.add(getNotificationTemplateDto(template));
        }
        return templateDtoList;
    }

    public NotificationTemplateDto getNotificationTemplateDto(NotificationTemplate template) throws CustomException {
        NotificationTemplateDto templateDto = new NotificationTemplateDto();
        try{
            BeanUtils.copyProperties(templateDto, template.getNotificationType());
            BeanUtils.copyProperties(templateDto, template);

        } catch (Exception e){
            ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.NOTIFICATION_SERVICE_0007,
                    Constants.NOTIFICATION_SERVICE_0007_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return templateDto;
    }
}
