package com.dsi.notification.model;

/**
 * Created by sabbir on 11/8/16.
 */
public enum NotificationStatus {
    
    SUCCESS("Success"), FAILED("Failed"), PROCESS("Process");

    private String value;

    NotificationStatus(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
