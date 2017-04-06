package com.dsi.notification.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sabbir on 10/17/16.
 */

@Entity
@Table(name = "dsi_notification_process")
public class NotificationProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "notification_process_id")
    private Long notificationProcessId;

    @ManyToOne
    @JoinColumn(name = "notification_id")
    private Notification notification;

    @Column(name = "status", length = 40)
    private String status;

    @Column(name = "retry_count")
    private int retryCount;

    private int version;

    public Long getNotificationProcessId() {
        return notificationProcessId;
    }

    public void setNotificationProcessId(Long notificationProcessId) {
        this.notificationProcessId = notificationProcessId;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
