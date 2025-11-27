package org.sopt.global.jwt.exception;

import lombok.Getter;
import org.sopt.global.exception.errorcode.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
public enum JwtErrorCode implements ErrorCode {
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "J001", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "J002", "만료된 토큰입니다."),
    EMPTY_TOKEN(HttpStatus.UNAUTHORIZED, "J003", "토큰이 비어있습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
    JwtErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
