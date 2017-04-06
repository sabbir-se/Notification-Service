package com.dsi.notification.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sabbir on 10/17/16.
 */

@Entity
@Table(name = "dsi_notification_config")
public class NotificationConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "notification_config_id")
    private Long notificationConfigId;

    @ManyToOne
    @JoinColumn(name = "notification_type_id")
    private NotificationType notificationType;

    @Column(name = "system_id", length = 40)
    private String systemId;

    @Column(name = "config_json", columnDefinition = "TEXT")
    private String configJson;

    private int version;

    public Long getNotificationConfigId() {
        return notificationConfigId;
    }

    public void setNotificationConfigId(Long notificationConfigId) {
        this.notificationConfigId = notificationConfigId;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getConfigJson() {
        return configJson;
    }

    public void setConfigJson(String configJson) {
        this.configJson = configJson;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
