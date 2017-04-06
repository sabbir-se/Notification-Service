package com.dsi.notification.exception;

/**
 * Created by sabbir on 6/20/16.
 */
public class ErrorContext {

    private String id;
    private String entity;
    private String developerMessage;

    public ErrorContext(String id, String entity, String developerMessage) {
        this.id = id;
        this.entity = entity;
        this.developerMessage = developerMessage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }
}
