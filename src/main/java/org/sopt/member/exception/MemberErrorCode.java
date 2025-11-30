package org.sopt.member.exception;

import org.sopt.global.exception.errorcode.ErrorCode;
import org.springframework.http.HttpStatus;

public enum MemberErrorCode implements ErrorCode {
    AGE_LOW(HttpStatus.BAD_REQUEST,"M001","20세 미만은 가입할 수 없습니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT,"M002","이미 존재하는 이메일입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND,"M003","해당 ID의 회원을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    MemberErrorCode(HttpStatus status, String code, String message) {
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
