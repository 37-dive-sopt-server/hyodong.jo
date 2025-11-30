package org.sopt.global.exception.errorcode;

import org.springframework.http.HttpStatus;

public enum GlobalErrorCode implements ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"G001", "서버 내부 오류입니다."),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "G002", "입력값이 올바르지 않습니다."),

    // jwt 관련
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "J001", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "J002", "만료된 토큰입니다."),
    EMPTY_TOKEN(HttpStatus.UNAUTHORIZED, "J003", "토큰이 비어있습니다."),
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN,"J004","접근 권한이 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    GlobalErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
