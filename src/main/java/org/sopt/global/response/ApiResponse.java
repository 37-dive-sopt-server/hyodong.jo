package org.sopt.global.response;

public class ApiResponse<T> {

    private final String code;
    private final String message;
    private final T data;

    private ApiResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> of(String code, String message, T data) {
        return new ApiResponse<>(code,message,data);
    }

    public static <T> ApiResponse<T> success(T data) {
        return of("SUCCESS","요청이 성공적으로 처리되었습니다",data);
    }

    public static <T> ApiResponse<T> success(String message,T data) {
        return of("SUCCESS",message,data);
    }

    public static <T> ApiResponse<T> fail(String code, String message) {
        return of(code,message,null);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
