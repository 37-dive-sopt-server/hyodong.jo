package org.sopt.exception.common;

public class ErrorResponse<T> {

    private final String code;
    private final String message;
    private final T data;

    // 외부에서 접근 막기
    private ErrorResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 정적 메서드로만 만들기 가능
    public static <T> ErrorResponse<T> success(T data) {
        return new ErrorResponse<>("200", "요청이 성공했습니다.", data);
    }

    public static <T> ErrorResponse<T> fail(String code,String message) {
        return new ErrorResponse<>(code, message, null);
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
