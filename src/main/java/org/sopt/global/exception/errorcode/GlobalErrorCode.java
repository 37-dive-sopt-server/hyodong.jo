package org.sopt.global.exception.errorcode;

import org.springframework.http.HttpStatus;

public enum GlobalErrorCode implements ErrorCode {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"G001", "서버 내부 오류입니다."),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "G002", "입력값이 올바르지 않습니다.");

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
