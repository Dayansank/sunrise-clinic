package com.sunrise.clinic.dto;

public class ApiResponse {
    private boolean success;
    private String message;
    private Object data;

    public static ApiResponse ok(String message, Object data) {
        ApiResponse response = new ApiResponse();
        response.success = true;
        response.message = message;
        response.data = data;
        return response;
    }

    public static ApiResponse fail(String message) {
        ApiResponse response = new ApiResponse();
        response.success = false;
        response.message = message;
        return response;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
