package com.org.androidtools.network.bean;

import java.io.Serializable;

/**
 * Created by wanglin on 2015/8/5.
 */
public class ResultInfo implements Serializable {

    private int errorCode;

    private String errorMessage;

    private int status;

    private String message;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResultInfo{" +
                "errorCode=" + errorCode +
                ", errorMessage='" + errorMessage + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
