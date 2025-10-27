package org.sopt.global.response;

public class ApiResponse<T> {

    private final String code;
    private final String message;
    private final T data;

    // 외부에서 접근 막기
    private ApiResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 정적 메서드로만 만들기 가능
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("200", "요청이 성공했습니다.", data);
    }

    public static <T> ApiResponse<T> fail(String code, String message) {
        return new ApiResponse<>(code, message, null);
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
