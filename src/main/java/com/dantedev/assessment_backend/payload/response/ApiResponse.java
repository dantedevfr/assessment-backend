package com.dantedev.assessment_backend.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private Object errors;
    private String errorCode;
    private int httpStatusCode;
    private LocalDateTime timestamp;
    private String path;

    public ApiResponse(boolean success, String message, T data, String errorCode, int httpStatusCode, String path) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.errorCode = errorCode;
        this.httpStatusCode = httpStatusCode;
        this.errors = null;
        this.timestamp = LocalDateTime.now();
        this.path = path;
    }

    public ApiResponse(boolean success, String message, T data, String errorCode, int httpStatusCode, String path, Object errors) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.errorCode = errorCode;
        this.httpStatusCode = httpStatusCode;
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
        this.path = path;
    }

    // Getters and setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
