package com.dsi.notification.exception;

/**
 * Created by sabbir on 6/20/16.
 */
public class ErrorMessage {

    private String errorCode;
    private String errorMessage;
    private ErrorContext errorContext;

    public ErrorMessage(String errorCode, String errorMessage, ErrorContext errorContext) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorContext = errorContext;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorContext getErrorContext() {
        return errorContext;
    }

    public void setErrorContext(ErrorContext errorContext) {
        this.errorContext = errorContext;
    }
}
