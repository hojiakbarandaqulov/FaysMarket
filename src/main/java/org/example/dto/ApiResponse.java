package org.example.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private int code; // 1 ok,  -1 error
    private String message; // message
    private T data;

    public ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ApiResponse build(int code, String message) {
        ApiResponse apiResponse = new ApiResponse(code, message);
        return apiResponse;
    }

    public static <T> ApiResponse ok(T data) {
        ApiResponse apiResponse = new ApiResponse(1, null, data);
        return apiResponse;
    }
}
