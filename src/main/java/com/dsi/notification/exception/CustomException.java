package com.dsi.notification.exception;

/**
 * Created by sabbir on 6/20/16.
 */
public class CustomException extends Exception {

    private ErrorMessage errorMessage;

    public CustomException(ErrorMessage errorMessage){
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
}
